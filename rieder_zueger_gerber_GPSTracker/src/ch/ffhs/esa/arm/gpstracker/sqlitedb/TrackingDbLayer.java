package ch.ffhs.esa.arm.gpstracker.sqlitedb;

import java.util.ArrayList;
import java.util.List;

import ch.ffhs.esa.arm.gpstracker.TrackingItem;
import ch.ffhs.esa.arm.gpstracker.TrackingPosition;
import android.content.Context;
import android.database.Cursor;

public class TrackingDbLayer {

	TrackingTbl trackingTbl;
	TrackingDetTbl trackingDetTbl;
	
	public TrackingDbLayer(Context context)
	{	
		trackingTbl = new TrackingTbl(context);
		trackingDetTbl = new TrackingDetTbl(context);
	}
	
	public TrackingItem insertTrackingItemWithoutPosition(TrackingItem trackingItem)
	{
    	trackingTbl.open();
    	
    	long trackingId = trackingTbl.insertTracking(trackingItem.getName());
    	    	
    	trackingItem.setTrackingId(trackingId);
    	
    	trackingTbl.close();
    	
    	return trackingItem;
	}
	
	public long insertTrackingPosition(long trackingId, TrackingPosition trackingPostion)
	{
		trackingDetTbl.open();
    	
    	long trackingPostionId = trackingDetTbl.insertTracking(
    			trackingId,
    			Double.toString(trackingPostion.getLatitude()),
    			Double.toString(trackingPostion.getLongitude()));
    	    	    	
    	trackingDetTbl.close();
    	
    	return trackingPostionId;
	}
	
	public void deleteTrackingItem(long trackingId)
	{
    	trackingTbl.open();
    	
    	trackingTbl.deleteTracking(trackingId);
    	    	    	
    	trackingTbl.close();
	}
	
	public List<TrackingItem> getAllTrackingItemsWithoutPosition()
	{
		trackingTbl.open();
		
		Cursor cursor = trackingTbl.getAllCursor();
    	
		cursor.moveToFirst();

		List<TrackingItem> trackingItems = new ArrayList<TrackingItem>();
		
		while (!cursor.isAfterLast()) {
			long trackingId = Long.parseLong(cursor.getString(0));
	    	String trackingName = cursor.getString(1);
	    	trackingItems.add(new TrackingItem(trackingId, trackingName, null));
	    	cursor.moveToNext();
	    }
		
		trackingTbl.close();
		   	
		return trackingItems;
	}
	
	public TrackingItem getTrackingItemById(long trackingItemId)
	{
		trackingTbl.open();
		
		Cursor cursor = trackingTbl.getTracking(trackingItemId);
		
    	String trackingName = cursor.getString(1);
	
    	trackingTbl.close();  	
    	
		// Load associated trackingPositions from Database.
    	List<TrackingPosition> trackingPositions = getTrackingPositions(trackingItemId);
    	TrackingItem trackingItem = new TrackingItem(trackingItemId, trackingName, trackingPositions);

		return trackingItem;
	}
	
	private List<TrackingPosition> getTrackingPositions(long trackingItemId)
	{
    	trackingDetTbl.open();
    	
		List<TrackingPosition> trackingPositions = new ArrayList<TrackingPosition>();
   	
    	Cursor cursor = trackingDetTbl.getTrackingPosition(trackingItemId);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			
			double latitud = Double.parseDouble(cursor.getString(2));
			double longitude = Double.parseDouble(cursor.getString(3));
	    	trackingPositions.add(new TrackingPosition(latitud, longitude));
	    	
	    	cursor.moveToNext();
	    }
		
		trackingDetTbl.close();
		
		return trackingPositions;
	}
	
	
	// Ugly because only used for testing.
	public void ClearDB()
	{
		trackingTbl.open();
		trackingTbl.deleteTrackings();
		trackingTbl.close();
	}
}
