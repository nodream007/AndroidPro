/*
 * Description: APIs for get or post data.
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */

package com.ceres.jailmon;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.AgentInfoList;
import com.ceres.jailmon.data.AuthResult;
import com.ceres.jailmon.data.BedInfoList;
import com.ceres.jailmon.data.BuyDailyAnalyze;
import com.ceres.jailmon.data.BuyFoodAnalyze;
import com.ceres.jailmon.data.BuyLaborAnalyze;
import com.ceres.jailmon.data.BuyShopping;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.CommonResult;
import com.ceres.jailmon.data.DutyInfoList;
import com.ceres.jailmon.data.GoodsList;
import com.ceres.jailmon.data.IndexParse;
import com.ceres.jailmon.data.MapInfo;
import com.ceres.jailmon.data.MapLocationList;
import com.ceres.jailmon.data.MedCleanHistoryListParse;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.MedRoundSendMedicineListParse;
import com.ceres.jailmon.data.MedSendMedicineInHealthyInfoParse;
import com.ceres.jailmon.data.MedSendMedicineInHistoryListParse;
import com.ceres.jailmon.data.MedSendMedicineOutHistoryListParse;
import com.ceres.jailmon.data.MedcineInfoList;
import com.ceres.jailmon.data.MedcineResult;
import com.ceres.jailmon.data.MedicineCleanHistory;
import com.ceres.jailmon.data.MedicineHealthyInfo;
import com.ceres.jailmon.data.MedicineHistory;
import com.ceres.jailmon.data.MedicinePeopleInfo;
import com.ceres.jailmon.data.MedicineRoundPeopleInfoParse;
import com.ceres.jailmon.data.MedicineTypeAndNamesListParse;
import com.ceres.jailmon.data.MedicineUnit;
import com.ceres.jailmon.data.MedicineUnitsParse;
import com.ceres.jailmon.data.MonitorInfoList;
import com.ceres.jailmon.data.OutInfoList;
import com.ceres.jailmon.data.PatrolHistory;
import com.ceres.jailmon.data.PatrolHistoryParse;
import com.ceres.jailmon.data.PatrolResult;
import com.ceres.jailmon.data.Police;
import com.ceres.jailmon.data.PoliceInfo;
import com.ceres.jailmon.data.PowerCtrlResult;
import com.ceres.jailmon.data.PowerInfoList;
import com.ceres.jailmon.data.PrisonerDetail;
import com.ceres.jailmon.data.PrisonerList;
import com.ceres.jailmon.data.PurchaseResult;
import com.ceres.jailmon.data.ReceiptProduct;
import com.ceres.jailmon.data.ReceiptProductParse;
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

public class ApiClient {

	public static final String UNIT_ID = "43068111";

	public static final String API_LOGIN = "/VerificationUse.aspx?usr=%s&pass=%s";

	public static final String API_SIGNIN = "/signin.aspx?id=%s";

	public static final String API_GET_CELL_LIST = "/getjsnum.aspx?cid=%s";

	public static final String API_GET_CELL_PRISONER_LIST = "/getpersonsbyroom.aspx?roomid=%s";

	public static final String API_GET_PERSON_DETAIL = "/getbasepersoninfobyid.aspx?pid=%s";

	public static final String API_GET_PERSON_PHOTO = "/getphotobyid.aspx?pid=%s";

	public static final String API_GET_POLICE_PHOTO = "/getpolicephoto.aspx?id=%s";

	public static final String API_GET_PERSON_BALANCE = "/getpersonremainder.aspx?personid=%s";

	public static final String API_GET_SECURITY_INFO_LIST = "/getallsafetydefenseinfo.aspx?timefrom=&timeto=&suoid=";

	public static final String API_GET_MEDICATION_LIST = "/getmedicinfo.aspx?roomid=%s";

	public static final String API_GET_ILLTYPE_INCLUDE_MEDICINE_LIST = "/getIllTypeIncludeMedicine.aspx";

	public static final String API_GET_MED_ROUND_SEND_MED_LIST = "/getRoundSendMedicine.aspx?roomid=%s";

	public static final String API_GET_GOODS_LIST = "/getgoodinfo.aspx?suoid="
			+ UNIT_ID;

	public static final String API_GET_MAP_INFO = "/getmapinfo.aspx?suoid="
			+ UNIT_ID;

	public static final String API_GET_POLICE_LOCATION = "/getpersoncoordinate.aspx?suoid="
			+ UNIT_ID;

	public static final String API_GET_OUT_INFO = "/getoutinfo.aspx?userName=%s";

	public static final String API_GET_POWER_INFO = "/getpowerinfo.aspx?roomid=%s&suoid="
			+ UNIT_ID;

	public static final String API_GET_SHIFT_INFO = "/getshiftinfo.aspx?suoid="
			+ UNIT_ID + "&weekday=%s";

	public static final String API_POST_MEDICATION_RESULT_INFO = "/postmedicinfo.aspx";

	public static final String API_POST_PURCHASE_RESULT_INFO = "/postpurchase.aspx";

	public static final String API_POST_RECEIPT_RESULT_INFO = "/postreceipt.aspx";

	public static final String API_POST_POWERCTRL_RESULT_INFO = "/postpowerctrl.aspx";

	public static final String API_GET_RULE_BREAK_INFO_LIST = "/getrulebreak.aspx?pid=%s";

	public static final String API_GET_TRADE_INFO_LIST = "/getpersonaccount.aspx?pid=%s";

	public static final String API_GET_TRAINING_LIST = "/gettraining.aspx";

	public static final String API_POST_TRAINING_RESULT_INFO = "/posttrainingctrl.aspx";

	public static final String API_GET_RULE_BREAK_ITEMS_LIST = "/getrbitems.aspx";

	public static final String API_GET_MONITOR_INFO_LIST = "/getmonitorinfo.aspx?cid=%s";

	public static final String API_POST_RULE_BREAK_INFO = "/postrulebreak.aspx";

	public static final String API_GET_BED_INFO_LIST = "/getbedinfo.aspx?cid=%s";

	public static final String API_GET_DUTY_INFO_LIST = "/getdutyinfo.aspx?cid=%s";

	public static final String API_GET_AGENT_INFO_LIST = "/getagentinfo.aspx?cid=%s";

	public static final String API_POST_DOCTOR_NOTE = "/postdoctornote.aspx";

	public static final String API_POST_POLICE_NOTE = "/postpolicenote.aspx";

	public static final String API_GET_SERVER_TIME = "/gettime.aspx";

	public static final String API_GET_SECURITY_RB_INFO_LIST = "/getsafetydefenserbinfo.aspx?cid=%s";

	public static final String API_GET_MED_PEOPLE_INFO = "/getMedicinePeopleInfo.aspx?pid=%s";

	public static final String API_GET_MED_HISTORY_INFO = "/getmedhistory.aspx?roomid=%s&pid=%s&startDate=%s&endDate=%s&medType=%s";
	
	public static final String API_GET_MED_OUT_HISTORY_INFO = "/getmedOuthistory.aspx?roomid=%s&pid=%s&startDate=%s&endDate=%s&medType=%s";
	
	public static final String API_GET_MED_HEALTHY_INFO = "/gethealthyinfo.aspx?pid=%s";

	public static final String API_GET_MED_CLEAN_HISTORY_INFO = "/getmedcleanhistory.aspx?roomid=%s&pid=%s&startDate=%s&endDate=%s&medType=%s";

	public static final String API_GET_MEDICINE_TYPE_AND_NAMES = "/getMedicineTypeAndNames.aspx";

	public static final String API_GET_MEDICINE_UNIT = "/getMedicineUnits.aspx";

	public static final String API_GIVE_MEDICINE = "/giveMedConfirm.aspx";

	public static final String API_GET_RYP_PRODUCT = "/getrypproduct.aspx";

	public static final String API_GET_LBYP_PRODUCT = "/getlbypproduct.aspx";

	public static final String API_GET_FOOD_PRODUCT = "/getfoodproduct.aspx";

	public static final String API_GET_RECEIPT_PRODUCT = "/getreceiptproduct.aspx?pid=%s";

	public static final String API_GET_TX_RESULT = "/gettxresult.aspx";

	public static final String API_GET_ALL_POLICE = "/getPoliceManInfo.aspx";

	public static final String API_GET_PATROL_HISTORY = "/GetPolicePatrol.aspx?deviceid=%s";

	public static final String API_GET_INDEX = "/getIndexData.aspx?deviceid=%s";

	public static final String API_GET_POST_PATROL = "/PostPolicePatrol.aspx";

	static final String[][] LOCAL_TABLE = { { "getjsnum", "getjsnum.xml" },
			{ "getpersonsbyroom", "getpersonsbyroom.xml" },
			{ "VerificationUse", "VerificationUse.xml" },
			{ "getbasepersoninfobyid", "getbasepersoninfobyid.xml" },
			{ "getallsafetydefenseinfo", "getallsafetydefenseinfo.xml" },
			{ "getmedicinfo", "getmedicinfo.xml" },
			{ "getpersonremainder", "getpersonremainder.xml" },
			{ "getgoodinfo", "getgoodinfo.xml" },
			{ "getmapinfo", "getmapinfo.xml" }, { "map.jpg", "map.jpg" },
			{ "getpersoncoordinate", "getpersoncoordinate.xml" },
			{ "getoutinfo", "getoutinfo.xml" },
			{ "getpowerinfo", "getpowerinfo.xml" },
			{ "getshiftinfo", "getshiftinfo.xml" },
			{ "getphotobyid", "photo.jpg" },
			{ "postmedicinfo", "postmedicinfo.xml" },
			{ "postpowerctrl", "postpowerctrl.xml" },
			{ "postpurchase", "postpurchase" },
			{ "postreceipt", "postreceipt" },
			{ "getrulebreak", "getrulebreak.xml" },
			{ "getpersonaccount", "getpersonaccount.xml" },
			{ "gettraining", "gettraining.xml" },
			{ "getrbitems", "getrbitems.xml" },
			{ "postrulebreak", "postrulebreak.xml" },
			{ "getmonitorinfo", "getmonitorinfo.xml" },
			{ "getbedinfo", "getbedinfo.xml" },
			{ "getdutyinfo", "getdutyinfo.xml" },
			{ "getsafetydefenserbinfo", "getsafetydefenserbinfo.xml" },
			{ "getpolicephoto", "police_photo.jpg" },
			{ "postpolicenote", "postpolicenote.xml" },
			{ "postdoctornote", "postdoctornote.xml" },
			{ "signin.aspx", "signin.xml" },
			{ "getagentinfo", "getagentinfo.xml" },
			{ "getagentinfo", "getagentinfo.xml" },
			{ "getIllTypeIncludeMedicine", "getIllTypeIncludeMedicine.xml" },
			{ "getRoundSendMedicine", "getRoundSendMedicine.xml" },
			{ "getMedicinePeopleInfo", "getMedicinePeopleInfo.xml" },
			{ "getmedhistory", "getmedhistory.xml" },
			{ "getmedcleanhistory", "getmedcleanhistory.xml" },
			{ "getMedicineTypeAndNames", "getMedicineTypeAndNames.xml" },
			{ "getMedicineUnits", "getMedicineUnits.xml" },
			{ "giveMedConfirm", "sendMedicineResult.xml" },
			{ "getrypproduct", "getrypproduct.xml" },
			{ "getlbypproduct", "getlbypproduct.xml" },
			{ "getfoodproduct", "getfoodproduct.xml" },
			{ "getreceiptproduct", "getreceiptproduct.xml" },
			{ "gettxresult", "gettxresult.xml" },
			{ "getPoliceManInfo", "getpolice.xml" },
			{ "GetPolicePatrol", "getPatrolHistory.xml" },
			{ "PostPolicePatrol", "PatrolResult" },
			{ "getIndexData", "getIndex.xml" },
			{ "gethealthyinfo", "gethealthyinfo.xml" },
			{ "getmedOuthistory", "getmedhistoryout.xml" }};

	public AppContext m_application;

	public ApiClient(AppContext app) {
		m_application = app;

	}

	static String getDemoXMLFile(String url) {
		for (int i = 0; i < LOCAL_TABLE.length; i++) {
			if (url.contains(LOCAL_TABLE[i][0]))
				return LOCAL_TABLE[i][1];
		}
	
		return null;
	}

	public String getAPIUrl(String url) {

		String server = m_application.m_setting.getServer();
		boolean bTest = false;

		if (server.contains("@php")) {
			server = server.replace("@php", "");
			bTest = true;
		}

		if (bTest) {
			url = url.replace("aspx", "php");
			return "http://" + server + "/JailMon" + url;
		} else
			return "http://" + server + /* "/JailMon" + */url;
	}

	public AuthResult getAuthResult(String user, String passwd)
			throws AppException {
		String url = String.format(getAPIUrl(API_LOGIN), user, passwd);

		AuthResult info = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				info = AuthResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public SignInResult getSignInResult(String id) throws AppException {
		String url = String.format(getAPIUrl(API_SIGNIN), id);

		SignInResult info = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				info = SignInResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public CellList getCellList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_CELL_LIST), cid);

		CellList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = CellList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public PrisonerList getPrisonerList(String cell) throws AppException {
		String url = String.format(getAPIUrl(API_GET_CELL_PRISONER_LIST), cell);

		PrisonerList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = PrisonerList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public PrisonerDetail getPrisonerDetail(String pid) throws AppException {
		String url = String.format(getAPIUrl(API_GET_PERSON_DETAIL), pid);

		PrisonerDetail info = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				info = PrisonerDetail.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public Bitmap getPrisonerPhoto(String pid) throws AppException {
		String url = String.format(getAPIUrl(API_GET_PERSON_PHOTO), pid);

		Bitmap bmp = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				// options.inSampleSize = 10; //width，hight设为原来的十分一
				bmp = BitmapFactory.decodeStream(is, null, options);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return bmp;
	}

	public Bitmap getPolicePhoto(String id) throws AppException {
		String url = String.format(getAPIUrl(API_GET_POLICE_PHOTO), id);

		Bitmap bmp = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				bmp = BitmapFactory.decodeStream(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return bmp;
	}

	public Account getPrisonerAccount(String pid) throws AppException {
		String url = String.format(getAPIUrl(API_GET_PERSON_BALANCE), pid);

		Account info = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				info = Account.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public ShiftInfoList getShiftInfoList(final String day) throws AppException {

		String url = String.format(getAPIUrl(API_GET_SHIFT_INFO), day);

		ShiftInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = ShiftInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public MapInfo getMapInfo() throws AppException {

		String url = String.format(getAPIUrl(API_GET_MAP_INFO));

		MapInfo info = null;

		boolean bRefresh = true;

		try {
			InputStream is = null;
			InputStream isBmp = null;

			is = requestHttpGet(url);

			if (is != null) {
				info = MapInfo.parse(is);
				is.close();
			}

			if (info != null) {
				int cacheMapVer = m_application.m_setting.loadMapVersion();

				bRefresh = (info.ver != cacheMapVer);

				info.m_bmp = null;

				if (!bRefresh) {
					String filepath = m_application.getFilesDir() + "/map.jpg";
					info.m_bmp = BitmapFactory.decodeFile(filepath);
				} else
					m_application.m_setting.saveMapVersion(info.ver);

				if (info.m_bmp == null) {
					String urlBmp = (info.url.contains("http")) ? info.url
							: getAPIUrl(info.url);
					isBmp = requestHttpGet(urlBmp);

					if (isBmp != null) {
						info.m_bmp = BitmapFactory.decodeStream(isBmp);
						saveBitmap(info.m_bmp, "/map.jpg");
						isBmp.close();
					}
				}
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public MapLocationList getMapLocationList() throws AppException {

		String url = String.format(getAPIUrl(API_GET_POLICE_LOCATION));

		MapLocationList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = MapLocationList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public MedcineInfoList getMedcineInfoList(final String cid)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MEDICATION_LIST), cid);

		MedcineInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = MedcineInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public TotalPrisonSituationList getTotalPrisonSituationList()
			throws AppException {

		String url = String
				.format(getAPIUrl(API_GET_ILLTYPE_INCLUDE_MEDICINE_LIST));

		TotalPrisonSituationList totalPrisonSituationList = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				totalPrisonSituationList = TotalPrisonSituationList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return totalPrisonSituationList;
	}

	public MedicinePeopleInfo getMedicinePeopleInfo(String roomId, String pid)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_PEOPLE_INFO), pid);
		MedicinePeopleInfo medicinePeopleInfo = null;
		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicinePeopleInfo = MedicineRoundPeopleInfoParse.parse(is);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicinePeopleInfo;
	}

	public MedicineHistory getSendMedHistoryList(String roomId, String pid,
			String startDate, String endDate, String medType)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_HISTORY_INFO), roomId,
				pid, startDate, endDate, medType);

		MedicineHistory medicinePatientInfo = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicinePatientInfo = MedSendMedicineInHistoryListParse
						.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicinePatientInfo;
	}
	/**
	 * 所外医疗记录
	 * @param roomId
	 * @param pid
	 * @param startDate
	 * @param endDate
	 * @param medType
	 * @return
	 * @throws AppException
	 */
	public MedicineHistory getSendMedOutHistoryList(String roomId, String pid,
			String startDate, String endDate, String medType)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_OUT_HISTORY_INFO), roomId,
				pid, startDate, endDate, medType);

		MedicineHistory medicinePatientInfo = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicinePatientInfo = MedSendMedicineOutHistoryListParse
						.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicinePatientInfo;
	}
	/**
	 * 查询犯人的健康情况
	 * @param pid
	 * @return
	 * @throws AppException
	 */
	public MedicineHealthyInfo getSendMedHealthy(String pid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_HEALTHY_INFO), pid);

		MedicineHealthyInfo medicineHealthyInfo = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicineHealthyInfo = MedSendMedicineInHealthyInfoParse
						.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicineHealthyInfo;
	}

	public List<ReceiptProduct> getReceiptList(String pid) throws AppException {
		String url = String.format(getAPIUrl(API_GET_RECEIPT_PRODUCT), pid);
		Log.d("jiayy", "url = " + url);
		List<ReceiptProduct> productList = null;
		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				productList = ReceiptProductParse.parse(is);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}

	public List<BuyShopping> getBuyDailyList() throws AppException {
		String url = String.format(getAPIUrl(API_GET_RYP_PRODUCT));
		List<BuyShopping> shoppingList = null;
		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				shoppingList = BuyDailyAnalyze.getBuyDaily(is, m_application);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoppingList;
	}

	public List<BuyShopping> getBuyLaborList() throws AppException {
		String url = String.format(getAPIUrl(API_GET_LBYP_PRODUCT));
		List<BuyShopping> shoppingList = null;
		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				shoppingList = BuyLaborAnalyze.getBuyLabor(is, m_application);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoppingList;
	}

	public List<BuyShopping> getBuyFoodList() throws AppException {
		String url = String.format(getAPIUrl(API_GET_FOOD_PRODUCT));
		List<BuyShopping> shoppingList = null;
		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				shoppingList = BuyFoodAnalyze.getBuyFood(is, m_application);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoppingList;
	}

	public MedicineCleanHistory getMedCleanHistoryList(String roomId,
			String pid, String startDate, String endDate, String medType)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_CLEAN_HISTORY_INFO),
				roomId, pid, startDate, endDate, medType);

		MedicineCleanHistory medicinePatientInfo = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicinePatientInfo = MedCleanHistoryListParse.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicinePatientInfo;
	}

	public MedRoundSendMedicineList getMedRoundSendMedicineList(String cid)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MED_ROUND_SEND_MED_LIST),
				cid);

		MedRoundSendMedicineList medRoundSendMedicineList = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medRoundSendMedicineList = MedRoundSendMedicineListParse
						.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medRoundSendMedicineList;
	}

	public MedcineResult postMedcineResultInfo(final String cid,
			final String postParam) throws AppException {

		String url = String.format(getAPIUrl(API_POST_MEDICATION_RESULT_INFO));

		MedcineResult info = null;

		String field = String.format("roomid=%s&result=%s", cid, postParam);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = MedcineResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public MedicineTypeAndNamesListParse getSendMedTypeAndNames()
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_MEDICINE_TYPE_AND_NAMES),
				"");

		MedicineTypeAndNamesListParse mediceAndNamesListParse = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				mediceAndNamesListParse = MedicineTypeAndNamesListParse
						.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return mediceAndNamesListParse;

	}

	public MedicineUnit getMedicineUnits() throws AppException {

		String url = String.format(getAPIUrl(API_GET_MEDICINE_UNIT), "");

		MedicineUnit medicineUnit = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				medicineUnit = MedicineUnitsParse.parse(is);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return medicineUnit;

	}

	public MedcineResult submitMedicineData(String pId, String currentTime,
			String cId, List<SendMedicineData> sendMedicineList, String uId)
			throws AppException {

		MedcineResult info = null;
		String medStr = getMedicineListStr(sendMedicineList);
		String url = String.format(getAPIUrl(API_GIVE_MEDICINE));
		url = url.replaceAll(" ", "");

		String field = String.format(
				"pId=%s&currentTime=%s&cId=%s&medStr=%s&userName=%s", pId,
				currentTime, cId, medStr, m_application.getSharedPreferences("LoginActivity", 0).getString("USER", "admin"));
		try {
			InputStream is = requestHttpPost(url, field);
			if (is != null) {
				info = MedcineResult.parse(is);
				is.close();
			}
		} catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;

	}

	private String getMedicineListStr(List<SendMedicineData> sendMedicineList) {
		StringBuilder medicineData = new StringBuilder();
		for (SendMedicineData sendMedicineData : sendMedicineList) {
			medicineData.append(sendMedicineData.getMedId() + "|"
					+ sendMedicineData.getMedName() + "|"
					+ sendMedicineData.getMedNum() + "|"
					+ sendMedicineData.getMedType() + "|"
					+ sendMedicineData.getMedUnit() + "|"
					+ sendMedicineData.getMedInstruction() + "|"
					+ sendMedicineData.getMedRemark() + ";");
		}
		return medicineData.toString();
	}

	public OutInfoList getOutInfoList(final String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_OUT_INFO), cid);

		OutInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = OutInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public PowerInfoList getPowerInfoList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_POWER_INFO), cid);

		PowerInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = PowerInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public PowerCtrlResult postPowerCtrlResult(final String cids,
			final String postParam) throws AppException {

		String url = String.format(getAPIUrl(API_POST_POWERCTRL_RESULT_INFO));

		PowerCtrlResult info = null;

		String field = String.format("roomid=%s&ctrl=%s", cids, postParam);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = PowerCtrlResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public SecurityInfoList getSecurityInfoList() throws AppException {

		String url = String.format(getAPIUrl(API_GET_SECURITY_INFO_LIST));

		SecurityInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = SecurityInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public GoodsList getGoodsList() throws AppException {

		String url = String.format(getAPIUrl(API_GET_GOODS_LIST));

		GoodsList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = GoodsList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public BedInfoList getBedInfoList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_BED_INFO_LIST), cid);

		BedInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = BedInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public DutyInfoList getDutyInfoList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_DUTY_INFO_LIST), cid);

		DutyInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = DutyInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public AgentInfoList getAgentInfoList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_AGENT_INFO_LIST), cid);

		AgentInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = AgentInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public RuleBreakInfoList getRuleBreakInfoList(String pid)
			throws AppException {

		String url = String
				.format(getAPIUrl(API_GET_RULE_BREAK_INFO_LIST), pid);

		RuleBreakInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = RuleBreakInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public TradeInfoList getTradeInfoList(String pid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_TRADE_INFO_LIST), pid);

		TradeInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = TradeInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public TrainingList getTrainingList() throws AppException {

		String url = String.format(getAPIUrl(API_GET_TRAINING_LIST));

		TrainingList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = TrainingList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public RuleBreakItemsList getRuleBreakItemsList() throws AppException {

		String url = String.format(getAPIUrl(API_GET_RULE_BREAK_ITEMS_LIST));

		RuleBreakItemsList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = RuleBreakItemsList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public MonitorInfoList getMonitorInfoList(String cid) throws AppException {

		String url = String.format(getAPIUrl(API_GET_MONITOR_INFO_LIST), cid);

		MonitorInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = MonitorInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public ReceiptResult postRecieptResult(final String pid, final String pwd)
			throws AppException {

		String url = String.format(getAPIUrl(API_POST_RECEIPT_RESULT_INFO));

		ReceiptResult info = null;

		String field = String.format("pid=%s&pwd=%s", pid, pwd);

		try {
			InputStream is = requestHttpPost(url, field);
			info = new ReceiptResult();
			if (is != null) {
				String result = inputStream2String(is);
				if (!TextUtils.isEmpty(result)) {
					info.setM_bOK(result);
				}
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public PurchaseResult postPurchaseResult(final String pid,
			final String postParam) throws AppException {

		String url = String.format(getAPIUrl(API_POST_PURCHASE_RESULT_INFO));
		PurchaseResult info = null;
		String field = String.format("pid=%s&goodslist=%s", pid, postParam);

		try {
			InputStream is = requestHttpPost(url, field);
			info = new PurchaseResult();
			if (is != null) {
				String result = inputStream2String(is);
				if (!TextUtils.isEmpty(result)) {
					info.setM_bOK(result);
				}
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public TXResult postTXResult(final String txID, final String txFlag)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_TX_RESULT));

		TXResult info = null;

		String field = String.format("txID=%s&txFlag=%s", txID, txFlag);

		try {
			InputStream is = requestHttpPost(url, field);
			info = new TXResult();
			if (is != null) {
				String result = inputStream2String(is);
				if (!TextUtils.isEmpty(result)) {
					info.setM_bOK(result);
				}
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	private String inputStream2String(InputStream is) throws IOException {
		int lenght = is.available();
		// 创建byte数组
		byte[] buffer = new byte[lenght];
		// 将文件中的数据读到byte数组中
		is.read(buffer);
		String result = EncodingUtils.getString(buffer, "utf-8");
		return result;
	}

	public TrainingResult postTrainingResult(final String cids,
			final String type, final String id) throws AppException {

		String url = String.format(getAPIUrl(API_POST_TRAINING_RESULT_INFO));

		TrainingResult info = null;

		String field = String.format("roomid=%s&type=%s&id=%s", cids, type, id);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = TrainingResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public RuleBreakResult postRuleBreakResult(final String cid,
			final String pid, final String content) throws AppException {

		String url = String.format(getAPIUrl(API_POST_RULE_BREAK_INFO));

		RuleBreakResult info = null;

		String field = String.format("cid=%s&pid=%s&%s", cid, pid, content);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = RuleBreakResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public CommonResult postDoctorNote(final String id, final String note)
			throws AppException {

		String url = String.format(getAPIUrl(API_POST_DOCTOR_NOTE));

		CommonResult info = null;

		String field = String.format("id=%s&note=%s", id, note);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = CommonResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public CommonResult postPoliceNote(final String id, final String note)
			throws AppException {

		String url = String.format(getAPIUrl(API_POST_POLICE_NOTE));

		CommonResult info = null;

		String field = String.format("id=%s&note=%s", id, note);

		try {
			InputStream is = requestHttpPost(url, field);

			if (is != null) {
				info = CommonResult.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public PatrolResult postPatrol(String pid, final String ip, String deviceId)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_POST_PATROL));
		PatrolResult info = null;
		String field = String.format("PoliceID=%s&nIp=%s&DeviceID=%s", pid, ip,
				deviceId);
		try {
			InputStream is = requestHttpPost(url, field);
			info = new PatrolResult();
			if (is != null) {
				String result = inputStream2String(is);
				if (!TextUtils.isEmpty(result)) {
					info.setM_bOK(result);
				}
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return info;
	}

	public SecurityInfoList getSecurityRBInfoList(String cid)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_SECURITY_RB_INFO_LIST),
				cid);

		SecurityInfoList infolist = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				infolist = SecurityInfoList.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return infolist;
	}

	public ServerTime getServerTime() throws AppException {

		String url = getAPIUrl(API_GET_SERVER_TIME);

		ServerTime svrtime = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				svrtime = ServerTime.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return svrtime;
	}

	public List<Police> getAllPolice() throws AppException {

		String url = getAPIUrl(API_GET_ALL_POLICE);

		List<Police> policeList = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				policeList = PoliceInfo.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return policeList;
	}

	public List<PatrolHistory> getPatrolHistory(String deviceId)
			throws AppException {

		String url = String.format(getAPIUrl(API_GET_PATROL_HISTORY), deviceId);

		List<PatrolHistory> patrolHistoryList = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				patrolHistoryList = PatrolHistoryParse.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return patrolHistoryList;
	}

	public List<RoomIndex> getIndexData(String deviceId) throws AppException {

		String url = String.format(getAPIUrl(API_GET_PATROL_HISTORY), deviceId);

		List<RoomIndex> roomIndexList = null;

		try {
			InputStream is = requestHttpGet(url);

			if (is != null) {
				roomIndexList = IndexParse.parse(is);
				is.close();
			}
		}

		catch (AppException e) {
			throw e;
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return roomIndexList;
	}

	static final int RET_OK = 0;
	static final int RET_TRANSFER = -1;
	static final int RET_WRONGXML = -2;
	static final int RET_INVALIDURL = -3;

	private static int m_last_error = RET_OK;

	static void setLastError(int error) {
		m_last_error = error;
	}

	int getLastError() {
		return m_last_error;
	}

	private InputStream requestHttpGet(String url) throws AppException {

		if (url == null)
			return null;

		Log.d("JM", String.format("Request URL : %s", url));

		InputStream stream = null;

		try {

			if (m_application.m_setting.getServer().equals("localhost"))
				stream = read_local(url);
			else
				stream = http_get(url);
			return stream;
		} catch (AppException e) {
			throw e;
		}
	}

	private InputStream requestHttpPost(String url, String param)
			throws AppException {

		if (url == null)
			return null;

		Log.d("JM", String.format("Request URL : %s", url));

		InputStream stream = null;

		try {

			if (m_application.m_setting.getServer().equals("localhost"))
				stream = read_local(url);
			else
				stream = http_post(url, param);
			return stream;
		} catch (AppException e) {
			throw e;
		}
	}

	private InputStream read_local(String url) throws AppException {

		String xml_file = getDemoXMLFile(url);
		Log.d("jiayy", "xml_file = " + xml_file);
		if (xml_file == null) {
			Exception e = new Exception();
			throw AppException.http(e);
		}

		AssetManager am = m_application.getAssets();
		InputStream is = null;

		try {
			is = am.open(xml_file);
		} catch (IOException e) {
			throw AppException.http(e);
		}

		return is;
	}

	private static InputStream http_get(String urlString) throws AppException {

		InputStream stream = null;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setUseCaches(true);
			conn.setRequestProperty("accept-charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			// Starts the query
			conn.connect();
			stream = conn.getInputStream();
			return stream;
		} catch (MalformedURLException e) {
			throw AppException.http(e);
		} catch (FileNotFoundException e) {
			throw AppException.filenotfound(e);
		} catch (IOException e) {

			throw AppException.http(e);
		}
	}

	private static InputStream http_post(String urlString, String param)
			throws AppException {

		InputStream stream = null;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			conn.setDoInput(true);

			// Starts the query
			conn.getOutputStream().write(param.getBytes());
			conn.connect();
			stream = conn.getInputStream();
			return stream;
		} catch (MalformedURLException e) {
			throw AppException.http(e);
		} catch (FileNotFoundException e) {
			throw AppException.filenotfound(e);
		} catch (IOException e) {

			throw AppException.http(e);
		}
	}

	public void saveBitmap(Bitmap bm, String fileName) throws IOException {

		String filepath = m_application.getFilesDir() + fileName;

		File file = new File(filepath);

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(file));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}
}
