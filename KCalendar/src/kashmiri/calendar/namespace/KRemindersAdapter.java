package kashmiri.calendar.namespace;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class KRemindersAdapter extends CursorAdapter{

	
	  public static final String KEY_REM_NAME = "name";
	  public static final String KEY_REM_DESC = "description";
	  public static final String KEY_OCCURANCE = "occurance";
	  
	public KRemindersAdapter(Context context, Cursor c) {
		super(context, c);
	}
 
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView name = (TextView)view.findViewById(R.id.reminder_name);
		name.setText(cursor.getString(
				cursor.getColumnIndex(KEY_REM_NAME)));
		TextView desc = (TextView)view.findViewById(R.id.reminder_description);
		desc.setText(cursor.getString(
				cursor.getColumnIndex(KEY_REM_DESC)));
		TextView occ = (TextView)view.findViewById(R.id.reminder_occurance);
		occ.setText(cursor.getString(
				cursor.getColumnIndex(KEY_OCCURANCE)));
	}
 
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.reminder_list, parent, false);
		return v;
	}
	
}
