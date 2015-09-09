package com.feifeinet.reader.warcraft.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feifeinet.utils.db.SQLiteManager;


/**
 * 用户存档数据
 * @author Rex
 *
 */
public class UserProgressData {
	
	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}

	public static final byte AUTO_SAVE = 1;
	public static final byte USER_SAVE = 2;
	/*
	 *  "\"_ID\" INTEGER PRIMARY KEY AUTOINCREMENT," + 
			    "\"userName\" INTEGER," + //用户名
			    "\"bookName\" TEXT," +  //书名
			    "\"bookPath\" TEXT," +  //书路径
			    "\"bookId\" INTEGER," + //书ID
			    "\"scrollPosition\" INTEGER," + //阅读进度
			    "\"markType\" INTEGER," + //存档类型  1为自动存档 2为手动存档
			    "\"content\" TEXT);");
	 */
	private long _ID;
	
	private String bookName;
	
	private String bookPath;
	
	private int bookId;
	
	private int scrollPosition;
	
	private int markType;
	
	private String content;
	private int lineCount;
	private int lineHeight;
	
	public UserProgressData()
	{
		
	}

	public long get_ID() {
		return _ID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPath() {
		return bookPath;
	}

	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public int getMartType() {
		return markType;
	}

	public void setMartType(int markType) {
		this.markType = markType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 保存
	 */
	public void save()
	{
		SQLiteDatabase db = SQLiteManager.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("bookName", bookName);
		cv.put("bookPath", bookPath);
		cv.put("bookId", bookId);
		cv.put("scrollPosition", scrollPosition);
		cv.put("markType", markType);
		cv.put("content", content);
		cv.put("lineCount", lineCount);
		cv.put("lineHeight", lineHeight);
		_ID=db.insert(Constant.USER_PROGRESS_TABLENAME, null, cv);
	
	}
	
	/**
	 * 从数据库中读取数据
	 * @param cursor
	 * @return
	 */
	public static UserProgressData load(Cursor cursor)
	{
		UserProgressData data = new UserProgressData();
		data._ID = cursor.getLong(cursor.getColumnIndex("_ID"));
		data.bookId = cursor.getInt(cursor.getColumnIndex("bookId"));
		data.bookName = cursor.getString(cursor.getColumnIndex("bookName"));
		data.bookPath = cursor.getString(cursor.getColumnIndex("bookPath"));
		data.content = cursor.getString(cursor.getColumnIndex("content"));
		data.markType = cursor.getInt(cursor.getColumnIndex("markType"));
		data.scrollPosition = cursor.getInt(cursor.getColumnIndex("scrollPosition"));
		data.lineCount = cursor.getInt(cursor.getColumnIndex("lineCount"));
		data.lineHeight = cursor.getInt(cursor.getColumnIndex("lineHeight"));
		return data;
	}
	
	/**
	 * 删除书签
	 */
	public void delete()
	{
		SQLiteDatabase db = SQLiteManager.getWritableDatabase();
		db.delete(Constant.USER_PROGRESS_TABLENAME, "_ID=?", new String[]{String.valueOf(_ID)});
	}
	
	public static void delete(long id)
	{
		SQLiteDatabase db = SQLiteManager.getWritableDatabase();
		db.delete(Constant.USER_PROGRESS_TABLENAME, "_ID=?", new String[]{String.valueOf(id)});
	}
}
