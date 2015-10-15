package com.wenyu.Utils;


import imsdk.data.IMSDK.OnActionProgressListener;
import imsdk.data.mainphoto.IMMyselfMainPhoto;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.wenyu.kaijiw.R;

public class UrlToImage {
	public static void getImage(String path,RequestQueue queue,ImageView img) throws Exception{
		if(path.contains("default")){
			path = ConstantClassField.SERVICE_URL+"images/default.png";
		}
		ImageLoader imageLoader = new ImageLoader(queue, new BitmapImage());
		ImageListener imageListener = ImageLoader.getImageListener(img, R.drawable.filmonimg, R.drawable.filmonimg);
		imageLoader.get(path, imageListener);
	}

	public static void uploadIM(Bitmap bitmap,final Context context) {

		// ��IMSDK���������ϴ�ͼƬ��Ϣ
		IMMyselfMainPhoto.upload(bitmap, new OnActionProgressListener() {
			@Override
			public void onSuccess() {
				//  					 Toast.makeText(context, "�ϴ��ɹ�",Toast.LENGTH_SHORT).show(); 
			}

			@Override
			public void onProgress(double progress) {
				// �ϴ����ȵĻص�
				// progressΪȡֵ��Χ��0��1�ĸ�����
				if(progress!=1){
					//  					 Toast.makeText(context, "�ϴ����� : " + (int) (progress * 100) + "%",Toast.LENGTH_SHORT).show(); 
				}
			}

			@Override
			public void onFailure(String error) {
				//  					 Toast.makeText(context, "�ϴ�ʧ�� : " + error,Toast.LENGTH_SHORT).show(); 
			}
		});




	}


}
