package com.feifeinet.reader.warcraft;

import com.feifeinet.reader.warcraft.data.GlobalDataManager;
import com.feifeinet.utils.ExceptionLog;
import com.feifeinet.utils.db.SQLiteManager;

import android.app.Application;

public class ReadApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		GlobalDataManager.init(getApplicationContext());
		SQLiteManager.setContext(getApplicationContext());
		ExceptionLog.register(getApplicationContext());
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		SQLiteManager.closeDatabase();
	}
}
