package com.ceres.jailmon.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.IndexMajorSecurity;

public class CustomAdapter_IndexMajorSecurity extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<IndexMajorSecurity> m_infolist = new ArrayList<IndexMajorSecurity>();

	static class ListItemView {
		public ImageView imgPhoto;
		public TextView textName;
	};

	public CustomAdapter_IndexMajorSecurity(Context ctx, List<IndexMajorSecurity> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ListItemView listItemView = null;
		
		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.index_major_security, null);

			listItemView.imgPhoto = (ImageView) convertView
					.findViewById(R.id.major_security_pic);
			listItemView.textName = (TextView) convertView
					.findViewById(R.id.major_security_name);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		IndexMajorSecurity indexOutPrisoner = m_infolist.get(position);
		String name = indexOutPrisoner.getName();
		listItemView.textName.setText(name);
		Bitmap bmp = indexOutPrisoner.getBmp();
		Drawable drawable =new BitmapDrawable(bmp);
		if (bmp != null) {
			listItemView.imgPhoto.setBackgroundDrawable(drawable);
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
