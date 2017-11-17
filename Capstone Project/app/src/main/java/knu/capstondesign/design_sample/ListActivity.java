package knu.capstondesign.design_sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2015-12-17.
 */
public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] linkArray = {
            "경북대 공식 홈페이지",
            "경북대 산학 협력단",
            "국제 교류원",
            "인재 개발원",
            "링크 사업단",
            "정보 전산원",
            "생활관",
            "어학 교육원",
            "체육 진흥센터"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_layout);

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, linkArray);

        ListView list = (ListView) findViewById(R.id.link_list);
        list.setAdapter(Adapter);

        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        String c_list = linkArray[i];

        if (c_list == "경북대 공식 홈페이지") {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://knu.ac.kr/wbbs/")));
        } else if (c_list == "경북대 산학 협력단") {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://iac.knu.ac.kr/")));
        } else if (c_list == "국제 교류원") {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://gp.knu.ac.kr/")));
        } else if (c_list == "인재 개발원"){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://knujob.knu.ac.kr/")));
        } else if (c_list == "링크 사업단") {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://linc.knu.ac.kr/")));
        } else if (c_list == "정보 전산원"){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://uicc.knu.ac.kr/")));
        } else if (c_list == "생활관"){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://dorm.knu.ac.kr/")));
        } else if (c_list == "어학 교육원"){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://lang.knu.ac.kr/")));
        } else if (c_list == "체육 진흥센터"){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://sports.knu.ac.kr/")));
        }

    }

}
