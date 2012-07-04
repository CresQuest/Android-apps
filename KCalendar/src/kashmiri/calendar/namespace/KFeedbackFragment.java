package kashmiri.calendar.namespace;

import java.util.Vector;

import kashmiri.calendar.namespace.data.KReminderData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class KFeedbackFragment extends DialogFragment{
    static KRemindersFragment newInstance() {
        return new KRemindersFragment();
    }
    
    EditText user_name;
    String mail_message;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	view =  inflater.inflate(R.layout.tab_feedback, null);
    	Button send_feedback = (Button)view.findViewById(R.id.send_feedback);
    	send_feedback.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
		    	EditText mail = (EditText)view.findViewById(R.id.edit_feedback);
		    	user_name = (EditText) view.findViewById(R.id.edit_user_name);
		    	RatingBar rating = (RatingBar)view.findViewById(R.id.rating_value);
		    	mail_message = "Rating: " + rating.getRating() + " " + mail.getText().toString();
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"CresQuest@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Kashmiri Calendar Feedback by" + user_name.getText().toString());
				i.putExtra(Intent.EXTRA_TEXT   , mail_message);
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
    	return view;
    	
	    
    }
    
 


}
