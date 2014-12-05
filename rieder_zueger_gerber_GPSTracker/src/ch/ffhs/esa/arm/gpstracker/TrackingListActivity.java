package ch.ffhs.esa.arm.gpstracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;
import ch.ffhs.esa.arm.gpstracker.utils.GPSTracker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * 
 * @author roger
 * @changed marc (table creation deactivated)
 * @changed andreas (trackingList logic)
 */
public class TrackingListActivity extends BaseActivity {
	
	public static final String LATITUDES = "latitudes";
	public static final String LONGITUDES = "longitudes";
	
	private TrackingListAdapter adapter;
    TrackingDbLayer dbLayer;;
	ListView listView;
    GPSTracker gps;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_list);
        
        dbLayer = new TrackingDbLayer(this);
        
        // TODO: to delete, just for testing until service is running.
        // It Break test, for run UnitTest please comment out.
        // InsertByFirstCallSomeDummyData();
    }

	public void removeOnClickHandler(View view) {
		TrackingItem itemToRemove = (TrackingItem)view.getTag();
		adapter.remove(itemToRemove);
		
		dbLayer.deleteTrackingItem(itemToRemove.getTrackingId());
	}
	
	public void showMapOnClickHandler(View view) {

		long selectedTrackingItemId = ((TrackingItem)view.getTag()).getTrackingId();

		// Load the trackingPosition from DB, until now we load only all TrackingItems without positions from Database.
		TrackingItem selectedTrackingItem = dbLayer.getTrackingItemById(selectedTrackingItemId);
		
        List<TrackingPosition> trackingPositions = selectedTrackingItem.getTrackingPositions();
        
        double[] latitudes = new double[trackingPositions.size()];
        double[] longitudes = new double[trackingPositions.size()];
        int i = 0;
        for(TrackingPosition position : trackingPositions)
        {
        	latitudes[i] = position.getLatitude();
        	longitudes[i] = position.getLongitude();
        	i++;
        }
        
		Intent intent = new Intent(this, MapViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDoubleArray(TrackingListActivity.LATITUDES, latitudes);
        bundle.putDoubleArray(TrackingListActivity.LONGITUDES, longitudes);
        intent.putExtras(bundle);
        
        setIntent(intent);    
        
        startActivity(intent);
	}
	
	// TODO: Remove. Dummy data, should be insert by tracking service. 
	private void InsertByFirstCallSomeDummyData()
	{
		TrackingPosition positionBern = new TrackingPosition(46.57, 7.28);
		TrackingPosition positionZuerich = new TrackingPosition(47.3910085,8.5092357);
		TrackingPosition positionBasel = new TrackingPosition(47.35, 7.35);
		
		TrackingItem trackingItem = dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("Z�rich - Bern"));
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionZuerich);
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionBern);
		
		trackingItem = dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("Z�rich - Bern - Basel"));
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionZuerich);
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionBern);
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionBasel);
		
		trackingItem = dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("Z�rich"));
		dbLayer.insertTrackingPosition(trackingItem.getTrackingId(), positionZuerich);
	}
	
	@Override
	public void onResume() {
	  super.onResume();
	  
		List<TrackingItem> items = dbLayer.getAllTrackingItemsWithoutPosition();
		
		adapter = new TrackingListAdapter(TrackingListActivity.this, R.layout.tracking_list_item, items);
		listView = (ListView)findViewById(R.id.trakcing_ListView);
		listView.setAdapter(adapter);
	}
}
