package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.SendMedicineActivity;
import com.ceres.jailmon.data.MedRoundSendMedTotalPatient;

public class CustomAdapter_MedIllType extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;
	private String mCid;
	protected List<String> m_infolist;
	public static final String CELL_ID = "cell_id";
	public static final String PERSON_ID = "person_id";
	private int mPosition = -1;
	static class ListItemView {
		public TextView illTypeText;
	};

	public CustomAdapter_MedIllType(Context ctx, List<String> infolist,String cId) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
		mCid = cId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.med_total_situaition_ill_type, null);

			listItemView.illTypeText = (TextView) convertView
					.findViewById(R.id.ill_type);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		if(mPosition == position){
			convertView.setBackgroundResource(R.drawable.sub_02_selector);
		}else{
			convertView.setBackgroundResource(R.drawable.test);
		}
		String illTypeNameStr = m_infolist.get(position);
		listItemView.illTypeText.setText(illTypeNameStr);
		return convertView;
	}

	public void setPostion(int position){
		mPosition = position;
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
