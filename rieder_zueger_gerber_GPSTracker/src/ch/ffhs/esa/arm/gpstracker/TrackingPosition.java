package ch.ffhs.esa.arm.gpstracker;

public class TrackingPosition {

	private double latitude = 0.0d;
	private double longitude = 0.0d;
	
	public TrackingPosition(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
