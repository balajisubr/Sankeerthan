package com.sankeerthan.tabs;

import java.util.regex.Pattern;


import com.sankeerthan.R;
import com.sankeerthan.display.SankeerthanDialog;
import com.sankeerthan.email.GmailSender;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackTab extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.feedback, container, false);
		EditText feedback = (EditText) view.findViewById(R.id.feedback);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view){
				View view1 = getView();
				InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

				EditText name = (EditText) view1.findViewById(R.id.name);
				EditText email = (EditText) view1.findViewById(R.id.email);
				EditText feedback = (EditText) view1.findViewById(R.id.feedback);
				EditText location = (EditText) view1.findViewById(R.id.country);
				
				mgr.hideSoftInputFromWindow(name.getWindowToken(), 0);
				mgr.hideSoftInputFromWindow(email.getWindowToken(), 0);
				mgr.hideSoftInputFromWindow(feedback.getWindowToken(), 0);
				mgr.hideSoftInputFromWindow(location.getWindowToken(), 0);

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
				
				String receiver = "sankeerthan.bhajan@gmail.com";
				String subject  = "Sankeerthan Feedback from " + nameString + " " + emailString + " Location: " + locationString;
				String body     = feedbackString;

                try {
                	new SendMail().execute(new String[]{subject, body, emailString, receiver});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
				
			}
		});
		return view;
	}
	
    class SendMail extends AsyncTask<String, Void, Void> {
        ProgressDialog pd = null;
        String param = "";
        
        public void onPreExecute() {
         	getActivity().runOnUiThread(new Runnable() {
                public void run() {
	    				pd = new ProgressDialog(FeedbackTab.this.getActivity());
	    				pd.setTitle("Sending Email...");
	    				pd.setMessage("Please wait.");
	    				pd.setCancelable(false);
	    				pd.setIndeterminate(true);
	    				pd.show();
	    	        }});
         	}

     
 		protected Void doInBackground(String... params) {
			GmailSender sender = new GmailSender("sankeerthan.bhajan@gmail.com", "satsivsun11");
 		    try {
				sender.sendMail(params[0], params[1], params[2], params[3]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			
			return null;

 		}
 		
 		public void onPostExecute(Void a) {
 			getActivity().runOnUiThread(new Runnable(){
 				public void run(){
	    				if(pd != null) 
                             pd.dismiss();
	    				SankeerthanDialog.getAlertDialog(FeedbackTab.this.getActivity(), "Thanks for your valuable feedback!").show();
	    				View view1 = getView();
	    				EditText name = (EditText) view1.findViewById(R.id.name); name.setText("");
	    				EditText email = (EditText) view1.findViewById(R.id.email); email.setText("");
	    				EditText feedback = (EditText) view1.findViewById(R.id.feedback); feedback.setText("");
	    				EditText location = (EditText) view1.findViewById(R.id.country); location.setText("");
 				}
 			});
 		}     	
     }

}
