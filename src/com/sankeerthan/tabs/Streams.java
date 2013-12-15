package com.sankeerthan.tabs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import com.sankeerthan.R;
import com.sankeerthan.R.id;
import com.sankeerthan.R.layout;
import com.sankeerthan.display.expand.*;
import com.sankeerthan.model.FavoriteDB;
import com.sankeerthan.tabs.SearchTab;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SeekBar;


	@SuppressLint("ValidFragment")
	public class Streams extends Fragment implements OnClickListener, OnBufferingUpdateListener, OnPreparedListener {
	  	//private final String url = AppConfig.URL + "/play/song.mp3";	    
	    private Button btn_play;
		private SeekBar seekBar;
		private MediaPlayer mediaPlayer;
		private int currentStream=0;
		private String currentButton="";

	
		public void onCreate(Bundle savedInstanceState) {
			//Bundle details = this.getArguments();
			//setDetails(details);
			super.onCreate(savedInstanceState);
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			//Looper.prepare();
			//Looper.loop();
			//handler = new Handler();
			//Bundle details = this.getArguments();
			//setDetails(details);
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,this.getBhajanDetails());
			//setListAdapter(adapter);
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.streams, container, false);
			Button bhajan_stream = (Button) view.findViewById(R.id.btn_bhajan_stream);
			Button discourse_stream = (Button) view.findViewById(R.id.btn_discourse_stream);
			Button telugu_stream = (Button) view.findViewById(R.id.btn_telugu_stream);
			Button asia_stream = (Button) view.findViewById(R.id.btn_asia_stream);
			Button afri_stream = (Button) view.findViewById(R.id.btn_afri_stream);
			Button ameri_stream = (Button) view.findViewById(R.id.btn_ameri_stream);
			
			bhajan_stream.setOnClickListener(this);
			discourse_stream.setOnClickListener(this);
			telugu_stream.setOnClickListener(this);
			asia_stream.setOnClickListener(this);
			afri_stream.setOnClickListener(this);
			ameri_stream.setOnClickListener(this);
			
			return view;
			
		}
				
		public void onClick(View view) {
			Button streamButton;
	    	streamButton = (Button)view;

	    	if(mediaPlayer!=null && 
	    		((streamButton.getText().toString().contains(currentButton) || view.getId() == currentStream)) 
	    		) {	
			        if(mediaPlayer.isPlaying() ||
			        		streamButton.getText().toString().matches("Playing.*")){
				        streamButton.setText(currentButton);
				        pauseAudio();
			        }
		 	    else {
				    playAudio();
			        streamButton.setText("Playing " + currentButton);
			    } }
	    	 else { 
	    	     if(mediaPlayer == null) {
	    	     mediaPlayer = new MediaPlayer();
	    	     mediaPlayer.setOnPreparedListener(this);
	    	     //mediaPlayer.setOnBufferingUpdateListener(this);
	       	     }
	    	     else { 
	    	    	 mediaPlayer.stop();
	    	    	 mediaPlayer.reset();
	    	     }
	    	     String url = "";
	    	     switch (view.getId()) { 
	    	     case R.id.btn_afri_stream:  url = "http://stream.radiosai.net:8004/"; break;
	    	     case R.id.btn_asia_stream:  url = "http://stream.radiosai.net:8002/"; break; 
	    	     case R.id.btn_ameri_stream: url = "http://stream.radiosai.net:8006/"; break; 
	    	     case R.id.btn_bhajan_stream:url = "http://stream.radiosai.net:8000/"; break; 
	    	     case R.id.btn_telugu_stream:url = "http://stream.radiosai.net:8020/"; break;
	    	     case R.id.btn_discourse_stream:	url = "http://stream.radiosai.net:8008/"; break;
	    	     }
	    	     resetOtherButtons();
	    	     currentStream = view.getId();
	    	     currentButton = streamButton.getText().toString();
	    	     try {
					mediaPlayer.setDataSource(url);
					String tmp = streamButton.getText().toString();
					streamButton.setText("Fetching " + tmp);
					mediaPlayer.prepare();
					streamButton.setText("Playing " + tmp);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}}
		
		public void onDestroy() {
			mediaPlayer.stop();	
			super.onDestroy();
		}
		
		public void onDetach()
		{
			super.onDetach();		
		}

		private void pauseAudio() {
			mediaPlayer.pause();
		}

		private void playAudio() {
			mediaPlayer.start();
		}
		
		private void resetOtherButtons()
		{
			if(currentStream == 0) return;
			else {	 
			     View view = getView();
		         Button button = (Button) view.findViewById(currentStream);
		         button.setText(currentButton);
			}
        }

		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			if(percent == 50)
		  	    playAudio();
		}

		public void onPrepared(MediaPlayer arg0) {
            playAudio();
		}
	}
