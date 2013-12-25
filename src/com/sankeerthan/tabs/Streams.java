package com.sankeerthan.tabs;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sankeerthan.R;
import com.sankeerthan.search.display.BhajanDetailsFragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


	public class Streams extends Fragment implements OnClickListener, OnBufferingUpdateListener, OnPreparedListener {
		private MediaPlayer mediaPlayer;
		private int currentStream=0;
		private String currentButton="";
		private HashMap<Integer, Boolean> activeStreamMap = new HashMap<Integer, Boolean>();
		Button streamButton;


		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
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
			Iterator<Entry<Integer, Boolean>> it = activeStreamMap.entrySet().iterator();
			while(it.hasNext())
			{
				 Entry<Integer, Boolean> entry =(Entry<Integer, Boolean>) it.next();
				 if(entry.getValue() == true){
					 Button b = (Button) view.findViewById(entry.getKey());
                     b.setText("Playing " + b.getText().toString());
				 }
				
			}
			return view;
			
		}
				
		public void onClick(View view) {
	    	streamButton = (Button)view;

	    	if(mediaPlayer!=null && 
	    		((streamButton.getText().toString().contains(currentButton) || view.getId() == currentStream)) 
	    		) {	
			        if(mediaPlayer.isPlaying() ||
			        		streamButton.getText().toString().matches("Playing.*")){
				        streamButton.setText(currentButton);
				        activeStreamMap.put(currentStream, false);
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
	    	     activeStreamMap.put(currentStream, true);
	    	     currentButton = streamButton.getText().toString();
	    	     try {
	    	     new PreparePlayer().execute(url); 	 
				}catch (IllegalArgumentException e) {
				 	e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
	    	}}
		
		public void onDestroy() {
			if(mediaPlayer !=null) mediaPlayer.stop();	
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
		         activeStreamMap.put(currentStream, false);
		         button.setText(currentButton);
			}
        }

		
		 class PreparePlayer extends AsyncTask<String, Void, Void> {
		        ProgressDialog pd = null;
		        String param = "";
		        
		        public void onPreExecute() {
			    			pd = new ProgressDialog(Streams.this.getActivity());
			    			pd.setTitle("Loading Audio..");
			    			pd.setMessage("Please wait.");
			    			pd.setCancelable(false);
			    			pd.setIndeterminate(true);
			    			pd.show();
		        }

		     
		 		protected Void doInBackground(String... urls) {
					try {
						String url = urls[0];
	                    
   	    	    	    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); 
						mediaPlayer.setDataSource(url);
						mediaPlayer.prepare();
				        playAudio();
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                return null;

		 		}
		 		
		 		public void onPostExecute(Void a) {
			    		if(pd != null) 
		                    pd.dismiss();
			    			streamButton.setText("Playing " + streamButton.getText().toString());
		 				}
		     }
	}
