package test.db.namespace;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class TestDbActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        myDbHelper = new DataBaseHelper(this);
 
        try {
 
        	myDbHelper.createDataBase();
 
 	} catch (IOException ioe) {
 
 		throw new Error("Unable to create database");
 
 	}
      
 	try {
 
 		myDbHelper.openDataBase();
 		  String dbname = myDbHelper.getDatabaseName();
 	        SQLiteDatabase st = myDbHelper.getReadableDatabase();
 	        String table_query = "SELECT * FROM KCalendar";
 	        Cursor cursor = st.rawQuery(table_query, null);
 	        if(cursor.moveToFirst())
 	        {
 	        	String str = cursor.getString(0);
 	        	String str1 = cursor.getString(1);
 	        	String str2 = cursor.getString(2);
 	        	String str3 = cursor.getString(3);
 	        }
 	}catch(SQLException sqle){
 
 		throw sqle;
 
 	}
        setContentView(R.layout.main);
    }
}