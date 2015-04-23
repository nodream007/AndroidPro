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

import com.ceres.jailmon.CustomerListView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.BaseWarning;
import com.ceres.jailmon.data.Warning;

public class CustomAdapter_index extends BaseAdapter {
	private List<BaseWarning> mWarningList;

	static class ListItemView {
		public TextView warningTypeText;
		public CustomerListView warningTypeList;
	};

	protected LayoutInflater listContainer;

	protected Context context;
	protected List<Warning> items;

	public CustomAdapter_index(Context ctx, List<?> list) {
		listContainer = LayoutInflater.from(ctx);
		context = ctx;
		items = (List<Warning>) list;
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
					R.layout.warning_list_content, null);

			listItemView.warningTypeText = (TextView) convertView
					.findViewById(R.id.waring_type);
			listItemView.warningTypeList = (CustomerListView) convertView
					.findViewById(R.id.warning_list_list);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Warning info = items.get(position);

		if (info != null) {
			String warningType = info.getType();
			if (warningType != null)
				listItemView.warningTypeText.setText(warningType);
				mWarningList =  (List<BaseWarning>) info.getWarningList();
				CustomAdapter_Waring_List adapter = new CustomAdapter_Waring_List(context,mWarningList);
				listItemView.warningTypeList.setAdapter(adapter);
		}

		return convertView;
	}
	
	public void setData(List<?> list){
		items = (List<Warning>) list;
		notifyDataSetChanged();
	}

}