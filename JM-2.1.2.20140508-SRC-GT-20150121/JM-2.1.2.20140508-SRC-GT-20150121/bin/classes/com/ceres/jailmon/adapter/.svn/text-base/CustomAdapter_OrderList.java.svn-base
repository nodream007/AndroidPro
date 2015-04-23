/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.TradeInfo;

public class CustomAdapter_OrderList extends BaseAdapter {
	
	class ListItemView
	{
		public TextView txtTime;
		public TextView txtType;
		public TextView txtMoney;
		public TextView txtRemain;
	}
	public Context context;
	public List<TradeInfo> items;
	
	private LayoutInflater listContainer;

	public CustomAdapter_OrderList(Context ctx, List<TradeInfo> list) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		items = list;
	}

	@Override
	public int getCount() {
		return items.size();
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

			convertView = listContainer.inflate(
					R.layout.custom_list_trade_info, null);

			listItemView.txtTime = (TextView) convertView
					.findViewById(R.id.textTime);
			listItemView.txtType = (TextView) convertView
					.findViewById(R.id.textType);
			listItemView.txtMoney = (TextView) convertView
					.findViewById(R.id.textMoney);
			listItemView.txtRemain = (TextView) convertView
					.findViewById(R.id.textRemain);
			
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		
		listItemView.txtTime.setText(items.get(position).time);
		listItemView.txtType.setText(items.get(position).type );
		listItemView.txtMoney.setText(items.get(position).money);
		listItemView.txtRemain.setText(items.get(position).balance );

		
		return convertView;
	}
}
