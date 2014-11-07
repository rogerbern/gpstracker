package ch.ffhs.esa.arm.gpstracker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessengerServiceAutostart extends BroadcastReceiver {
  private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.MessengerServiceAutostart";
  public void onReceive(Context context, Intent intent) {
	Intent serviceIntent = new Intent(context, MessengerService.class);
	context.startService(serviceIntent);
	Log.i(LOG, "Autostart started");
  }
}
