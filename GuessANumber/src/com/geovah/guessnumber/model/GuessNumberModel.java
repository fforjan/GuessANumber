package com.geovah.guessnumber.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * @author GeoVah
 * Database adapter for the guess number game
 */
public class GuessNumberModel {

	/**
	 * @author GeoVah
	 * Defines our database column by their keys
	 */
	/**
	 * @author GeoVah
	 *
	 */
	public static final class Keys 
	{
		
		/**
		 * Id column
		 */
		public static final String ID = "_id";
		/**
		 * Elapsed time column
		 */
		public static final String ElapsedTime ="ElapsedTime";
		
		
		/**
		 * Guess count column
		 */
		public static final String GuessCount ="GuessCount"; 
	
	}


	    private static final String TAG = "GuessNumberDbAdapter";
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;

	    /**
	     * Database creation sql statement
	     */
	    private static final String DATABASE_CREATE =
	        "create table GuessGame (_id integer primary key autoincrement, "
	        + "ElapsedTime integer, GuessCount integer);";

	    private static final String DATABASE_NAME = "data";
	    private static final String DATABASE_TABLE = "GuessGame";
	    private static final int DATABASE_VERSION = 2;

	    private final Context mCtx;

	    private static class DatabaseHelper extends SQLiteOpenHelper {

	        DatabaseHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {

	            db.execSQL(DATABASE_CREATE);
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");

	            db.execSQL(String.format("DROP TABLE IF EXISTS %0$s",DATABASE_TABLE));
	            onCreate(db);
	        }
	    }

	    /**
	     * Constructor - takes the context to allow the database to be
	     * opened/created
	     * 
	     * @param ctx the Context within which to work
	     */
	    public GuessNumberModel(Context ctx) {
	        this.mCtx = ctx;
	    }

	    /**
	     * Open the notes database. If it cannot be opened, try to create a new
	     * instance of the database. If it cannot be created, throw an exception to
	     * signal the failure
	     * 
	     * @return this (self reference, allowing this to be chained in an
	     *         initialization call)
	     * @throws SQLException if the database could be neither opened or created
	     */
	    public GuessNumberModel open() throws SQLException {
	        mDbHelper = new DatabaseHelper(mCtx);
	        mDb = mDbHelper.getWritableDatabase();
	        return this;
	    }

	    /**
	     * Close the database connection
	     */
	    public void close() {
	        mDbHelper.close();
	    }


	    
	    /**
	     * Store the result into the database
	     * @param elapsedTime elapsed time for the game
	     * @param guessCount guess count for the game
	     * @return index of the new row
	     */
	    public long storeResult(int elapsedTime,int guessCount) {
	        ContentValues initialValues = new ContentValues();
	        
	        
	        initialValues.put(Keys.ElapsedTime, elapsedTime);
	        initialValues.put(Keys.GuessCount, guessCount);

	        return mDb.insert(DATABASE_TABLE, null, initialValues);
	    }

	
	    /**
	     * Delete a result row
	     * @param rowId
	     * @return true if there is no error - row deleted
	     */
	    public boolean deleteResult(long rowId) {

	        return mDb.delete(DATABASE_TABLE, Keys.ID + "=" + rowId, null) > 0;
	    }

	    /**
	     * Return a Cursor over the list of all results in the database
	     * 
	     * @return Cursor over all results
	     */
	    public Cursor fetchAllResult() {

	        return mDb.query(DATABASE_TABLE, new String[] {Keys.ID, Keys.ElapsedTime,
	                Keys.GuessCount}, null, null, null, null, null);
	    }

	    /**
	     * Return a Cursor positioned at the result that matches the given rowId
	     * 
	     * @param rowId id of note to retrieve
	     * @return Cursor positioned to matching note, if found
	     * @throws SQLException if note could not be found/retrieved
	     */
	    public Cursor fetchResult(long rowId) throws SQLException {

	        Cursor mCursor =

	            mDb.query(true, DATABASE_TABLE, new String[] {Keys.ID,
	                    Keys.ElapsedTime, Keys.GuessCount}, Keys.ID + "=" + rowId, null,
	                    null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;

	    }
	    
	    /**
	     * @author GeoVah
	     * our result data
	     */
	    public class Result
	    {
	    	/**
	    	 * Default constructor
	    	 */
	    	public Result() {
	    	min = Integer.MAX_VALUE;
	    	max = Integer.MIN_VALUE;
	    	average = 0;
	    	
	    	}
	    	/**
	    	 * minimum guess 
	    	 */
	    	public int min;
	    	/**
	    	 * maximum guess
	    	 */
	    	public int max;
	    	/**
	    	 * average guess
	    	 */
	    	public double average;
	    }

	    /**
	     * calculate the result information from data
	     * @return
	     * @throws SQLException
	     */
	    public Result calculateResult() throws SQLException {
	    	Result result = new Result();
	    	Cursor mCursor =

		            mDb.  query(true, DATABASE_TABLE, new String[] {"min(GuessCount)",
		            		"max(GuessCount)", "avg(GuessCount)"}, null, null,
		                    null, null, null, null);
		        if (mCursor != null) {
		            mCursor.moveToFirst();
		            result.min = mCursor.getInt(0);
		            result.max = mCursor.getInt(1);
		            result.average = mCursor.getDouble(2);
		        }
	    	return result;
	    }
	   
}
