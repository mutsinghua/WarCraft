package com.feifeinet.reader.warcraft.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.feifeinet.reader.warcraft.data.ArticleDocument;
import com.feifeinet.reader.warcraft.data.Configuration;
import com.feifeinet.reader.warcraft.data.Constant;
import com.feifeinet.reader.warcraft.data.UserProgressData;
import com.feifeinet.utils.Tools;

public class ReaderActivity extends BaseActivity {

	/**
	 * 文档数据
	 */
	private ArticleDocument doc; 
	
	private ReadView textView;
	
	private ScrollView scrollView;
	
	
	private final int REQUEST_MENU = 0;
	
	public final static int DO_NOTHING = -1;
	public final static int FONT_SETUP = 1;
	public final static  int SAVE_MARK = 2;
	public final  static int LOAD_MARK = 3;
	public final static  int AUTO_SCROLL = 4;
	public final  static int ADV_SETUP = 5;
	public final  static int RETURN_CONTENT = 6;

	private int scrollHightBefore;

	private int currentYBefore;
	
	private String novelName;
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
				default:
					int drawedLine = textView.getLineCount();
					int newamount = drawedLine * textView.getLineHeight();
					if( scrollHightBefore != newamount)
					{
						
						int newY = newamount * currentYBefore / scrollHightBefore;
						scrollHightBefore = newamount;
						lineCountBefore = drawedLine;
						currentYBefore = newY;
						Log.i("Reader", "total amount after change font:" + newamount);
						Log.i("Reader", "current Y  after change font:" + newY);
						int lineCount2 = textView.getLineCount();
						Log.i("Reader", "lineCount2  after change font:" + lineCount2);
						scrollView.scrollTo(scrollView.getScrollX(),newY);
					}
			}
		}

	};

	private int lineCountBefore;

	private int articleId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readermain);
		if( doc == null)
		{
			doc = new ArticleDocument();
		}
		articleId = getIntent().getIntExtra("ARTICLE_ID", 0);
		String path = Tools.getStorePath(this, Constant.FILE_PATH);
		Log.i("Reader", path);
		doc.openFile(path + "/"+Constant.FILE_LIST[articleId]);
		novelName = Constant.BOOK_NAME[articleId];
		textView =  (ReadView) findViewById(R.id.mainReader);
		textView.setTextSize(Configuration.getInstance().getFontSize());
		textView.setOnDrawListener(new DrawListener() {
			
			@Override
			public void onDrawed() {
				int drawedallHight = textView.getLineCount()* textView.getLineHeight();
				Log.i("Reader","OnDrawed run");
				Log.i("Reader","drawedallHight " + drawedallHight);
				Log.i("Reader","lineCountBefore " + lineCountBefore);
				if( drawedallHight != scrollHightBefore && scrollHightBefore!=0)
				{
					handler.sendMessage(new Message());
				}
			}
		});
		String s = doc.getNext();
		Log.i("Reader", "getNext");
		textView.setText(s);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		Log.i("Reader", "append");
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		doc.closeFile();
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
//		if(item.getItemId() == R.id.font_size)
//		{
//			textView.setTextSize(20);
//			return true;
//		}
//		else if(item.getItemId() == R.id.turn_page)
//		{
//			Rect rect = new Rect();
//			scrollView.getLocalVisibleRect(rect);
//			scrollView.setSmoothScrollingEnabled(true);
//			scrollView.smoothScrollTo(rect.left, rect.bottom-rect.top+rect.bottom);
//			return true;
//		}
		return super.onMenuItemSelected(featureId, item);
	}


	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			showCustomerMenu();
			break;

		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 显示自定义菜单
	 */
	private void showCustomerMenu() {
		Intent i = new Intent(this,OptionMenuActivity.class);
		startActivityForResult(i, REQUEST_MENU);
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch( resultCode)
		{
		case FONT_SETUP:
			
			onFontRefresh(Configuration.getInstance().getFontSize());
			break;
		case RETURN_CONTENT:
			finish();
			break;
		case SAVE_MARK:
			UserProgressData upData = getCurrentReadProgress();
			upData.setMartType(UserProgressData.USER_SAVE);
			upData.save();
			MakeToast(0, "保存成功", Toast.LENGTH_LONG, this).show();
			break;
		case LOAD_MARK:
			long bookID = data.getLongExtra("articleId", -1);
			
			return;
		}
	}

	private UserProgressData getCurrentReadProgress()
	{
		UserProgressData upData = new UserProgressData();
		upData.setLineCount(textView.getLineCount());
		upData.setLineHeight(textView.getLineHeight());
		upData.setBookName(novelName);
		upData.setBookPath(doc.getFilePath());
		upData.setScrollPosition(scrollView.getScrollY());
		upData.setBookId(articleId);
		return upData;
	}
	
	/**
	 * 字体改变时调用 
	 * @param fontSize
	 */
	private void onFontRefresh(int fontSize)
	{
		scrollHightBefore = textView.getLineCount() * textView.getLineHeight();
		currentYBefore = scrollView.getScrollY();
		Log.i("Reader", "total amount before change font:" + scrollHightBefore);
		Log.i("Reader", "current Y  before change font:" + currentYBefore);
		lineCountBefore = textView.getLineCount();
		Log.i("Reader", "lineCount before change font:" + lineCountBefore);
		textView.setTextSize(fontSize);
//		int scrollHight2 = scrollView.getMaxScrollAmount();
//		int currentY2 = scrollHight2 * currentY / scrollHighted ;
//		Log.i("Reader", "total amount after change font:" + scrollHighted);
//		Log.i("Reader", "current Y  after change font:" + currentY2);
//		int lineCount2 = textView.getLineCount();
//		Log.i("Reader", "lineCount2  after change font:" + lineCount2);
		
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
//		MenuInflater mi = getMenuInflater();
//		mi.inflate(R.menu.reader_menu, menu);
		return super.onPrepareOptionsMenu(menu);
	}

}
