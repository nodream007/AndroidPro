package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class ReceiptProductParse {
	public static List<ReceiptProduct> parse(InputStream in)
			throws AppException {
		List<ReceiptProduct> receiptProducts = null;
		ReceiptProduct product = null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("receiptproducts")) {
						receiptProducts = new ArrayList<ReceiptProduct>();
					} else if (name.equals("product")) {
						product = new ReceiptProduct();
						product.setId(parser.getAttributeValue(null, "id"));
						product.setName(parser.getAttributeValue(null, "name"));
						product.setPrice(Double.parseDouble(parser.getAttributeValue(null, "price")));
						product.setCount(Integer.parseInt(parser.getAttributeValue(null, "count")));
						receiptProducts.add(product);
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {

				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} catch (IOException e) {
			throw AppException.xml(e);
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		}
		return receiptProducts;
	}
}
