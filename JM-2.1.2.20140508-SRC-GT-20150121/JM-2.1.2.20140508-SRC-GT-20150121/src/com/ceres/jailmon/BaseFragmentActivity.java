/*
 * Description: Base Activity
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.AgentInfo;
import com.ceres.jailmon.data.AgentInfoList;
import com.ceres.jailmon.data.AuthResult;
import com.ceres.jailmon.data.BedInfoList;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.CommonResult;
import com.ceres.jailmon.data.DutyInfoList;
import com.ceres.jailmon.data.GoodsList;
import com.ceres.jailmon.data.MapInfo;
import com.ceres.jailmon.data.MapLocationList;
import com.ceres.jailmon.data.MedcineInfoList;
import com.ceres.jailmon.data.MedcineResult;
import com.ceres.jailmon.data.MedicinePeopleInfo;
import com.ceres.jailmon.data.MonitorInfoList;
import com.ceres.jailmon.data.OutInfoList;
import com.ceres.jailmon.data.Police;
import com.ceres.jailmon.data.PowerCtrlResult;
import com.ceres.jailmon.data.PowerInfoList;
import com.ceres.jailmon.data.Prisoner;
import com.ceres.jailmon.data.PrisonerDetail;
import com.ceres.jailmon.data.PrisonerList;
import com.ceres.jailmon.data.PurchaseResult;
import com.ceres.jailmon.data.ReceiptResult;
import com.ceres.jailmon.data.RuleBreakInfoList;
import com.ceres.jailmon.data.RuleBreakItemsList;
import com.ceres.jailmon.data.RuleBreakResult;
import com.ceres.jailmon.data.SecurityInfoList;
import com.ceres.jailmon.data.SendMedicineData;
import com.ceres.jailmon.data.SendMedicineResult;
import com.ceres.jailmon.data.ServerTime;
import com.ceres.jailmon.data.ShiftInfoList;
import com.ceres.jailmon.data.SignInResult;
import com.ceres.jailmon.data.TotalPrisonSituationList;
import com.ceres.jailmon.data.TradeInfoList;
import com.ceres.jailmon.data.TrainingList;
import com.ceres.jailmon.data.TrainingResult;

public class BaseFragmentActivity extends FragmentActivity {

	final static int API_GET_CELLLIST_OK = 0;
	final static int API_GET_PRISONERLIST_OK = 1;
	final static int API_GET_PRISONER_DETAIL_OK = 2;
	final static int API_GET_PRISONER_ACCOUNT_OK = 3;
	final static int API_GET_GOODS_LIST_OK = 4;
	final static int API_GET_MEDCINE_INFO_LIST_OK = 5;
	final static int API_GET_OUT_INFO_LIST_OK = 6;
	final static int API_GET_POWER_INFO_LIST_OK = 7;
	final static int API_GET_SECURITY_INFO_LIST_OK = 8;
	final static int API_GET_AUTH_RESULT_OK = 9;
	final static int API_GET_MAP_INFO = 10;
	final static int API_GET_MAP_LOCATION_OK = 11;
	final static int API_GET_PRISONER_PHOTO_OK = 12;
	final static int API_POST_MEDCINE_RESULT_INFO_OK = 13;
	final static int API_POST_PURCHASE_RESULT_INFO_OK = 14;
	final static int API_POST_POWERCTRL_RESULT_INFO_OK = 15;
	final static int API_GET_SHIFT_INFO_LIST_OK = 16;
	final static int API_GET_RULEBREAK_INFO_LIST_OK = 17;
	final static int API_GET_TRADE_INFO_LIST_OK = 18;
	final static int API_GET_TRAINING_LIST_OK = 19;
	final static int API_POST_TRAINING_REUSLT_INFO_OK = 20;
	final static int API_GET_RULEBREAK_ITEMS_LIST_OK = 21;
	final static int API_POST_RULE_BREAK_INFO_OK = 22;
	final static int API_GET_MONITOR_INFO_LIST_OK = 23;
	final static int API_GET_BED_INFO_LIST_OK = 24;
	final static int API_GET_DUTY_INFO_LIST_OK = 25;
	final static int API_GET_AGENT_INFO_LIST_OK = 26;
	final static int API_GET_POLICE_PHOTO_OK = 27;
	final static int API_POST_DOCTOR_NOTE_OK = 28;
	final static int API_POST_POLICE_NOTE_OK = 29;
	final static int API_GET_SIGNIN_OK = 30;
	final static int API_GET_SERVER_TIME_OK = 31;
	final static int API_GET_SECURITY_RB_INFO_LIST_OK = 32;
	final static int API_GET_ALL_PRISONER_PHOTO_OK = 1001;
	final static int API_GET_ALL_AGENT_PHOTO_OK = 1002;
	final static int API_GET_ALL_POLICE_PHOTO_OK = 1003;
	final static int API_GET_MED_SEND_MEDICINE_PELPLE_INFO_OK = 1004;
	final static int API_GET_MED_SEND_MEDICINE_RESULT = 1005;
	final static int API_POST_RECIEPT_RESULT_INFO_OK = 1006;
	final static int API_GET_FAIL = -1;

	protected AppContext m_AppContext = null;

	LayoutInflater layoutInflater;
	View myLoginView;
	Dialog alertDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_AppContext = (AppContext) this.getApplication();

		layoutInflater = LayoutInflater.from(this);
		myLoginView = layoutInflater.inflate(R.layout.process, null);

		alertDialog = new AlertDialog.Builder(this).setView(myLoginView)
				.create();

	}

	protected void initBackButton(int resid) {
		Button btnBack = (Button) findViewById(resid);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	public void showProcessDialog() {
		alertDialog.show();
		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = 350;
		alertDialog.getWindow().setAttributes(lp);
	}

	public void closeProcessDialog() {
		if (alertDialog.isShowing())
			alertDialog.dismiss();
	}

	public SettingData getSetting() {
		return AppContext.m_setting;
	}

	public void showSubmitResult(boolean bOK) {

		int nRes = bOK ? R.string.submit_ok : R.string.submit_fail;

		Toast.makeText(getApplicationContext(), getString(nRes),
				Toast.LENGTH_LONG).show();
	}

	protected Handler m_basehandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case API_GET_AUTH_RESULT_OK:
				onReceiveAuthResult((AuthResult) msg.obj);
				break;

			case API_GET_CELLLIST_OK:
				onReceiveCellList((CellList) msg.obj);
				break;

			case API_GET_MEDCINE_INFO_LIST_OK:
				onReceiveMedinfoList((TotalPrisonSituationList) msg.obj);
				break;

			case API_POST_MEDCINE_RESULT_INFO_OK:
				onReceiveMedinfoPostResult((MedcineResult) msg.obj);
				break;

			case API_GET_TRAINING_LIST_OK:
				onReceiveTrainingList((TrainingList) msg.obj);
				break;

			case API_POST_TRAINING_REUSLT_INFO_OK:
				onPostTrainingResult((TrainingResult) msg.obj);
				break;

			case API_GET_SHIFT_INFO_LIST_OK:
				onReceiveShiftInfoList((ShiftInfoList) msg.obj);
				break;

			case API_GET_SECURITY_INFO_LIST_OK:
				onReceiveSecurityInfo((SecurityInfoList) msg.obj);
				break;

			case API_GET_SECURITY_RB_INFO_LIST_OK:
				onReceiveSecurityRBInfo((SecurityInfoList) msg.obj);
				break;

			case API_GET_OUT_INFO_LIST_OK:
				onReceiveOutInfoList((OutInfoList) msg.obj);
				break;

			case API_GET_PRISONERLIST_OK:
				onReceivePrisonerList((PrisonerList) msg.obj);
				break;

			case API_GET_GOODS_LIST_OK:
				onReceiveGoodsList((GoodsList) msg.obj);
				break;

			case API_GET_PRISONER_ACCOUNT_OK:
				onReceiveAccount((Account) msg.obj);
				break;

			case API_POST_PURCHASE_RESULT_INFO_OK:
				onReceivePurchaseResult((PurchaseResult) msg.obj);
				break;
				
			case API_POST_RECIEPT_RESULT_INFO_OK:
				onReceiveReceiptResult((ReceiptResult) msg.obj);
				break;

			case API_GET_POWER_INFO_LIST_OK:
				onReceivePowerInfoList((PowerInfoList) msg.obj);
				break;

			case API_POST_POWERCTRL_RESULT_INFO_OK:
				onPostPowerCtrlResult((PowerCtrlResult) msg.obj);
				break;

			case API_GET_BED_INFO_LIST_OK:
				onReceiveBedInfoList((BedInfoList) msg.obj);
				break;

			case API_GET_DUTY_INFO_LIST_OK:
				onReceiveDutyInfoList((DutyInfoList) msg.obj);
				break;

			case API_GET_AGENT_INFO_LIST_OK:
				onReceiveAgentInfoList((AgentInfoList) msg.obj);
				break;

			case API_GET_PRISONER_DETAIL_OK:
				onReceivePrisonerDetail((PrisonerDetail) msg.obj);
				break;

			case API_GET_PRISONER_PHOTO_OK:
				onReceivePrisonerPhoto((Bitmap) msg.obj);
				break;

			case API_GET_POLICE_PHOTO_OK:
				onReceivePolicePhoto((Bitmap) msg.obj);
				break;

			case API_GET_RULEBREAK_INFO_LIST_OK:
				onReceiveRuleBreakInfoList((RuleBreakInfoList) msg.obj);
				break;

			case API_GET_TRADE_INFO_LIST_OK:
				onReceiveTradeInfoList((TradeInfoList) msg.obj);
				break;

			case API_GET_RULEBREAK_ITEMS_LIST_OK:
				onReceiveRuleBreakItemsList((RuleBreakItemsList) msg.obj);
				break;

			case API_POST_RULE_BREAK_INFO_OK:
				onPostRuleBreakInfoResult((RuleBreakResult) msg.obj);
				break;

			case API_GET_MAP_INFO:
				onReceiveMapInfo((MapInfo) msg.obj);
				break;

			case API_GET_MAP_LOCATION_OK:
				onReceiveMapLocation((MapLocationList) msg.obj);
				break;

			case API_GET_ALL_PRISONER_PHOTO_OK:
				onReceiveAllPrisonerPhotoNotify();
				break;

			case API_GET_ALL_POLICE_PHOTO_OK:
				onReceiveAllPolicePhotoNotify();
				break;

			case API_GET_ALL_AGENT_PHOTO_OK:
				onReceiveAgentPhotoNotify();
				break;

			case API_POST_DOCTOR_NOTE_OK:
				onReceiveDoctorNoteResult((CommonResult) msg.obj);
				break;

			case API_POST_POLICE_NOTE_OK:
				onReceivePoliceNoteResult((CommonResult) msg.obj);
				break;

			case API_GET_SIGNIN_OK:
				onReceiveSignInResult((SignInResult) msg.obj);
				break;

			case API_GET_SERVER_TIME_OK:
				onReceiveServerTimeResult((ServerTime) msg.obj);
				break;

			case API_GET_MED_SEND_MEDICINE_PELPLE_INFO_OK:
				onReceiveMedSendMedPeopleInfo((MedicinePeopleInfo) msg.obj);
				break;
				
			case API_GET_MED_SEND_MEDICINE_RESULT:
				onReceiveMedSendMedResult((MedcineResult) msg.obj);
				 break;
				 
			case API_GET_FAIL:
				onReceiveFail((AppException) msg.obj);
				break;
			}
		}
	};

	protected void onReceiveFail(AppException exception) {
		closeProcessDialog();
		exception.makeToast(BaseFragmentActivity.this);
	}

	// 获取药方配药人员信息
	public void getSendMedPersonInfo(final Handler handler, final String pId,
			final String cId) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedicinePeopleInfo infolist = m_AppContext
							.getMedicinePeopleInfo(cId, pId);
					msg.what = API_GET_MED_SEND_MEDICINE_PELPLE_INFO_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					Log.d("jiayy",
							"getSendMedPersonInfo catch msg.what = API_GET_FAI");
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	// 确认配药
	public void submitMedicineData(final Handler handler, final String pId,
			 final String cId,final String currentTime,
			final List<SendMedicineData> sendMedicineList,final String uId) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedcineResult info = m_AppContext
							.submitMedicineData(pId,currentTime,cId,sendMedicineList,uId);
					msg.what = API_GET_MED_SEND_MEDICINE_RESULT;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					Log.d("jiayy",
							"getSendMedPersonInfo catch msg.what = API_GET_FAI");
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void getAuthResult(final Handler handler, final String user,
			final String passwd) {

		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					AuthResult info = m_AppContext.getAuthResult(user, passwd);
					msg.what = API_GET_AUTH_RESULT_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveAuthResult(AuthResult ret) {

	}

	public void getCellList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					CellList celllist = m_AppContext.getCellList();
					msg.what = API_GET_CELLLIST_OK;
					msg.obj = celllist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveCellList(CellList celllist) {
	}

	public void getPrisonerList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					PrisonerList infolist = m_AppContext.getPrisonerList(cid);
					msg.what = API_GET_PRISONERLIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePrisonerList(PrisonerList infolist) {

	}

	public void getPrisonserDetail(final Handler handler, final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					PrisonerDetail info = m_AppContext.getPrisonerDetail(pid);
					msg.what = API_GET_PRISONER_DETAIL_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePrisonerDetail(PrisonerDetail info) {

	}

	public void getPrisonserPhoto(final Handler handler, final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					Bitmap bmp = m_AppContext.getPrisonerPhoto(pid);
					msg.what = API_GET_PRISONER_PHOTO_OK;
					msg.obj = bmp;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePrisonerPhoto(Bitmap bmp) {

	}

	protected void onReceiveMedSendMedPeopleInfo(MedicinePeopleInfo info) {

	}
	
	protected void onReceiveMedSendMedResult(MedcineResult info) {

	}

	public void getPrisonserAccount(final Handler handler, final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					Account info = m_AppContext.getPrisonerAccount(pid);
					msg.what = API_GET_PRISONER_ACCOUNT_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveAccount(Account info) {

	}

	public void getGoodsList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					GoodsList infolist = m_AppContext.getGoodsList();
					msg.what = API_GET_GOODS_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveGoodsList(GoodsList infolist) {

	}
	//确认购买
	public void postPurchaseResult(final Handler handler, final String pid,
			final String param) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					PurchaseResult info = m_AppContext.postPurchaseResult(pid,
							param);
					msg.what = API_POST_PURCHASE_RESULT_INFO_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	//确认收货
		public void postReceiptResult(final Handler handler, final String pid,
				final String pwd) {
			new Thread() {
				public void run() {
					Message msg = new Message();

					try {
						ReceiptResult info = m_AppContext.postRecieptResult(pid,
								pwd);
						msg.what = API_POST_RECIEPT_RESULT_INFO_OK;
						msg.obj = info;
					} catch (AppException e) {
						e.printStackTrace();
						msg.what = API_GET_FAIL;
						msg.obj = e;
					}
					handler.sendMessage(msg);
				}
			}.start();
		}

	
	protected void onReceivePurchaseResult(PurchaseResult ret) {

	}
	
	protected void onReceiveReceiptResult(ReceiptResult ret) {

	}

	public void getMedcineInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					MedcineInfoList infolist = m_AppContext
							.getMedcineInfoList(cid);
					msg.what = API_GET_MEDCINE_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveMedinfoList(TotalPrisonSituationList infolist) {

	}

	public void postMedcineResultInfo(final Handler handler, final String cid,
			final String param) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					MedcineResult info = m_AppContext.postMedcineResultInfo(
							cid, param);
					msg.what = API_POST_MEDCINE_RESULT_INFO_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveMedinfoPostResult(MedcineResult ret) {
	}

	public void getOutInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					OutInfoList infolist = m_AppContext.getOutInfoList(cid);
					msg.what = API_GET_OUT_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveOutInfoList(OutInfoList infolist) {
	}

	public void getMapInfo(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					MapInfo info = m_AppContext.getMapInfo();
					msg.what = API_GET_MAP_INFO;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveMapInfo(MapInfo info) {

	}

	public void getMapLocationList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					MapLocationList infolist = m_AppContext
							.getMapLocationList();
					msg.what = API_GET_MAP_LOCATION_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveMapLocation(MapLocationList infolist) {

	}

	public void getPowerInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					PowerInfoList infolist = m_AppContext.getPowerInfoList(cid);
					msg.what = API_GET_POWER_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePowerInfoList(PowerInfoList infolist) {

	}

	public void postPowerCtrlResult(final Handler handler, final String cids,
			final String param) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					PowerCtrlResult info = m_AppContext.postPowerCtrlResult(
							cids, param);
					msg.what = API_POST_POWERCTRL_RESULT_INFO_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onPostPowerCtrlResult(PowerCtrlResult ret) {

	}

	public void postTrainingResult(final Handler handler, final String cids,
			final String type, final String id) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					TrainingResult info = m_AppContext.postTrainingResult(cids,
							type, id);
					msg.what = API_POST_TRAINING_REUSLT_INFO_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onPostTrainingResult(TrainingResult ret) {
	}

	public void postRuleBreakResult(final Handler handler, final String cid,
			final String pid, final String content) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					RuleBreakResult info = m_AppContext.postRuleBreakResult(
							cid, pid, content);
					msg.what = API_POST_RULE_BREAK_INFO_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onPostRuleBreakInfoResult(RuleBreakResult ret) {

	}

	public void getSecurityfoList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					SecurityInfoList infolist = m_AppContext
							.getSecurityInfoList();
					msg.what = API_GET_SECURITY_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveSecurityInfo(SecurityInfoList infolist) {

	}

	public void getShiftInfoList(final Handler handler, final String day) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					ShiftInfoList infolist = m_AppContext.getShiftInfoList(day);
					msg.what = API_GET_SHIFT_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveShiftInfoList(ShiftInfoList infolist) {

	}

	public void getRuleBreakInfoList(final Handler handler, final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					RuleBreakInfoList infolist = m_AppContext
							.getRuleBreakInfoList(pid);
					msg.what = API_GET_RULEBREAK_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveRuleBreakInfoList(RuleBreakInfoList infolist) {

	}

	public void getRuleBreakItemsList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					RuleBreakItemsList infolist = m_AppContext
							.getRuleBreakItemsList();
					msg.what = API_GET_RULEBREAK_ITEMS_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveRuleBreakItemsList(RuleBreakItemsList infolist) {

	}

	public void getTradeInfoList(final Handler handler, final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					TradeInfoList infolist = m_AppContext.getTradeInfoList(pid);
					msg.what = API_GET_TRADE_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	void onReceiveTradeInfoList(TradeInfoList infolist) {
	}

	public void getTrainingList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					TrainingList infolist = m_AppContext.getTrainingList();
					msg.what = API_GET_TRAINING_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveTrainingList(TrainingList infolist) {
	}

	public void getMonitorInfoList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					MonitorInfoList infolist = m_AppContext
							.getMonitorInfoList();
					msg.what = API_GET_MONITOR_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void getBedInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					BedInfoList infolist = m_AppContext.getBedInfoList(cid);
					msg.what = API_GET_BED_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveBedInfoList(BedInfoList infolist) {

	}

	public void getDutyInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					DutyInfoList infolist = m_AppContext.getDutyInfoList(cid);
					msg.what = API_GET_DUTY_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveDutyInfoList(DutyInfoList infolist) {

	}

	public void getAgentInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					AgentInfoList infolist = m_AppContext.getAgentInfoList(cid);
					msg.what = API_GET_AGENT_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveAgentInfoList(AgentInfoList infolist) {

	}

	String getDeviceID() {
		String tmp = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);

		return tmp.substring(0, tmp.length() - 1);
	}

	public void getAllPrisonserPhoto(final Handler handler,
			final PrisonerList infolist) {
		new Thread() {
			public void run() {
				if (infolist == null)
					return;

				Prisoner info;

				for (int i = 0; i < infolist.m_list.size(); i++) {
					info = infolist.m_list.get(i);

					if (info.getID() != null) {
						try {
							info.setPhoto(m_AppContext.getPrisonerPhoto(info
									.getID()));
							Message msg = new Message();
							msg.what = API_GET_ALL_PRISONER_PHOTO_OK;
							handler.sendMessage(msg);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}

				Police info2;

				for (int i = 0; i < infolist.m_listPolice.size(); i++) {
					info2 = infolist.m_listPolice.get(i);

					if (info2.getID() != null) {
						try {
							info2.setPhoto(m_AppContext.getPolicePhoto(info2
									.getID()));
							Message msg = new Message();
							msg.what = API_GET_ALL_POLICE_PHOTO_OK;
							handler.sendMessage(msg);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	protected void onReceiveAllPrisonerPhotoNotify() {

	}

	protected void onReceiveAllPolicePhotoNotify() {

	}

	public void getAllPolicePhoto(final Handler handler,
			final List<Police> infolist) {
		new Thread() {
			public void run() {
				if (infolist == null)
					return;

				Police info;

				for (int i = 0; i < infolist.size(); i++) {
					info = infolist.get(i);

					if (info.getID() != null) {
						try {
							info.setPhoto(m_AppContext.getPolicePhoto(info
									.getID()));
							Message msg = new Message();
							msg.what = API_GET_ALL_POLICE_PHOTO_OK;
							handler.sendMessage(msg);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	public void getPolicePhoto(final Handler handler, final String id) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					Bitmap bmp = m_AppContext.getPolicePhoto(id);
					msg.what = API_GET_POLICE_PHOTO_OK;
					msg.obj = bmp;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePolicePhoto(Bitmap bmp) {

	}

	public void loadAgentsPhoto(final Handler handler, final AgentInfo info) {
		new Thread() {
			public void run() {
				if (info == null)
					return;

				String pid;

				for (int i = 0; i < info.m_pid_list.size(); i++) {
					pid = info.m_pid_list.get(i);

					if (pid != null) {
						try {
							Bitmap photo = m_AppContext.getPrisonerPhoto(pid);
							info.m_pid_bitmap.add(photo);

							Message msg = new Message();
							msg.what = API_GET_ALL_AGENT_PHOTO_OK;
							handler.sendMessage(msg);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	protected void onReceiveAgentPhotoNotify() {

	}

	public void postDoctorNote(final Handler handler, final String cid,
			final String note) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					CommonResult info = m_AppContext.postDoctorNote(cid, note);
					msg.what = API_POST_DOCTOR_NOTE_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveDoctorNoteResult(CommonResult ret) {

	}

	public void postPoliceNote(final Handler handler, final String cid,
			final String note) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					CommonResult info = m_AppContext.postPoliceNote(cid, note);
					msg.what = API_POST_POLICE_NOTE_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceivePoliceNoteResult(CommonResult ret) {

	}

	public void getSignInResult(final Handler handler, final String id) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					SignInResult info = m_AppContext.getSignInResult(id);
					msg.what = API_GET_SIGNIN_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveSignInResult(SignInResult ret) {

	}

	public void getSecurityRBInfoList(final Handler handler, final String cid) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					SecurityInfoList infolist = m_AppContext
							.getSecurityRBInfoList(cid);
					msg.what = API_GET_SECURITY_RB_INFO_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveSecurityRBInfo(SecurityInfoList infolist) {

	}

	public void getServerTime(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					ServerTime info = m_AppContext.getServerTime();
					msg.what = API_GET_SERVER_TIME_OK;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	protected void onReceiveServerTimeResult(ServerTime srvtime) {

	}

	boolean verifyRegisterNum(String strAuthCode, String strSN) {
		Encryption enc = new Encryption();

		String strDec = null;
		String strID = getDeviceID();

		try {
			strDec = enc.decrypt(strAuthCode, strSN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return true;

		return (strDec != null && strDec.equals(strID));
	}

	String getPersonPhotoUrl(String pid) {
		return String.format(m_AppContext.m_apiclient
				.getAPIUrl(m_AppContext.m_apiclient.API_GET_PERSON_PHOTO), pid);
	}
}
