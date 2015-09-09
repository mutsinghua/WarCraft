package com.feifeinet.utils.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface TableData{
	
	/**
	 *
	 * 琛ㄧ粨鏋勬鏌�
	 * 
	 * @param db
	 */
	void checkStructure(SQLiteDatabase db);
	
	/**
	 * 浠庢暟鎹簱涓鍙栨暟鎹�
	 * 鍜屽師鏉ms鐨刧etData鏂规硶绫讳技,闇�new涓�釜鏂板璞¤繑鍥�
	 * @param output
	 */
	TableData readFrom(Cursor cursor);
    
    /**
     * 
     * 鎶婃暟鎹啓濡傛暟鎹簱
     * @param input
     */
    long insertTo(SQLiteDatabase db);
}
