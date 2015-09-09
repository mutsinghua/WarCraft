package com.feifeinet.reader.warcraft.data;

import com.feifeinet.reader.warcraft.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 书目录生成
 * @author Rex
 *
 */
public class ListViewApater extends BaseAdapter {

	private Context context;
	
	public ListViewApater (Context context)
	{
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Constant.BOOK_NAME.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Constant.BOOK_NAME[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if( convertView != null)
		{
			TextView tv = (TextView) convertView.getTag();
			tv.setText(getItem(position).toString());
			return convertView;
		}
		else
		{
			LayoutInflater mli = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = mli.inflate(R.layout.book_content_item, null);
			TextView tv = (TextView) view.findViewById(R.id.book_content_text);
			tv.setText(getItem(position).toString());
			tv.setTextSize(22);
			
			view.setTag(tv);
			return view;
		}
		
	}

	
}
