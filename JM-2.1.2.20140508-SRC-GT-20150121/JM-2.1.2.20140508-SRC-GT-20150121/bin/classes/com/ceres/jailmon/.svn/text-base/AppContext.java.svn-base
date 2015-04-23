/*
 * Description: Implement Application
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */

package com.ceres.jailmon;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.AgentInfoList;
import com.ceres.jailmon.data.AuthResult;
import com.ceres.jailmon.data.BedInfoList;
import com.ceres.jailmon.data.BuyShopping;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.CommonResult;
import com.ceres.jailmon.data.DutyInfoList;
import com.ceres.jailmon.data.GoodsList;
import com.ceres.jailmon.data.MapInfo;
import com.ceres.jailmon.data.MapLocationList;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.MedcineInfoList;
import com.ceres.jailmon.data.MedcineResult;
import com.ceres.jailmon.data.MedicineCleanHistory;
import com.ceres.jailmon.data.MedicineHistory;
import com.ceres.jailmon.data.MedicinePeopleInfo;
import com.ceres.jailmon.data.MedicineTypeAndNamesListParse;
import com.ceres.jailmon.data.MedicineUnit;
import com.ceres.jailmon.data.MonitorInfoList;
import com.ceres.jailmon.data.OutInfoList;
import com.ceres.jailmon.data.PatrolHistory;
import com.ceres.jailmon.data.PatrolResult;
import com.ceres.jailmon.data.Police;
import com.ceres.jailmon.data.PoliceInfo;
import com.ceres.jailmon.data.PowerCtrlResult;
import com.ceres.jailmon.data.PowerInfoList;
import com.ceres.jailmon.data.PrisonerDetail;
import com.ceres.jailmon.data.PrisonerList;
import com.ceres.jailmon.data.PurchaseResult;
import com.ceres.jailmon.data.ReceiptProduct;
import com.ceres.jailmon.data.ReceiptResult;
import com.ceres.jailmon.data.RoomIndex;
import com.ceres.jailmon.data.RuleBreakInfoList;
import com.ceres.jailmon.data.RuleBreakItemsList;
import com.ceres.jailmon.data.RuleBreakResult;
import com.ceres.jailmon.data.SecurityInfoList;
import com.ceres.jailmon.data.SendMedicineData;
import com.ceres.jailmon.data.ServerTime;
import com.ceres.jailmon.data.ShiftInfoList;
import com.ceres.jailmon.data.SignInResult;
import com.ceres.jailmon.data.TXResult;
import com.ceres.jailmon.data.TotalPrisonSituationList;
import com.ceres.jailmon.data.TradeInfoList;
import com.ceres.jailmon.data.TrainingList;
import com.ceres.jailmon.data.TrainingResult;
import com.ceres.jailmon.util.IpUtil;
public class AppContext extends Application {
	
	public static final int TYPE_DAILY = 0;
	public static final int TYPE_LABOR = 1;
	public static final int TYPE_FOOD = 2;

	ApiClient m_apiclient = new ApiClient(this);
	public static SettingData m_setting;
	private int m_monitor_type = 0;

	@Override
	public void onCreate() {
		m_setting = new SettingData(this);
		m_setting.loadServer();
		m_setting.loadID();
		super.onCreate(); 
	}
	
	public void setMonitorType( int type )
	{
		m_monitor_type = type;
	}
	
	public int getMonitorType()
	{
		return m_monitor_type;
	}
	
	public int getVerNum()
	{
		return m_setting.m_version;
	}

	private boolean isNetworkConnected() {
		boolean wifiConnected = false;
		boolean mobileConnected = false;
		boolean lanContected = false;

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

		if (activeInfo != null && activeInfo.isConnected()) {
			wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
			lanContected = activeInfo.getType() == ConnectivityManager.TYPE_ETHERNET;
		}

		if( getVerNum() == 2 )
			return wifiConnected || mobileConnected || lanContected;
		else
			return wifiConnected || mobileConnected;
	}

	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public AuthResult getAuthResult(String user, String passwd)
			throws AppException {

		AuthResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getAuthResult(user, passwd);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new AuthResult();
		}

		return info;
	}
	
	
	public SignInResult getSignInResult(String id)
			throws AppException {

		SignInResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getSignInResult(id);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new SignInResult();
		}
		else
			setMonitorType( info.mMonitorType);

		return info;
	}


	public CellList getCellList() throws AppException {

		CellList list = null;

		try {
			checkNetworkConnected();
			list = m_apiclient.getCellList( m_setting.getID() );
		} catch (AppException e) {
			throw e;
		}

		if (list == null) {
			list = new CellList();
		}

		return list;
	}

	private void checkNetworkConnected() throws AppException {
		
		if ( m_setting.getServer().equals("localhost") )
			return;
		
		if (!isNetworkConnected()) {
			ConnectException e = new ConnectException();
			throw AppException.network(e);
		}
	}

	public PrisonerList getPrisonerList(String cell) throws AppException {

		PrisonerList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getPrisonerList(cell);
		} catch (AppException e) {
			throw e;
		}

		if (infolist == null) {
			infolist = new PrisonerList();
		}

		return infolist;
	}

	public PrisonerDetail getPrisonerDetail(String pid) throws AppException {

		PrisonerDetail info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getPrisonerDetail(pid);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new PrisonerDetail();
		}

		return info;
	}

	public Account getPrisonerAccount(String pid) throws AppException {

		Account info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getPrisonerAccount(pid);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new Account();
		}

		return info;
	}

	public Bitmap getPrisonerPhoto(String pid) throws AppException {

		Bitmap bmp = null;

		try {
			checkNetworkConnected();
			bmp = m_apiclient.getPrisonerPhoto(pid);
		} catch (AppException e) {
			throw e;
		}

		return bmp;
	}
	
	public Bitmap getPolicePhoto(String id) throws AppException {

		Bitmap bmp = null;

		try {
			checkNetworkConnected();
			bmp = m_apiclient.getPolicePhoto(id);
		} catch (AppException e) {
			throw e;
		}

		return bmp;
	}

	public GoodsList getGoodsList() throws AppException {

		GoodsList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getGoodsList();
		} catch (AppException e) {
			throw e;
		}

		if (infolist == null) {
			infolist = new GoodsList();
		}

		return infolist;
	}
	
	
	public PurchaseResult postPurchaseResult(String pid, String postParam) throws AppException {

		PurchaseResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postPurchaseResult(pid, postParam);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	
	public TXResult postTXResult(String txID, String txFlag) throws AppException {

		TXResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postTXResult(txID, txFlag);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	

	public ReceiptResult postRecieptResult(String pid, String pwd) throws AppException {

		ReceiptResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postRecieptResult(pid, pwd);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}

	public ShiftInfoList getShiftInfoList(String day) throws AppException {

		ShiftInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getShiftInfoList(day);
		} catch (AppException e) {
			throw e;
		}

		if (infolist == null) {
			infolist = new ShiftInfoList();
		}

		return infolist;
	}

	public MedcineInfoList getMedcineInfoList(String cid) throws AppException {

		MedcineInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getMedcineInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new MedcineInfoList();
		}

		return infolist;
	}
	//所内总况
	public TotalPrisonSituationList getTotalPrisonSituationList() throws AppException {

		TotalPrisonSituationList totalPrisonSituationList = null;

		try {
			checkNetworkConnected();
			totalPrisonSituationList = m_apiclient.getTotalPrisonSituationList();
		} catch (AppException e) {
			throw e;
		}
		if (totalPrisonSituationList == null) {
			totalPrisonSituationList = new TotalPrisonSituationList();
		}

		return totalPrisonSituationList;
	}
	
	//巡诊开药
	public MedRoundSendMedicineList getMedRoundSendMedicineList(String cid) throws AppException {

		MedRoundSendMedicineList medRoundSendMedicineList = null;

		try {
			checkNetworkConnected();
			medRoundSendMedicineList = m_apiclient.getMedRoundSendMedicineList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (medRoundSendMedicineList == null) {
			medRoundSendMedicineList = new MedRoundSendMedicineList();
		}

		return medRoundSendMedicineList;
	}
	
	public MedicinePeopleInfo getMedicinePeopleInfo(String roomId, String pid) throws AppException {

		MedicinePeopleInfo medicinePeopleInfo = null;

		try {
			checkNetworkConnected();
			medicinePeopleInfo = m_apiclient.getMedicinePeopleInfo(roomId, pid);
		} catch (AppException e) {
			throw e;
		}
		if (medicinePeopleInfo == null) {
			medicinePeopleInfo = new MedicinePeopleInfo();
		}

		return medicinePeopleInfo;
	}
	
	public List<ReceiptProduct> getReceiptList(String pid) throws AppException {
		List<ReceiptProduct> receiptList = null;

		try {
			checkNetworkConnected();
			receiptList = m_apiclient.getReceiptList(pid);
		} catch (AppException e) {
			throw e;
		}
		if (receiptList == null) {
			receiptList = new ArrayList<ReceiptProduct>();
		}

		return receiptList;
	}
	
	public List<BuyShopping> getBuyShoppingList(int type) throws AppException {
		List<BuyShopping> shoppingList = null;

		try {
			checkNetworkConnected();
			switch(type) {
			case TYPE_DAILY:
				shoppingList = m_apiclient.getBuyDailyList();
				break;
			case TYPE_LABOR:
				shoppingList = m_apiclient.getBuyLaborList();
				break;
			case TYPE_FOOD:
				shoppingList = m_apiclient.getBuyFoodList();
				break;
			default:
				break;
			}
		} catch (AppException e) {
			throw e;
		}
		if (shoppingList == null) {
			shoppingList = new ArrayList<BuyShopping>();
		}

		return shoppingList;
	}
	
	//所内巡诊历史
	public MedicineHistory getMedHistoryList(String roomId, String pid,String startDate,String endDate,String medType) throws AppException {

		MedicineHistory medicineHistory = null;

		try {
			checkNetworkConnected();
			medicineHistory = m_apiclient.getSendMedHistoryList(roomId, pid,startDate,endDate,medType);
		} catch (AppException e) {
			throw e;
		}
		if (medicineHistory == null) {
			medicineHistory = new MedicineHistory();
		}

		return medicineHistory;
	}
	
	//所内卫生防疫
		public MedicineCleanHistory getMedCleanHistoryList(String roomId, String pid,String startDate,String endDate,String medType) throws AppException {

			MedicineCleanHistory medicineHistory = null;

			try {
				checkNetworkConnected();
				medicineHistory = m_apiclient.getMedCleanHistoryList(roomId, pid,startDate,endDate,medType);
			} catch (AppException e) {
				throw e;
			}
			if (medicineHistory == null) {
				medicineHistory = new MedicineCleanHistory();
			}

			return medicineHistory;
		}
	
		//药方配药药品获取
		public MedicineTypeAndNamesListParse getSendMedTypeAndNames() throws AppException {

			MedicineTypeAndNamesListParse mediceAndNamesListParse = null;

			try {
				checkNetworkConnected();
				mediceAndNamesListParse = m_apiclient.getSendMedTypeAndNames();
			} catch (AppException e) {
				throw e;
			}
			if (mediceAndNamesListParse == null) {
				mediceAndNamesListParse = new MedicineTypeAndNamesListParse();
			}

			return mediceAndNamesListParse;
		}
		
		//药品单位
		public MedicineUnit getMedicineUnits() throws AppException {

			MedicineUnit medicineUnit = null;

			try {
				checkNetworkConnected();
				medicineUnit = m_apiclient.getMedicineUnits();
			} catch (AppException e) {
				throw e;
			}
			if (medicineUnit == null) {
				medicineUnit = new MedicineUnit();
			}

			return medicineUnit;
		}
		
		
		   //配药
				public MedcineResult submitMedicineData(String pId,String currentTime,String cId,
						List<SendMedicineData> sendMedicineList,String uId) throws AppException {

					MedcineResult medicineResult = null;

					try {
						checkNetworkConnected();
						medicineResult = m_apiclient.submitMedicineData(pId,currentTime,cId,sendMedicineList,uId);
					} catch (AppException e) {
						throw e;
					}
					if (medicineResult == null) {
						medicineResult = new MedcineResult();
					}

					return medicineResult;
				}
		
		
		
	public MedcineResult postMedcineResultInfo(String cid, String postParam) throws AppException {

		MedcineResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postMedcineResultInfo(cid, postParam);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}

	public OutInfoList getOutInfoList(String cid) throws AppException {

		OutInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getOutInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new OutInfoList();
		}

		return infolist;
	}

	public SecurityInfoList getSecurityInfoList() throws AppException {

		SecurityInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getSecurityInfoList();
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new SecurityInfoList();
		}

		return infolist;
	}

	public PowerInfoList getPowerInfoList(String cid) throws AppException {

		PowerInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getPowerInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new PowerInfoList();
		}

		return infolist;
	}
	
	public RuleBreakInfoList getRuleBreakInfoList(String pid) throws AppException {

		RuleBreakInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getRuleBreakInfoList(pid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new RuleBreakInfoList();
		}

		return infolist;
	}
	
	public RuleBreakItemsList getRuleBreakItemsList() throws AppException {

		RuleBreakItemsList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getRuleBreakItemsList();
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new RuleBreakItemsList();
		}

		return infolist;
	}
	
	public TradeInfoList getTradeInfoList(String pid) throws AppException {

		TradeInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getTradeInfoList(pid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new TradeInfoList();
		}

		return infolist;
	}
	
	public TrainingList getTrainingList() throws AppException {

		TrainingList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getTrainingList();
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new TrainingList();
		}

		return infolist;
	}
	

	public PowerCtrlResult postPowerCtrlResult(String cids, String postParam) throws AppException {

		PowerCtrlResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postPowerCtrlResult(cids, postParam);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	
	public TrainingResult postTrainingResult(final String cids, final String type, final String id ) throws AppException {

		TrainingResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postTrainingResult(cids, type, id);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}

	
	public RuleBreakResult postRuleBreakResult(final String cid, final String pid, final String content) throws AppException {

		RuleBreakResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postRuleBreakResult(cid, pid, content);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	
	public MapInfo getMapInfo() throws AppException {

		MapInfo info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getMapInfo();
		} catch (AppException e) {
			throw e;
		}
		if (info == null) {
			info = new MapInfo();
		}

		return info;
	}

	public MapLocationList getMapLocationList() throws AppException {

		MapLocationList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getMapLocationList();
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new MapLocationList();
		}

		return infolist;
	}
	
	
	public MonitorInfoList getMonitorInfoList() throws AppException {

		MonitorInfoList infolist = null;

		try {
			checkNetworkConnected();
			String cid = m_setting.getID();
			infolist = m_apiclient.getMonitorInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new MonitorInfoList();
		}

		return infolist;
	}
	
	
	public BedInfoList getBedInfoList( String cid) throws AppException {

		BedInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getBedInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new BedInfoList();
		}

		return infolist;
	}
	
	
	public DutyInfoList getDutyInfoList( String cid) throws AppException {

		DutyInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getDutyInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new DutyInfoList();
		}

		return infolist;
	}
	
	public AgentInfoList getAgentInfoList( String cid) throws AppException {

		AgentInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getAgentInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new AgentInfoList();
		}

		return infolist;
	}
	
	
	public CommonResult postDoctorNote(String cid, String postParam) throws AppException {

		CommonResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postDoctorNote(cid, postParam);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	
	public CommonResult postPoliceNote(String cid, String postParam) throws AppException {

		CommonResult info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.postPoliceNote(cid, postParam);
		} catch (AppException e) {
			throw e;
		}
		
		return info;
	}
	
	
	public SecurityInfoList getSecurityRBInfoList(String cid) throws AppException {

		SecurityInfoList infolist = null;

		try {
			checkNetworkConnected();
			infolist = m_apiclient.getSecurityRBInfoList(cid);
		} catch (AppException e) {
			throw e;
		}
		if (infolist == null) {
			infolist = new SecurityInfoList();
		}

		return infolist;
	}
	
	public ServerTime getServerTime() throws AppException {

		ServerTime info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getServerTime();
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new ServerTime();
		}

		return info;
	}
	
	public PatrolResult postPatrol(String pid) throws AppException {

		PatrolResult info = null;

		try {
			checkNetworkConnected();
			String ip = IpUtil.getIp();
			String deviceId = m_setting.getID();
			info = m_apiclient.postPatrol(pid,ip,deviceId);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new PatrolResult();
		}

		return info;
	}
	
	
	
	
	public List<Police> getAllPolice() throws AppException {

		List<Police> info = null;

		try {
			checkNetworkConnected();
			info = m_apiclient.getAllPolice();
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new ArrayList<Police>();
		}

		return info;
	}
	
	public List<PatrolHistory> getPatrolHistory() throws AppException {

		List<PatrolHistory> info = null;
		String deviceId = m_setting.getID();
		try {
			checkNetworkConnected();
			info = m_apiclient.getPatrolHistory(deviceId);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new ArrayList<PatrolHistory>();
		}

		return info;
	}
	
	public List<RoomIndex> getIndexData() throws AppException {

		List<RoomIndex> info = null;
		String deviceId = m_setting.getID();
		try {
			checkNetworkConnected();
			info = m_apiclient.getIndexData(deviceId);
		} catch (AppException e) {
			throw e;
		}

		if (info == null) {
			info = new ArrayList<RoomIndex>();
		}

		return info;
	}
	
}
