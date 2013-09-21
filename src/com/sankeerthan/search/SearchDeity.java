package com.sankeerthan.search;
import com.sankeerthan.Sankeerthan;
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

public class SearchDeity extends SearchInfo {
	public Bhajan result = null;
  //public ArrayList<String> serverErrors = new ArrayList<String>(); 
	public ArrayList<String> list = new ArrayList<String>();
	private static String subURL = "/find_deities/";
	public SearchDeity(String key) throws InterruptedException {
		super(key, subURL);
    }
  
	public void getData() {
		String serverResult;
		try {
			serverResult = this.fetchData();
			parseData(serverResult);
			} catch (ClientProtocolException e) {
			this.serverErrors.add("There was an error! Please try again later! #4");
			} catch (IOException e) {
			this.serverErrors.add("There was an error in accessing data! Please try later #5");
			} catch (JSONException e) {
			this.serverErrors.add("There was an error! Please try later! #6");
			}	  
		}
  
	public String fetchData() throws ClientProtocolException, IOException {
		return super.fetchData(); 
	}
  
	public void parseData(String result) throws ClientProtocolException, IOException, JSONException {
		super.parseData(result);
	}
  
	protected void extractData(JSONObject jsonObject) {
		JSONArray bhajanJSON;
		try {
			bhajanJSON = jsonObject.getJSONArray("bhajan_names");
			for (int i=0; i<bhajanJSON.length(); i++) {
				list.add(bhajanJSON.getString(i) );
			} 
		} catch (JSONException e) {
			serverErrors.add("There was an error! Please try later! #7 ");
	   }
	}
 }