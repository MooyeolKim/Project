package knu.capstondesign.design_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Bella on 2015-12-17.
 */
public class splash extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(splash.this, StartActivity.class);
                startActivity(intent);
                // 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
                finish();
            }


        }, 2000);

    }

}
