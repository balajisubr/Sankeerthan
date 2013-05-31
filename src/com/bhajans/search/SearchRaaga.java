package com.bhajans.search;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchRaaga extends SearchInfo  {
 
  //public ArrayList<String> serverErrors = new ArrayList<String>();
	public ArrayList<String> list = new ArrayList<String>();
	private static String subURL = "/find_raagas/";
	public SearchRaaga(String key) throws InterruptedException {
		super(key, subURL);
	}
	
	public void getData() {
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
  



