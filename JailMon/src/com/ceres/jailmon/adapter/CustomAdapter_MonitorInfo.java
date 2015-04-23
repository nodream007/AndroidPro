/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MonitorInfo;
import com.ceres.jailmon.data.MonitorInfoList;

public class CustomAdapter_MonitorInfo extends BaseAdapter {
	class ListItemView {
		public ImageView imgItem;
		public TextView tvItem;
	};

	protected LayoutInflater listContainer;

	protected Context context;
	protected String[] m_txtItems;
	protected MonitorInfoList m_infolist;
	protected int[] m_imgItems;
	protected int m_nCount;
	protected int m_imgItem = -1;

	public CustomAdapter_MonitorInfo(Context ctx, MonitorInfoList infolist,
			int imgItem) {
		listContainer = LayoutInflater.from(ctx);
		context = ctx;
		m_infolist = infolist;
		m_imgItem = imgItem;
	}

	@Override
	public int getCount() {

		if (m_infolist != null)
			return m_infolist.m_list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.custom_list_item_main,
					null);

			listItemView.imgItem = (ImageView) convertView
					.findViewById(R.id.imgIcon);
			listItemView.tvItem = (TextView) convertView
					.findViewById(R.id.list_item_1_text);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		if (m_infolist != null) {
			MonitorInfo item = m_infolist.m_list.get(position);
			if (item != null)
				listItemView.tvItem.setText(item.toString());

			if (item.available)
				listItemView.tvItem.setTextColor(Color.WHITE);
			else
				listItemView.tvItem.setTextColor(Color.GRAY);
		}

		if (m_imgItems != null) {
			listItemView.imgItem.setImageResource(m_imgItems[position]);
		} else if (m_imgItem != -1) {
			listItemView.imgItem.setImageResource(m_imgItem);
		}

		return convertView;
	}

}