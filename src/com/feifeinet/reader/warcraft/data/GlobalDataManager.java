package com.feifeinet.reader.warcraft.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 数据存储
 * @author rexzou
 *
 */
public class GlobalDataManager {
	
	private Context context;
	
	private static GlobalDataManager instance = null;
	
	private SharedPreferences preferences;

	public static void init(Context context)
	{
		instance = new GlobalDataManager(context);	
	}
	
	public GlobalDataManager(Context context)
	{
		this.context = context;
		preferences = context.getSharedPreferences("WarReader", 0);
		
	}
	
	public void setBooleanData(String key, boolean value)
	{
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public boolean getBooleanData(String key, boolean defaultValue)
	{
		return preferences.getBoolean(key, defaultValue);
	}
	
	public void setStringData(String key, String value)
	{
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getStringData(String key, String defaultValue)
	{
		return preferences.getString(key, defaultValue);
	}
	
	public static GlobalDataManager getInstance()
	{
		return instance;
	}
	
	public int getIntegerData(String key, int defaultValue)
	{
		return preferences.getInt(key, defaultValue);
	}
	
	public void setIntegerData(String key, int value)
	{
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
}
