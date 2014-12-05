package ch.ffhs.esa.arm.gpstracker;

import java.util.ArrayList;
import java.util.List;

import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;
import ch.ffhs.esa.arm.gpstracker.utils.GPSTracker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

/**
 * 
 * @author roger
 * @author andreas
 *
 */
public class MapViewActivity extends BaseActivity {
			
    private GoogleMap map;
	  
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);	
        
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Need to reset else the old Marker are still visible.
		map.clear();
		
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(TrackingListActivity.LATITUDES) 
        		&& bundle.containsKey(TrackingListActivity.LONGITUDES)) 
        {
        	double[] latitudes = bundle.getDoubleArray(TrackingListActivity.LATITUDES);
        	double[] longitudes = bundle.getDoubleArray(TrackingListActivity.LONGITUDES);
        	
        	showTrackingDataOnMap(latitudes, longitudes);
        }
		else
		{
			showCurrentPositionOnMap();
		}

	}
	
	/**
	 * Override super.onNewIntent() so that calls to getIntent() will return the
	 * latest intent that was used to start this Activity rather than the first
	 * intent.
	 */
	@Override
	public void onNewIntent(Intent intent){
	    super.onNewIntent(intent);
	    setIntent(intent);
	}
	
	private void showTrackingDataOnMap(double[] latitudes, double[] longitudes)
	{
        List<LatLng> routePoints = new ArrayList<LatLng>();
    	for(int i = 0; i < latitudes.length; i++)
    	{
            routePoints.add(new LatLng(latitudes[i], longitudes[i]));
        }
        
    	PolylineOptions rectOptions = new PolylineOptions().width(3).color(Color.RED);
        Polyline route = map.addPolyline(rectOptions);
        route.setPoints(routePoints);
        LatLng lastPosition = new LatLng(latitudes[latitudes.length-1], longitudes[longitudes.length-1]);
        showMap(lastPosition, "Last Position!");
	}
	
	private void showCurrentPositionOnMap()
	{
    	GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled     
        if (gps.canGetLocation()) {
        	
        	Location location = gps.getLocation();
        	LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            showMap(currentPosition, "You are here!");
            
        } else {
        	
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
	}
	
	private void showMap(LatLng position, String markerText)
	{
    	map.addMarker(new MarkerOptions().position(position).title(markerText));
        
        // Move the camera instantly to last tracked position with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}
}
