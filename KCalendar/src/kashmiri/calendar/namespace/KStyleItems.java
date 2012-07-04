package kashmiri.calendar.namespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class KStyleItems {

	public static View GetNormalTextView(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container,null);
		
		View text_normal = parent.findViewById(R.id.normal_text);
		parent.removeAllViews();
		return text_normal;
	}
	
	public static View GetReminderButton(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container_controls,null);
		
		View text_normal = parent.findViewById(R.id.button_normal);
		parent.removeAllViews();
		return text_normal;
	}
	
	
	public static View GetSmallTextView(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container_controls,null);
		
		View text_normal = parent.findViewById(R.id.text_view_small);
		parent.removeAllViews();
		return text_normal;
	}
	public static View GetReminderTableTextView(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container_controls,null);
		
		View text_normal = parent.findViewById(R.id.reminder_text_name);
		parent.removeAllViews();
		return text_normal;
	}
	
	public static View GetCellLayout(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container,null);
		
		View cell = parent.findViewById(R.id.cell_layout);
		ScrollView par = (ScrollView)cell.getParent();
		par.removeView(cell);
		return cell;
	}
	
	public static View GetTableReminderRow(Context con)
	{
		LayoutInflater inflater = (LayoutInflater)con.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		@SuppressWarnings("deprecation")
		AbsoluteLayout parent = (AbsoluteLayout)inflater.inflate(R.layout.style_container,null);
		
		View cell = parent.findViewById(R.id.table_row_normal);
		parent.removeAllViews();
		return cell;
	}

	public static View GetTableReminder(Context applicationContext) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)applicationContext.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.reminder_table, null);
		return v;
	}
	

	public static View GetReminderTableRow(Context applicationContext) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)applicationContext.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.reminder_table, null);
		return v.findViewById(R.id.reminder_row);
	}
	
	
	
	
}
