package ch.ffhs.esa.arm.gpstracker.sqlitedb;
 
import java.sql.Date;
import java.util.Calendar;

import ch.ffhs.esa.arm.gpstracker.sqlitedb.DbHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Marc
 * 
 * Class TrackingDetTbl
 * Class-Schema of TRACKING_DET_T
 */

public class TrackingDetTbl implements TrackingDetColumns {
	private final DbHelper dbHelper;
	private SQLiteDatabase db;

	public TrackingDetTbl(Context context) {
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

	// liest Details eines Trackings aus
	public Cursor getTracking(long id, long id_ref) {
		Cursor result = db.query(TABLE_TRACKING_DET, PROJECTION_FULL, FIELD_ID + "=" + id + 
								"AND" + FIELD_REF_TRACKING_ID + "=" + id_ref, null, null, null, null);
		boolean found = result.moveToFirst();
		if (found) {
			return result;
		}
		else {
			result.close();
			return null;
		}
	}
	
	// liest TrackingPositions aus
	public Cursor getTrackingPosition(long id) {
		return db.query(TABLE_TRACKING_DET, PROJECTION_FULL, FIELD_REF_TRACKING_ID + "=" + id, null, null, null, null);
	}
	
	public Cursor getAllCursor() {
		Cursor cursor = db.query(TABLE_TRACKING_DET, new String[] { FIELD_ID,
				FIELD_REF_TRACKING_ID, FIELD_LATITUDE, FIELD_LONGITUDE, FIELD_DATE }, null, null, null, null, null);
		return cursor;
	}

	// loescht ein Tracking in der DB
	public boolean deleteTracking(long id, long id_ref) {
		return db.delete(TABLE_TRACKING_DET, FIELD_ID + "=" + id + "AND" + FIELD_REF_TRACKING_ID + "=" + id_ref, null) > 0;
	}

	// erzeugt ein Tracking in der DB
	public long insertTracking(long id_ref, String latitude, String longitude) {
				
		ContentValues values = new ContentValues();
		values.put(FIELD_REF_TRACKING_ID, id_ref);
		values.put(FIELD_LATITUDE, latitude);
		values.put(FIELD_LONGITUDE, longitude);
		values.put(FIELD_DATE, DbHelper.GetCurrentSqlDate());
		long id = db.insert(TABLE_TRACKING_DET, null, values);
		return id;
	}

	// aktualisiert ein Tracking in der DB
	public boolean updateTracking(long id, String trackingName) {
		//ContentValues values = new ContentValues();
		//values.put(FIELD_TRACKINGNAME, trackingName);
		//return db.update(TABLE_TRACKING, values, FIELD_ID + "=" + id, null) > 0;
		return false;
	}
	
	public Cursor getMaxId() {
		Cursor result = db.query(TABLE_TRACKING_DET, null, "TRACKING_DET_ID=(SELECT MAX(TRACKING_DET_ID) FROM TRACKING_DET_T)", null, null, null, null);
		boolean found = result.moveToFirst();
		if (found) {
			return result;
		}
		else {
			result.close();
			return null;
		}
	}
}
