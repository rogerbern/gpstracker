package ch.ffhs.esa.arm.gpstracker.utils;

import java.util.Set;

import android.location.Location;
import android.telephony.SmsManager;

/**
 * Sends sms to all phone numbers in the received list, with the actual geoposition.
 * Subject depends on the type (urgency, or status update)
 * @author roger
 *
 */
public final class SMSSender extends Thread {
  private static final String MAP_URL = "http://maps.google.com/?q=";
  private static final String DELIMITER = ",";
  
  private Set<String> phoneNumbers;
  private MessageType messageType;
  
  /**
   * Constructor
   * @param phoneNumbers
   * @param messageType
   */
  public SMSSender(Set<String> phoneNumbers, MessageType messageType) {
	this.phoneNumbers = phoneNumbers;
	this.messageType = messageType;
  }

  public void run() {
	  if (messageType.PASSWORD == this.messageType) {
		  // send sms without location data but with the current password
			sendSMS(this.phoneNumbers.iterator().next(), this.messageType.getText());
		} else if (messageType.URGENCY == this.messageType) {
		    Location location;
		    // TODO: determine if gps is activated, if not stop here
		    // try to get actual position
		    location = LocationHelper.getCurrentPosition();
		    if (location == null) {
		      // try to get last stored position
		      location = LocationHelper.getLastPositionFromActiveTracking();
		    }
		    // if location is still null, try as many times to get a position from the device
		    // until one is returned:
		    while (location == null) {
		      location = LocationHelper.getCurrentPosition();
		    }
		    // send sms for each phoneNumber
		    for (String phoneNumber : phoneNumbers) {
		      sendSMS(phoneNumber, messageType.getText() + getMapLink(location));
		    }
		  }
  }
  
  /**
   * Helper for sending a sms.
   * @param phoneNumber
   * @param messageText
   */
  private void sendSMS(String phoneNumber, String messageText) {
	  SmsManager smsManager = SmsManager.getDefault();
	  smsManager.sendTextMessage(phoneNumber, null, messageText, null, null);
  }
  
  /**
   * Helper that constructs the url for the google maps link to be written in the sms
   * @param position
   * @return
   */
  private String getMapLink(Location position) {
	  return MAP_URL + position.getLongitude() + DELIMITER + position.getLatitude();
  }
}
