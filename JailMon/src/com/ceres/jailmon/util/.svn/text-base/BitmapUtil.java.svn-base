package com.ceres.jailmon.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class BitmapUtil {
	public static Bitmap getImage(Activity activity,String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		byte[] data;
		con.setRequestMethod("GET");
		if (con.getResponseCode() == 200) {
			InputStream in = con.getInputStream();
			data = read2Byte(in);
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int widthPixels = dm.widthPixels;
			int heightPixels = dm.heightPixels;
			int displaypixels = widthPixels * heightPixels;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);
			opts.inJustDecodeBounds = false;
			return BitmapFactory.decodeByteArray(data, 0, data.length, opts);

		} else
			return null;
	}
	
	
	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		byte[] data;
		con.setRequestMethod("GET");
		if (con.getResponseCode() == 200) {
			InputStream in = con.getInputStream();
			data = read2Byte(in);
			return data;

		} else
			return null;
	}

	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	private static byte[] read2Byte(InputStream in) throws IOException {
		byte[] data;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			bout.write(buf, 0, len);
		}
		data = bout.toByteArray();
		return data;
	}
}
