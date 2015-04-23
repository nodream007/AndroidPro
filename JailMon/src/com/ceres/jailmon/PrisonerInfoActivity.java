/*
 * Description: 在押人员概况
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink Information Technology Co.Ltd
 */

package com.ceres.jailmon;

import java.util.ArrayList;
import java.util.List;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo;
import com.ceres.jailmon.adapter.CustomAdapter_OrderList;
import com.ceres.jailmon.data.*;

public class PrisonerInfoActivity extends BaseActivity {

	private LinearLayout m_layoutRooms, m_layoutPrisoners,
			m_layoutPrisonerDetail;
	private ListView m_listViewRoom, m_listViewPrisoner;

	private TextView m_tvPID, m_tvName, m_tvGender, m_tvNation, m_tvBday,
			m_tvIDCard, m_tvAddr, m_tvEngage, m_tvVIP;
	private TextView m_tvComment, m_tvLastBalance;
	
	private Button m_btnInput, m_btnSubmit, m_btnReturn;

	private CellList m_celllist = null;
	private PrisonerList m_prisonerlist = null;
	private PrisonerDetail m_prisonerDetail = null;
	
	private ImageView m_imgPhoto = null;
	
	private ListView m_lstBreakRule, m_lstTradeInfo;

	private AnimationUtil m_aniset;
	Cell m_cell = null;
	Prisoner m_prisoner = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.prisonerinfo);

		m_layoutRooms = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutPrisoners = (LinearLayout) findViewById(R.id.layoutPrisoners);
		m_layoutPrisonerDetail = (LinearLayout) findViewById(R.id.layoutPrisonerDetail);

		m_listViewRoom = (ListView) findViewById(R.id.lstRooms);
		m_listViewPrisoner = (ListView) findViewById(R.id.lstPrisoners);
		m_imgPhoto = (ImageView) findViewById(R.id.imagePortrait);
		
		m_lstBreakRule = (ListView)findViewById(R.id.lstBreakRule);
		m_lstTradeInfo = (ListView)findViewById(R.id.lstTradeInfo);

		Button btnBack = (Button) findViewById(R.id.buttonBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		m_listViewRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null)
				{
					m_cell = m_celllist.getCell(arg2);				
					loadPrisoners(m_cell );
				}
			}

		});

		m_listViewPrisoner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				Prisoner prisoner = m_prisonerlist.m_list.get((int) arg2);

				m_prisoner = prisoner;
				if (prisoner != null)
					loadPrisonerDetail(prisoner.getID());
			}

		});

		InitPrisonerDetailLayout();

		// Register BroadcastReceiver to track connection changes.
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		// receiver = new NetworkReceiver();
		// this.registerReceiver(receiver, filter);

		loadRooms();
	}

	LinearLayout[] m_layout = new LinearLayout[5];
	TextView[] m_txtlabel = new TextView[4];
	TextView m_txtBasicP2;

	void InitPrisonerDetailLayout() {
		m_tvPID = (TextView) findViewById(R.id.textPID);
		m_tvName = (TextView) findViewById(R.id.textName);
		m_tvGender = (TextView) findViewById(R.id.textGender);
		m_tvNation = (TextView) findViewById(R.id.textNation);
		m_tvBday = (TextView) findViewById(R.id.textBirthday);
		m_tvIDCard = (TextView) findViewById(R.id.textIDCard);
		m_tvAddr = (TextView) findViewById(R.id.textAddr);
		m_tvEngage = (TextView) findViewById(R.id.textEngage);
		m_tvVIP = (TextView) findViewById(R.id.textVIP);
		m_tvComment = (TextView) findViewById(R.id.textComment);
		m_tvLastBalance = (TextView) findViewById(R.id.textLastBalance);

		m_txtBasicP2 = (TextView) this.findViewById(R.id.textP2);
		
		m_btnInput = (Button)this.findViewById(R.id.buttonInput);

		m_layout[0] = (LinearLayout) this.findViewById(R.id.layout1);
		m_layout[1] = (LinearLayout) this.findViewById(R.id.layout2);
		m_layout[2] = (LinearLayout) this.findViewById(R.id.layout3);
		m_layout[3] = (LinearLayout) this.findViewById(R.id.layout4);
		m_layout[4] = (LinearLayout) this.findViewById(R.id.layout5);

		m_txtlabel[0] = (TextView) this.findViewById(R.id.textView1);
		m_txtlabel[1] = (TextView) this.findViewById(R.id.textView2);
		m_txtlabel[2] = (TextView) this.findViewById(R.id.textView3);
		m_txtlabel[3] = (TextView) this.findViewById(R.id.textView4);

		setLabelSelected(0);
		setLayoutVisibility(0);

		m_txtlabel[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(0);
				setLayoutVisibility(0);
				m_layoutPrisonerDetail.setBackgroundResource(R.drawable.tab_01);
				m_layout[0].startAnimation(m_aniset.m_AniFlipLeftIn);
			}
		});

		m_txtlabel[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(1);
				setLayoutVisibility(1);
				m_layoutPrisonerDetail.setBackgroundResource(R.drawable.tab_02);
				m_layout[1].startAnimation(m_aniset.m_AniFlipLeftIn);
				loadPrisonerRuleBreak(m_prisoner.getID());
			}
		});

		m_txtlabel[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(2);
				setLayoutVisibility(2);
				m_layoutPrisonerDetail.setBackgroundResource(R.drawable.tab_03);
				m_layout[2].startAnimation(m_aniset.m_AniFlipLeftIn);
				loadTradeInfoList(m_prisoner.getID());
			}
		});

		m_txtlabel[3].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(3);
				setLayoutVisibility(3);
				m_layoutPrisonerDetail.setBackgroundResource(R.drawable.tab_04);
				m_layout[3].startAnimation(m_aniset.m_AniFlipLeftIn);
			}
		});
		
		m_btnInput.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				InitInputRuleBreakItems();
				m_layout[1].setVisibility( View.GONE );
				m_layout[4].setVisibility( View.VISIBLE );				
			}});
	}
	
	
	CheckBox m_chkGroup;
	Spinner m_spinReason, m_spinLevel, m_spinPolice, m_spinSolution;
	TimePicker m_timepicker;
	
	
	boolean m_bGroup = false;
	int m_nHour, m_nMinute;
	
	void InitInputRuleBreakItems()
	{
		m_bGroup = false;
		
		if( m_chkGroup == null )
		{
			m_chkGroup = (CheckBox)this.findViewById(R.id.checkGroup);
			
			m_chkGroup.setOnCheckedChangeListener( new OnCheckedChangeListener() {
	            
	            @Override
	            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
	            	m_bGroup = arg1;
	            }
	        });
		}
		
		if(m_spinReason==null )
			m_spinReason = (Spinner)this.findViewById(R.id.spinnerReason);
		
		if( m_spinLevel == null )
		{
			m_spinLevel = (Spinner)this.findViewById(R.id.spinnerLevel);
			
			m_spinLevel.setOnItemSelectedListener( new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					int i = arg2;
					showInputRuleBreakReason( m_rbitemslist.m_list_level.get(i).id );					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}});
		}
		
		if(m_spinPolice==null )
			m_spinPolice = (Spinner)this.findViewById(R.id.spinnerPolice);
		
		if( m_spinSolution == null )
			m_spinSolution = (Spinner)this.findViewById(R.id.spinnerSolution);
		
		if( m_timepicker == null )			
		{
			m_timepicker = (TimePicker)this.findViewById(R.id.timePicker);
			
			m_timepicker.setOnTimeChangedListener( new OnTimeChangedListener() {  
		          
		        @Override  
		        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {  
		        	m_nHour = hourOfDay;
		        	m_nMinute = minute;
		        } 
			});

		}
		
		if( m_btnSubmit == null )
		{
			m_btnSubmit = (Button)this.findViewById(R.id.buttonSubmit);		
			
			m_btnSubmit.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					int n1, n2, n3, n4;
					
					n1 = m_spinReason.getSelectedItemPosition();
					n2 = m_spinLevel.getSelectedItemPosition();
					n3 = m_spinPolice.getSelectedItemPosition();
					n4 = m_spinSolution.getSelectedItemPosition();
					
					String strReason = m_rbitemslist.m_list_reason.get(n1).id;
					String strLevel = m_rbitemslist.m_list_reason.get(n2).id;
					String strPolice = m_rbitemslist.m_list_reason.get(n3).id;
					String strSolution = m_rbitemslist.m_list_reason.get(n4).id;
					
					String strTime = String.format("%d:%d", m_nHour, m_nMinute );
					
					String strContent = String.format("group=%s&reason=%s&level=%s&police=%s&solution=%s&time=%s",
							m_bGroup?"Y":"N", strReason,strLevel,strPolice,strSolution, strTime );
					
					Toast.makeText(getApplicationContext(), "正在提交" + strContent,
							Toast.LENGTH_LONG).show();
					
					showProcessDialog();
					postRuleBreakResult(m_handler, m_cell.getID(), m_prisoner.getID(), strContent );
					
				}});
		}
				
		if( m_btnReturn == null )
		{
			m_btnReturn = (Button)this.findViewById(R.id.buttonReturn);
			
			m_btnReturn.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					m_layout[4].setVisibility( View.GONE );
					m_layout[1].setVisibility( View.VISIBLE );					
				}});
		}
		
		if( m_rbitemslist == null )
			loadRuleBreakItemsList();
		else 
			showInputRuleBreakItems();
	}
	
	
	public List<RuleBreakItems> m_list_reason_showing = new ArrayList<RuleBreakItems>();
	
	void showInputRuleBreakReason( String levelID )
	{
		m_list_reason_showing.clear();
		
		RuleBreakItems items;
		
		for( int i=0; i<m_rbitemslist.m_list_reason.size(); i++ )
		{
			items = m_rbitemslist.m_list_reason.get(i);
			if( items.lid.equals(levelID) )
			{
				m_list_reason_showing.add(  items );
			}
		}
		
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner_item, R.id.TextView01, m_list_reason_showing );
        
		m_spinReason.setAdapter(adapter);
	}
	
	
	void showInputRuleBreakItems()
	{
		if( m_rbitemslist == null )
			return;
		
		
		if(m_spinLevel!=null && m_rbitemslist.m_list_level!=null)
		{
			ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner_item, R.id.TextView01, m_rbitemslist.m_list_level);
	        
	        m_spinLevel.setAdapter(adapter);
		}
		
		showInputRuleBreakReason(m_rbitemslist.m_list_level.get(0).id );
		
		if(m_spinPolice!=null && m_rbitemslist.m_list_police!=null)
		{
			ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner_item, R.id.TextView01, m_rbitemslist.m_list_police);
	        
	        m_spinPolice.setAdapter(adapter);
		}
		
		if(m_spinSolution!=null && m_rbitemslist.m_list_solution!=null)
		{
			ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner_item, R.id.TextView01, m_rbitemslist.m_list_solution);
	        
	        m_spinSolution.setAdapter(adapter);
		}
			
	}
	

	void setLayoutVisibility(int layout) {
		for (int i = 0; i < 4; i++) {
			if (i == layout)
				m_layout[i].setVisibility(View.VISIBLE);
			else
				m_layout[i].setVisibility(View.GONE);
		}
	}

	void setLabelSelected(int text) {
		for (int i = 0; i < 4; i++) {
			if (i == text)
				m_txtlabel[i].setSelected(true);
			else
				m_txtlabel[i].setSelected(false);
		}
	}

	void ShowPrisonerDetailInfo(PrisonerDetail pd) {
		String label_pid = getString(R.string.prisoner_pid);
		String label_name = getString(R.string.prisoner_name);
		String label_gender = getString(R.string.prisoner_gender);
		String label_nation = getString(R.string.prisoner_nation);
		String label_birthday = getString(R.string.prisoner_birthday);
		String label_idcard = getString(R.string.prisoner_idcard);
		String label_address = getString(R.string.prisoner_address);
		String label_engagedate = getString(R.string.prisoner_engagedate);
		String label_vip = getString(R.string.prisoner_vip);
		String label_litigation = getString(R.string.prisoner_litigation);
		String label_case_name = getString(R.string.prisoner_type);
		String label_case_unit = getString(R.string.prisoner_case_unit);
		String label_case_detail = getString(R.string.prisoner_case_detail);

		m_tvPID.setText(String.format("%s：%s", label_pid, pd.pid));
		m_tvName.setText(String.format("%s：%s", label_name, pd.name));
		m_tvGender.setText(String.format("%s：%s", label_gender, pd.gender));
		m_tvNation.setText(String.format("%s：%s", label_nation, pd.nation));
		m_tvBday.setText(String.format("%s：%s", label_birthday, pd.birthday));
		m_tvIDCard.setText(String.format("%s：%s", label_idcard, pd.idcard));
		m_tvAddr.setText(String.format("%s：%s", label_address, pd.address));
		m_tvEngage.setText(String.format("%s：%s", label_engagedate,
				pd.engagedate));
		m_tvVIP.setText(String.format("%s：%s", label_vip, pd.vip));
		m_tvComment.setText( pd.comment );

		String p2 = String.format("\n%s：%s\n\n%s：%s\n\n%s：%s\n\n%s：%s",
				label_case_name, pd.case_name, label_litigation, pd.litigation,
				label_case_unit, pd.case_unit, label_case_detail,
				pd.case_detail);

		m_txtBasicP2.setText(p2);
	}
	
	RuleBreakItemsList m_rbitemslist;

	private Handler m_handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == API_GET_CELLLIST_OK) {
				
				closeProcessDialog();

				m_celllist = (CellList) msg.obj;

				if (m_celllist != null) {

					CustomAdapter_MainList adapter = new CustomAdapter_MainList(
							PrisonerInfoActivity.this, m_celllist.getList(),
							R.drawable.list_icon_home);

					m_listViewRoom.setAdapter(adapter);

					m_layoutRooms.setVisibility(View.VISIBLE);

					m_layoutRooms.startAnimation(m_aniset.m_aniFadeIn);

				} else {

				}
			} else if (msg.what == API_GET_PRISONERLIST_OK) {

				m_prisonerlist = (PrisonerList) msg.obj;

				if (m_prisonerlist != null) {
					ArrayAdapter adapter = new ArrayAdapter(
							PrisonerInfoActivity.this,
							R.layout.custom_list_item_sub,
							R.id.list_item_1_text, m_prisonerlist.m_list);

					m_listViewPrisoner.setAdapter(adapter);

					m_layoutPrisoners.setVisibility(View.VISIBLE);

					m_layoutPrisoners.startAnimation(m_aniset.m_AniSlideIn);
				}
			} else if (msg.what == API_GET_PRISONER_DETAIL_OK) {
				m_prisonerDetail = (PrisonerDetail) msg.obj;

				if (m_prisonerDetail != null) {

					m_layoutPrisonerDetail.setVisibility(View.VISIBLE);

					ShowPrisonerDetailInfo(m_prisonerDetail);

					m_layoutPrisonerDetail.startAnimation(m_aniset.m_aniFadeIn);
				}
			}
			else if (msg.what == API_GET_PRISONER_PHOTO_OK) {
				Bitmap bmp = (Bitmap) msg.obj;

				if (bmp != null) {

					m_imgPhoto.setImageBitmap(bmp);
				}
			} 
			else if (msg.what == API_GET_RULEBREAK_INFO_LIST_OK) {
				RuleBreakInfoList infolist = (RuleBreakInfoList) msg.obj;

				if (infolist != null) {

					CustomAdapter_NormalInfo adapter = new CustomAdapter_NormalInfo(
							PrisonerInfoActivity.this, infolist.getList());

					m_lstBreakRule.setAdapter(adapter);
					m_lstBreakRule.setVisibility( View.VISIBLE );
				}
			} 		
			
			else if(msg.what == API_GET_TRADE_INFO_LIST_OK )
			{
				TradeInfoList infolist = (TradeInfoList) msg.obj;
				
				if (infolist != null) {
					
					m_tvLastBalance.setText( "当前余额 : " + infolist.balance );
					m_tvLastBalance.setVisibility( View.VISIBLE );

					CustomAdapter_OrderList  adapter = new CustomAdapter_OrderList(PrisonerInfoActivity.this, infolist.m_list); 

					 m_lstTradeInfo.setAdapter(adapter);
					 m_lstTradeInfo.setVisibility(View.VISIBLE);

				}
				
			}
			
			else if(msg.what == API_GET_RULEBREAK_ITEMS_LIST_OK )
			{
				m_rbitemslist = (RuleBreakItemsList) msg.obj;	
				showInputRuleBreakItems();
			}
			
			else if( msg.what == API_POST_RULE_BREAK_INFO_OK )
			{
				RuleBreakResult ret = (RuleBreakResult)msg.obj;
				
				closeProcessDialog();
				
				if (ret != null && ret.m_bOK == true )
				{
					Toast.makeText(getApplicationContext(), "操作成功",
							Toast.LENGTH_LONG).show();
					
					loadPrisonerRuleBreak(m_prisoner.getID());
					m_layout[1].setVisibility( View.VISIBLE );
					m_layout[4].setVisibility( View.GONE );
				}
				else
					Toast.makeText(getApplicationContext(), "操作失败",
							Toast.LENGTH_LONG).show();
					
			}
			
			else if (msg.what == API_GET_FAIL) {
				closeProcessDialog();
				AppException exception = (AppException) msg.obj;
				exception.makeToast(PrisonerInfoActivity.this);
			}
		}
	};

	private void loadRooms() {

		m_layoutRooms.setVisibility(View.GONE);
		m_layoutPrisoners.setVisibility(View.GONE);
		m_layoutPrisonerDetail.setVisibility(View.GONE);
		
		showProcessDialog();

		getCellList(m_handler);
	}

	private void loadPrisoners( Cell cell ) {
		if( cell != null && cell.getID() != null )
		{
		m_layoutPrisoners.setVisibility(View.GONE);
		m_layoutPrisonerDetail.setVisibility(View.GONE);
		getPrisonerList(m_handler, cell.getID());
		}
	}

	private void loadPrisonerDetail(final String pid) {

		m_layoutPrisonerDetail.setVisibility(View.GONE);
		m_imgPhoto.setImageResource(R.drawable.prisoner_sample);		

		getPrisonserDetail(m_handler, pid);
		getPrisonserPhoto(m_handler, pid);
	}
	
	private void loadPrisonerRuleBreak( final String pid)
	{
		m_lstBreakRule.setVisibility( View.INVISIBLE );
		getRuleBreakInfoList(m_handler, pid);
	}
	
	private void loadTradeInfoList( final String pid )
	{
		m_lstTradeInfo.setVisibility(View.INVISIBLE);
		m_tvLastBalance.setVisibility(View.INVISIBLE);
		getTradeInfoList(m_handler, pid);
	}
	
	private void loadRuleBreakItemsList()
	{
		getRuleBreakItemsList(m_handler);
	}

}
