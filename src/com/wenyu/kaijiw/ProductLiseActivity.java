package com.wenyu.kaijiw;

import android.app.Activity;

public class ProductLiseActivity  extends Activity{
//	private Spinner sp1,sp2,sp3;
//	private ArrayAdapter<String> bb,cc,dd;
//	private ListView listview;
//	private ScreenManager sm;
//	ImageView back;
//	private Map<String,String> paramsValue;
//	private List<String>str,str1,str2;
//	private String s,s1,s2,ss ;
//	private  String url ="http://192.168.1.136/film-on-web/public/service/equiplist";
//	Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			switch(msg.what){
//			case 0:
//				Toast.makeText(ProductLiseActivity.this, "网络连接异常 ", 1000).show();
//				break;
//			case 1:
//				bb = new ArrayAdapter<String>(ProductLiseActivity.this,android.R.layout.simple_spinner_item,str);
//				sp1.setAdapter(bb);
//				cc = new ArrayAdapter<String>(ProductLiseActivity.this,android.R.layout.simple_spinner_item,str1);
//				sp2.setAdapter(cc);
//				dd = new ArrayAdapter<String>(ProductLiseActivity.this,android.R.layout.simple_spinner_item, str2);
//				sp3.setAdapter(dd);
//				break;
//			case 3:
//				hla =new HomeYing(fyslist, ProductLiseActivity.this);
//				listview.setAdapter(hla);
//				break;
//			}
//		};
//	};
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.productlist);
//		initView();
//		movesPeople();
//		initSp();
//		movesShaixuan();
//	}
//
//	
//	private void movesShaixuan() {
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				if(("").equals(s)&&("").equals(s1)&& ("").equals(s2)){
//					handler.sendEmptyMessage(5);
//				}else {
//					if(("").equals(s)){
//						Toast.makeText(ProductLiseActivity.this, "第一个列表重新选择", 1000).show();
//					}else {
//						
//					}if(("").equals(s1)){
//						
//						Toast.makeText(ProductLiseActivity.this, "第二个列表重新选择", 1000).show();
//					}else{
//					}if(("").equals(s2)){
//						Toast.makeText(ProductLiseActivity.this, "第三个列表重新选择", 1000).show();
//					}else {
//					}
//					initThread();
//					
//					
//				}
//
//			}
//
//		}).start();
//
//	}
//	private void initThread() {
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//
//				try {
//					paramsValue=new HashMap<String, String>();  
//					paramsValue.put("city","北京市");
//					paramsValue.put("type", s);
//					paramsValue.put("regional", s1);
//					paramsValue.put("orderby", s2);
//					if(NetWorkUtil.isNetAvailable(ProductLiseActivity.this)){
//						ss = new HttpP().sendPOSTRequestHttpClient(url1,paramsValue);
//						initying(ss);
//						handler.sendEmptyMessage(3);
//					}else {
//						handler.sendEmptyMessage(0);
//					}
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//
//			}
//
//		}).start();
//
//		
//	}
//	
//	private void initView() {
//		sm =  ScreenManager.gerScreenManager();
//		sm.pushActivity(ProductLiseActivity.this);
//		sp1 = (Spinner) findViewById(R.id.city);
//		sp2 = (Spinner)findViewById(R.id.ci);
//		sp3 = (Spinner)findViewById(R.id.ty);
//		listview = (ListView) findViewById(R.id.listview);
//		back = (ImageView) findViewById(R.id.imageView1);
//		//	bb = new ArrayAdapter<String>(ProductLise.this,android.R.layout.simple_spinner_item,str);
//		sp1.setAdapter(bb);
//
//	}
//
//	OnClickListener ocl = new OnClickListener(){
//
//		@Override
//		public void onClick(View arg0) {
//			switch(arg0.getId()){
//			case R.id.equipImageView1:
//				sm.popActivity(sm.currentActivity());
//				break;
//			}
//
//		}
//
//	};
//	private void movesPeople() {
//
//
//
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//
//				try {
//					paramsValue=new HashMap<String, String>();  
//					paramsValue.put("city","北京市");
//					if(NetWorkUtil.isNetAvailable(ProductLiseActivity.this)){
//						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
////						fa(ss);
//						handler.sendEmptyMessage(1);
//					}else {
//						handler.sendEmptyMessage(0);
//					}
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//
//			}
//		}).start();
//
//	}
////	public 
//	public void initSp() {
//		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				s= sp1.getSelectedItem().toString();
//			}
//			
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				
//			}
//		});
//		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				s1= sp2.getSelectedItem().toString();
//			}
//			
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				
//			}
//		});
//		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				s2= sp3.getSelectedItem().toString();
//			}
//			
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				
//			}
//		});
//	}
}
