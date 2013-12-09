package com.sankeerthan.tabs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sankeerthan.R;
import com.sankeerthan.display.expand.Child;
import com.sankeerthan.display.expand.ExpandListAdapter;
import com.sankeerthan.display.expand.Group;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class FAQFeedback extends Fragment{
	
    LinkedHashMap<String, String> QnA = new LinkedHashMap<String, String>();
    
    public FAQFeedback(){
    }
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.faq, container, false);
	    
    	QnA.put("►Is Sankeerthan the app of Radiosai?", "No, Sankeerthan is not related to Radiosai in any way.");
    	QnA.put("►What is raaga? ","Raagas are standard Indian melodic modes. Unless mentioned, all raagas in Sankeerthan refer to Carnatic raagas. Hindustani Raagas are indicated by (H)");
    	QnA.put("►I am only able to see the names of Bhajans, Raagas and Deities but when I select any of those and search, I get an error. What’s wrong? ","To be able to list all the details of a Bhajan or to list all Bhajans by Raaga or Deity, you must have access to Internet or cellular data. ");
    	QnA.put("►Can I view list of Bhajans, Raagas or Deities available when offline? ","You need an Internet connection the first time and must be on the search page to initially populate the list of Bhajans, Raagas and Deities. After that, that data is stored on your phone memory and you can view the lists anytime even when there is no Internet or cellular data. We also try to update the list every 24 hours. To receive each update, you must connect to the Internet again and restart the app.");
        QnA.put("►How can I listen to my favorite Bhajans?", "You can view the list of favorite Bhajans whenever you want(even when there is no Internet or cellular data). However ,to view the details, you must click on the Bhajans and must have access to Internet which will fetch Bhajan Data.");    			
    	QnA.put("►Can I download the Bhajans for offline use? ","Sorry. Right now you can’t download Bhajans.");
    	QnA.put("►How does the Bhajan Stream work? ","As you might have seen, the Bhajan Stream is from Radiosai. To listen, you must be connected to Internet or cellular data. Once the page loads and streaming starts, you can do other things on Sankeerthan or your phone.");
    	QnA.put("►Where are the Bhajans and data collected from? ","We make every effort to bring every possible Bhajan we can to you with details like meaning, raaga, deity etc. Some Bhajans are from Radiosai. Some are from the collections of creators of Sankeerthan. Raaga information are from the creators of Sankeerthan and Radiosai.");
    	QnA.put("►I have a correction to suggest in the lyrics, meaning, Raaga or deity of a Bhajan. What should I do ? ","Please mail Sankeerthan.Bhajan@gmail.com . We will consider your request.");
    	QnA.put("►I would like to contribute a Bhajan to Sankeerthan. What should I do ? ","If you have any info and would like to contribute, please email us all details including the mp3 file at Sankeerthan.Bhajan@gmail.com. We will try our best to honor your request.");
    	QnA.put("►Is there a Facebook page for Sankeerthan?", "Yes! We have a page at facebook.com/SankeerthanApp ! Please add it to your favorites to be informed of latest updates");

	    
        ExpandableListView expandList = (ExpandableListView) view.findViewById(R.id.ExpList);
        ArrayList<Group> expandListItems = setFAQ(QnA);
        ExpandListAdapter expandListAdapter = new ExpandListAdapter(this.getActivity(), expandListItems);
        expandList.setAdapter(expandListAdapter);
        return view;
		}
	

	public ArrayList<Group> setFAQ(LinkedHashMap<String, String> details) {
	    ArrayList<Group> questions = new ArrayList<Group>();
	    ArrayList<Child> answers;
	    Group question;
	    Child answer;
        Iterator<Entry<String, String>> i = details.entrySet().iterator();
        while(i.hasNext()) {
        	question = new Group();
        	answer = new Child();
        	Entry<String, String> qna = (Entry<String, String>) i.next();
        	question.setName(qna.getKey());
        	answer.setName(qna.getValue());
        	answer.setTag(null);
        	answers = new ArrayList<Child>();
        	answers.add(answer);
        	question.setItems(answers);
            questions.add(question);        	
        }
        return questions;
	}

}
