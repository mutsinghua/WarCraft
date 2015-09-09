package com.feifeinet.reader.warcraft.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class ReadView extends TextView {

	private DrawListener drawListener;
	
	public ReadView(Context context) {
		super(context);
		
	}
	

	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public void setOnDrawListener(DrawListener dl)
	{
		drawListener = dl;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.i("Reader","Ondraw");
		if(drawListener != null)
		{
			Log.i("Reader","Ondrawed");
			drawListener.onDrawed();
			
		}
	}
}
