package kashmiri.calendar.namespace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/kashmiri.calendar.namespace/databases/";
 
    private static String DB_NAME = "KCal";
    
    private static String DB_NAME_REMINDERS = "KReminders";
 
    private SQLiteDatabase myDataBase; 
 // Database Name
    private static final String DATABASE_NAME = "KCal";
 
    // Calendar table name
    private static final String TABLE_CAL = "KCalendar";

    // Calendar table name
    private static final String TABLE_REMINDERS = "KReminders";

    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHandler(Context context) {
 
    	super(context, DB_NAME, null, 3);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
    	//dbExist = false;
   	
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
    	boolean exists = false;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		checkDB.rawQuery("SELECT * from KReminders", null);
    		exists = true;
    	}catch(SQLiteException e){
 
    		exists = false;
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return exists;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
   
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	  public Cursor getMonthInformation(int month)
	    {
	    	
	    	String countQuery; 
	    	if(month ==  0)
	    	{
	    		//2012 March
	    		countQuery = "SELECT * FROM " +  TABLE_CAL + " WHERE Date LIKE '%Mar'  LIMIT 9";
	    		
	    	} else
	    	if(month == 1)
	    	{
	    		//2012 Apr
	    		countQuery = "SELECT * FROM " +  TABLE_CAL + " WHERE Date LIKE '%Apr'  LIMIT 30";
	    	}else
    		if(month ==  12)
	    	{
	    		//2013 March
	    		countQuery = "SELECT * FROM " +  TABLE_CAL + " WHERE Date LIKE '%Mar'  LIMIT 31 OFFSET 9 ";
	    		
	    	} else
	    	if(month == 13)
	    	{
	    		//2014 Apr
	    		countQuery = "SELECT * FROM " +  TABLE_CAL + " WHERE Date LIKE '%Apr'  LIMIT 11 OFFSET 30";
	    	} else
	    	{
	    		String[] Months =  {"MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC","JAN", "FEB", "MAR", "APR"};
		    	String strMonth = Months[month];	
		    	countQuery = "SELECT  * FROM " + TABLE_CAL + " WHERE Date LIKE " + "\'%" + strMonth + "\'";
	    	} 
	    	 
	    
	    	 Cursor cursor1 = this.getReadableDatabase().rawQuery(countQuery, null);
	         return cursor1;
	    	
	    }
	  
	  public Cursor getAllReminders() throws SQLException
	  {
		  String countQuery = "SELECT  * FROM " + TABLE_REMINDERS ;
		  Cursor cur = this.getReadableDatabase().rawQuery(countQuery, null);
		  int count = cur.getCount();
		  return cur;
	  }
	  
	  public Cursor getMatchingReminders(String name, 
			  String fordate, String month) throws SQLException
	  {
		  String countQuery = "SELECT  * FROM KReminders WHERE name='" + name + "' AND date='" + fordate + "' AND current_month='" + month + "'";
		  Cursor cur = this.getReadableDatabase().rawQuery(countQuery, null);
		  int count = cur.getCount();
		  return cur;
  	  }
	  
	  
	  
	  public void InsertReminder(String name, String description, String Occurance, String fordate, String month) throws SQLException
	  {
		  String countQuery = "INSERT into KReminders VALUES(" + name+ "," + description + "," +  Occurance;
		  //Cursor cur = this.getWritableDatabase().rawQuery(countQuery, null);
		  
		  ContentValues Values = new ContentValues(); 
		  Values.put("name", name);
		  Values.put("description", description);
		  Values.put("occurance", Occurance);
		  Values.put("date", fordate);
		  Values.put("current_month", month);
		  long rowId = this.getWritableDatabase().insert(TABLE_REMINDERS, null, Values);
	  }
	  

	  public void DeleteReminder(String name, 
			  String fordate, String month) throws SQLException
	  {
		  String countQuery = "DELETE FROM KReminders WHERE name='" + name + "' AND date='" + fordate + "' AND current_month='" + month + "'";
		  String where = "name = '%s'"
				    + " AND date = '%s'"
				    + " AND current_month = '%s'";
		  String whereClause = String.format(where, name,fordate,month);
				String[] whereArgs = null;	  
		  int cur = this.getWritableDatabase().delete("KReminders", whereClause, whereArgs);
		  cur++;
  	  }
	  
	  public void DeleteAllReminders()
	  {
		  String countQuery = "DELETE FROM KReminders";
		  Cursor cur = this.getWritableDatabase().rawQuery(countQuery, null);
	  }
	  public static void DeleteDatabase(Context context)
	  {
		  context.deleteDatabase(DB_PATH + DB_NAME);
	  }

	public Cursor getAllDays() {
		// TODO Auto-generated method stub
			String countQuery = "SELECT  * FROM " + TABLE_CAL ;
		  Cursor cur = this.getReadableDatabase().rawQuery(countQuery, null);
		  int count = cur.getCount();
		  return cur;
	}
	}
