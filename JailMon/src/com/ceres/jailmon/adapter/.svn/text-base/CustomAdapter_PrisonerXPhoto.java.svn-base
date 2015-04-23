package com.ceres.jailmon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Prisoner;
import com.ceres.jailmon.data.PrisonerList;

public class CustomAdapter_PrisonerXPhoto extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected PrisonerList m_infolist;

	static class ListItemView {
		public ImageView imgPhoto;
		public TextView textName;
		public TextView personId;
	};

	public CustomAdapter_PrisonerXPhoto(Context ctx, PrisonerList infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_photo, null);

			listItemView.imgPhoto = (ImageView) convertView
					.findViewById(R.id.imgPhoto);
			listItemView.textName = (TextView) convertView
					.findViewById(R.id.textName);
			listItemView.personId = (TextView) convertView
					.findViewById(R.id.prisoner_id);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Prisoner info = m_infolist.m_list.get(position);

		if (info != null) {
			String strName = info.getName();
			if (strName != null)
				listItemView.textName.setText(strName);

			if (info.getVIP())
				listItemView.textName.setTextColor(Color.RED);

			Bitmap bmpPhoto = info.getPhoto();
			if (bmpPhoto != null)
				listItemView.imgPhoto.setImageBitmap(bmpPhoto);
			else
				listItemView.imgPhoto
						.setImageResource(R.drawable.prisoner_sample);
			String prisonerId = info.getID();
			listItemView.personId.setText(prisonerId);
		}

		return convertView;
	}

	public final int getCount() {
		return m_infolist.m_list.size();
	}

	public final Object getItem(int position) {
		return m_infolist.m_list.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}
}
