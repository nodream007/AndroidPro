package com.ceres.jailmon.fragment;

import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.AppException;
import com.ceres.jailmon.data.BuyShopping;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.MedicineCleanHistory;
import com.ceres.jailmon.data.MedicineHistory;
import com.ceres.jailmon.data.MedicineTypeAndNamesListParse;
import com.ceres.jailmon.data.MedicineUnit;
import com.ceres.jailmon.data.ReceiptProduct;
import com.ceres.jailmon.data.TotalPrisonSituationList;
import com.ceres.jailmon.database.GoodsDao;
import com.ceres.jailmon.util.BitmapUtil;

public class BaseFragment extends Fragment {
	protected AppContext m_AppContext = null;
	final static int API_GET_TOTAL_PRISON_SITUATION_LIST_OK = 33;
	final static int API_GET_ROUND_SEND_MEDICICE_LIST_OK = 34;
	final static int API_GET_MED_HISTORY_LIST_OK = 35;
	final static int API_GET_CLEAN_HISTORY_LIST_OK = 36;
	final static int API_GET_SEND_MED_TYPE_AND_NAMES_OK = 37;
	final static int API_GET_MEDICINE_UNIT_OK = 38;
	final static int API_GET_BUY_SHOPPING_LIST = 39;
	final static int API_GET_RECEIPT_LIST = 40;
	final static int API_GET_ALL_PRISONER_PHOTO_OK = 41;
	final static int API_GET_IMG_OK = 42;
	final static int API_GET_FAIL = -1;

	// 获取所内总况
	public void getTotalPrisonSituationList(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					TotalPrisonSituationList infolist = m_AppContext
							.getTotalPrisonSituationList();
					msg.what = API_GET_TOTAL_PRISON_SITUATION_LIST_OK;
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

	// 获取巡诊开药
	public void getRoundsSendMedList(final Handler handler, final String cId) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedRoundSendMedicineList infolist = m_AppContext
							.getMedRoundSendMedicineList(cId);
					msg.what = API_GET_ROUND_SEND_MEDICICE_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					Log.d("jiayy", "catch API_GET_FAIL");
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	// 获取所内诊疗记录
	public void getSendMedHistoryList(final Handler handler, final String cId,
			final String pid, final String startDate, final String endDate,
			final String medType) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedicineHistory infolist = m_AppContext.getMedHistoryList(
							cId, pid, startDate, endDate, medType);
					msg.what = API_GET_MED_HISTORY_LIST_OK;
					msg.obj = infolist;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					Log.d("jiayy", "getSendMedHistoryList API_GET_FAIL");
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	// 获取卫生防疫记录
	public void getCleanHistoryList(final Handler handler, final String cId,
			final String pid, final String startDate, final String endDate,
			final String medType) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedicineCleanHistory infolist = m_AppContext
							.getMedCleanHistoryList(cId, pid, startDate,
									endDate, medType);
					msg.what = API_GET_CLEAN_HISTORY_LIST_OK;
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

	// 获取药方配药配药信息
	public void getSendMedTypeAndNames(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedicineTypeAndNamesListParse infolist = m_AppContext
							.getSendMedTypeAndNames();
					msg.what = API_GET_SEND_MED_TYPE_AND_NAMES_OK;
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

	// 获取药品单位
	public void getMedicineUnits(final Handler handler) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MedicineUnit infolist = m_AppContext.getMedicineUnits();
					msg.what = API_GET_MEDICINE_UNIT_OK;
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

	public void getBuyShoppingList(final Handler handler, final String roomId,
			final String pid, final int type) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					List<BuyShopping> shoppingList = m_AppContext
							.getBuyShoppingList(type);
					msg.what = API_GET_BUY_SHOPPING_LIST;
					msg.obj = shoppingList;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	// 获取图片
	public void getPatientPhoto(final Handler handler,
			final String patientId) {
		new Thread() {
			public void run() {
					if (patientId != null) {
						try {
							Bitmap bitmap = m_AppContext.getPrisonerPhoto(patientId);
							Message msg = new Message();
							msg.what = API_GET_ALL_PRISONER_PHOTO_OK;
							msg.obj = bitmap;
							handler.sendMessage(msg);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}
		}.start();
	}

	public void getReceiptList(final Handler handler, final String roomId,
			final String pid) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					List<ReceiptProduct> receiptList = m_AppContext
							.getReceiptList(pid);
					msg.what = API_GET_RECEIPT_LIST;
					msg.obj = receiptList;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = API_GET_FAIL;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void insertImg(final int goodsId, final String picUrl,
			final GoodsDao dao, final String table) {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					byte[] imgByte = BitmapUtil.getImage(picUrl);
					dao.updateDataPic(imgByte, goodsId, table);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.what = API_GET_IMG_OK;
				m_basehandler.sendMessage(msg);
			}
		}.start();
	}

	protected Handler m_basehandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case API_GET_TOTAL_PRISON_SITUATION_LIST_OK:
				onReceiveTotalPrisonSituationList((TotalPrisonSituationList) msg.obj);
				break;
			case API_GET_ROUND_SEND_MEDICICE_LIST_OK:
				onReceiveMedRoundSendMessageList((MedRoundSendMedicineList) msg.obj);
				break;

			case API_GET_MED_HISTORY_LIST_OK:
				onReceiveMedicineHistoryList((MedicineHistory) msg.obj);
				break;

			case API_GET_CLEAN_HISTORY_LIST_OK:
				onReceiveMedicineCleanHistoryList((MedicineCleanHistory) msg.obj);
				break;
			case API_GET_SEND_MED_TYPE_AND_NAMES_OK:
				onReceiveMedicineNameAndTypes((MedicineTypeAndNamesListParse) msg.obj);
				break;
			case API_GET_MEDICINE_UNIT_OK:
				onReceiveMedicineUnitList((MedicineUnit) msg.obj);
				break;
			case API_GET_BUY_SHOPPING_LIST:
				onReceiveBuyShoppingList((List<BuyShopping>) msg.obj);
				break;
			case API_GET_RECEIPT_LIST:
				onReceiveReceiptList((List<ReceiptProduct>) msg.obj);
				break;
			case API_GET_ALL_PRISONER_PHOTO_OK:
				onReceiveAllPrisonerPhotoNotify((Bitmap) msg.obj);
				break;
			case API_GET_IMG_OK:
				onReceiveAllGoodsPhotoNotify();
				break;
			case API_GET_FAIL:
				onReceiveFail((AppException) msg.obj);
				break;
			}
		}
	};

	protected void onReceiveAllGoodsPhotoNotify() {
	}

	protected void onReceiveTotalPrisonSituationList(
			TotalPrisonSituationList totalPrisonSituationList) {
	}

	protected void onReceiveMedRoundSendMessageList(
			MedRoundSendMedicineList infolist) {

	}

	protected void onReceiveAllPrisonerPhotoNotify(Bitmap bitmap) {

	}

	protected void onReceiveMedicineHistoryList(MedicineHistory medicineHistory) {
	}

	protected void onReceiveMedicineNameAndTypes(
			MedicineTypeAndNamesListParse mediceAndNamesListParse) {
	}

	protected void onReceiveMedicineCleanHistoryList(
			MedicineCleanHistory medicineCleanHistory) {
	}

	protected void onReceiveBuyShoppingList(List<BuyShopping> shoppingList) {
	}

	protected void onReceiveReceiptList(List<ReceiptProduct> receiptList) {
	}

	protected void onReceiveMedicineUnitList(MedicineUnit medicineUnit) {
	}

	protected void onReceiveFail(AppException exception) {
		exception.makeToast(this.getActivity());
	}
}
