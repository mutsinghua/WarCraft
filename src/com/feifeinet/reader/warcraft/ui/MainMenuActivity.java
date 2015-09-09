package com.feifeinet.reader.warcraft.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.feifeinet.reader.warcraft.data.ListViewApater;

public class MainMenuActivity extends BaseActivity {

	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_content);
		listView = (ListView) findViewById(R.id.bookContent);
		ListViewApater la = new ListViewApater(this);
		listView.setAdapter(la);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Intent i = new Intent(MainMenuActivity.this, ReaderActivity.class);
				i.putExtra("ARTICLE_ID", position);
				startActivity(i);
				
			}
		});
	}

}
