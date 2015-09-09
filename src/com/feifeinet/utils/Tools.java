package com.feifeinet.utils;

import java.io.File;

import android.content.Context;

public class Tools {
	/**
	 * 有SD卡，先存sd卡
	 * @return
	 */
	public static String getStorePath(Context context,String path) {

		// 获取SdCard状态

		String state = android.os.Environment.getExternalStorageState();

		// 判断SdCard是否存在并且是可用的

		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {

			if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
				
			File file = new File(android.os.Environment.getExternalStorageDirectory()
					.getPath()+ path);
				if( !file.exists())
				{
					file.mkdirs();
				}
				return file.getAbsolutePath();

			}

		}

		return context.getFilesDir().getAbsolutePath();

	}
}
