package com.wenyu.kaijiw;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.wenyu.Data.ScreenManager;
import com.wenyu.kaijiw.fragment.LightScene;
import com.wenyu.kaijiw.fragment.LightShopFragment;

/**
 * Ê×Ò³_µÆ¹âÆ÷²Ä
 * @author shasha
 *
 */
public class LightActivity extends FragmentActivity {
	private List<Fragment> li;
	private LightShopFragment fs;
	private LightScene fsc;
	private RadioButton  scan,collect;
	private  ImageView image;
	ScreenManager sm;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.footl_list_title);
		initView();
	}

	private void initView() {
	sm =  ScreenManager.gerScreenManager();
	sm.pushActivity(LightActivity.this);
		image = (ImageView) findViewById(R.id.image);
		scan = (RadioButton)findViewById(R.id.liu);
		collect = (RadioButton)findViewById(R.id.shoucang);
		scan.setText("µê¼Ò");
		collect.setText("Æ÷²Ä");;
		scan.setOnClickListener(ol);
		collect.setOnClickListener(ol);
		li = new ArrayList<Fragment>();
		fs = new LightShopFragment();
		fsc =  new LightScene();
		li.add(fs);
		li.add(fsc);
		String name1 = getIntent().getStringExtra("name");
		String s = getIntent().getStringExtra("s");
		String customer_id = getIntent().getStringExtra("customer_id");
		Bundle bundle = new Bundle();
		bundle.putString("name", name1);
		bundle.putString("s", s);
		bundle.putString("customer_id", customer_id);
		fs.setArguments(bundle);
		fsc.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.frame, fs).hide(fs).add(R.id.frame, fsc).show(fsc).commit();
		image.setOnClickListener(ol);
	}
	
	
	
	OnClickListener ol =new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.liu:
				scan.setTextColor(Color.WHITE);
				collect.setTextColor(Color.RED);
				scan.setBackgroundResource(R.drawable.redleft);
				collect.setBackgroundResource(R.drawable.writeright);
				replace(0);
				break;

			case R.id.shoucang:
				collect.setTextColor(Color.WHITE);
				scan.setTextColor(Color.RED);
				collect.setBackgroundResource(R.drawable.right);
				scan.setBackgroundResource(R.drawable.left);
				replace(1);
				break;
			case R.id.image:
				sm.finishActivity();
			break;

			}
		}
	};
	private void replace(int i) {
		for (int j = 0; j < li.size(); j++) {
			if(j==i){
				getSupportFragmentManager().beginTransaction().show(li.get(i)).commit();
			}else{
				getSupportFragmentManager().beginTransaction().hide(li.get(j)).commit();
			}
		}

	}
}


