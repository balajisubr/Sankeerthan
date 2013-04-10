package com.bhajans.search;
import com.bhajans.AppConfig;
import com.bhajans.model.*;

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

public class SearchDeity extends SearchInfo  {
 
  public Bhajan result = null;
  //public ArrayList<String> serverErrors = new ArrayList<String>(); 
  public ArrayList<String> list = new ArrayList<String>();
  private static String subURL = "/find_deities/";
  public SearchDeity(String key) throws InterruptedException
  {
	super(key, subURL);
  }
  
  public void getData() 
  {
	String result;
	try {
		result = this.fetchData();
		System.out.println("REsult in getdata is " + result);
	    parseData(result);
		 
	} catch (ClientProtocolException e) {
		this.serverErrors.add("There was an error! Please try again later!");
	} catch (IOException e) {
		this.serverErrors.add("There was an error in accessing data! Please try later");
	} catch (JSONException e) {
		this.serverErrors.add("There was an error! Please try later!");
	}	  
  }
  
  public String fetchData() throws ClientProtocolException, IOException
  {
    return super.fetchData(); 
  }
  
  public void parseData(String result) throws ClientProtocolException, IOException, JSONException
  {
    super.parseData(result);
  }
  
  protected void extractData(JSONObject jsonObject)
  {
	  System.out.println("The size of the list is" + this.list.size());
	  JSONArray bhajanJSON;
	try {
		bhajanJSON = jsonObject.getJSONArray("bhajan_names");
		String bhajanName = bhajanJSON.toString();
		System.out.println("bhajan name is 1" + bhajanName);
		for (int i=0; i<bhajanJSON.length(); i++) {
		  list.add(bhajanJSON.getString(i) );
		} 
	} catch (JSONException e) {
		this.serverErrors.add("There was an error! Please try later!");
	   }
	  }
  }


  




/*
package com.bhajans.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

import com.bhajans.AppConfig;

import android.widget.Toast;

public class SearchDeity implements Runnable {
 
  public ArrayList<String> result;
  public String deity;
  public SearchDeity(String deity) throws InterruptedException
  {
   this.deity = deity;	  
   Thread t = new Thread(this);
   t.start();
   t.join();
  // if(t!=null)
//	   System.out.println("t is not null");
//	   t.interrupt();
  }
  
  public void run()
  {
   try {
	result = this.searchDeity(deity);
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
  
  public ArrayList<String> searchDeity(String deity) throws ClientProtocolException,JSONException, IOException, URISyntaxException
  {
	//String bhajans[] = new String[10]; 	  
  HttpClient client = new DefaultHttpClient();
  String URL = AppConfig.URL;
  String url = URL + "/find_deities/" + deity + ".json";
  URI uri = new URI(url.replace(" ", "%20"));
  HttpGet request = new HttpGet(uri.toString());
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
  JSONArray bhajanJSON = jsonObject.getJSONArray("bhajan_names");
  String bhajanName = bhajanJSON.toString();
  
  System.out.println("bhajan name is 1" + bhajanName);
  ArrayList<String> list = new ArrayList<String>();
  for (int i=0; i<bhajanJSON.length(); i++) {
      list.add(bhajanJSON.getString(i) );
  }

 // System.out.println("Bhajan names ind. are " + list.get(0) + list.get(1));  
  
  return list;//sb.toString();
 
  }

}

*/