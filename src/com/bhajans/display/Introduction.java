package com.bhajans.display;

import com.bhajans.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Introduction extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.intro, container, false);	
		return view;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
  
}
