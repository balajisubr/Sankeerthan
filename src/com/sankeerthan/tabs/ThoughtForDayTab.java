package com.sankeerthan.tabs;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sankeerthan.search.SearchInfo;
import com.sankeerthan.search.SearchWebUrls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThoughtForDayTab extends CustomWebView {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	String url = "http://radiosai.org";
    	try {
			SearchWebUrls searchThought = new SearchWebUrls("/radiosai_thought","");
			searchThought.getData();
			url = searchThought.resultURL;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(url == null || url.isEmpty()) url = "http://radiosai.org"; 
        setWebViewURL(url);
        return super.onCreateView(inflater, container, savedInstanceState);
        }

    }