package com.sankeerthan.lookup;
import com.sankeerthan.AppConfig;
import com.sankeerthan.model.*;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


import android.widget.Toast;

public class ODeityLookup implements Runnable {
 
  public ArrayList<String> result;
  public String bhajanName;
  public ODeityLookup() throws InterruptedException
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
	result = this.lookupDeity();
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
  
  public static ArrayList<String> lookupDeity() throws ClientProtocolException,JSONException, IOException, URISyntaxException
  {
	//String bhajans[] = new String[10]; 	  
  HttpClient client = new DefaultHttpClient();
  String URL = AppConfig.URL;
  String url = URL + "/lookup_all/deities.json";
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
  JSONTokener tokener = new JSONTokener(sb.toString());
  JSONObject jsonObject = new JSONObject(tokener);
  JSONArray namesList = jsonObject.getJSONArray("names_list");
//  HashMap<String,String> bhajanDetails = new HashMap<String,String>();
 String namesListString = namesList.toString();
  
  ArrayList<String> list = new ArrayList<String>();
  for (int i=0; i<namesList.length(); i++) {
      list.add(namesList.getString(i) );
  }
  return list;//sb.toString();
  }

}

