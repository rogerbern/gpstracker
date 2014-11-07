package ch.ffhs.esa.arm.gpstracker.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerService extends Service {
  static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.MessengerService";
  static final int MSG_REGISTER = 1;
  static final int MSG_SAY_HELLO = 2;
  static final int MSG_START = 3;
  
  // Handler
  @SuppressLint("HandlerLeak") class IncomingHandler extends Handler {
	  @Override
	  public void handleMessage(Message msg) {
		  switch(msg.what) {
		    case MSG_REGISTER:
		    	Log.i(LOG, "Service: received client Messenger! " + msg.what);
		    	break;
		    case MSG_SAY_HELLO:
		    	Log.i(LOG, "Client said hello! " + msg.what);
		    	break;
		    case MSG_START:
		    	Log.i(LOG, "Client wants service to start location tracking. " + msg.what);
		    	break;
		    default:
		    	super.handleMessage(msg);
		  }
	  }
  }
  
  // Target
  final Messenger mServiceMessenger = new Messenger(new IncomingHandler());
  
  @Override
  public IBinder onBind(Intent intent) {
	  return mServiceMessenger.getBinder();
  }
  
  @Override
  public void onCreate() {
	  super.onCreate();
	  Log.i(LOG, "Messenger service was started");
  }
}
