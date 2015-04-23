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
import com.ceres.jailmon.data.PrisonerList;

public class CustomAdapter_illTypePerson extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<String> m_infolist;

	static class ListItemView {
		public ImageView imgPhoto;
		public TextView textName;
	};

	public CustomAdapter_illTypePerson(Context ctx, List<String> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.ill_type_person, null);

			listItemView.imgPhoto = (ImageView) convertView
					.findViewById(R.id.ill_type_person_icon);
			listItemView.textName = (TextView) convertView
					.findViewById(R.id.ill_type_person_name);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		String personName = m_infolist.get(position);

		if (!TextUtils.isEmpty(personName)) {
				listItemView.textName.setText(personName);
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
