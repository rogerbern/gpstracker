package ch.ffhs.esa.arm.gpstracker;

import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;

/**
 * 
 * @author roger
 *
 */
public class MapViewActivity extends BaseActivity {
	private static final String TRACKING_ID_PARAM = "tracking_id";
	private Long trackingId;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(TRACKING_ID_PARAM)) {
            this.trackingId = bundle.getLong(TRACKING_ID_PARAM);
            setContentView(R.layout.activity_map_view);	
        }   
    }
	
	@Override
	protected void onResume() {
		ArrayList<Location> locationList = new ArrayList<Location>();
		// determine if a tracking id was transmitted on item start
		// TODO: case if no tracking id was submitted, but a tracking is currently active -> show it (load route from db)
		if (this.trackingId != null) {
			locationList.addAll(LocationHelper.getLocationsForTracking(this.trackingId));
		} else {
			Location location = LocationHelper.getCurrentPosition();
			if (location != null) {
				locationList.add(location);	
			}  
		}
		if (!locationList.isEmpty()) {
			// TODO: show map with locations
		} else {
			// TODO: show error message
		}
	}
}
