package com.ceres.jailmon.adapter;

import java.util.ArrayList;

import com.ceres.jailmon.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_AgentPhoto extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected ArrayList<Bitmap> m_infolist;

	static class ListItemView {
		public ImageView imgPhoto;
		public TextView textName;
	};

	public CustomAdapter_AgentPhoto(Context ctx, ArrayList<Bitmap> infolist) {
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
			
			listItemView.textName.setVisibility( View.GONE);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Bitmap bmp = m_infolist.get(position);
		
		if (bmp != null) {
			listItemView.imgPhoto.setImageBitmap(bmp);
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
