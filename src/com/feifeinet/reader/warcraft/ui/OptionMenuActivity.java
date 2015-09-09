package com.feifeinet.reader.warcraft.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ViewSwitcher.ViewFactory;

import com.feifeinet.reader.warcraft.data.Configuration;

public class OptionMenuActivity extends BaseActivity implements View.OnClickListener, OnFocusChangeListener, OnTouchListener {

	private ImageButton fontBt;
	private ImageButton saveBt;
	private ImageButton loadBt;
	private ImageButton autoBt;
	private ImageButton advBt;
	private ImageButton returnBt;
	private Dialog fontDialog;
	private View fontDialogView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_menu);
		fontBt = (ImageButton) findViewById(R.id.menu_fontsetup);
		saveBt = (ImageButton) findViewById(R.id.menu_saveprogress);
		loadBt = (ImageButton) findViewById(R.id.menu_load_progress);
		autoBt = (ImageButton) findViewById(R.id.menu_auto_scroll);
		advBt = (ImageButton) findViewById(R.id.menu_adv_setup);
		returnBt = (ImageButton) findViewById(R.id.menu_return_content);
		
		fontBt.setOnClickListener(this);
		fontBt.setOnFocusChangeListener(this);
		fontBt.setOnTouchListener(this);
		
		saveBt.setOnClickListener(this);
		saveBt.setOnFocusChangeListener(this);
		saveBt.setOnTouchListener(this);
		
		autoBt.setOnClickListener(this);
		autoBt.setOnFocusChangeListener(this);
		autoBt.setOnTouchListener(this);
		
		loadBt.setOnClickListener(this);
		loadBt.setOnFocusChangeListener(this);
		loadBt.setOnTouchListener(this);
		
		advBt.setOnClickListener(this);
		advBt.setOnFocusChangeListener(this);
		advBt.setOnTouchListener(this);
		
		returnBt.setOnClickListener(this);
		returnBt.setOnFocusChangeListener(this);
		returnBt.setOnTouchListener(this);
			
	}


	@Override
	public void onClick(View v) {
		switch( v.getId())
		{
		case R.id.menu_return_content:
			setResult(ReaderActivity.RETURN_CONTENT);
			finish();
			break;
		case R.id.menu_fontsetup:
			buildFontDialog();
			break;
		case R.id.menu_saveprogress:
			setResult(ReaderActivity.SAVE_MARK);
			finish();
			break;
		case R.id.menu_load_progress:
			startActivityForResult(new Intent(this, MarkListActivity.class), 0);
			break;
		}
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if( requestCode == 0)
		{
			Intent i = new Intent();
			i.putExtra("mark",data);
			setResult(ReaderActivity.LOAD_MARK);
			finish();
		}
	}


	private void buildFontDialog()
	{
		 LayoutInflater factory = LayoutInflater.from(this);
         fontDialogView = factory.inflate(R.layout.dialog_font_size, null);
         fontDialog = new AlertDialog.Builder(this)
         .setIcon(android.R.drawable.ic_dialog_info)
         .setTitle("字体设置")
         .setView(fontDialogView)
         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {

            	 SeekBar seekbar = (SeekBar) fontDialogView.findViewById(R.id.font_sekkbar);
			        int position = seekbar.getProgress();
			        fontDialog.dismiss();
				setFont(caculateFontSize(position));
				
             }
         })
         .setNegativeButton("取消", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {

            	 cancelMenu();
             }
         })
         .create();

		SeekBar seekbar = (SeekBar) fontDialogView.findViewById(R.id.font_sekkbar);
		seekbar.setMax(15);
		 int currentFont = Configuration.getInstance().getFontSize();
		 seekbar.setProgress((currentFont-12)/2);
		 
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextSwitcher textview = (TextSwitcher) fontDialogView.findViewById(R.id.font_switcher);
				TextView tv = (TextView) textview.getChildAt(0);
				tv.setTextSize(caculateFontSize(progress));
				tv.setText( caculateFontSize(progress) + "号字体");
			}
		});
		
		TextSwitcher switcher = (TextSwitcher) fontDialogView.findViewById(R.id.font_switcher);
		switcher.setFactory(new ViewFactory()
		{

			@Override
			public View makeView() {
				
		        TextView t = new TextView(fontDialog.getContext());
		        t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		        SeekBar seekbar = (SeekBar) fontDialogView.findViewById(R.id.font_sekkbar);
		        int position = seekbar.getProgress();
		        t.setTextSize(caculateFontSize(position));
				return t;
			}
			
		});

        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);
        TextSwitcher textview = (TextSwitcher) fontDialogView.findViewById(R.id.font_switcher);
		TextView tv = (TextView) textview.getChildAt(0);
		tv.setTextSize(currentFont);
		tv.setText( currentFont + "号字体");
       
       fontDialog.show();
        
	}

	private int caculateFontSize(int position)
	{
		return position*2 + 12;
	}
	
	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if(view instanceof ImageButton )
		{
			if( hasFocus)
			{
				((ImageButton)view).setImageResource(R.drawable.imagebutton_hightlight);
			}
			else
			{
				((ImageButton)view).setImageDrawable(null);
			}
		}
		
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(v instanceof ImageButton )
		{
			if( event.getAction() == MotionEvent.ACTION_DOWN)
			{
				((ImageButton)v).setImageResource(R.drawable.imagebutton_hightlight);
				Log.i("Reader", "Motion:"+MotionEvent.ACTION_DOWN + "" );
			}
			else if(event.getAction() == MotionEvent.ACTION_UP ||  event.getAction() == MotionEvent.ACTION_OUTSIDE)
			{
				((ImageButton)v).setImageDrawable(null);	
				Log.i("Reader", "Motion:"+event.getAction() +"");
				onClick(v);
			}
			else if(event.getAction() == MotionEvent.ACTION_MOVE )
			{
				Rect r = new Rect();
//				v.getGlobalVisibleRect(r);
				v.getDrawingRect(r);
				
				if( !r.contains( (int)event.getX(), (int)event.getY()))
				{
					((ImageButton)v).setImageDrawable(null);
				}
				else
				{
					if(((ImageButton)v).getDrawable()==null )
					{
					((ImageButton)v).setImageResource(R.drawable.imagebutton_hightlight);
					}
				}
				Log.i("Reader", "Motion:"+event.getAction() +"");
			}
		}
		return true;
	}

	private void setFont(int fontsize) {
		Configuration.getInstance().setFontSize(fontsize);
		Configuration.getInstance().save();
		setResult(ReaderActivity.FONT_SETUP);
		finish();
	}

	private void cancelMenu()
	{
		setResult(ReaderActivity.DO_NOTHING);
		finish();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			setResult(ReaderActivity.DO_NOTHING);
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
