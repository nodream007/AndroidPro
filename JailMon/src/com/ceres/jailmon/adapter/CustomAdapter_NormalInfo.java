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

public class CustomAdapter_NormalInfo extends BaseAdapter {

	static class ListItemView {
		public TextView tvHeadline;
		public TextView tvContent;
	};

	protected LayoutInflater listContainer;

	protected Context context;
	protected List<BaseData> items;

	public CustomAdapter_NormalInfo(Context ctx, List<?> list) {
		listContainer = LayoutInflater.from(ctx);
		context = ctx;
		items = (List<BaseData>) list;
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
					R.layout.custom_list_item_normal, null);

			listItemView.tvHeadline = (TextView) convertView
					.findViewById(R.id.textHeadline);
			listItemView.tvContent = (TextView) convertView
					.findViewById(R.id.textContent);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		BaseData info = items.get(position);

		if (info != null) {
			String strHeadline = info.getHeadline();
			if (strHeadline != null)
				listItemView.tvHeadline.setText(strHeadline);

			String strContent = info.getContent();
			if (strContent != null)
				listItemView.tvContent.setText(strContent+"\n");
		}

		return convertView;
	}

}