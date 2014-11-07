package ch.ffhs.esa.arm.gpstracker.utils;

import java.util.HashSet;
import java.util.Set;

import ch.ffhs.esa.arm.gpstracker.EditPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Helper class for getting phone numbers or phone number lists of different classes
 * @author roger
 *
 */
public class PhoneNumberHelper {
  public static String URGENCY_PHONECALL_RECEIVER = "pref_key_urgency_phonecall_receiver";
  public static String URGENCY_SMS_RECEIVER_ONE = "pref_key_urgency_sms_receiver1";
  public static String URGENCY_SMS_RECEIVER_TWO = "pref_key_urgency_sms_receiver2";
  public static String URGENCY_SMS_RECEIVER_THREE = "pref_key_urgency_sms_receiver3";
  public static String TRACKING_SMS_RECEIVER_ONE = "pref_key_tracking_sms_receiver1";
  public static String TRACKING_SMS_RECEIVER_TWO = "pref_key_tracking_sms_receiver2";
  public static String TRACKING_SMS_RECEIVER_THREE = "pref_key_tracking_sms_receiver3";
  private static SharedPreferences preferences;
  
  private PhoneNumberHelper() {}
  /**
   * Returns the phone number for urgency call stored in app preferences as a string.
   * If the stored value is empty this method returns an empty string.
   * @param context
   * @return
   */
  public static String getUrgencyPhoneNumber(Context context) {
  	  //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	  preferences = context.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
      return preferences.getString(URGENCY_PHONECALL_RECEIVER, "");
  }
  
  /**
   * Returns all non empty urgency sms receiver phone numbers as strings.
   * @param context
   * @return
   */
  public static Set<String> getUrgencySMSReceivers(Context context) {
  	return getSMSReceivers(context, URGENCY_SMS_RECEIVER_ONE, URGENCY_SMS_RECEIVER_TWO, URGENCY_SMS_RECEIVER_THREE);
  }
  
  /**
   * Returns all non empty tracking sms receiver phone numbers as strings.
   * @param context
   * @return
   */
  public static Set<String> getTrackingSMSReceivers(Context context) {
	return getSMSReceivers(context, TRACKING_SMS_RECEIVER_ONE, TRACKING_SMS_RECEIVER_TWO, TRACKING_SMS_RECEIVER_THREE);
  }
  
  /**
   * Generic helper method that reads received preference strings and return all non empty values
   * @param context
   * @param prefs
   * @return
   */
  private static Set<String> getSMSReceivers(Context context, String ...prefs) {
    HashSet<String> phoneNumbers = new HashSet<String>();
	preferences = PreferenceManager.getDefaultSharedPreferences(context);
	  for (String pref : prefs) {
		  String value = preferences.getString(pref, "");
		  if (value.length() >0) {
			  phoneNumbers.add(value);
		  }
	  }
    return phoneNumbers;
  }
}
