package ch.ffhs.esa.arm.gpstracker.utils;

import java.util.Set;

import android.content.Context;
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
  private Location location;
  private Context context;
  
  /**
   * Constructor
   * @param phoneNumbers
   * @param messageType
   */
  public SMSSender(Set<String> phoneNumbers, MessageType messageType, Context context) {
	this.phoneNumbers = phoneNumbers;
	this.messageType = messageType;
	this.context = context;
  }
  
  public SMSSender(Set<String> phoneNumbers, MessageType messageType, Location location, Context context) {
	this(phoneNumbers, messageType, context);
	this.location = location;
  }

  public void run() {
	  switch (messageType) {
	    case PASSWORD: 		handlePasswordType();
	    			   		break;
	    case URGENCY:  		handleUrgencyType();
	    			   		break;
	    case STATUS_UPDATE: handleStatusUpdateType();
	    					break;
	  }
  }
  
  private void handlePasswordType() {
	// send sms without location data but with the current password
	sendSMS(this.phoneNumbers.iterator().next(), this.messageType.getText()); 
  }
  
  private void handleUrgencyType() {
	Location location;
	// TODO: determine if gps is activated, if not stop here
	// try to get actual position
	location = LocationHelper.getCurrentPosition(this.context);
	if (location == null) {
	  // try to get last stored position
	  location = LocationHelper.getLastPositionFromActiveTracking();
	}
	// if location is still null, try as many times to get a position from the device
	// until one is returned:
	while (location == null) {
	  location = LocationHelper.getCurrentPosition(this.context);
	}	
	sendPositionSMS(this.phoneNumbers, this.messageType, location);
  }
  
  private void handleStatusUpdateType() {
    sendPositionSMS(this.phoneNumbers, this.messageType, this.location);
  }
  
  private void sendPositionSMS(Set<String> phoneNumbers, MessageType messageType, Location location) {
	for (String phoneNumber : phoneNumbers) {
	  sendSMS(phoneNumber, messageType.getText() + getMapLink(location));
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
