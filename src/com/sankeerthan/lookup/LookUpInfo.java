package com.sankeerthan.lookup;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sankeerthan.search.SearchInfo;

public class LookUpInfo extends SearchInfo {

	public ArrayList<String> list = new ArrayList<String>();
	public LookUpInfo(String key, String subURL) {
		super(key, subURL);
	}
	
	public void lookupInfo() {
		String result;
		try {
			result = this.fetchData();
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

	  
	  protected void extractData(JSONObject jsonObject)	{
		  JSONArray namesList = new JSONArray();
		  try 
		  {
			  namesList = jsonObject.getJSONArray("names_list");
			  for (int i=0; i<namesList.length(); i++) 
				  list.add(namesList.getString(i) );
		  } catch (JSONException e) {
			  this.serverErrors.add("There was an error! Please try later!");		
		  }
	  }
}
