package ch.ffhs.esa.arm.gpstracker.sqlitedb;
 
/** 
 * @author Marc
 * 
 * Interface TrackingDETColumns
 * Schema-Interface of Table TRACKING_POSITIONS
 */

public interface TrackingDetColumns {
	
	String TABLE_TRACKING_DET = "TRACKING_POSITIONS_V2";
	
	String FIELD_ID = "TRACKING_DET_ID";
	String FIELD_REF_TRACKING_ID = "REF_TRACKING_ID";
	String FIELD_LATITUDE = "LATITUDE";
	String FIELD_LONGITUDE = "LONGITUDE";
	String FIELD_DATE = "CREATE_DATE";
	
	String[] PROJECTION_FULL = new String[] { FIELD_ID, FIELD_REF_TRACKING_ID, FIELD_LATITUDE, FIELD_LONGITUDE, FIELD_DATE };
	
	String SQL_DB_CREATE = "CREATE TABLE " + TABLE_TRACKING_DET +  " ( " +
						   FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						   FIELD_REF_TRACKING_ID + " INTEGER REFERENCES " + TrackingColumns.TABLE_TRACKING + " ON DELETE CASCADE," +
						   FIELD_LATITUDE + " REAL NOT NULL," +
						   FIELD_LONGITUDE + " REAL NOT NULL," +
						   FIELD_DATE +" DATE NOT NULL);";
}
