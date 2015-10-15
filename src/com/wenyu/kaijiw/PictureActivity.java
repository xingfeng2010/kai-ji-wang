package com.wenyu.kaijiw;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.example.test.ImageCallBackDetail;
import com.example.test.MyGallery;
/**
 * ͼƬ
 * @author Life.Shen
 *
 */
public class PictureActivity  extends Activity{
	private ImageView image;
	private ImageCallBackDetail imageAdapter;
	private ArrayList<String> data,data2;
	private MyGallery gallery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictures);
		initView();
	}

	private void initView() {
		data = new ArrayList<String>();
		gallery = (MyGallery) findViewById(R.id.gy);
		data = getIntent().getStringArrayListExtra("pictures");
//		String pic =getIntent().getStringExtra("picture");
//		data2.add(pic);
		imageAdapter = new ImageCallBackDetail(PictureActivity.this,data);
		gallery.setAdapter(imageAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			int count = 1;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(count%2==0){
					
				}else{
					PictureActivity.this.finish();
				}
				count++;
			}
		});
	}

}
