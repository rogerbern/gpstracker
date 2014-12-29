package ch.ffhs.esa.arm.gpstracker.sqlitedb;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.ffhs.esa.arm.gpstracker.Tracking;
import ch.ffhs.esa.arm.gpstracker.TrackingItem;
import ch.ffhs.esa.arm.gpstracker.TrackingPosition;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author andreas
 */

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
			long id = cursor.getLong(TrackingPosition.ID_POSITION);
			long trackingId = cursor.getLong(TrackingPosition.TRACKING_ID_POSITION);
			double latitude = Double.parseDouble(cursor.getString(TrackingPosition.LATITUDE_POSITION));
			double longitude = Double.parseDouble(cursor.getString(TrackingPosition.LONGITUDE_POISITION));
			Date trackingDate = new Date(cursor.getLong(TrackingPosition.TRACKING_DATE_POSITION));
	    	trackingPositions.add(new TrackingPosition(id, trackingId, latitude, longitude, trackingDate));
	    	
	    	cursor.moveToNext();
	    }
		
		trackingDetTbl.close();
		
		return trackingPositions;
	}
	
	/**
	 * Return a the latest stored tracking, the one with the highest id value.
	 * @return newest Tracking
	 */
	public Tracking getNewestTracking() {
	  trackingTbl.open();
	  
	  Cursor cursor = trackingTbl.getMaxId();
	  cursor.moveToFirst();
	  long id = cursor.getLong(Tracking.ID_POSITION);
	  String name = cursor.getString(Tracking.NAME_POSITION);
	  
	  return new Tracking(id, name);
	}
	
	/**
	 * Return the latest stored trackingposition, the one with the highest id value.
	 * @return
	 */
	public TrackingPosition getNewestTrackingPosition() {
		TrackingPosition trackingPosition;
		
		trackingDetTbl.open();
		
		Cursor cursor = trackingDetTbl.getMaxId();
		cursor.moveToFirst();
		long id = cursor.getLong(TrackingPosition.ID_POSITION);
		long trackingId = cursor.getLong(TrackingPosition.TRACKING_ID_POSITION);
		double latitude = Double.parseDouble(cursor.getString(TrackingPosition.LATITUDE_POSITION));
		double longitude = Double.parseDouble(cursor.getString(TrackingPosition.LONGITUDE_POISITION));
		Date trackingDate = new Date(cursor.getLong(TrackingPosition.TRACKING_DATE_POSITION));
    	trackingPosition = new TrackingPosition(id, trackingId, latitude, longitude, trackingDate);
    	
    	return trackingPosition;
	}
	
	public void ClearDB()
	{
		trackingTbl.open();
		trackingTbl.deleteTrackings();
		trackingTbl.close();
	}
}
