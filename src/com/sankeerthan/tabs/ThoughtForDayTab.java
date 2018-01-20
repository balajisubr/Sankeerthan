package com.sankeerthan.tabs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThoughtForDayTab extends CustomWebView {
       String url = "";
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
    	   
    	Locale current = getResources().getConfiguration().locale;
        Calendar cal = Calendar.getInstance(current);
        String year = Integer.toString(cal.get(Calendar.YEAR));
        String month = Integer.toString(cal.get(Calendar.MONTH)+1); if(month.length() == 1) month = "0" + month;
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)); if(day.length() == 1) day = "0" + day;
        
        url = "http://media.radiosai.org/sai_inspires/"  + year + "/SI_" +  year + month + day + ".htm";
                
        Thread t = new Thread(new HTTPStatusVerify());
        t.start();
        try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        setWebViewURL(url);
        return super.onCreateView(inflater, container, savedInstanceState);
        }
    
    public class HTTPStatusVerify implements Runnable {
    
      public void run()
       {
		URL obj = null;
		HttpURLConnection con = null;

		int responseCode = 0;
		try {
			obj = new URL(ThoughtForDayTab.this.url);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			responseCode = con.getResponseCode();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    catch (IOException e1) {
			e1.printStackTrace();
		}
        if(responseCode != 200)
        	ThoughtForDayTab.this.url = "http://radiosai.org";
       }
    }
    }