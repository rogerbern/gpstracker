package ch.ffhs.esa.arm.gpstracker;

import ch.ffhs.esa.arm.gpstracker.services.MessengerServiceConnector;
import ch.ffhs.esa.arm.gpstracker.services.TrackingCallerIntentService;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Model for ActionBar Items.
 * Controls the appeareance of the ActionBar depending on context settings.
 * Singleton
 * @author roger
 *
 */
public class Navigation {
  private static Navigation instance = null;
  private boolean trackingActive = false; // ToDo: read from stored state
  private boolean trackingPlay = false; // ToDo: read from stored state
  private boolean lockActive = false; // ToDo: read from stored state
  private MenuItem menuItemTaskPlay;
  private MenuItem menuItemTaskPause;
  private MenuItem menuItemTaskStopp;
  private MenuItem menuItemTaskNew;
  private MenuItem menuItemMapView;
  private MenuItem menuItemTaskList;
  private MenuItem menuItemUrgencySettings;
  private MenuItem menuItemHelp;
  private MenuItem menuItemLock;
  
  // Singleton, so only private constructor
  private Navigation() {
  }
  
  /**
   * Returns an instance of the singleton Navigation
   * Handles the ActionBar
   * @return
   */
  public static Navigation getInstance() {
	  if (instance == null) {
		  instance = new Navigation();
	  }
	  return instance;
  }
  
  /**
   * Configures the actionBar
   * @param menu
   */
  public void calculateActionBar(Menu menu) {
	  initialize(menu);
	  processTracking();
  }
  
  /**
   * Initializes the private Members with the MenuItems once.
   * @param menu
   */
  private void initialize(Menu menu) {
    this.menuItemTaskPlay = menu.findItem(R.id.action_tracking_start);
	this.menuItemTaskPause = menu.findItem(R.id.action_tracking_pause);
	this.menuItemTaskStopp = menu.findItem(R.id.action_tracking_stop);
	this.menuItemTaskNew = menu.findItem(R.id.action_tracking_add);
	this.menuItemMapView = menu.findItem(R.id.action_map);
	this.menuItemTaskList = menu.findItem(R.id.action_tracking_list);
	this.menuItemUrgencySettings = menu.findItem(R.id.action_settings);
	this.menuItemHelp = menu.findItem(R.id.action_help);
	this.menuItemLock = menu.findItem(R.id.action_lock);
  }
  
  /**
   * Handles Navigation on MenuOptionItem Select
   * @param item
   * @param activity
   * @return
   */
  public boolean onOptionsItemSelected(MenuItem item, Activity activity) {
	  switch (item.getItemId()) {
	  case android.R.id.home:
		  actionMainIntend(activity);
		  return true;
        case R.id.action_tracking_start:
          setTrackingPlay(true);
          // messageIntent(activity, TrackingCallerIntentService.TRACKING_START);
          new MessengerServiceConnector(activity).bindService();
          return true;
        case R.id.action_tracking_pause:
          setTrackingPlay(false);
          //messageIntent(activity, TrackingCallerIntentService.TRACKING_PAUSE);
          new MessengerServiceConnector(activity).bindService();
          return true;
        case R.id.action_tracking_stop:
          setTrackingPlay(false);
          setTrackingActive(false);
          messageIntent(activity, TrackingCallerIntentService.TRACKING_STOP);
          return true;
        case R.id.action_tracking_add:
          actionNewIntend(activity);
          return true;
        case R.id.action_map:
          actionMapViewIntend(activity);
          return true;
        case R.id.action_tracking_list:
          actionTrackingListIntend(activity);
          return true;
        case R.id.action_settings:
        	actionPreferenceIntend(activity);
        	return true;
        case R.id.action_help:
        	actionHelpIntend(activity);
        	return true;
        case R.id.action_lock:
        	// TODO: Remove all Icons form the Navigationbar. And add the Lockicon
        	// TODO: Set the preference locked
        	// TODO: on all Activities (put in BaseActivity and do not forget the PreferenceActivity)
        	// onResume() determine if lock is set, if so redirect to the MainActivity
        default:
          return false;
    }
  }
  
  private void messageIntent(Activity activity, String value) {
    Intent msgIntent = new Intent(activity, TrackingCallerIntentService.class);
    msgIntent.putExtra(TrackingCallerIntentService.TRACKING_CALLER_MESSAGE_NAME, value);
    activity.startService(msgIntent);
  }
  
  /**
   * Helper Method that starts an activity
   * @param c Class to handle the activity
   * @param activity
   */
  private <T> void startActivity(Class<T> c, Activity activity) {
	  Intent i = new Intent(activity, c);
	  activity.startActivity(i);
  }
  
  public void actionMainIntend(Activity activity) {
	  startActivity(MainActivity.class, activity);
  }
  
  private void actionTrackingListIntend(Activity activity) {
    startActivity(TrackingListActivity.class, activity);
  }
  
  private void actionNewIntend(Activity activity) {
	startActivity(TrackingNewActivity.class, activity);
  }
  
  private void actionMapViewIntend(Activity activity) {
	startActivity(MapViewActivity.class, activity);
  }
  
  private void actionPreferenceIntend(Activity activity) {
	startActivity(EditPreferences.class, activity);
  }
  
  private void actionHelpIntend(Activity activity) {
	startActivity(HelpActivity.class, activity);
  }
  
  /**
   * Alter the state wether tracking is running or not
   * @param state
   */
  public void setTrackingPlay(boolean state) {
	  // ToDo: add error handling
	  this.trackingPlay = state;
	  processTrackingPlay();
  }
  
  /**
   * Alter the state wether a tracking is active or not
   * @param state
   */
  public void setTrackingActive(boolean state) {
	  // ToDo: add error handling
	  this.trackingActive = state;
	  processTracking();
  }
  
  /**
   * Alter the state wether the actionBar lock ist set or not
   * @param state
   */
  public void setLock(boolean state) {
	  // ToDo: add error handling
	  this.lockActive = state;
	  processLock();
  }
  
  private void processTracking() {
	  if (this.trackingActive) {
		  alterVisibility(this.trackingActive, menuItemTaskStopp);
		  alterVisibility(!this.trackingActive, menuItemTaskNew);
		  processTrackingPlay();
	  } else {
		  alterVisibility(this.trackingActive, menuItemTaskStopp, menuItemTaskPlay, menuItemTaskPause);
		  alterVisibility(!this.trackingActive, menuItemTaskNew);
	  }
  }
  
  private void processTrackingPlay() {
	  alterVisibility(!this.trackingPlay, menuItemTaskPlay);
	  alterVisibility(this.trackingPlay, menuItemTaskPause);
  }
  
  /**
   * Helper method that checks lock state and calls appropriate processing method 
   */
  private void processLock() {
	  if (lockActive) {
		processActiveLock();
	  } else {
		  processInactiveLock();
	  }
  }
  
  /**
   * Handles the active lock state
   */
  private void processActiveLock() {
	  alterVisibility(false, menuItemTaskPlay, menuItemTaskPause, menuItemTaskStopp, menuItemTaskNew, menuItemMapView, menuItemTaskList, menuItemUrgencySettings);
	  alterVisibility(true, menuItemLock, menuItemHelp);
  }
  
  /**
   * Handles the inactive lock state
   */
  private void processInactiveLock() {
	  alterVisibility(false, menuItemLock);
	  alterVisibility(true, menuItemTaskPlay, menuItemTaskPause, menuItemTaskStopp, menuItemTaskNew, menuItemMapView, menuItemTaskList, menuItemUrgencySettings, menuItemHelp);
  }
  
  /**
   * Helper method for altering visibility of ActionMenu Items
   * @param items
   */
  private void alterVisibility(boolean state, MenuItem ... items) {
	  for (MenuItem item : items) {
		  item.setVisible(state);
	  }
  }
}
