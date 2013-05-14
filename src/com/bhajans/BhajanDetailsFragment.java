package com.bhajans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.bhajans.lookup.LookUpData;
import com.bhajans.model.Bhajan;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

@SuppressLint("ValidFragment")
public class BhajanDetailsFragment extends ListFragment implements  OnTouchListener, OnCompletionListener, OnClickListener, OnBufferingUpdateListener {
	
	private ArrayList<String> bhajanDetails = new ArrayList<String>();
	private Bundle bundle;
    private Button btn_play;
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private int lengthOfAudio = 0;
	private int length=0;
	private final String url = AppConfig.URL + "/play/song.mp3";
	//private final String url = "http://dl.radiosai.org/BV_U003_V001_04_SHALINEE_SAI_HEY_ANATHA_NATHA.mp3";
    private final Handler handler = new Handler();
    private final String[] keys = new String[]{"raaga","lyrics","meaning", "deity"};
	private final Runnable r = new Runnable() {	
		public void run() {
			updateSeekProgress();					
		}
	};

	
	public BhajanDetailsFragment(Bundle bundle)
	{
this.bundle = bundle;	
System.out.println("The raaga in the bundle in the fragment is" + bundle.getString("raaga"));
System.out.println("The lyrics in the bundle in the fragment is" + bundle.getString("lyrics"));
System.out.println("The meaning in the bundle in the fragment is" + bundle.getString("meaning"));
System.out.println("The deity in the bundle in the fragment is" + bundle.getString("deity"));

for(String s: keys)
{
	 System.out.println("The value of s1 is"  + s);
 String value = bundle.getString(s);
 System.out.println("The value is " + value);
 if(value == null || value.isEmpty())
         value= "No data for" + s;
 System.out.println("The value of value1 is"  + value);
 System.out.println("The value of s2 is"  + s);
 
 bhajanDetails.add(value);
}
setBhajanDetails(bhajanDetails);
System.out.println("The raaga in the bundle in the fragment is" + bundle.getString("raaga"));
System.out.println("The lyrics in the bundle in the fragment is" + bundle.getString("lyrics"));
System.out.println("The meaning in the bundle in the fragment is" + bundle.getString("meaning"));
System.out.println("The deity in the bundle in the fragment is" + bundle.getString("deity"));

	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajanDetails);
		setListAdapter(adapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.list_bhajans, container, false);
		btn_play = (Button) view.findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
	    seekBar = (SeekBar)view.findViewById(R.id.seekBar);
		seekBar.setOnTouchListener(this);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
       
		return view;
	}
	
	
	public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
		seekBar.setSecondaryProgress(percent);
	}

	public void onCompletion(MediaPlayer mp) {
		btn_play.setText("play");
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (mediaPlayer.isPlaying()) {
			SeekBar tmpSeekBar = (SeekBar) v;
			mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress() );
			System.out.println("lengthofAudio is" + lengthOfAudio);
		}
		return false;
	}


	private void updateSeekProgress() {
		 {
				if (mediaPlayer.isPlaying()) {

			seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));
			handler.postDelayed(r, 2000);
				}
		}
	}
	
	public void onClick(View view) {

		try {
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepare();
			lengthOfAudio = mediaPlayer.getDuration();
		} catch (Exception e) {
			//Log.e("Error", e.getMessage());
		}
		seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));
		handler.postDelayed(r, 1000);
		SeekBar tmpSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
		switch (view.getId()) {
		case R.id.btn_play:
			if(mediaPlayer.isPlaying()){
				btn_play.setText("play");
				pauseAudio();
				 length=mediaPlayer.getCurrentPosition();
			}
			else
			{
				if(mediaPlayer!=null)
				{
				System.out.println("Mediplayer is not null");	
				if(tmpSeekBar==null)
				{
					tmpSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
					System.out.println("tmpseekbar is null");	
				}
				//mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress() );
				playAudio();
				btn_play.setText("pause");
				}
				else
				{
				 System.out.println("Here as mediaPlayer is null");	
				}
			}
						break;
		default:
			break;
		}
		
		updateSeekProgress();
	}
	
	public void onDestroy()
	{
	 mediaPlayer.stop();	
	 super.onDestroy();
	}

	private void pauseAudio() {
		mediaPlayer.pause();
	}

	private void playAudio() {
		mediaPlayer.start();
	}
	
    public ArrayList<String> getBhajanDetails()
    {
      if(bhajanDetails.size() == 0)
      {
            int i;
            for(i=0;i<4;i++)
              bhajanDetails.add("No Data found");
      }
      return bhajanDetails;
    }

    public void setBhajanDetails(ArrayList<String> string)
    {
     this.bhajanDetails = string ;
    }

}
