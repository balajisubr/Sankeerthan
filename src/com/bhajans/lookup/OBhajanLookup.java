package com.bhajans.lookup;
import com.bhajans.model.*;
import com.bhajans.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


import android.widget.Toast;

public class OBhajanLookup implements Runnable {
 
  public ArrayList<String> result;
  public String bhajanName;
  public OBhajanLookup() throws InterruptedException
  {
   Thread t = new Thread(this);
   t.start();
   t.join();
if(t!=null)
	   t.interrupt();
  }
  
  public void run()
  {
   try {
	result = this.lookupBhajans();
	System.out.println("Result in thread is" + result);
} catch (ClientProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}  
  }
  
  public static ArrayList<String> lookupBhajans() throws ClientProtocolException,JSONException, IOException, URISyntaxException
  {
	//String bhajans[] = new String[10]; 	 
System.out.println("In the Bhajan look up");	  
  HttpClient client = new DefaultHttpClient();
  HttpParams params = client.getParams();
  HttpConnectionParams.setConnectionTimeout(params, 30000);
  String URL = AppConfig.URL;
  String url = URL + "/lookup_all/bhajans.json";
  System.out.println("url is" + url);
  HttpGet request = new HttpGet(url);
  HttpResponse response = client.execute(request);
  HttpEntity entity = response.getEntity();
  InputStream stream = entity.getContent();
  BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
  StringBuilder sb = new StringBuilder();

  String line = null;
  try {
      while ((line = reader.readLine()) != null) {
    	  System.out.println("Line is " + line);
          sb.append(line + "\n");
      }
  } catch (IOException e) {
      e.printStackTrace();
  } finally {
      try {
          stream.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  //System.out.println(sb);
  System.out.println("sb is " + sb);
  JSONTokener tokener = new JSONTokener(sb.toString());
  JSONObject jsonObject = new JSONObject(tokener);
  JSONArray namesList = jsonObject.getJSONArray("names_list");
//  HashMap<String,String> bhajanDetails = new HashMap<String,String>();
 String namesListString = namesList.toString();
  
  System.out.println("bhajan names list is" + namesListString );
  ArrayList<String> list = new ArrayList<String>();
  for (int i=0; i<namesList.length(); i++) {
      list.add(namesList.getString(i) );
  }

  System.out.println("Bhajan names ind. are " + list.get(0) + "\n" + list.get(1) + "\n" + list.get(2));  
  
  return list;//sb.toString();
  }

}
