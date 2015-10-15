package com.example.test;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncBitmapLoader {
	private HashMap<String, SoftReference<BitmapDrawable>> imageCache;
	private ExecutorService executorService;

	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<BitmapDrawable>>();
		executorService = Executors.newFixedThreadPool(3);
	}

	public BitmapDrawable loadBitmap(final String imageURL,
			final ImageView imageView, final ImageCallBack imageCallBack) {
		if (imageCache.containsKey(imageURL)) {
			SoftReference<BitmapDrawable> reference = imageCache.get(imageURL);
			BitmapDrawable bitmapDrawable = reference.get();
			if (bitmapDrawable != null) {
				return bitmapDrawable;
			}
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				imageCallBack.imageLoad(imageView, (BitmapDrawable) msg.obj);
			}
		};
		executorService.submit(new Thread() {
			public void run() {
				BitmapDrawable bitmapDrawable = loadImageFromUrl(imageURL);
				imageCache.put(imageURL, new SoftReference<BitmapDrawable>(
						bitmapDrawable));
				Message message = handler.obtainMessage(0, bitmapDrawable);
				handler.sendMessage(message);
			};
		});
		return null;
	}

	public static BitmapDrawable loadImageFromUrl(String imageURL) {
		BitmapDrawable bitmapDrawable = null;
		try {
			URL url = new URL(imageURL);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			bitmapDrawable = new BitmapDrawable(
					httpURLConnection.getInputStream());
			httpURLConnection.disconnect();
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return bitmapDrawable;
	}
}
