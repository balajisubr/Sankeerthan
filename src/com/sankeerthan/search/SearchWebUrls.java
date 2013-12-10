package com.sankeerthan.search;
import com.sankeerthan.model.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchWebUrls extends SearchInfo  {
 
	public Bhajan result = null;
	public String resultURL = "";
	private String key = "";
	
	public SearchWebUrls(String key, String subURL) throws InterruptedException {
		super(key, subURL);
		this.key = key;
		this.subURL = subURL;
	}
	
	public SearchWebUrls(){
		super();
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
		this.resultURL = jsonObject.optString("url");
	}

}  
  



