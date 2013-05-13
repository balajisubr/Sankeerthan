package com.bhajans;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

@TargetApi(11)
public class OPlayerFragment extends Fragment implements  OnTouchListener, OnCompletionListener, OnClickListener, OnBufferingUpdateListener{

    private Button btn_play;
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private int lengthOfAudio = 0;
	private int length=0;
	//private final String url = AppConfig.URL + "/play/song.mp3";
	private final String url = "http://dl.radiosai.org/BV_U003_V001_04_SHALINEE_SAI_HEY_ANATHA_NATHA.mp3";
    private final Handler handler = new Handler();
	private final Runnable r = new Runnable() {	
		public void run() {
			updateSeekProgress();					
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.player_fragment, container, false);
		btn_play = (Button) view.findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
	    seekBar = (SeekBar)view.findViewById(R.id.seekBar);
		seekBar.setOnTouchListener(this);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
	
		return view;
	}
	
	public void init() {
	    //btn_play = (Button)getActivity().findViewById(R.id.btn_play);
	
		//btn_pause = (Button)findViewById(R.id.btn_pause);
		//btn_pause.setOnClickListener(this);
		//btn_pause.setEnabled(false);
		//btn_stop = (Button)findViewById(R.id.btn_stop);
		//btn_stop.setOnClickListener(this);
		//btn_stop.setEnabled(false);
		
				
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


	}
