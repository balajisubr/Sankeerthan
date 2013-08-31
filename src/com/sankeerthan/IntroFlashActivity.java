package com.sankeerthan;


import com.sankeerthan.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class IntroFlashActivity extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout);
             
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(IntroFlashActivity.this,MainActivity.class);
                IntroFlashActivity.this.startActivity(mainIntent);
                IntroFlashActivity.this.finish();
            }
        }, 3500);
    }
}