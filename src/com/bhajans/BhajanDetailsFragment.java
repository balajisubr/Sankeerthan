package com.bhajans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.bhajans.lookup.LookUpData;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

@SuppressLint("ValidFragment")
public class BhajanDetailsFragment extends ListFragment {
	
	private ArrayList<String> bhajanDetails;
	public BhajanDetailsFragment()
	{}
	
	public BhajanDetailsFragment(ArrayList<String> bhajanDetails)
	{
	  this.bhajanDetails = bhajanDetails;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajanDetails);
		setListAdapter(adapter);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.list_bhajans, container, false);	   		
		return view;
	}


}
