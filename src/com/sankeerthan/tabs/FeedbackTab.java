package com.sankeerthan.tabs;

import java.util.regex.Pattern;

import com.sankeerthan.R;
import com.sankeerthan.display.SankeerthanDialog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackTab extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.feedback, container, false);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view){
				View view1 = getView();
				EditText name = (EditText) view1.findViewById(R.id.name);
				EditText email = (EditText) view1.findViewById(R.id.email);
				EditText feedback = (EditText) view1.findViewById(R.id.feedback);
				EditText location = (EditText) view1.findViewById(R.id.country);
				
				String nameString = name.getText().toString();
				String emailString = email.getText().toString();
				String feedbackString = feedback.getText().toString();
				String locationString = location.getText().toString();

				if(locationString == null || locationString.isEmpty()) 
					locationString = "No Country";
				if(nameString.isEmpty() || 
				   emailString.isEmpty() ||
				   feedbackString.isEmpty())
				{
					SankeerthanDialog.getAlertDialog(FeedbackTab.this.getActivity(), "Please complete required fields!").show();
				    return;
				}
				String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				Pattern pattern = Pattern.compile(PATTERN);
				if(!pattern.matcher(emailString).matches())
				{
					SankeerthanDialog.getAlertDialog(FeedbackTab.this.getActivity(), "Please enter valid email!").show();
				    return;
				}
				
				if(!nameString.matches("([a-zA-Z\\s-]+)"))
				{
					SankeerthanDialog.getAlertDialog(FeedbackTab.this.getActivity(), "Please enter a valid name").show();
				    return;
				}
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);

				String receiver = "sankeerthan.bhajan@gmail.com";
				String subject  = "Sankeerthan Feedback from " + nameString + " " + email + "Location: " + location;
				String body     = feedbackString;

				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, receiver);
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
				emailIntent.setType("text/plain");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
				startActivity(emailIntent);
				
				Toast.makeText(getActivity(), "Feedback successfully sent!", Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}
	
	

}
