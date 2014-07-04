package com.chedanji.flashlight;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class FlashlightService extends Service {

	private Camera camera;
	private Parameters params;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// show notification
		showNotification();
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		openFlashlight();
		return START_NOT_STICKY;
	}


	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		closeFlashlight();
	}
	
	// show notification
	private void showNotification() {
		Intent notificationIntent = new Intent();
		notificationIntent.setClass(this, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = 
				PendingIntent.getActivity(getApplicationContext(),
						0, 
						notificationIntent,
						PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		Notification notification = 
			(Notification)builder.setSmallIcon(R.drawable.notification_icon)
			.setOngoing(true)
			.setContentTitle(getResources().getString(R.string.notification_title))
			.setContentText(getResources().getString(R.string.notification_text))
			.setContentIntent(pendingIntent)
			.build();
		
		startForeground(1, notification);
	}
	
	// open flashlight
	private void openFlashlight() {
		if(camera == null) {
			camera = Camera.open();
			params = camera.getParameters();
		}
		
		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(params);
		camera.startPreview();
	}

	// close flashlight
	private void closeFlashlight() {
		if(camera == null) {
			camera = Camera.open();
			params = camera.getParameters();
		}
		params.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(params);
		camera.stopPreview();
		camera.release();
	}
	
	

}
