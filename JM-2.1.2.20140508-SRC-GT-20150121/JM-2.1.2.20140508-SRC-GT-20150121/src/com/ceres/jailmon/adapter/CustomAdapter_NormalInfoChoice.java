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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.BaseData;

public class CustomAdapter_NormalInfoChoice  extends CustomAdapter_NormalInfo{

	public boolean[] m_state;
	
	static class ListItemChoiceView {
		public TextView tvHeadline;
		public TextView tvContent;
		public CheckBox checkItem;
	};
	
	public CustomAdapter_NormalInfoChoice(Context ctx, List<?> list) {
		super(ctx, list);
		
		m_state = new boolean[list.size()];
		for( int i = 0; i<m_state.length; i++)
			m_state[i] = false;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemChoiceView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemChoiceView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_normal_choise, null);

			listItemView.tvHeadline = (TextView) convertView
					.findViewById(R.id.textHeadline);
			listItemView.tvContent = (TextView) convertView
					.findViewById(R.id.textContent);
			listItemView.checkItem = (CheckBox) convertView
					.findViewById(R.id.checkItem);
			
			
			listItemView.checkItem.setOnCheckedChangeListener( new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					m_state[position] = isChecked;
				}}
				);
			
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemChoiceView) convertView.getTag();
		}

		BaseData info = items.get(position);
		
		if( info != null )
		{		
			String strHeadline = info.getHeadline();
			if(  strHeadline != null )
				listItemView.tvHeadline.setText(strHeadline);
			
			String strContent = info.getContent();
			if( strContent != null )
				listItemView.tvContent.setText(strContent);
		}
		
		return convertView;
	}
}
