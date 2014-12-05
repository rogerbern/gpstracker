package ch.ffhs.esa.arm.gpstracker;

import java.util.Date;

import android.location.Location;

/**
 * Model for a tracking position
 * @author who?, 
 * @author roger: completed model
 *
 */
public class TrackingPosition {
    public static int ID_POSITION = 0;
    public static int TRACKING_ID_POSITION = 1;
    public static int LATITUDE_POSITION = 2;
    public static int LONGITUDE_POISITION = 3;
    public static int TRACKING_DATE_POSITION = 4;
	
	private long id;
	private long trackingId;
	private double latitude = 0.0d;
	private double longitude = 0.0d;
	private Date trackingDate;
	
	public TrackingPosition(long id, long trackingId, double latitude, double longitude, Date trackingDate)
	{
		this.id = id;
		this.trackingId = trackingId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.trackingDate = trackingDate;
	}
	
	public TrackingPosition(long id, long trackingId, Location location, Date trackingDate) {
		this.id = id;
		this.trackingId = trackingId;
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
		this.trackingDate = trackingDate;
	}
	
	public TrackingPosition(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public TrackingPosition(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}
	
	public long getId() {
		return this.id;
	}
	
	public long getTrackingId() {
		return this.trackingId;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public Date getTrackingDate() {
		return this.trackingDate;
	}
}
