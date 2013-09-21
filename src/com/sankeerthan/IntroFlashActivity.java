package com.sankeerthan;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sankeerthan.R;
import com.sankeerthan.model.LookUpData;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class IntroFlashActivity extends Activity {
	
	static LinkedHashMap<String, ArrayList<String>> lookUpValues = new LinkedHashMap<String, ArrayList<String>>();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout);
        LookUpData.setContext(this);
    	new FetchData().execute();
        
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(IntroFlashActivity.this,MainActivity.class);
                IntroFlashActivity.this.startActivity(mainIntent);
                IntroFlashActivity.this.finish();
            }
        }, 6000);
    }
    
    
    public static void setLookUpValues(String type, ArrayList<String> values)
    {
    	lookUpValues.put(type, values);
    }
    
    public static ArrayList<String> getLookUpValues(String type)
    {
    	return lookUpValues.get(type);
    }
    
    
    class FetchData extends AsyncTask<Void, Void, Void>
    {
		protected Void doInBackground(Void... params) {
        	setLookUpValues(Sankeerthan.BHAJANS, LookUpData.getData(Sankeerthan.BHAJANS));
        	setLookUpValues(Sankeerthan.RAAGAS, LookUpData.getData(Sankeerthan.RAAGAS));
        	setLookUpValues(Sankeerthan.DEITIES, LookUpData.getData(Sankeerthan.DEITIES));
			return null;
		}
    	
    }
    
}