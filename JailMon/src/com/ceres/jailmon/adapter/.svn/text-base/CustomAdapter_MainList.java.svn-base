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
import android.widget.ImageView;
import android.widget.TextView;
import com.ceres.jailmon.R;

abstract class MenuList
{
	String m_strHeadline;
	String m_strContent;
	
	public abstract String getHeadline();
	public abstract String getContent();
	
}

public class CustomAdapter_MainList extends BaseAdapter {
	
	class ListItemView {
		public ImageView imgItem;
		public TextView tvItem;
	};
	
	protected LayoutInflater listContainer;
	
	protected Context context;
	protected String[] m_txtItems;
	protected List<?> m_listItems;
	protected int[] m_imgItems;
	protected int m_nCount;
	protected int m_imgItem = -1;
	protected int mSelectedPosition = -1;

	public CustomAdapter_MainList(Context ctx, String[] txtItems, int[] imgItems, int count ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		m_txtItems = txtItems;
		m_imgItems = imgItems;
		m_nCount = count;
	}
	
	public CustomAdapter_MainList(Context ctx, String[] txtItems, int imgItem, int count ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		m_txtItems = txtItems;
		m_imgItem = imgItem;
		m_nCount = count;
	}
	
	public CustomAdapter_MainList(Context ctx, List<?>listItems, int imgItem ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		m_listItems = listItems;
		m_imgItem = imgItem;
	}

	@Override
	public int getCount() {
		
		if( m_txtItems != null )
			return m_nCount;
		else if( m_listItems != null )
			return m_listItems.size();
		else
			return 0;
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
					R.layout.custom_list_item_main, null);

			listItemView.imgItem = (ImageView) convertView
					.findViewById(R.id.imgIcon);
			listItemView.tvItem = (TextView) convertView
					.findViewById(R.id.list_item_1_text);		
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		if(mSelectedPosition == position){
			convertView.setBackgroundResource(R.drawable.sub_02_selector);
		}else{
			convertView.setBackgroundResource(R.drawable.test);
		}
		
		if( m_txtItems != null )
		{
			if(  m_txtItems[position] != null )
				listItemView.tvItem.setText(m_txtItems[position]);
		}
		else if( m_listItems != null )
		{
			Object item = m_listItems.get(position);
			if( item != null )
				listItemView.tvItem.setText( item.toString() );			
		}
		
		if( m_imgItems != null )
		{
			listItemView.imgItem.setImageResource(m_imgItems[position]);
		}
		else if( m_imgItem != -1 )
		{			
			listItemView.imgItem.setImageResource(m_imgItem);
		}
		
		return convertView;
	}
	
	public void setPosition(int position){
		mSelectedPosition = position;
		notifyDataSetChanged();
	}
	
	public void setList(List<?> listItems){
		m_listItems = listItems;
	}

}