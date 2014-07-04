package com.chedanji.flashlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	private boolean hasFlash;
	private static boolean isFlashOn;
	private ImageButton button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// detect flashlight 
		dectFlashlight();
		
		button = (ImageButton) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FlashlightService.class);
				if (!isFlashOn){
					startService(intent);
				} else {
					stopService(intent);
				}
				isFlashOn = !isFlashOn;
			}
		});
		
	}
	
	// detect flashlight 
	private void dectFlashlight() {
		hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		if(!hasFlash) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setTitle(getResources().getString(R.string.dialog_title))
				.setMessage(getResources().getString(R.string.dialog_text))
				.setPositiveButton(getResources().getString(R.string.dialog_button), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// closing the application 
						finish();
					}
				})
				.setCancelable(false)
				.create().show();
		}
	} 

}
