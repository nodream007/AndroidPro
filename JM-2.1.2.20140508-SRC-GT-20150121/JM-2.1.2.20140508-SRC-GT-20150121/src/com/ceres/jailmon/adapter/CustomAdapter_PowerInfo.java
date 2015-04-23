/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.PowerInfo;


@SuppressLint("NewApi")
public class CustomAdapter_PowerInfo extends BaseAdapter {
	
	class ListItemView { // 自定义控件集合
		public ImageView imgItem;
		public TextView txtItem;
		public Switch switchItem;
	};
	
	private LayoutInflater listContainer;
	
	public Context context;
	public List<PowerInfo> items;
	protected int[] m_imgItems;

	public CustomAdapter_PowerInfo(Context ctx, int[] imgItems, List<PowerInfo> list) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		items = list;
		m_imgItems = imgItems;
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

	
	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_onoff, null);

			listItemView.imgItem = (ImageView) convertView
					.findViewById(R.id.imgIcon);
			listItemView.txtItem = (TextView) convertView
					.findViewById(R.id.textItem);
			listItemView.switchItem = (Switch) convertView
					.findViewById(R.id.switchItem);
		

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		
		if( m_imgItems != null && position<m_imgItems.length )
			listItemView.imgItem.setImageResource(m_imgItems[position]);
		
		listItemView.txtItem.setText(items.get(position).toString());
		
		listItemView.switchItem.setChecked( items.get(position).status);
		
		listItemView.switchItem.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				items.get(position).status = isChecked;				
			}
			
		});
		/*listItemView.btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				GoodsInfo info= items.get(selectID);
				// 显示物品详情
				if( AddSum(info.price) )
				{
					info.num++;
					notifyDataSetChanged();
				}
				else
					Toast.makeText(context, "超出账户余额", Toast.LENGTH_SHORT).show();
			}
		});*/

		return convertView;
	}

}