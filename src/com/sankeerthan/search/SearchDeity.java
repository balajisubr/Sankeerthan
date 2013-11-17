package com.sankeerthan.search;
import com.sankeerthan.model.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchDeity extends SearchInfo {
	public Bhajan result = null;
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
  
	public String fetchData() {
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