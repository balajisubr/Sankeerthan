package com.sankeerthan;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sankeerthan.R;
import com.sankeerthan.model.LookUpData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class IntroFlashActivity extends Activity {
	
	public ProgressBar bar;
	static LinkedHashMap<String, ArrayList<String>> lookUpValues = new LinkedHashMap<String, ArrayList<String>>();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.flash);
        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        LookUpData.setContext(this);
        
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(IntroFlashActivity.this,MainActivity.class);
                IntroFlashActivity.this.startActivity(mainIntent);
                IntroFlashActivity.this.finish();
            }
        }, 1500);
    } 
}
