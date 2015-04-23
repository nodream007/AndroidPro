package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Police;

public class CustomAdapter_PoliceInfo extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<Police> m_infolist;

	static class ListItemView {
		public ImageView imgPhoto;
		public TextView textName;
		public TextView textType;
	};

	public CustomAdapter_PoliceInfo(Context ctx, List<Police> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_police_photo, null);

			listItemView.imgPhoto = (ImageView) convertView
					.findViewById(R.id.imgPhoto2);
			listItemView.textName = (TextView) convertView
					.findViewById(R.id.textName2);
			listItemView.textType = (TextView) convertView
					.findViewById(R.id.police_type);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Police info = m_infolist.get(position);

		if (info != null) {
			String strName = info.getName();
			if (strName != null)
				listItemView.textName.setText(strName);
			String type = info.getmType();
			if (!TextUtils.isEmpty(type))
				listItemView.textType.setText(type);
			Bitmap bmpPhoto = info.getPhoto();
			if (bmpPhoto != null)
				listItemView.imgPhoto.setImageBitmap(bmpPhoto);
			else
				listItemView.imgPhoto
						.setImageResource(R.drawable.prisoner_sample);
		}

		return convertView;
	}

	public final int getCount() {
		return m_infolist.size();
	}

	public final Object getItem(int position) {
		return m_infolist.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}
}
