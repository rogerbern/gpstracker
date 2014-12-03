package ch.ffhs.esa.arm.gpstracker.sqlitedb;

/**
 * @author Marc
 * 
 * Interface TrackingColumns
 * Schema-Interface of Table TRACKING
 */

public interface TrackingColumns {

	String TABLE_TRACKING = "TRACKING_V2";
	
	String FIELD_ID = "TRACKING_ID";
	String FIELD_TRACKINGNAME = "TRACKING_NAME";
	String FIELD_DATE = "CREATE_DATE";
	
	String[] PROJECTION_FULL = new String[] {FIELD_ID, FIELD_TRACKINGNAME, FIELD_DATE };
	
	String SQL_DB_CREATE = "CREATE TABLE " + TABLE_TRACKING + " ( " +
			FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			FIELD_TRACKINGNAME + " TEXT NOT NULL," + FIELD_DATE + ");";

}
