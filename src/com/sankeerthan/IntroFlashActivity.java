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
        setContentView(R.layout.main_layout);
        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        LookUpData.setContext(this);
    	new FetchData().execute();
        
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mainIntent = new Intent(IntroFlashActivity.this,MainActivity.class);
                IntroFlashActivity.this.startActivity(mainIntent);
                IntroFlashActivity.this.finish();
            }
        }, 3000);
    }
    
    
    public static void setLookUpValues(String type, ArrayList<String> values)
    {
    	lookUpValues.put(type, values);
    }
    
    public static ArrayList<String> getLookUpValues(String type)
    {
    	return lookUpValues.get(type);
    }
    
    
    class FetchData extends AsyncTask<Void, Void, Void>{
       ProgressDialog pd = null;
       
       public void onPreExecute() {
        	IntroFlashActivity.this.runOnUiThread(new Runnable() {
    	        public void run() {
    	            IntroFlashActivity.this.bar.setVisibility(View.VISIBLE);
    	        	}}     	       
        	);
        }

    
		protected Void doInBackground(Void... params) {
			Runnable lookUpBhajans = new Runnable() {
				public void run()
				{
					setLookUpValues(Sankeerthan.BHAJANS, LookUpData.getData(Sankeerthan.BHAJANS));
				}
			};
			
			Runnable lookUpRaagas = new Runnable() {
				public void run()
				{
					setLookUpValues(Sankeerthan.RAAGAS, LookUpData.getData(Sankeerthan.RAAGAS));
				}				
			};
			
			Runnable lookUpDeities = new Runnable() {
				public void run()
				{
		        	setLookUpValues(Sankeerthan.DEITIES, LookUpData.getData(Sankeerthan.DEITIES));

				}				
			};
        	Thread t1 = new Thread(lookUpBhajans);
        	Thread t2 = new Thread(lookUpRaagas);
        	Thread t3 = new Thread(lookUpDeities);
        	
        	t1.start(); 
        	t2.start();
        	t3.start();
        	
        	try {
				t1.join();
	        	t2.join();
	        	t3.join();
			} catch (InterruptedException e) {
				
			}
        	
			return null;
		}
		
		public void onPostExecute() {
            IntroFlashActivity.this.bar.setVisibility(View.GONE);
		}
    	
    }
    
}