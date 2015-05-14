/*
 * Description: 在押人员概况
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink Information Technology Co.Ltd
 */

package com.ceres.jailmon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo;
import com.ceres.jailmon.adapter.CustomAdapter_OrderList;
import com.ceres.jailmon.adapter.CustomAdapter_PolicePhoto;
import com.ceres.jailmon.adapter.CustomAdapter_PrisonerXPhoto;
import com.ceres.jailmon.data.*;

public class PrisonerInfoGridActivity extends BaseActivity {

	private LinearLayout m_layoutCells, m_layoutPrisoners;
	private ListView m_lvCells;
	private CellList m_celllist = null;
	private PrisonerList m_prisonerlist = null;
	private AnimationUtil m_aniset;

	private GridView m_grid_prisoner;
	private GridView m_grid_police;

	Cell m_cell = null;
	Prisoner m_prisoner = null;

	private LinearLayout m_layoutPrisonerDetail;

	private TextView m_tvPID, m_tvName, m_tvGender, m_tvNation, m_tvBday,
			m_tvIDCard, m_tvAddr, m_tvEngage, m_tvVIP;
	private TextView m_tvComment, m_tvLastBalance;
	private Button m_btnInput, m_btnSubmit, m_btnReturn;
	private PrisonerDetail m_prisonerDetail = null;
	private ImageView m_imgPhoto = null;
	private TextView m_txtSum;
	private ListView m_lstBreakRule, m_lstTradeInfo;
	CustomAdapter_PrisonerXPhoto m_adpter_grid_prisoner = null;
	CustomAdapter_PolicePhoto m_adpter_grid_police = null;
	private String mCellType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.prisonerinfo_grid);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutPrisoners = (LinearLayout) findViewById(R.id.layoutPrisoners);
		m_layoutPrisonerDetail = (LinearLayout) findViewById(R.id.layoutPrisonerDetail);

		m_imgPhoto = (ImageView) findViewById(R.id.imagePortrait);
		m_lstBreakRule = (ListView) findViewById(R.id.lstBreakRule);
		m_lstTradeInfo = (ListView) findViewById(R.id.lstTradeInfo);

		m_lvCells = (ListView) findViewById(R.id.lstRooms);

		m_txtSum = (TextView) findViewById(R.id.textSum);

		Button btnBack = (Button) findViewById(R.id.buttonBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (m_layoutPrisonerDetail.getVisibility() == View.VISIBLE) {
					m_layoutPrisonerDetail.setVisibility(View.GONE);
					m_layoutPrisoners.setVisibility(View.VISIBLE);
				} else
					finish();
			}

		});

		m_lvCells.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null) {
					m_cell = m_celllist.getCell(arg2);
					mCellType = m_cell.getM_strType();
					loadPrisoners(m_cell);
				}
			}

		});

		initPoliceGridViews();

		initPrisonerGridViews();

		initPrisonerDetailLayout();

		loadRooms();
	}

	private void initPoliceGridViews() {
		m_grid_police = (GridView) findViewById(R.id.GridPolices);

		m_grid_police.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_prisonerlist == null)
					return;

				Police police = m_prisonerlist.getPolice(arg2);
				if (police == null)
					return;

				Intent intent = new Intent(PrisonerInfoGridActivity.this,
						NoteActivity.class);
				intent.putExtra("ID", police.getID());
				intent.putExtra("Name", police.getName());
				intent.putExtra("Role", "Police");
				startActivity(intent);
			}
		});
	}

	private void initPrisonerGridViews() {
		m_grid_prisoner = (GridView) findViewById(R.id.GridPrisoners);

		m_grid_prisoner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				loadPrisonerDetail(arg2);
			}
		});
	}

	private void loadRooms() {

		m_layoutCells.setVisibility(View.GONE);
		m_layoutPrisoners.setVisibility(View.GONE);
		m_layoutPrisonerDetail.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}

	@Override
	protected void onReceiveCellList(CellList celllist) {
		closeProcessDialog();

		m_celllist = celllist;

		if (m_celllist != null) {

			CustomAdapter_MainList adapter = new CustomAdapter_MainList(
					PrisonerInfoGridActivity.this, m_celllist.getList(),
					R.drawable.list_icon_home);

			m_lvCells.setAdapter(adapter);

			m_layoutCells.setVisibility(View.VISIBLE);

			m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);

		}
	}

	private void loadPrisoners(Cell cell) {

		if (cell != null && cell.getID() != null) {
			m_layoutPrisoners.setVisibility(View.GONE);
			m_layoutPrisonerDetail.setVisibility(View.GONE);

			getPrisonerList(m_basehandler, cell.getID());
		}
	}

	protected void onReceivePrisonerList(PrisonerList infolist) {

		if (m_prisonerlist != null)
			m_prisonerlist.recycle();

		m_prisonerlist = infolist;

		if (infolist != null) {

			int nTotal = infolist.getNum();

			// Calculate sum of VIP prisoner.
			int nVIP = 0;
			Prisoner prisoner;

			for (int i = 0; i < nTotal; i++) {
				prisoner = infolist.getPrisoner(i);

				if (prisoner != null && prisoner.getVIP() == true)
					nVIP++;
			}

			// Set TextView content

			String strSum = "监室类型：" + mCellType + "  "
					+ getString(R.string.cell_prisoners)
					+ Integer.toString(nTotal) + " / "
					+ getString(R.string.vip_prisoners)
					+ Integer.toString(nVIP);

			m_txtSum.setText(strSum);

			// Create adapter for grid.
			m_adpter_grid_prisoner = new CustomAdapter_PrisonerXPhoto(
					PrisonerInfoGridActivity.this, infolist);

			m_grid_prisoner.setAdapter(m_adpter_grid_prisoner);

			// Create adapter for police grid.
			m_adpter_grid_police = new CustomAdapter_PolicePhoto(
					PrisonerInfoGridActivity.this, infolist.m_listPolice);

			m_grid_police.setAdapter(m_adpter_grid_police);

			getAllPrisonserPhoto(m_basehandler, infolist);

			m_layoutPrisoners.setVisibility(View.VISIBLE);

			m_layoutPrisoners.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	protected void onReceiveAllPrisonerPhotoNotify() {
		m_adpter_grid_prisoner.notifyDataSetChanged();
	}

	protected void onReceiveAllPolicePhotoNotify() {
		m_adpter_grid_police.notifyDataSetChanged();
	}

	LinearLayout[] m_layout = new LinearLayout[5];
	TextView[] m_txtlabel = new TextView[4];
	TextView m_txtBasicP2;

	void initPrisonerDetailLayout() {
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

		m_btnInput = (Button) this.findViewById(R.id.buttonInput);

		int i;

		int[] layout_res_id = { R.id.layout1, R.id.layout2, R.id.layout3,
				R.id.layout4, R.id.layout5 };
		for (i = 0; i < m_layout.length; i++)
			m_layout[i] = (LinearLayout) this.findViewById(layout_res_id[i]);

		int[] txtlabel_res_id = { R.id.textView1, R.id.textView2,
				R.id.textView3, R.id.textView4 };
		for (i = 0; i < m_txtlabel.length; i++) {
			m_txtlabel[i] = (TextView) this.findViewById(txtlabel_res_id[i]);
			m_txtlabel[i].setWidth(148);
		}

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

		m_btnInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initInputRuleBreakItems();
				m_layout[1].setVisibility(View.GONE);
				m_layout[4].setVisibility(View.VISIBLE);
			}
		});
	}

	CheckBox m_chkGroup;
	Spinner m_spinReason, m_spinLevel, m_spinPolice, m_spinSolution;
	TimePicker m_timepicker;

	boolean m_bGroup = false;
	int m_nHour = -1, m_nMinute = -1;

	void initInputRuleBreakItems() {
		m_bGroup = false;

		if (m_chkGroup == null) {
			m_chkGroup = (CheckBox) this.findViewById(R.id.checkGroup);

			m_chkGroup
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean arg1) {
							m_bGroup = arg1;
						}
					});
		}

		if (m_spinReason == null)
			m_spinReason = (Spinner) this.findViewById(R.id.spinnerReason);

		if (m_spinLevel == null) {
			m_spinLevel = (Spinner) this.findViewById(R.id.spinnerLevel);

			m_spinLevel.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					int i = arg2;
					showInputRuleBreakReason(m_rbitemslist.m_list_level.get(i).id);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}

		if (m_spinPolice == null)
			m_spinPolice = (Spinner) this.findViewById(R.id.spinnerPolice);

		if (m_spinSolution == null)
			m_spinSolution = (Spinner) this.findViewById(R.id.spinnerSolution);

		if (m_timepicker == null) {
			m_timepicker = (TimePicker) this.findViewById(R.id.timePicker);

			m_timepicker.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					m_nHour = hourOfDay;
					m_nMinute = minute;
				}
			});

		}

		if (m_btnSubmit == null) {
			m_btnSubmit = (Button) this.findViewById(R.id.buttonSubmit);

			m_btnSubmit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					int n1, n2, n3, n4;

					n1 = m_spinReason.getSelectedItemPosition();
					n2 = m_spinLevel.getSelectedItemPosition();
					n3 = m_spinPolice.getSelectedItemPosition();
					n4 = m_spinSolution.getSelectedItemPosition();
					String strReason = "";
					String strLevel =  "";
					String strPolice =  "";
					String strSolution =  "";
					if(!m_rbitemslist.m_list_reason.isEmpty() &&  m_rbitemslist.m_list_reason.get(n1) != null){
					 strReason = m_rbitemslist.m_list_reason.get(n1).id;
					}
					if(!m_rbitemslist.m_list_level.isEmpty() && m_rbitemslist.m_list_level.get(n2) != null){
					 strLevel = m_rbitemslist.m_list_level.get(n2).id;
					}
					if(!m_rbitemslist.m_list_police.isEmpty() && m_rbitemslist.m_list_police.get(n3) != null){
					 strPolice = m_rbitemslist.m_list_police.get(n3).id;
					}
					if(!m_rbitemslist.m_list_solution.isEmpty() && m_rbitemslist.m_list_solution.get(n4) != null){
					 strSolution = m_rbitemslist.m_list_solution.get(n4).id;
					}
					String strTime;

					if (m_nHour == -1 || m_nMinute == -1) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"hh:mm");
						Date curDate = new Date(System.currentTimeMillis());
						strTime = formatter.format(curDate);
					} else
						strTime = String
								.format("%02d:%02d", m_nHour, m_nMinute);

					String strContent = String
							.format("group=%s&reason=%s&level=%s&police=%s&solution=%s&time=%s",
									m_bGroup ? "Y" : "N", strReason, strLevel,
									strPolice, strSolution, strTime);

					/*
					 * Toast.makeText(getApplicationContext(), "正在提交" +
					 * strContent, Toast.LENGTH_LONG).show();
					 */

					showProcessDialog();
					postRuleBreakResult(m_basehandler, m_cell.getID(),
							m_prisoner.getID(), strContent);

				}
			});
		}

		if (m_btnReturn == null) {
			m_btnReturn = (Button) this.findViewById(R.id.buttonReturn);

			m_btnReturn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					m_layout[4].setVisibility(View.GONE);
					m_layout[1].setVisibility(View.VISIBLE);
				}
			});
		}

		if (m_rbitemslist == null)
			loadRuleBreakItemsList();
		else
			showInputRuleBreakItems();
	}

	public List<RuleBreakItems> m_list_reason_showing = new ArrayList<RuleBreakItems>();

	void showInputRuleBreakReason(String levelID) {
		m_list_reason_showing.clear();

		RuleBreakItems items;

		for (int i = 0; i < m_rbitemslist.m_list_reason.size(); i++) {
			items = m_rbitemslist.m_list_reason.get(i);
			if (items.lid.equals(levelID)) {
				m_list_reason_showing.add(items);
			}
		}

		ArrayAdapter adapter = new ArrayAdapter(this,
				R.layout.custom_spinner_item, R.id.TextView01,
				m_list_reason_showing);

		m_spinReason.setAdapter(adapter);
	}

	void showInputRuleBreakItems() {
		if (m_rbitemslist == null)
			return;

		if (m_spinLevel != null && m_rbitemslist.m_list_level != null) {
			ArrayAdapter adapter = new ArrayAdapter(this,
					R.layout.custom_spinner_item, R.id.TextView01,
					m_rbitemslist.m_list_level);

			m_spinLevel.setAdapter(adapter);
		}

		showInputRuleBreakReason(m_rbitemslist.m_list_level.get(0).id);

		if (m_spinPolice != null && m_rbitemslist.m_list_police != null) {
			ArrayAdapter adapter = new ArrayAdapter(this,
					R.layout.custom_spinner_item, R.id.TextView01,
					m_rbitemslist.m_list_police);

			m_spinPolice.setAdapter(adapter);
		}

		if (m_spinSolution != null && m_rbitemslist.m_list_solution != null) {
			ArrayAdapter adapter = new ArrayAdapter(this,
					R.layout.custom_spinner_item, R.id.TextView01,
					m_rbitemslist.m_list_solution);

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

	void showPrisonerDetailInfo(PrisonerDetail pd) {
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
		m_tvComment.setText(pd.comment);

		String p2 = String.format("\n%s：%s\n\n%s：%s\n\n%s：%s\n\n%s：%s",
				label_case_name, pd.case_name, label_litigation, pd.litigation,
				label_case_unit, pd.case_unit, label_case_detail,
				pd.case_detail);

		m_txtBasicP2.setText(p2);
	}

	RuleBreakItemsList m_rbitemslist;

	private void loadPrisonerDetail(int index) {

		if (m_prisonerlist == null)
			return;

		m_prisoner = m_prisonerlist.getPrisoner(index);
		if (m_prisoner == null)
			return;

		m_layoutPrisonerDetail.setVisibility(View.GONE);
		m_imgPhoto.setImageResource(R.drawable.prisoner_sample);

		getPrisonserDetail(m_basehandler, m_prisoner.getID());
		getPrisonserPhoto(m_basehandler, m_prisoner.getID());
	}

	protected void onReceivePrisonerDetail(PrisonerDetail info) {
		m_prisonerDetail = info;

		if (m_prisonerDetail != null) {

			m_layoutPrisoners.setVisibility(View.GONE);
			m_layoutPrisonerDetail.setVisibility(View.VISIBLE);

			setLabelSelected(0);
			setLayoutVisibility(0);
			m_layoutPrisonerDetail.setBackgroundResource(R.drawable.tab_01);

			showPrisonerDetailInfo(m_prisonerDetail);

			m_layoutPrisonerDetail.startAnimation(m_aniset.m_aniFadeIn);
			m_layout[0].startAnimation(m_aniset.m_AniFlipLeftIn);
		}
	}

	protected void onReceivePrisonerPhoto(Bitmap bmp) {
		if (bmp != null)
			m_imgPhoto.setImageBitmap(bmp);
	}

	private void loadPrisonerRuleBreak(final String pid) {
		m_lstBreakRule.setVisibility(View.INVISIBLE);
		getRuleBreakInfoList(m_basehandler, pid);
	}

	protected void onReceiveRuleBreakInfoList(RuleBreakInfoList infolist) {
		if (infolist != null) {

			CustomAdapter_NormalInfo adapter = new CustomAdapter_NormalInfo(
					PrisonerInfoGridActivity.this, infolist.getList());

			m_lstBreakRule.setAdapter(adapter);
			m_lstBreakRule.setVisibility(View.VISIBLE);
		}
	}

	private void loadTradeInfoList(final String pid) {
		m_lstTradeInfo.setVisibility(View.INVISIBLE);
		m_tvLastBalance.setVisibility(View.INVISIBLE);
		getTradeInfoList(m_basehandler, pid);
	}

	void onReceiveTradeInfoList(TradeInfoList infolist) {
		if (infolist != null) {

			m_tvLastBalance.setText(getString(R.string.current_balance)
					+ infolist.balance);
			m_tvLastBalance.setVisibility(View.VISIBLE);

			CustomAdapter_OrderList adapter = new CustomAdapter_OrderList(
					PrisonerInfoGridActivity.this, infolist.m_list);

			m_lstTradeInfo.setAdapter(adapter);
			m_lstTradeInfo.setVisibility(View.VISIBLE);
		}
	}

	private void loadRuleBreakItemsList() {
		getRuleBreakItemsList(m_basehandler);
	}

	protected void onReceiveRuleBreakItemsList(RuleBreakItemsList infolist) {
		m_rbitemslist = infolist;
		showInputRuleBreakItems();
	}

	protected void onPostRuleBreakInfoResult(RuleBreakResult ret) {
		closeProcessDialog();

		boolean bOK = (ret != null && ret.m_bOK == true);

		showSubmitResult(bOK);

		if (bOK) {
			loadPrisonerRuleBreak(m_prisoner.getID());
			m_layout[1].setVisibility(View.VISIBLE);
			m_layout[4].setVisibility(View.GONE);
		}
	}
}
