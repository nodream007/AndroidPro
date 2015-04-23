/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */

package com.ceres.jailmon.adapter;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ceres.jailmon.R;

abstract class MenuListChoice
{
	String m_strHeadline;
	String m_strContent;
	
	public abstract String getHeadline();
	public abstract String getContent();
	
}

public class CustomAdapter_MainListChoice extends BaseAdapter {
	
	class ListItemView {
		public CheckBox ckItem;
		public TextView tvItem;
	};
	
	protected LayoutInflater listContainer;
	
	protected Context context;
	protected String[] m_txtItems;
	protected List<?> m_listItems;
	protected int m_nCount;
	private HashMap<Integer, Boolean> isSelected; 

	public CustomAdapter_MainListChoice(Context ctx, String[] txtItems, int count ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		m_txtItems = txtItems;
		m_nCount = count;
		isSelected = new HashMap<Integer, Boolean>();  
		for (int i = 0; i < m_txtItems.length; i++)  
            getIsSelected().put(i, false);  
	}
	
	
	public CustomAdapter_MainListChoice(Context ctx, List<?>listItems ) {
		listContainer = LayoutInflater.from(ctx ); 
		context = ctx;
		m_listItems = listItems;
		isSelected = new HashMap<Integer, Boolean>();  
		for (int i = 0; i < m_listItems.size(); i++)  
            getIsSelected().put(i, false);  
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_main_choice, null);

			listItemView.ckItem = (CheckBox) convertView
					.findViewById(R.id.checkBox_item);
			listItemView.tvItem = (TextView) convertView
					.findViewById(R.id.list_item_1_text);		

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
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
		
		listItemView.ckItem.setChecked(getIsSelected().get(position));  
	
		
		listItemView.ckItem.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isSelected.put(position, isChecked);				
			}
			
		});
		
		return convertView;
	}

	public HashMap<Integer, Boolean> getIsSelected() {  
        return isSelected;  
    }  
  
    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
    	this.isSelected = isSelected;  
    }  
}