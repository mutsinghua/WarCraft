package com.feifeinet.reader.warcraft.ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;

import com.feifeinet.reader.warcraft.data.Constant;
import com.feifeinet.reader.warcraft.data.GlobalDataManager;
import com.feifeinet.utils.Tools;

public class SplashAcitivty extends BaseActivity {
    

	private Context context;
	
	private long time = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        int currentFlash = Constant.SPLASH_PIC[(int) (System.currentTimeMillis()%13)];
        Drawable flash = getResources().getDrawable(currentFlash);
        if (flash != null) {
           
            ImageView splashview = (ImageView) findViewById(R.id.splash_image);
//            splashview.setBackgroundColor(color);
            splashview.setBackgroundDrawable(flash);
        }
        doCheck();
        time = System.currentTimeMillis();
//        context = this;
    }
    
    /**
     * 进度对话框
     */
    private ProgressDialog pd ;
    
    private Thread thread = new Thread(){
    	public void run()
    	{
    		copyFile();
    	}

		
    };
    
    /**
     * 进度报告
     */
    private static final byte PROCESS_REPORT = 1;
    
    /**
     * 复制完成
     */
    private static final byte PROCESS_DONE = 2;  
    
    /**
     * 闪屏时间到
     */
    private static final byte TIMES_UP = 3;
    
    
    private Handler handler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		
    		switch( msg.what)
    		{
    		case PROCESS_REPORT:
    			pd.setProgress(msg.arg1);
    			break;
    		case PROCESS_DONE: //消失后，继续执行
    			
    			GlobalDataManager.getInstance().setBooleanData("COPYED", true);
    			pd.dismiss();
    			long time2 = System.currentTimeMillis() - time;
    			if( time2 < 3000 )
    			{
    				handler.sendEmptyMessageDelayed(TIMES_UP, 3000-time2);
    				break;
    			}
    		case TIMES_UP:
    			if(!GlobalDataManager.getInstance().getBooleanData("COPYED", false) )
    			{
    				return;
    			}
    	        Intent i = new Intent(SplashAcitivty.this, SplashLogoActivity.class);
    	      
    	        startActivityForResult(i, 0);
    	        overridePendingTransition(R.anim.fade, 0);
    	     
    			break;
    		}
    	}
    };
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}

	/**
     * 检查是否需要复制文件 
     */
    private void doCheck()
    {
    	
    	boolean copyed = GlobalDataManager.getInstance().getBooleanData("COPYED", false);
    	if( !copyed)
    	{
    		pd = new ProgressDialog(this);
    		pd.setCancelable(false);
    		pd.setMax(Constant.FILE_LIST.length);
    		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		pd.setMessage("程序第一次启动，初始化中...");
    		pd.show();
    		thread.start();
    	}
    	handler.sendEmptyMessageDelayed(TIMES_UP, 3000);
    }
    
    /**
     * 开始复制文件
     */
    private void copyFile() {
		for( int i=0;i< Constant.FILE_LIST.length;i++)
		{
			BufferedInputStream dis = null;
			InputStream is = null;
			BufferedOutputStream dos = null;
			try {
				is = getAssets().open(Constant.FILE_LIST[i]);
			
			dis = new BufferedInputStream(is);
			String path = Tools.getStorePath(context, Constant.FILE_PATH );
			path = path +"/"+ Constant.FILE_LIST[i];
			File file = new File(path);
			if( file.exists())
			{
				file.delete();
			}
			dos = new BufferedOutputStream(new FileOutputStream(path));
			doCopy(dis,dos);
			dis.close();
			dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.what = PROCESS_REPORT;
			msg.arg1 = i+1;
			handler.sendMessage(msg);
		}
		Message msg = new Message();
		msg.what = PROCESS_DONE;
		
		handler.sendMessage(msg);
	}
    
    /**
     * 复制一个文件
     */
    private void doCopy(BufferedInputStream dis, BufferedOutputStream dos)
    {
    	byte[] buf = new byte[64*1024];
    	try {
			int r = dis.read(buf);
			while ( r!= -1)
			{
				dos.write(buf, 0, r);
				r = dis.read(buf);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}