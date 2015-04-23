package com.ceres.jailmon.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.ceres.jailmon.AppContext;

import android.util.Log;
import android.util.Xml;

public class BuyLaborAnalyze {
	private final static String TAG = "BuyLaborAnalyze";
	public static List<BuyShopping> getBuyLabor(InputStream inStream,AppContext context)
			throws Exception {
		List<BuyShopping> bsps = null;
		BuyShopping bsp = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(inStream, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				bsps = new ArrayList<BuyShopping>();
				break;
			case XmlPullParser.START_TAG:
				if ("lbypproduct".equals(pullParser.getName())) {
					int id = new Integer(pullParser.getAttributeValue(0));
					bsp = new BuyShopping();
					bsp.setId(id);
				} else if (bsp != null) {
					if ("count".equals(pullParser.getName())){
						bsp.setCount(pullParser.nextText());
					} else if ("pname".equals(pullParser.getName())) {
						bsp.setName(pullParser.nextText());
					} else if("content".equals(pullParser.getName())){
						bsp.setExplain(pullParser.nextText());
					} else if ("picurl".equals(pullParser.getName())) {
						String ip = context.m_setting.getServer();
						if (!ip.contains("localhost")) {
							String[] resultArray = null;
							String ipWithoutPort = "";
							if (ip.contains(":")) {
								resultArray = ip.trim().split(":");
								if (resultArray != null) {
									ipWithoutPort = resultArray[0];
									ip = ipWithoutPort + ":31012";
								}
							}
							bsp.setPicture("http://" + ip + "/"
									+ pullParser.nextText());
						} else {
							bsp.setPicture(pullParser.nextText());
						}
					} else if("price".equals(pullParser.getName())){
						bsp.setPrice(pullParser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if ("lbypproduct".equals(pullParser.getName())) {
					bsps.add(bsp);
				}
				break;
			}
			event = pullParser.next();
		}
		return bsps;
	}
}
