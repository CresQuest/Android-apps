package kashmiri.calendar.namespace;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class KReminderActivity extends Activity  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.reminder_go, null);
        
        
        
        TextView viewReminder  = (TextView) v.findViewById(R.id.reminder_text);
        String rem = getIntent().getExtras().getString("REMINDER_NAME");
        viewReminder.setText(rem);
       
        setContentView(v);
        
    }


}