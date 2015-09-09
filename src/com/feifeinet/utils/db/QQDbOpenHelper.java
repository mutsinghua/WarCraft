package com.feifeinet.utils.db;

import com.feifeinet.reader.warcraft.data.Constant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QQDbOpenHelper extends SQLiteOpenHelper {
	

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "feifeinet.reader.warcraft.database";
		
	
	Context context;
	QQDbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//for table user_progress
		db.execSQL("CREATE TABLE IF NOT EXISTS \""+Constant.USER_PROGRESS_TABLENAME+"\" (" + 
			    "\"_ID\" INTEGER PRIMARY KEY AUTOINCREMENT," + 
			    "\"userName\" INTEGER," + //用户名
			    "\"bookName\" TEXT," +  //书名
			    "\"bookPath\" TEXT," +  //书路径
			    "\"bookId\" INTEGER," + //书ID
			    "\"scrollPosition\" INTEGER," + //阅读进度
			    "\"lineCount\" INTEGER," + //书ID
			    "\"lineHeight\" INTEGER," + //阅读进度
			    "\"markType\" INTEGER," + //存档类型  1为自动存档 2为手动存档
			    "\"content\" TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}
	
    protected void finalize() {
    	try{
    		this.close();
    	}
    	catch(Exception ex){    		
    	}
    }

}
