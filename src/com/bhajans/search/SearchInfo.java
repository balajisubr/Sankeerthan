package com.bhajans.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.bhajans.AppConfig;
import com.bhajans.model.Bhajan;

public abstract class SearchInfo {
  protected final String URL = AppConfig.URL;
  protected HashMap<String, String> dataHash = new HashMap<String,String>();
  public String key = "";
  protected String subURL = "";
  protected String responseString = "";
  protected HttpClient client = null;
  public String errorMsg = "";
  public ArrayList<String> serverErrors = new ArrayList<String>();
  boolean isResultNull = true;
  
  protected SearchInfo(String key, String subURL)
  {
    this.key = key;	  
    this.subURL = subURL;
    client = new DefaultHttpClient();
  }
  protected String fetchData() throws ClientProtocolException, IOException, ConnectException
  {
	try {
		HttpGet request = setupClient();
		HttpResponse response = client.execute(request);
		  
		HttpEntity entity = response.getEntity();
		if(entity==null) 
		  {
			System.out.println("entity is null");
			serverErrors.add("No response from server! Please try again later."); 
			//return false; }
		  }
		  InputStream stream = entity.getContent();
		  BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		  StringBuilder sb = new StringBuilder();

		  String line = null;
		  try {
		      while ((line = reader.readLine()) != null) {
		          sb.append(line);
		      }
		  } 
		  catch (IOException e) {  
			  serverErrors.add("Server Error encountered! Please try again later due to ioex in reading"); return "";
		  } 
		  finally {
		    try {
		        stream.close();
		      } 
		    catch (IOException e) {
		    	  serverErrors.add("Server Error encountered! Please try again later");
		          e.printStackTrace();
		          return "";
		      }
		  }
		 return sb.toString();
	} catch (URISyntaxException e) {
		e.printStackTrace();
		return "";
	}  
  }
  
  protected void parseData(String result) throws ClientProtocolException, IOException, JSONException
  {
    if(result==null) result = "";
    if(result!="")
    {
	JSONTokener tokener = new JSONTokener(result.toString());
    JSONObject jsonObject = new JSONObject(tokener);
    this.errorMsg = jsonObject.optString("error");
    System.out.println("error  is" + errorMsg);
    if(errorMsg == "")
	  {
	   extractData(jsonObject);
      }
    }
  }
    protected void extractData(JSONObject jsonObject)
    {
       	
    }
  
  protected HttpGet setupClient() throws URISyntaxException
  {
   HttpParams params = client.getParams();
   HttpConnectionParams.setConnectionTimeout(params, 20000); 
   String url = URL + subURL + key + ".json";
   System.out.println("The URL is" + url);
   URI uri = new URI(url.replace(" ", "%20"));
   HttpGet request = new HttpGet(uri.toString());
   return request;
  }
  
  protected void formatSubURL(boolean json)
  {
	if(json)  
	subURL = "/" + subURL + ".json";
  }
  
  protected void formatKey()
  {
   	  
  }
  
  
}
