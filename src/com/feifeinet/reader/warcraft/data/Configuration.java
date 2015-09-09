package com.feifeinet.reader.warcraft.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户配置文件
 * @author Rex
 *
 */
public class Configuration implements Parcelable {

	private static Configuration instance = null;
	
	private static final String KEY_READBOOK = "KEY_READBOOK";
	
	private static final String KEY_FONTSIZE = "KEY_FONTSIZE";
	
	private static final String KEY_SCROLLPOSITION = "KEY_SCROLLPOSITION";
	/**
	 * 当前看的书
	 */
	private int currentReadBook;
	
	/**
	 * 当前的字体大小
	 */
	private int fontSize;
	
	/**
	 * 当前窗口位置
	 */
	private int scrollPosition;
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentReadBook(int currentReadBook) {
		this.currentReadBook = currentReadBook;
	}

	public int getCurrentReadBook() {
		return currentReadBook;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}
	public static Configuration getInstance ()
	{
		if( instance== null)
		{
			instance = new Configuration();
		}
		return instance;
	}
	
	private Configuration()
	{
		setCurrentReadBook(GlobalDataManager.getInstance().getIntegerData(KEY_READBOOK, 0));
		setFontSize(GlobalDataManager.getInstance().getIntegerData(KEY_FONTSIZE, Constant.DEFAULT_FONT_SIZE));
		setScrollPosition(GlobalDataManager.getInstance().getIntegerData(KEY_SCROLLPOSITION,0));
	}
	
	public void save()
	{
		GlobalDataManager.getInstance().setIntegerData(KEY_READBOOK, getCurrentReadBook());
		GlobalDataManager.getInstance().setIntegerData(KEY_FONTSIZE, getFontSize());
		GlobalDataManager.getInstance().setIntegerData(KEY_SCROLLPOSITION, getScrollPosition());
	}
}
