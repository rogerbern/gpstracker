package ch.ffhs.esa.arm.gpstracker.sqlitedb;
 
import java.sql.Date;
import java.util.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 
 * @author Marc
 * 
 * DbHelp Class 
 * Creates/Migrates the Database
 */

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "TrackinLists";
	private static final int DB_VERSION = 2;
	
	private static DbHelper sINSTANCE;
	private static Object sLOCK = "";

	public static DbHelper getInstance(Context context) {
		if (sINSTANCE == null) {
			synchronized(sLOCK) {
				if (sINSTANCE == null) {
					sINSTANCE = new DbHelper(context);
				}
			}
		}
		return sINSTANCE;
	}
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// erzeugt das DB-Schema
		// hier koennten bereits initiale daten abgefuellt werden
		db.execSQL(TrackingColumns.SQL_DB_CREATE);
		db.execSQL(TrackingDetColumns.SQL_DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// transform db from old to new version
		try
		{
			db.execSQL("DROP TABLE " + TrackingColumns.TABLE_TRACKING);
			db.execSQL("DROP TABLE " + TrackingDetColumns.TABLE_TRACKING_DET);
		}
		catch (Exception ex){ /* not care about */ }
		
		db.execSQL(TrackingColumns.SQL_DB_CREATE);
		db.execSQL(TrackingDetColumns.SQL_DB_CREATE);
	}
	
	public static String GetCurrentSqlDate()
	{
		return new Date(Calendar.getInstance().getTimeInMillis()).toString();
	}
}
