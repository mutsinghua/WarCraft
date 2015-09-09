package com.feifeinet.reader.warcraft.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;


public class SplashLogoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
	                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.splash_logo);
		handler.sendMessageDelayed(new Message(), 1000);
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				Intent i = new Intent( SplashLogoActivity.this, MainMenuActivity.class);
				startActivity(i);
				finish();
				
				break;
			}
		}
		
	};
}
