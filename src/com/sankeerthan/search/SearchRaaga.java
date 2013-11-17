package com.sankeerthan.search;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchRaaga extends SearchInfo  {
 
	public ArrayList<String> list = new ArrayList<String>();
	private static String subURL = "/find_raagas/";
	public SearchRaaga(String key) throws InterruptedException {
		super(key, subURL);
	}
	
	public void getData() {
		String serverResult;
		try {
			serverResult = this.fetchData();
			System.out.println("REsult in getdata is " + serverResult);
			parseData(serverResult);
		} catch (ClientProtocolException e) {
			this.serverErrors.add("There was an error! Please try again later! #8");
		} catch (IOException e) {
			this.serverErrors.add("There was an error in accessing data! Please try later #9");
		} catch (JSONException e) {
			this.serverErrors.add("There was an error! Please try later! #10");
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
			this.serverErrors.add("There was an error! Please try later! #11");
		}
	  }
}  
  



