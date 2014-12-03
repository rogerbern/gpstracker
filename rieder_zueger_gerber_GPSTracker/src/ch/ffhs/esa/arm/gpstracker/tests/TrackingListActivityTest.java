package ch.ffhs.esa.arm.gpstracker.tests;

import ch.ffhs.esa.arm.gpstracker.R;
import ch.ffhs.esa.arm.gpstracker.TrackingItem;
import ch.ffhs.esa.arm.gpstracker.TrackingListActivity;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

public class TrackingListActivityTest extends ActivityInstrumentationTestCase2<TrackingListActivity> { 
	
    private TrackingListActivity activityUnterTest;
    private ListView testee;
	
	public TrackingListActivityTest() {
		super(TrackingListActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        activityUnterTest = getActivity();
        testee = (ListView) activityUnterTest.findViewById(R.id.trakcing_ListView);
    }
    
    public void test_trakcing_ListView_ShouldShowTwoItems() {
    	
    	TrackingDbLayer dbLayer = new TrackingDbLayer(activityUnterTest);
    	
    	dbLayer.ClearDB();
    	
    	dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("TrackingName1"));
    	dbLayer.insertTrackingItemWithoutPosition(new TrackingItem("TrackingName2"));
    	
    	final int expectedChildCount = 2;
    	final int actualChildCount = testee.getChildCount();
    	
        assertEquals(expectedChildCount, actualChildCount);
        
        dbLayer.ClearDB();
    }

}
