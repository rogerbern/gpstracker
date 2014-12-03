package ch.ffhs.esa.arm.gpstracker;

import java.text.DateFormat;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingTbl;

/**
 * 
 * @author roger
 *
 */
public class HelpActivity extends Activity {
	private TrackingTbl trackingTbl;
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        trackingTbl = new TrackingTbl(this);
		trackingTbl.open();
		//trackingTbl.deleteTrackings();
		Cursor maxId = trackingTbl.getMaxId();
		long id = maxId.getLong(maxId.getColumnIndex(TrackingTbl.FIELD_ID));
		displayTracking(id);
    }
    
    private void displayTracking(long id) {
		Cursor tracking = trackingTbl.getTracking(id);
		if (tracking == null) {
			return;
		}
		
		setViewText(R.id.tracking_id, tracking.getString(tracking.getColumnIndex(TrackingTbl.FIELD_ID)));
		setViewText(R.id.trackingname, tracking.getString(tracking.getColumnIndex(TrackingTbl.FIELD_TRACKINGNAME)));
		setViewText(R.id.date, DATE_FORMAT.format(tracking.getInt(tracking.getColumnIndex(TrackingTbl.FIELD_DATE))));
	}
    
    private void setViewText(int id, String text) {
		((TextView) findViewById(id)).setText(text);
	}
}
