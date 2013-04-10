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

public class SearchRaaga extends SearchInfo  {
 
  //public ArrayList<String> serverErrors = new ArrayList<String>();
  public ArrayList<String> list = new ArrayList<String>();

  private static String subURL = "/find_raagas/";
  public SearchRaaga(String key) throws InterruptedException
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
	 // System.out.println("Bhajan names ind. are " + list.get(0) + list.get(1));  	  
}  
  



