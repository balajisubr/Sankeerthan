package com.sankeerthan.search;
import com.sankeerthan.model.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchBhajan extends SearchInfo  {
 
	public Bhajan result = null;
	private static String subURL = "/find_bhajans/";
	private String key = "";
	public SearchBhajan(String key, boolean initialize) throws InterruptedException {
		super(key, subURL);
		this.key = key;
		if(initialize)
		    result = new Bhajan();
	}
	
	public SearchBhajan(){
		super();
		result = new Bhajan();
	}
  
	public void getData() {
		String serverResult;
		try {
			serverResult = this.fetchData();
			parseData(serverResult);
		} catch (ClientProtocolException e) {
			this.serverErrors.add("There was an error! Please try again later! #1");
		} catch (IOException e) {
			this.serverErrors.add("There was an error in accessing data! Please try later #2");
		} catch (JSONException e) {
			this.serverErrors.add("There was an error! Please try later! #3");
		}	  
	}
  
	public String fetchData() {
		return super.fetchData(); 
	}
  
	public void parseData(String result) throws ClientProtocolException, IOException, JSONException {
		super.parseData(result);
	}
  
	protected void extractData(JSONObject jsonObject) {
		String raaga = jsonObject.optString("raaga");
		String meaning = jsonObject.optString("meaning");
		String lyrics = jsonObject.optString("lyrics");
		String deity = jsonObject.optString("deity");
		String url = jsonObject.optString("url");
		result = (new Bhajan(raaga,meaning,lyrics,deity, key, url));
	}

}  
  



