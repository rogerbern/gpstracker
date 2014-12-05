package ch.ffhs.esa.arm.gpstracker;
  
import java.util.Date;
import java.util.List;

public class TrackingItem {

	private Date creationDate;
	private String name;
	private List<TrackingPosition> trackingPositions;
	private long trackingId;

	public TrackingItem(long trackingId, String name, List<TrackingPosition> trackingPositions){
		this(name, trackingPositions);
		this.trackingId = trackingId;		
	}
	
	public TrackingItem(String name, List<TrackingPosition> trackingPositions) {
		this(name);
		this.creationDate = new Date();
		this.trackingPositions = trackingPositions;
	}
	
	public TrackingItem(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public List<TrackingPosition> getTrackingPositions() {
		return this.trackingPositions;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public long getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(long trackingId) {
		this.trackingId = trackingId;
	}
}
