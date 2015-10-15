package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import com.wenyu.Data.Urls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPositionActivity extends Activity {
	private PopupWindow popupwindow;  
	private Button button0,button1,button2,button3,button4,
	button5,button6,button7,button8,button9,ensure; 
	private View customView;
	private ImageView back;
	private TextView position;
	private ListView listView;
	private int width;
	private List<String[]> strs;
	private String production,result;
	private RelativeLayout imageback;
	private String url = Urls.Url_Certify;
	private String[] 
			str0={"编剧"},
			str1 = {"制片人","策划","制片主任", "外联制片", "现场制片", "生活制片", "演员制片", "制片助理"}, 
			str2 ={"导演","动作导演","执行导演","统筹","副导演","选角导演","场记","分镜师","导演助理"},
			str3 = {"美术指导", "造型指导", "执行美术", "副美术", "美术助理", "制景师"},
			str4={"摄影指导","摄影师","机械师","跟焦员","摄影助理","3D摄影师"},
			str5={"灯光指导","灯光师","灯光助理"},
			str6={"录音指导","录音师","录音助理"},
			str7={"造型指导","服装设计师","现场服装","服装组助理"},
			str8={"造型指导","化妆师","梳妆师","特效化妆师","化妆组助理"},
			str9={"道具设计师","现场道具师","特效道具师","陈设道具师"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_three_position);
		initView();
	}

	private void initView() {
		position=(TextView) findViewById(R.id.textPosition1);
		result = getIntent().getStringExtra("position");
		position.setText(result);
		button0 =(Button) findViewById(R.id.buttonPosition0);
		button1 =(Button) findViewById(R.id.buttonPosition1);
		button2 =(Button) findViewById(R.id.buttonPosition2);
		button3 = (Button) findViewById(R.id.buttonPosition3);
		button4 = (Button) findViewById(R.id.buttonPosition4);
		button5 = (Button) findViewById(R.id.buttonPosition5);
		button6 = (Button) findViewById(R.id.buttonPosition6);
		button7 = (Button) findViewById(R.id.buttonPosition7);
		button8 = (Button) findViewById(R.id.buttonPosition8);
		button9 = (Button) findViewById(R.id.buttonPosition9);

		ensure =	(Button) findViewById(R.id.buttonPosition);
		back =   (ImageView) findViewById(R.id.imagePosition1);
		strs = new ArrayList<String[]>();
		strs.add(str0);
		strs.add(str1);
		strs.add(str2);
		strs.add(str3);
		strs.add(str4);
		strs.add(str5);
		strs.add(str6);
		strs.add(str7);
		strs.add(str8);
		strs.add(str9);

		imageback = (RelativeLayout) findViewById(R.id.positionScreen);
		imageback.post(new Runnable() {	
			@Override
			public void run() {
				width = imageback.getWidth();
			}
		});
		button0.setOnClickListener(ol);	
		button1.setOnClickListener(ol);	
		button2.setOnClickListener(ol); 
		button3.setOnClickListener(ol);	
		button4.setOnClickListener(ol); 
		button5.setOnClickListener(ol);	
		button6.setOnClickListener(ol); 
		button7.setOnClickListener(ol);	
		button8.setOnClickListener(ol); 
		button9.setOnClickListener(ol);	
		ensure.setOnClickListener(ol);
		back.setOnClickListener(ol);
	}
	OnClickListener ol = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.buttonPosition:
				if(production!=null){
					Intent it = new Intent();    	
					if(result.equals("第一职业")){
						it.putExtra("production", production);
						FirstPositionActivity.this.setResult(1, it);
						FirstPositionActivity.this.finish();
					}else{
						it.putExtra("production2", production);
						FirstPositionActivity.this.setResult(2, it);
						FirstPositionActivity.this.finish();
					}

				}else{
					Toast.makeText(FirstPositionActivity.this, "请选择一个职业", Toast.LENGTH_SHORT).show();
				}	    	
				break;
			case R.id.buttonPosition0:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					//                return;  
				} else {  
					initmPopupWindowView(0);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  	
				break;
			case R.id.buttonPosition1:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					//                return;  
				} else {  
					initmPopupWindowView(1);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  	
				break;
			case R.id.buttonPosition2:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					//                 return;  
				} else {  
					initmPopupWindowView(2);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition3:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(3);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition4:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(4);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition5:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(5);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition6:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(6);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition7:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(7);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition8:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
				} else {  
					initmPopupWindowView(8);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.buttonPosition9:
				if (popupwindow != null&&popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					return;  
				} else {  
					initmPopupWindowView(9);  
					popupwindow.showAsDropDown(arg0, 0, 5);  
				}  		
				break;
			case R.id.imagePosition1:
				FirstPositionActivity.this.finish();
				break;
			default:
				break;
			}

		}
	};
	public void initmPopupWindowView(final int flag) {
		// // 获取自定义布局文件pop.xml的视图  
		customView = getLayoutInflater().inflate(R.layout.mine_three_position_menu,  
				null, false);  
		customView.setBackgroundColor(Color.GRAY);
		listView = (ListView) customView.findViewById(R.id.singlelistView3);
		listView.setAdapter(new ArrayAdapter<String>(customView.getContext(),
				android.R.layout.simple_list_item_single_choice, strs.get(flag)));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				default:
					production = strs.get(flag)[position];
					break;
				}

			}
		});

		// 创建PopupWindow实例,200,150分别是宽度和高度  
		popupwindow = new PopupWindow(customView, width, 280);    
		// 自定义view添加触摸事件  
		customView.setOnTouchListener(new OnTouchListener() {  

			@Override  
			public boolean onTouch(View v, MotionEvent event) {  
				if (popupwindow != null && popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					popupwindow = null;  
				}  

				return false;  
			}


		});  

	}
	OnClickListener ols = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			default:
				break;
			}

		}

	};
	//activity退出，结束窗口，防止窗口泄露
	@Override
	protected void onPause() {
		super.onPause();
		dismissPopupWindow();
	};
	//判断PopupWindow是不是存在，存在就把它dismiss掉  
	private void dismissPopupWindow() {

		if(popupwindow != null){
			popupwindow.dismiss();
			popupwindow = null;
		}
	}
}