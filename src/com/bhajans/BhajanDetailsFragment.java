package com.bhajans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.bhajans.display.FavoriteDB;
import com.bhajans.lookup.LookUpData;
import com.bhajans.model.Bhajan;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
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
	public String choice = "";
	final String FAV = "Favorite";
	final String UNFAV = "Unfavorite";
	private ArrayList<String> bhajanDetails = new ArrayList<String>();
	private Bundle bundle;
    private Button btn_play;
    private Button btn_fav;
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private int lengthOfAudio = 0;
	private int length=0;
	private String bhajanName = "";
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

for(String s: keys)
{
	 System.out.println("The value of key s is"  + s);
 String value = bundle.getString(s);
System.out.println("The value is " + value);
 if(value == null || value.isEmpty())
         value= "No data for" + s;
 System.out.println("The value of value1 is"  + value);
 
 bhajanDetails.add(value);
}
setBhajanDetails(bhajanDetails);
this.setBhajanName(bundle.getString("bname"));
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.row,bhajanDetails);
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
	    btn_fav = (Button) view.findViewById(R.id.btn_fav);
	    this.setFavText(btn_fav.getText().toString());
	
	    btn_fav.setOnClickListener(new OnClickListener()
	   	{
	     public void onClick(View v)
	     {
			onClick1();
		}});
   
		return view;
	}
	
	
	public void onClick1() {
		Button favButton = (Button) (getView().findViewById(R.id.btn_fav));
		String buttonText = favButton.getText().toString();
        FavoriteDB.setContext(this.getActivity());
        System.out.println("the bhajan in fragment is " + buttonText);
        System.out.println("The button text" + favButton.getText().toString());
        if(buttonText.equals(FAV))
        {
          FavoriteDB.addBhajan(this.getBhajanName());
          favButton.setText(UNFAV);
        }
        else
        {
          FavoriteDB.removeBhajan(this.getBhajanName());
  		favButton.setText(FAV);
        }
        System.out.println("in the onclick1, the bhajan name is" + this.getBhajanName());
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
	
	
	
	public void onClick2(View view) {
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
    
    public String getBhajanName()
    {
     return bhajanName;	
    }
    
    public void setBhajanName(String bhajanName)
    {
      this.bhajanName = bhajanName;
    }
	
	public void setFavText(String s)
	{
	 this.choice = s;
	}
	
	public String getFavText()
	{
	  if(choice == FAV)
		  return FAV;
	  else 
		  return UNFAV;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
    
  
    
    
    

}
