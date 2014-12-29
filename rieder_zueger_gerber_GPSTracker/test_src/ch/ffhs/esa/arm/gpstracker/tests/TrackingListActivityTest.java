package ch.ffhs.esa.arm.gpstracker.tests;
 
import ch.ffhs.esa.arm.gpstracker.R;
import ch.ffhs.esa.arm.gpstracker.TrackingItem;
import ch.ffhs.esa.arm.gpstracker.TrackingListActivity;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * 
 * @author andreas
 * Quelle: http://developer.android.com/tools/testing/activity_test.html
 */

public class TrackingListActivityTest extends ActivityInstrumentationTestCase2<TrackingListActivity> { 
	
    private TrackingListActivity activityUnterTest;
    private ListView testee;
    private TrackingDbLayer dbLayer;
	
	public TrackingListActivityTest() {
		super(TrackingListActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        activityUnterTest = getActivity();
        
        dbLayer = new TrackingDbLayer(activityUnterTest);  	
    }
    
    public void test_trakcing_ListView_ShouldShowTwoItems() {
    	
    	dbLayer.ClearDB();
    	testee = (ListView) activityUnterTest.findViewById(R.id.trakcing_ListView);
    	dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("TrackingName1"));
    	dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("TrackingName2"));
    	//dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("TrackingName3"));
    	
    	final int expectedChildCount = 2;
    	final int actualChildCount = testee.getChildCount();
    	
        assertEquals(expectedChildCount, actualChildCount);
        
    }

}
