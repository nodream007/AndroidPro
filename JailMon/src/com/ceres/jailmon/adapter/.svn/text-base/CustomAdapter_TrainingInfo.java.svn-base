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
import android.widget.Button;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Training;

public class CustomAdapter_TrainingInfo  extends BaseAdapter {
	
	
	class ListItemView
	{
		public TextView txtName;
		public Button btnPlay;
	}
	
	public Context context;
	public List<Training> items;
	private LayoutInflater listContainer;
	View.OnClickListener onlistener;
	
	onPlayListener onplay;

	public CustomAdapter_TrainingInfo(Context ctx, List<Training> list, onPlayListener onplay ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		items = list;
		this.onplay = onplay;
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
		final int selectID = position;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_training_info, null);

			listItemView.txtName = (TextView) convertView
					.findViewById(R.id.textTrainingItem);
			listItemView.btnPlay = (Button) convertView
					.findViewById(R.id.buttonPlay);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		
		listItemView.txtName.setText(items.get(position).name);
		

		listItemView.btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Training info= items.get(selectID);
				
				onplay.onPlay(info);
			}
		});

		return convertView;
	}



}
