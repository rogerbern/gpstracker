package ch.ffhs.esa.arm.gpstracker.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerServiceConnector {
  static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.MessengerServiceConnector";
  static final int MSG_REGISTER = 1;
  static final int MSG_SAY_HELLO = 2;
  
  // Messenger for sending messages to the service
  Messenger mServiceMessenger = null;
  // Messenger for receiving messages from the service
  Messenger mClientMessenger = null;
  
  // Target for clients
  private final IncomingHandler handler;
  
  // Hanlder thread to avoid running in the main thread
  private final HandlerThread handlerThread;
  
  // Flag indication if we called bind on the service
  boolean mBound;
  
  // Context of the activity from which this connector was launched
  private Context mCtx;
  
  // Handler of icoming messages from service
  class IncomingHandler extends Handler {
	  public IncomingHandler(HandlerThread thr) {
		  super(thr.getLooper());
	  }
	  
	  @Override
	  public void handleMessage(Message msg) {
		  switch (msg.what) {
		  case MSG_SAY_HELLO:
			  Log.i(LOG, "Service said hello!");
			  break;
		  default:
			  super.handleMessage(msg);
		  }
	  }
  }
  
  // Class for interacting with the main interface of the service.
  private ServiceConnection mConnection = new ServiceConnection() {
	 
	  public void onServiceConnected(ComponentName className, IBinder service) {
		  mServiceMessenger = new Messenger(service);
		  Message msg = Message.obtain(null, MSG_REGISTER, 0, 0);
		  msg.replyTo = mClientMessenger;
		  
		  // Bundle b = new Bundle(); b.putString("key", "hello"); msg.setData(b);
		  try {
			  // delete following line
			  mServiceMessenger.send(msg);
		  } catch(RemoteException e) {
			  Log.e(LOG, "Error occured while sending message to service");
		  }
		  mBound = true;
		  sayHello();
	  }
	  
	  public void onServiceDisconnected(ComponentName className) {
		  mServiceMessenger = null;
		  mBound = false;
		  Log.e(LOG, "Service disconnected");
	  }
  };
  
  // Constructor
  public MessengerServiceConnector(Context ctx) {
	  mCtx = ctx;
	  handlerThread = new HandlerThread("IPChandlerThread");
	  handlerThread.start();
	  handler = new IncomingHandler(handlerThread);
	  mClientMessenger = new Messenger(handler);
  }
  
  // Method used for binding with the service
  public boolean bindService() {
	  //Intent i = new Intent("ch.ffhs.esa.arm.gpstracker.messengerservice.ACTION_BIND");
	  Intent i = new  Intent(mCtx, ch.ffhs.esa.arm.gpstracker.services.MessengerService.class);
	  return mCtx.getApplicationContext().bindService(i, mConnection, Context.BIND_AUTO_CREATE);
  }
  
  public void unbindService() {
	  if (mBound) {
		  mCtx.getApplicationContext().unbindService(mConnection);
		  mBound = false;
	  }
  }
  
  public void sayHello() {
	  if (!mBound) {
		  return;
	  }
	  Message msg = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
	  try {
		  mServiceMessenger.send(msg);
	  } catch(RemoteException e) {
		  Log.e(LOG, "Error while sending Say Hello");
	  }
  }
  
}
