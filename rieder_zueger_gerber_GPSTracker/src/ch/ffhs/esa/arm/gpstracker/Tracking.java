package ch.ffhs.esa.arm.gpstracker;

/** 
 * Model for a tracking.
 * @author roger
 *
 */
public class Tracking {
  public static int ID_POSITION = 0;
  public static int NAME_POSITION = 1;
  
  private long trackingId;
  private String  trackingName;
  
  public Tracking(long trackingId, String trackingName) {
    this.trackingId = trackingId;
    this.trackingName = trackingName;
  }
  
  public long getTrackingId() {
    return this.trackingId;
  }
  
  public String getTrackingName() {
    return this.trackingName;
  }
}
