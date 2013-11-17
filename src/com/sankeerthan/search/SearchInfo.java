package com.sankeerthan.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sankeerthan.Sankeerthan;

public abstract class SearchInfo {
	protected final String URL = Sankeerthan.URL;
	protected HashMap<String, String> dataHash = new HashMap<String,String>();
	public String key = "";
	protected String buffer = "";
	protected String subURL = "";
	protected String responseString = "";
	protected HttpClient client = null;
	public String errorMsg = "";
	public ArrayList<String> serverErrors = new ArrayList<String>();
	boolean isResultNull = true;
  
	protected SearchInfo(String key, String subURL) {
		this.key = key;	  
		this.subURL = subURL;
		client = new DefaultHttpClient();
	}
	
	protected SearchInfo()
	{
		
	}
	
	protected String fetchData()
	{
		Thread t = new Thread(){
			public void run()
			{
				SearchInfo.this.buffer = fetchDataInThread();
			}
		};
		t.start();
		try {
			t.join();
			return buffer;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		finally {
			return buffer;
		}
		}
	
	protected String fetchDataInThread() {
		try {
			HttpGet request = setupClient();
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if(entity == null) {
				serverErrors.add("No valid response from server! Please try again later.#17"); 
			}
		
     		InputStream stream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();

			String line = "";
			try {
				while ((line = reader.readLine()) != null) {	
					sb.append(line);
				}
			} 
						
			catch (IOException e) {  
				serverErrors.add("Server Error encountered! Please try again later due to ioex in reading #18"); return "";
			} 
			
			finally {
				try {
					stream.close();
				} 
			catch (IOException e) {
				serverErrors.add("Server Error encountered! Please try again later #19");
				e.printStackTrace();
				return "";
		     }
			}
			return sb.toString();
		} catch (URISyntaxException e) {
		e.printStackTrace();
		return "";
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			return "";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return "";
		}  
	}
  
	protected void parseData(String result) throws ClientProtocolException, IOException, JSONException {
		try{
		    if(result == null) result = "";
		}
		
		catch (NullPointerException e){
			serverErrors.add("No valid response from server! Please try again later. #20");
		}

		if(!result.isEmpty()) {
			JSONTokener tokener = new JSONTokener(result.toString());
			JSONObject jsonObject = new JSONObject(tokener);
			this.errorMsg = jsonObject.optString("error");
			if(errorMsg.isEmpty())	{
				extractData(jsonObject);
			}
		}
	}
	
    protected void extractData(JSONObject jsonObject)
    {
       	
    }
  
    protected HttpGet setupClient() throws URISyntaxException {
    	HttpParams params = client.getParams();
    	HttpConnectionParams.setConnectionTimeout(params, 5000); 
    	String url = URL + subURL + key + ".json";
    	URI uri = new URI(url.replace(" ", "%20"));
    	HttpGet request = new HttpGet(uri.toString());
    	return request;
    }
  
    protected void formatSubURL(boolean json) {
    	if(json)  
    		subURL = "/" + subURL + ".json";
    }
  
    protected void formatKey() {   	  
    }
    
}
