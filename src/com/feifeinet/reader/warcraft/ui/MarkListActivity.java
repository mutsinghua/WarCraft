package com.feifeinet.reader.warcraft.ui;



import com.feifeinet.reader.warcraft.data.Constant;
import com.feifeinet.reader.warcraft.data.UserProgressData;
import com.feifeinet.reader.warcraft.data.UserProgressManager;
import com.feifeinet.utils.db.SQLiteManager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MarkListActivity extends ListActivity {

	MarkListViewApdater markApdater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		marks = SQLiteManager.getWritableDatabase().query(Constant.USER_PROGRESS_TABLENAME, null, "markType=?", new String[]{String.valueOf(UserProgressData.USER_SAVE)}, null, null, "_ID desc");
		markApdater = new MarkListViewApdater(this, marks);
		getListView().setAdapter(markApdater);
		

	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 
		if( keyCode == KeyEvent.KEYCODE_BACK)
		{
			setResult(-1);
			finish();
			return true;
		}
		else
		{
			return super.onKeyDown(keyCode, event);
		}
	}



	class MarkListViewApdater extends  CursorAdapter
	{

		LayoutInflater mInflater ;
		public MarkListViewApdater(Context context, Cursor c) {
			super(context, c);
			mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			if( cursor!= null)
			{
				long countLine = cursor.getLong(cursor.getColumnIndex("lineCount"));
				long lineHeight = cursor.getLong(cursor.getColumnIndex("lineHeight"));
				long curpos = cursor.getLong(cursor.getColumnIndex("scrollPosition"));
				int progress =Math.round(curpos * 100 / (countLine * lineHeight));
				
				long _ID = cursor.getLong(0);
				String name = cursor.getString(cursor.getColumnIndex("bookName"));
				TextView tv = (TextView) view.findViewById(R.id.book_name);
				tv.setText(name);
				tv.setTag(Long.valueOf(_ID));
				tv.setOnClickListener(clickListenertext);
				tv = (TextView) view.findViewById(R.id.book_progress);
				tv.setText(String.valueOf(progress));
				tv.setTag(Long.valueOf(_ID));
				tv.setOnClickListener(clickListenertext);
				Button bt = (Button) view.findViewById(R.id.delete_marked);
				bt.setTag(Long.valueOf(_ID));
				bt.setOnClickListener(clickListener);
				
				
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(R.layout.mark_list_item, null);
			
		}
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			long id = (Long) v.getTag();
			UserProgressManager.deleteByID(id);
		}
	};
	
	private OnClickListener clickListenertext = new OnClickListener() {

		@Override
		public void onClick(View view) {
			
			Intent i = new Intent();
			
			long _id = (Long) view.getTag();
			i.putExtra("markID", _id);
			setResult(0,i);
			finish();
		}
	};
	
	private Cursor marks;
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if( marks!=null)
		{
			marks.close();
		}
	}
}
