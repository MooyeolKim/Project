package knu.capstondesign.design_sample;

/**
 * Created by Bella on 2015-12-13.
 */
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodmenuActivity extends ActionBarActivity {



    private static String URL_PRIMARY = "http://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno="; //홈페이지 원본 주소이다.
    private int place = 40;
    private int day = 1;
    private int time = 1;
    private String url;
    private URL URL;
    private Source source;
    private ProgressDialog progressDialog;
    private TextView menu;
    private TextView notify;
    private Spinner placespinner;
    private ArrayAdapter<CharSequence> placeadapter = null;
    private Spinner dayspinner;
    private Spinner timespinner;
    private boolean MenuConv = false;
    private int BBSlocate;
    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;
    private ArrayList<String> menuList = new ArrayList<>() ;


    @Override
    protected void onStop() { //멈추었을때 다이어로그를 제거해주는 메서드
        super.onStop();
        if ( progressDialog != null)
            progressDialog.dismiss(); //다이어로그가 켜져있을경우 (!null) 종료시켜준다
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu);
        placespinner = (Spinner)findViewById(R.id.place);
        placeadapter = ArrayAdapter.createFromResource(this, R.array.eating_place,
                R.layout.spinner_layout);
        placeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placespinner.setAdapter(placeadapter);//스피너에 어댑터를 먹여준다.
        menu = (TextView)findViewById(R.id.menu);
        menu.setVerticalScrollBarEnabled(true);
        menu.setMovementMethod(new ScrollingMovementMethod());
        notify = (TextView)findViewById(R.id.notify);
        timespinner = (Spinner)findViewById(R.id.time);
        populateSubSpinners(timespinner,R.array.time3);
        dayspinner = (Spinner)findViewById(R.id.day);
        populateSubSpinners(dayspinner, R.array.day5);
        dayspinner.setOnItemSelectedListener(mSelectedListener);
        placespinner.setOnItemSelectedListener(mSelectedListener);
        timespinner.setOnItemSelectedListener(mSelectedListener);
    }

    AdapterView.OnItemSelectedListener mSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {

            if(adapterView.getId() == R.id.place) {
                MenuConv = false;
                menu.setText(" ");
                if (position == 1){
                    place = 46;
                    populateSubSpinners(timespinner,R.array.time1);   //GP감꽃
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 2){
                    place = 57;
                    populateSubSpinners(timespinner,R.array.time1);   //GP일청담
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 3) {
                    place = 54;
                    populateSubSpinners(timespinner,R.array.time2);  //공식 교직원
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 4) {
                    place = 40;
                    populateSubSpinners(timespinner,R.array.time1); //공식 학생
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 5) {
                    place = 36;
                    populateSubSpinners(timespinner,R.array.time1);   //복지관 교직원
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 6) {
                    place = 37;
                    populateSubSpinners(timespinner,R.array.time3);   //복지관 학생
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 7) {
                    place = 39;
                    populateSubSpinners(timespinner,R.array.time1);   //복현회관 교직원
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 8) {
                    place = 56;
                    populateSubSpinners(timespinner,R.array.time1);   //복현회관 학생
                    populateSubSpinners(dayspinner,R.array.day5);
                }
                else if (position == 9) {
                    place = 35;
                    populateSubSpinners(timespinner,R.array.time2);   //밥센터
                    populateSubSpinners(dayspinner,R.array.day7);
                }
                else {place = 46;
                    populateSubSpinners(timespinner,R.array.time1);   //GP감꽃
                    populateSubSpinners(dayspinner, R.array.day5);}
                getBoard();
            }
            else if(adapterView.getId() == R.id.time){
                String s = adapterView.getSelectedItem().toString();
                if (s.equals("조식")){
                    time = 0;
                }
                else if(s.equals("중식")) {
                    time = 1;
                }
                else if(s.equals("석식")) {
                    time = 2;
                }
                else time = 0;
            }
            else if(adapterView.getId() == R.id.day) {
                day = position-1;
                if(position >= 0 && position < menuList.size()) {
                    menuList = mstring(menuList);
                    text(place, time, day);
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //Do Nothing
        }
    };
    private void populateSubSpinners(Spinner spinner ,int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                itemNum,
                R.layout.spinner_layout);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fAdapter);
    }



    private void getBoard(){
        url = URL_PRIMARY + Integer.toString(place); //파싱하기전 PRIMARY URL 과 공지사항 URL 을 합쳐 완전한 URL 을만든다.
        if(isInternetCon()) { //false 반환시 if 문안의 로직 실행
            Toast.makeText(FoodmenuActivity.this, "인터넷에 연결되지않아 불러오기를 중단합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }else{ //인터넷 체크 통과시 실행할 로직
            try {
                process(); //네트워크 관련은 따로 쓰레드를 생성해야 UI 쓰레드와 겹치지 않는다. 그러므로 Thread 가 선언된 process 메서드를 호출한다.
            } catch (Exception e) {
                Log.d("ERROR", e + "");

            }
        }
    }

    private void process() throws IOException {

        new Thread() {

            @Override
            public void run() {

                Handler Progress = new Handler(Looper.getMainLooper()); //네트워크 쓰레드와 별개로 따로 핸들러를 이용하여 쓰레드를 생성한다.
                Progress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(FoodmenuActivity.this, "", "게시판 정보를 가져오는중 입니다.");
                    }
                }, 0);

                try {
                    menuList.clear();
                    URL = new URL(url);
                    InputStream html = URL.openStream();
                    source = new Source(new InputStreamReader(html, "utf-8")); //소스를 UTF-8 인코딩으로 불러온다.
                    source.fullSequentialParse(); //순차적으로 구문분석
                } catch (Exception e) {
                    Log.d("ERROR", e + "");
                }

                List<StartTag> tabletags = source.getAllStartTags(HTMLElementName.DIV); // DIV 타입의 모든 태그들을 불러온다.

                for(int arrnum = 0;arrnum < tabletags.size(); arrnum++){ //DIV 모든 태그중 week_table mt5 태그가 몇번째임을 구한다.


                    if(tabletags.get(arrnum).toString().equals("<div class=\"week_table\">")) {
                        BBSlocate = arrnum; //DIV 클래스가 이번주 식단이면 arrnum 값을 BBSlocate 로 몇번째인지 저장한다.
                        Log.d("BBSLOCATES", arrnum+""); //arrnum 로깅

                        Element BBS_DIV = (Element) source.getAllElements(HTMLElementName.DIV).get(BBSlocate); //BBSlocate 번째 의 DIV 를 모두 가져온다.
                        Element BBS_TABLE = (Element) BBS_DIV.getAllElements(HTMLElementName.TABLE).get(0); //테이블 접속
                        Element BBS_TBODY = (Element) BBS_TABLE.getAllElements(HTMLElementName.TBODY).get(0); //데이터가 있는 TBODY 에 접속
                        for(int C_TR = 0; C_TR < BBS_TBODY.getAllElements(HTMLElementName.TR).size(); C_TR++)//C_TR은 1~3개까지
                        {
                            Element BBS_TR = (Element) BBS_TBODY.getAllElements(HTMLElementName.TR).get(C_TR);
                            for(int C_TD = 0; C_TD < BBS_TR.getAllElements(HTMLElementName.TD).size(); C_TD++)//C_TD는 5~7개
                            {
                                Element BBS_TD = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(C_TD);
                                String Menu = BBS_TD.getTextExtractor().toString();
                                menuList.add(Menu);
                            }
                        }
                    }else if(tabletags.get(arrnum).toString().equals("<div class=\"week_table mt5\">"))//조식이 없을때만 들어오는 if문
                    {
                        BBSlocate = arrnum;
                        Log.d("BBSLOCATES", arrnum+""); //arrnum 로깅
                        Element BBS_DIV = (Element) source.getAllElements(HTMLElementName.DIV).get(BBSlocate); //BBSlocate 번째 의 DIV 를 모두 가져온다.
                        Element BBS_TABLE = (Element) BBS_DIV.getAllElements(HTMLElementName.TABLE).get(0); //테이블 접속
                        Element BBS_TBODY = (Element) BBS_TABLE.getAllElements(HTMLElementName.TBODY).get(0); //데이터가 있는 TBODY 에 접속
                        for(int C_TR = 0; C_TR < BBS_TBODY.getAllElements(HTMLElementName.TR).size(); C_TR++)//C_TR은 1~3개까지
                        {
                            Element BBS_TR = (Element) BBS_TBODY.getAllElements(HTMLElementName.TR).get(C_TR);
                            for(int C_TD = 0; C_TD < BBS_TR.getAllElements(HTMLElementName.TD).size(); C_TD++)//C_TD는 5~7개
                            {
                                Element BBS_TD = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(C_TD);
                                String Menu = BBS_TD.getTextExtractor().toString();
                                menuList.add(Menu);
                            }
                        }
                    }

                }



                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(day >= 0 && day < menuList.size()){
                            menuList = mstring(menuList);
                            //                           text(place, time, day);
                        }

                        progressDialog.dismiss(); //모든 작업이 끝나면 다이어로그 종료
                    }
                }, 0);

            }

        }.start();


    }

    private void text(int place, int time, int day){
        if(time == 0) { //조식
            if(place == 40)
                menu.setText("조식은 제공하지 않습니다.");
            else if(place == 37)
                menu.setText(menuList.get(day));
            else
                menu.setText("조식은 제공하지 않습니다.");
        }
        else if(time == 1){ //중식
            if(place == 57 || place == 54 || place == 39 || place == 56)    //중식 1종류
                menu.setText(menuList.get(day));
            else if(place == 46 || place == 36){    //중식 2종류 + 조식x + 5일영업
                menu.setText(menuList.get(day));
                menu.append("\n"+menuList.get(day+6));
            }
            else if(place == 40) {    //중식 2종류 + 조식x + 5일영업
                menu.setText(menuList.get(day));
                menu.append("\n"+menuList.get(day+6));
            }
            else if(place == 37){   //중식 3종류 + 조식o + 5일영업
                menu.setText(menuList.get(day+6));
                menu.append("\n"+menuList.get(day+12));
                menu.append("\n"+menuList.get(day+18));
            }
            else if(place == 35){   //중식 2종류 + 조식x + 7일영업
                if((menuList.size()%7) == 0){   //7일 영업하는 주
                    menu.setText(menuList.get(day));
                    menu.append("\n"+menuList.get(day+7));
                }
                else {  //6일 영업하는 주
                    if(day != 6) {
                        menu.setText(menuList.get(day));
                        menu.append("\n" + menuList.get(day + 6));
                    }
                    else menu.setText("이번주 일요일은 영업일이 아닙니다.");
                }
            }
            else
                menu.setText("time1 x");
        }
        else if(time == 2){
            if(place == 54) //조식0중식1석식1 5일영업
                menu.setText(menuList.get(day+6));
            else if(place == 40)    //조식1중식2석식1 5일영업
                menu.setText(menuList.get(day+18));
            else if(place == 37)    //조식1중식3조식1 5일영업
                menu.setText(menuList.get(day+24));
            else if(place == 35) {    //조식0중식2석식2 7일영업
                if((menuList.size()%7) == 0){
                    menu.setText(menuList.get(day+ 14));
                    menu.append("\n"+menuList.get(day+21));}
                else {
                    if(day != 6) {
                        menu.setText(menuList.get(day+12));
                        menu.append("\n" + menuList.get(day +18));
                    }
                    else menu.setText("이번주 일요일은 영업일이 아닙니다.");
                }
            }
            else
                menu.setText("time2 x");
        }
        else
            menu.setText("time이 설정되어있지않음");
    }   //열지마시오;;


    private ArrayList<String> mstring(ArrayList<String> StringArray) {
        if(!MenuConv) {
            ArrayList<String> tempArray = new ArrayList<>();
            for (int i = 0; i < StringArray.size(); i++) {
                String s = StringArray.get(i);
                String[] mString = s.split(" ");
                try {
                    for (int l = 0; l < mString.length; l++) {
                        if (mString[l].equals("정식"))
                            mString[l] = "[정식]";
                        if (mString[l].equals("특식"))
                            mString[l] = "[특식]";
                        if (mString[l].equals("￦")) {
                            mString[l] = "<가격>";
                            mString[l + 1] = mString[l + 1].replaceAll(",", "");
                            mString[l] = mString[l] + " " + mString[l + 1];
                            mString[l + 1] = "";
                        }
                        if (mString[l].equals("메뉴는"))
                            mString[l] = "메뉴는 변경될 수 있습니다.";
                        if (mString[l].equals("는")||mString[l].equals("변경")||mString[l].equals("될")||mString[l].equals("수")||mString[l].equals("있습니다"))
                            mString[l] = "";

                    }
                } catch (Exception e) {
                    if (mString[1].equals("[정식]") || mString[1].equals("[특식]"))
                        mString[0] = "";
                }
                try {
                    if (mString[1].equals("[정식]") || mString[1].equals("[특식]"))
                        mString[0] = "";

                }catch (Exception e){
                    Log.e("BBSLOCATES", "error"); //arrnum 로깅
                }

                s = Arrays.toString(mString);
                s = s.substring(1, s.length() - 1).replaceAll(",", "\n");
                tempArray.add(s);
            }
            MenuConv = true;
            return tempArray;
        }
        return StringArray;
    }

    private boolean isInternetCon() {
        cManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); //모바일 데이터 여부
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //와이파이 여부
        return !mobile.isConnected() && !wifi.isConnected(); //결과값을 리턴
    }

}


