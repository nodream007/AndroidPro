/*
DisciplineWarning * Description: Main menu
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
import com.ceres.jailmon.data.BaseData;
import com.ceres.jailmon.data.BaseWarning;

public class CustomAdapter_Waring_List extends BaseAdapter {

	static class ListItemView {
		public TextView nameText;
		public TextView baseInfoText;
		public TextView timeText;
	};

	protected LayoutInflater listContainer;

	protected Context context;
	protected List<BaseWarning> items;

	public CustomAdapter_Waring_List(Context ctx, List<BaseWarning> list) {
		listContainer = LayoutInflater.from(ctx);
		context = ctx;
		items = (List<BaseWarning>) list;
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
					R.layout.warning_list_list_content, null);
			listItemView.nameText = (TextView) convertView
					.findViewById(R.id.name);
			listItemView.baseInfoText = (TextView) convertView
					.findViewById(R.id.info);
			listItemView.timeText = (TextView) convertView
					.findViewById(R.id.time);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		BaseWarning info = items.get(position);
		if (info != null) {
			String name = info.getName();
			if (name != null)
				listItemView.nameText.setText(name);
			String baseinfo = info.getBaseInfo();
			if (baseinfo != null)
				listItemView.baseInfoText.setText(baseinfo);
			String time = info.getTime();
			if (time != null)
				listItemView.timeText.setText(time);
		}

		return convertView;
	}

}