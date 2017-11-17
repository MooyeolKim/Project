package knu.capstondesign.design_sample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.io.File;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity
        implements TMapGpsManager.onLocationChangedCallback, SensorEventListener, NavigationView.OnNavigationItemSelectedListener {
    public final class FeedReaderContract {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public FeedReaderContract() {}

        /* Inner class that defines the table contents */
        public abstract class CreateDB implements BaseColumns {
            public static final String TABLE_NAME = "building_infor";
            public static final String COLUMN_NAME_BUILDING_NUMBER = "building_number";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_LONGITUDE = "longitude_x";
            public static final String COLUMN_NAME_LATITUDE = "latitude_y";
            public static final String COLUMN_NAME_INFORMATION = "information";
            public static final String COLUMN_NAME_DISTANCE = "distance";
            public static final String COLUMN_NAME_ANGLE = "angle";
            public static final String _CREATE = "create table "+ TABLE_NAME + "("
                    + _ID+ " integer primary key autoincrement, "
                    +COLUMN_NAME_BUILDING_NUMBER+ " real not null, "
                    +COLUMN_NAME_NAME+ " varchar(100) not null, "
                    +COLUMN_NAME_LONGITUDE+ " real not null, "
                    +COLUMN_NAME_LATITUDE+ " real not null,"
                    +COLUMN_NAME_INFORMATION+ " text not null,"
                    +COLUMN_NAME_DISTANCE+ " real ,"
                    +COLUMN_NAME_ANGLE+" real );";
        }
    }

    private static String DATABASE_NAME = "build_test.db";
    private static int DB_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DATABASE_NAME;

    TMapView tmapview ;
    int count = 1;  //마커를 여러개 띄우기 위한 변수
    double lat=0;
    double lng=0;
    boolean MyPosit = false;
    TMapGpsManager mgps = null;
    TMapData tmapdata;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    double mAngle=370;
    double cali_Deg = 370;
    boolean CompOn = false;
    int mRadius = 200;
    boolean GPS_GET = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(StartActivity.this);
                alert_confirm.setMessage("원하시는 방향을 향해 스마트폰을 가리켜주세요. \n현재 위치와 방향을 가져옵니다.").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GPS_GET = false;
                                gpsSetting();
                                Toast.makeText(StartActivity.this, "시간이 다소 소요될 수 있습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tmapview = (TMapView)findViewById(R.id.tmap);
        tmapdata = new TMapData();
        tmapSetting();


        mDBHelper = new DatabaseHelper(this);
        mDB = mDBHelper.getReadableDatabase();

        SensorOn();

        tmapview.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {    //마커를 꾹 누르면 길찾기를 진행할 지 물어본다.
            @Override
            public void onLongPressEvent(ArrayList<TMapMarkerItem> markerlist, ArrayList<TMapPOIItem> poilist, TMapPoint point) {
                try {
                    final TMapMarkerItem marker = markerlist.get(0);
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(StartActivity.this);
                    alert_confirm.setMessage(marker.getName() + "으로 가는 길을 보시겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TMapPoint dest = marker.getTMapPoint();
                                    drawPedestrianPath(dest);
                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 'No'
                                    return;
                                }
                            });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();
                } catch (Exception e) {
                    return;
                }

            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(StartActivity.this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
            }
        });
        alert.setTitle("사용 가이드");
        alert.setMessage("안녕하십니까 방문자님, 경북대학교 방문을 환영합니다. " +
                "\n\n1. 우측 하단의 나침반 버튼을 누르시면 방문자님의 현재 위치와 보고계시는 방향을 가져옵니다." +
                "\n방문자님이 보고계시는 방향으로 100m 이내에 있는 건물의 위치를 알려드립니다." +
                "\n\n2. 그 중 가기를 원하시는 장소의 아이콘을 길게 터치하시면 경로를 알려드립니다." +
                "\n방문목적을 달성하시는데 도움이 되길 바라며 " +
                "\n경북대를 찾아주셔서 다시한번 감사드립니다.");
        alert.show();
    }
    private void gpsSetting(){
            mgps = new TMapGpsManager(this);
            mgps.setMinTime(1000);
            mgps.setMinDistance(5);
            mgps.setProvider(mgps.NETWORK_PROVIDER);
//        mgps.setProvider(mgps.GPS_PROVIDER);
            mgps.setLocationCallback();
            mgps.OpenGps();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lib) {
            // Handle the camera action
            startActivity(new Intent(this, LibraryActivity.class));
        } else if (id == R.id.nav_resto) {
            startActivity(new Intent(this, FoodmenuActivity.class));
        } else if (id == R.id.nav_notif) {
            startActivity(new Intent(this, NoticeActivity.class));
        } else if (id == R.id.nav_booksearch){
            startActivity(new Intent(this, ListActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void SensorOn(){
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void drawPedestrianPath(TMapPoint destination) {
        TMapPoint source = new TMapPoint(lat, lng);

        tmapdata.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, source, destination, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine polyLine) {
                tmapview.removeAllMarkerItem();
                tmapview.removeAllTMapCircle();
                polyLine.setLineColor(Color.BLUE);
                tmapview.addTMapPath(polyLine);
            }
        });
    }
    @Override
    public void onLocationChange(Location location) {   //gps 값이 바뀌었을 때 오는 곳.
        if(!GPS_GET) {
            Toast.makeText(StartActivity.this, "현재 위성의 수는 " + Integer.toString(mgps.getSatellite()) + "개 \n " +
                    "오차범위는 " + Double.toString(location.getAccuracy()) + "m \n" + mgps.getProvider() + "로부터 받아왔습니다.", Toast.LENGTH_LONG).show();
            lat = location.getLatitude();
            lng = location.getLongitude();

            if (!MyPosit)
                tmapview.setCenterPoint(lng, lat);
            MyPosit = true;
            withinDistance(lat, lng);
            setMyPosition(lat, lng);
            drawCircle(lat, lng);
            GPS_GET = true;
        }
        else
        mgps.CloseGps();
    }

    void drawCircle(double lat, double lng){
        TMapPoint tpoint = new TMapPoint(lat, lng);
        TMapCircle circle = new TMapCircle();
        circle.setRadius(mRadius);
        circle.setCenterPoint(tpoint);
        circle.setAreaAlpha(10);
        circle.setAreaColor(Color.BLUE);
        tmapview.addTMapCircle("Cid", circle);
    }
    void setMyPosition (double lat, double lng){
        TMapPoint tpoint = new TMapPoint(lat,lng);
        TMapMarkerItem tmarker = new TMapMarkerItem();

        tmarker.setTMapPoint(tpoint);
        tmarker.setCalloutTitle("현재위치");
        tmarker.setCalloutSubTitle(Double.toString(lat) + "," + Double.toString(lng));
        tmarker.setCanShowCallout(true);
        tmarker.setVisible(TMapMarkerItem.VISIBLE);

        Bitmap mIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_navigation_black_24dp);
        tmarker.setIcon(mIcon);

        tmapview.addMarkerItem("MyPosition", tmarker);
    }
    void setMarker(double lat, double lng, String title, String subtitle, int c){   //위도경도, 제목, 내용, id정도
        TMapPoint tpoint = new TMapPoint(lat,lng);
        TMapMarkerItem tmarker = new TMapMarkerItem();

        tmarker.setTMapPoint(tpoint);
        tmarker.setCalloutTitle(title);         //건물이름
        tmarker.setName(title);
        tmarker.setCalloutSubTitle(subtitle);   //설명
        tmarker.setCanShowCallout(true);
        tmarker.setVisible(TMapMarkerItem.VISIBLE);
        Bitmap mIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_place_black_24dp);
        tmarker.setIcon(mIcon);

        tmapview.addMarkerItem("tid" + Integer.toString(c), tmarker); //id가 똑같으면 1개만 뜬다. 그래서 id를 다 다르게 해줌
        count++;
    }

    void tmapSetting(){
        tmapview.setSKPMapApiKey("1ae72055-5c2d-3e45-ab42-1b133ad5638a");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setIconVisibility(true);
        tmapview.setCenterPoint(128.611330, 35.890052);  //경북대 본관
        tmapview.setZoomLevel(17);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setCompassMode(false);
        tmapview.setTrackingMode(false);
        tmapview.setIconVisibility(true);
    }

    private void withinDistance(double latitude, double longitude) {
        int cursor = 0;
        double source[] = new double[2];//계산을 위해 임시 변수를 선언
        double f_result[] = new double[2];  // 함수의 실행결과를 return 받음
        String [] columns = {FeedReaderContract.CreateDB._ID,
                FeedReaderContract.CreateDB.COLUMN_NAME_BUILDING_NUMBER,
                FeedReaderContract.CreateDB.COLUMN_NAME_NAME,
                FeedReaderContract.CreateDB.COLUMN_NAME_LONGITUDE,
                FeedReaderContract.CreateDB.COLUMN_NAME_LATITUDE,
                FeedReaderContract.CreateDB.COLUMN_NAME_INFORMATION,
                FeedReaderContract.CreateDB.COLUMN_NAME_DISTANCE,
                FeedReaderContract.CreateDB.COLUMN_NAME_ANGLE};
        Cursor c2 = mDB.query(FeedReaderContract.CreateDB.TABLE_NAME, columns, null, null, null, null, null);
        //어느 테이블을 쿼리에서 어떤 칼럼들을 보여줄꺼냐.
        int recordCount = c2.getCount();//총 행이 몇개인지 조사
        tmapview.removeAllMarkerItem();
        tmapview.removeAllTMapCircle();
        for(int i = 0; i < recordCount ; i++){
            c2.moveToNext();
            source[0] = c2.getDouble(3);
            source[1] = c2.getDouble(4);
            f_result = DistanceAngleCalculate(source, latitude, longitude);
            if((f_result[0] < mRadius ) && (mAngle-20 < f_result[1] && f_result[1] < mAngle+20)) {
                setMarker(c2.getDouble(3), c2.getDouble(4), c2.getString(2), c2.getString(5), count);
                cursor++;
            }
        }
        tmapview.rotate(-(float) cali_Deg);
  //      Log.i("cali_Deg", Double.toString(cali_Deg));
        //       Toast.makeText(MainActivity.this, "총 "+Integer.toString(cursor)+"개의 결과가 있음", Toast.LENGTH_SHORT).show();
        c2.close();

    }
    private double[] DistanceAngleCalculate(double[] source, double d_longitude, double d_latitude) {
        double x = source[0]; double y = source[1];
        int y_d, y_m; double y_s;
        int x_d, x_m; double x_s;
        y_d = (int)y;
        y_m = (int)((y-(int)y)*60);
        y_s = (y-y_d-y_m/60.0)*3600;
        x_d = (int)x;
        x_m = (int)((x-(int)x)*60);
        x_s = (x-x_d-x_m/60.0)*3600;    //DD -> DMS

        double y_2 = d_latitude; double x_2 = d_longitude;
        int y_d_2, y_m_2; double y_s_2;
        int x_d_2, x_m_2; double x_s_2;
        y_d_2 = (int)y_2;
        y_m_2 = (int)((y_2-(int)y_2)*60);
        y_s_2 = (y_2-y_d_2-y_m_2/60.0)*3600;
        x_d_2 = (int)x_2;
        x_m_2 = (int)((x_2-(int)x_2)*60);
        x_s_2 = (x_2-x_d_2-x_m_2/60.0)*3600;    //DD -> DMS

        int y_d_d, y_m_d; double y_s_d;
        int x_d_d, x_m_d; double x_s_d;
        y_d_d = y_d_2-y_d;
        y_m_d = y_m_2-y_m;
        y_s_d = y_s_2-y_s;
        double y_temp;
        y_temp = y_d_d*111000+y_m_d*1850+y_s_d*30.8;
        //위도 차이 구하기
        x_d_d = x_d_2-x_d;
        x_m_d = x_m_2-x_m;
        x_s_d = x_s_2-x_s;
        double x_temp;
        x_temp = x_d_d*88800+x_m_d*1480+x_s_d*25;
        //위도 차이 구하기
        double result[]  = new double[2];
        result[1] = Math.atan2(-x_temp, y_temp)*180.0/3.141592;//두 점 사이 각도
        result[0] = Math.sqrt(y_temp*y_temp+x_temp*x_temp);
        return result;
    }
    private class DatabaseHelper extends SQLiteOpenHelper {

        //helper constructor
        public DatabaseHelper(Context context){
            super(context, FILE_PATH, null, DB_VERSION);
            //     println(FILE_PATH + "에서 "+DATABASE_NAME+"파일을 불러옵니다.");
        }

        @Override
        //Execute only onetime.At Database first make.
        public void onCreate(SQLiteDatabase db){
            db.execSQL(FeedReaderContract.CreateDB._CREATE);
            //println("Create database");
        }
        //매번 실행됨.
        public void onOpen(SQLiteDatabase db) {
            //  println("opened database");
        }
        @Override
        //실행 안됨(아직까지는)
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.CreateDB.TABLE_NAME);
            onCreate(db);
        }
        //실행안됨(아직까지는)
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }

    @Override //각도를 알려주는 method
    public void onSensorChanged(SensorEvent event) {
        float azimuthInRadians, azimuthInDegress, cali_azimuthInDegress;
        int Degree=0;
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            azimuthInRadians = mOrientation[0];
            azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians));
            cali_azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians));
            Degree = (int)(azimuthInDegress+0.5);
            cali_Deg = (int)(cali_azimuthInDegress+0.5);
            convAngle(cali_azimuthInDegress + 0.5);   //mAngle 값을 바꿔줌
        }
        if (!CompOn) {
            //
            CompOn = true;
        }


    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }
    void convAngle(double Angle){
        double A = 100.0+Angle;
        if(A > 180.0)
            mAngle = A-360.0;
        else
            mAngle = A;
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }
    protected void onRestart(){
        super.onRestart();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
        if(mgps != null)
        mgps.CloseGps();
    }
    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
        if(mgps != null)
        mgps.CloseGps();
    }
    protected void onDestroy(){
        super.onDestroy();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
        if(mgps != null)
        mgps.CloseGps();
    }

}
