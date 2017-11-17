package knu.capstondesign.design_sample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bella on 2015-12-13.
 */
public class LibraryActivity extends AppCompatActivity {



    private static String URL_PRIMARY = "http://libseat.knu.ac.kr/"; //홈페이지 원본 주소이다.
    private static String First = "domian5.asp";//홈페이지 의 게시판을 나타내는 뒤 주소, 비슷한 게시판들은 거의 파싱이 가능하므로 응용하여 사용하자.
    private String url;
    private java.net.URL URL;
    private Source source;
    private ProgressDialog progressDialog;
    private BBSListAdapter BBSAdapter = null;
    private ListView BBSList;

    private SwipeRefreshLayout mSwipe;

    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;
    ArrayList<ListData> mListData = new ArrayList<>();



    @Override
    protected void onStop() { //멈추었을때 다이어로그를 제거해주는 메서드
        super.onStop();
        if ( progressDialog != null)
            progressDialog.dismiss(); //다이어로그가 켜져있을경우 (!null) 종료시켜준다
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libseat);
        BBSList = (ListView)findViewById(R.id.listView); //리스트선언
        BBSAdapter = new BBSListAdapter(this);
        BBSList.setAdapter(BBSAdapter); //리스트에 어댑터를 먹여준다.
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        Toast.makeText(LibraryActivity.this, "아래로 당겼다 놓으면 새로고침합니다", Toast.LENGTH_SHORT).show();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListData.clear();
                getBoard();
                mSwipe.setRefreshing(false);
            }
        });
        BBSList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (BBSList == null || BBSList.getChildCount() == 0) ?
                        0 : BBSList.getChildAt(0).getTop();
                mSwipe.setEnabled((topRowVerticalPosition >= 0));
            }
        });
        BBSList.setOnItemClickListener( //리스트 클릭시 실행될 로직 선언
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                        ListData mData = mListData.get(position); // 클릭한 포지션의 데이터를 가져온다.
                        String URL_BCS = mData.mUrl; //가져온 데이터 중 url 부분만 적출해낸다.

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL_PRIMARY + URL_BCS))); //적출해낸 url 을 이용해 URL_PRIMARY 와 붙이고

                    }
                });

        getBoard();


    }




    private void getBoard(){
        url = URL_PRIMARY + First; //파싱하기전 PRIMARY URL 과 공지사항 URL 을 합쳐 완전한 URL 을만든다.

        if(isInternetCon()) { //false 반환시 if 문안의 로직 실행
            Toast.makeText(LibraryActivity.this, "인터넷에 연결되지않아 불러오기를 중단합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }else{ //인터넷 체크 통과시 실행할 로직
            try {
                process(); //네트워크 관련은 따로 쓰레드를 생성해야 UI 쓰레드와 겹치지 않는다. 그러므로 Thread 가 선언된 process 메서드를 호출한다.
                BBSAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("ERROR", e + "");
                BBSAdapter.notifyDataSetChanged();

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
                        progressDialog = ProgressDialog.show(LibraryActivity.this, "", "게시판 정보를 가져오는중 입니다.");
                    }
                }, 0);

                try {
                    URL = new URL(url);
                    InputStream html = URL.openStream();
                    source = new Source(new InputStreamReader(html, "euc-kr")); //소스를 euc-kr 인코딩으로 불러온다.
                    source.fullSequentialParse(); //순차적으로 구문분석
                } catch (Exception e) {
                    Log.d("ERROR", e + "");
                }

                Element BBS_TABLE = (Element) source.getAllElements(HTMLElementName.TABLE).get(1); //2번째 테이블 접속

                for(int C_TR = 2; C_TR < BBS_TABLE.getAllElements(HTMLElementName.TR).size();C_TR++){//세번째 tr부터 가져옴
                    //여기서는 이제부터 게시된 게시물 데이터를 불러와 게시판 인터페이스를 구성할 것이다.
                    // 소스의 효율성을 위해서는 for 문을 사용하는것이 좋지만 , 이해를 돕기위해 소스를 일부로 늘려 두었다.

                    try {
                        Element BBS_TR = (Element) BBS_TABLE.getAllElements(HTMLElementName.TR).get(C_TR); //TR 접속
                        //td가 6개. 그중에 4개만 씀. 앞뒤껀 안씀

                        Element BC_TYPE_LIBRARY = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(1); //두번째 td. 열람실 이름
                        String BCS_library = BC_TYPE_LIBRARY.getTextExtractor().toString();  //열람실 이름
                        Element BC_TYPE_A = (Element)BC_TYPE_LIBRARY.getAllElements(HTMLElementName.A).get(0);  //열람실 url
                        String BCS_url = BC_TYPE_A.getAttributeValue("href");    //열람실 url
                        Element BC_TYPE_ALLSEAT = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(2);    //전체좌석
                        String BCS_allseat = BC_TYPE_ALLSEAT.getTextExtractor().toString();
                        Element BC_TYPE_USEDSEAT = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(3);   //사용좌석
                        String BCS_usedseat = BC_TYPE_USEDSEAT.getTextExtractor().toString();
                        Element BC_TYPE_EMPTYSEAT = (Element)BBS_TR.getAllElements(HTMLElementName.TD).get(4);  //잔여좌석
                        String BCS_emptyseat = BC_TYPE_EMPTYSEAT.getTextExtractor().toString();

                        mListData.add(new ListData(BCS_library, BCS_allseat, BCS_url, BCS_usedseat, BCS_emptyseat)); //데이터가 모이면 데이터 리스트 클래스에 데이터들을 등록한다.
                        /* Log.d("BCSARR","타입:"+BCS_type+"\n제목:" +BCS_title +"\n주소:"+BCS_url +"\n글쓴이:" + BCS_writer + "\n날짜:" + BCS_date);*/


                    }catch(Exception e){
                        Log.d("BCSARR","에러에여");
                    }
                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BBSAdapter.notifyDataSetChanged(); //모든 작업이 끝나면 리스트 갱신
                        progressDialog.dismiss(); //모든 작업이 끝나면 다이어로그 종료
                    }
                }, 0);



            }

        }.start();


    }


    private boolean isInternetCon() {
        cManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); //모바일 데이터 여부
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //와이파이 여부
        return !mobile.isConnected() && !wifi.isConnected(); //결과값을 리턴
    }




    // <리스트 적용부분
    class ViewHolder {

        public TextView mLibrary;
        public TextView mAllseat;
        public TextView mUsedseat;
        public TextView mEmptyseat;
    }


    public class BBSListAdapter extends BaseAdapter {
        private Context mContext = null;

        public BBSListAdapter(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.itemstyle_libseat, null);

                holder.mLibrary = (TextView)convertView.findViewById(R.id.item_library);
                holder.mAllseat = (TextView)convertView.findViewById(R.id.item_allseat);
                holder.mUsedseat = (TextView)convertView.findViewById(R.id.item_usedseat);
                holder.mEmptyseat = (TextView)convertView.findViewById(R.id.item_emptyseat);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            holder.mLibrary.setText(mData.mLibrary);
            holder.mAllseat.setText(mData.mAllseat);
            holder.mUsedseat.setText(mData.mUsedseat);
            holder.mEmptyseat.setText(mData.mEmptyseat);

            return convertView;

        }


    }

    public class ListData { // 데이터를 받는 클래스

        public String mLibrary;
        public String mAllseat;
        public String mUrl;
        public String mUsedseat;
        public String mEmptyseat;


        public ListData()  {


        }

        public ListData(String mLibrary,String mAllseat,String mUrl,String mUsedseat,String mEmptyseat)  { //데이터를 받는 클래스 메서드
            this.mLibrary = mLibrary;
            this.mAllseat = mAllseat;
            this.mUrl = mUrl;
            this.mUsedseat = mUsedseat;
            this.mEmptyseat = mEmptyseat;

        }

    }
    // 리스트 적용부분 >
}

