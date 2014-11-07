package ch.ffhs.esa.arm.gpstracker.utils;

/**
 * Enumeration for the message type of an position sms with text representation for sms text
 * In a multi language scenario the enum keys could be used to address a value in a text file
 * @author roger
 *
 */
public enum MessageType {
  URGENCY ("Urgency: Please go to the following position: "), 
  STATUS_UPDATE ("Status update: I'm now here: "),
  PASSWORD ("Ihr Passwort lautet: ");
  
  
  private String text;
  MessageType(String text) {
	  this.text = text;
  }
  public String getText() {
	  return this.text;
  }
}
