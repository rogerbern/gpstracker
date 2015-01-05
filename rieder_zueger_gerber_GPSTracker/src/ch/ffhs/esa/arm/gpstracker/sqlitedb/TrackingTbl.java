package ch.ffhs.esa.arm.gpstracker.sqlitedb;

import ch.ffhs.esa.arm.gpstracker.sqlitedb.DbHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/** 
 * @author Marc
 * 
 * Class TrackingTbl
 * Class-Schema of TRACKING_T
 */

public class TrackingTbl implements TrackingColumns {
	private final DbHelper dbHelper;
	private SQLiteDatabase db;

	public TrackingTbl(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() {
		if (db == null || !db.isOpen()) {
			db = dbHelper.getWritableDatabase();
		}
	}

	public void close() {
		dbHelper.close();
	}

	// liest ein Tracking aus
	public Cursor getTracking(long id) {
		Cursor result = db.query(TABLE_TRACKING, PROJECTION_FULL, FIELD_ID + "=" + id, null, null, null, null);
		boolean found = result.moveToFirst();
		if (found) {
			return result;
		}
		else {
			result.close();
			return null;
		}
	}
		
	public Cursor getMaxId() {
		Cursor result = db.query(TABLE_TRACKING, null, "TRACKING_ID=(SELECT MAX(TRACKING_ID) FROM " + TrackingTbl.TABLE_TRACKING + ")", null, null, null, null);
		boolean found = result.moveToFirst();
		if (found) {
			return result;
		}
		else {
			result.close();
			return null;
		}
	}

	public Cursor getAllCursor() {
		Cursor cursor = db.query(TABLE_TRACKING, new String[] { FIELD_ID,
				FIELD_TRACKINGNAME, FIELD_DATE }, null, null, null, null, null);
		return cursor;
	}

	// loescht ein Tracking in der DB
	public boolean deleteTracking(long id) {
		return db.delete(TABLE_TRACKING, FIELD_ID + "=" + id, null) > 0;
	}
	
	// loescht alle Tracking in der DB
	public boolean deleteTrackings() {
		return (db.delete(TABLE_TRACKING, null, null) > 0);
	}

	// erzeugt ein Tracking in der DB
	public long insertTracking(String trackingName) {
		ContentValues values = new ContentValues();
		values.put(FIELD_TRACKINGNAME, trackingName);
		values.put(FIELD_DATE, DbHelper.GetCurrentSqlDate());
		
		//values.put(FIELD_DATE, modificationDate.getTime());
		
		long id = db.insert(TABLE_TRACKING, null, values);
		
		return id;
	}

		
	// aktualisiert ein Tracking in der DB
	public boolean updateTracking(long id, String trackingName) {
		ContentValues values = new ContentValues();
		values.put(FIELD_TRACKINGNAME, trackingName);
		return db.update(TABLE_TRACKING, values, FIELD_ID + "=" + id, null) > 0;
	}
	
	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_TRACKING;
}
