package com.wenyu.kaijiw;

import android.app.Activity;
public class ShootProduct extends Activity{
//	private String url = ConstantClassField.SERVICE_URL + "service/studio";
//	List<Find_yingshi> list;
//	private String ss;
//	private Spinner sp1,sp2,sp3;
//	private TextView text;
//	private ListView gridview;
//	private ArrayAdapter<String> aa ;
//	private List<Myhome> arrlist,arrlist1;
//	private Home_List_Adapter hla;
//	private ImageView back;
//	ScreenManager sm;
//	private Map<String,String> paramsValue;
//	
//	Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			switch(msg.what){
//			case 0:
//				Toast.makeText(ShootProduct.this, "Õ¯¬Á¡¨Ω”“Ï≥£", 1000).show();
//				break;
//			case 1:
//				sddd d =new sddd(ShootProduct.this,list);
//				gridview.setAdapter(d);
//				break;
//
//			}
//		};
//	};
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.filmproduct);
//		initView();
//		movesPeople();
//		
//		
//	}
//	private void movesPeople() {
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//
//				try {
//					paramsValue=new HashMap<String, String>();  
//					paramsValue.put("id", "13");
//					if(NetWorkUtil.isNetAvailable(ShootProduct.this)){
//						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
//						list = getList(ss);
//						if(list!=null){
//							handler.sendEmptyMessage(1);
//						}else {
//							System.out.println(ss);
//						}
//						
//					}else {
//						handler.sendEmptyMessage(0);
//					}
//
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
//	public  List<Find_yingshi> getList(String jsonString) {
//		list = new ArrayList<Find_yingshi>();
//		try {
//			JSONArray arr = new JSONArray(jsonString);
//			for (int i = 0; i < arr.length(); i++) {
//				JSONObject jobj =arr.optJSONObject(i);
//			String  name =	jobj.optString("nameCn");
//			String image = 	jobj.optString("image");
//			Find_yingshi fy =	new Find_yingshi(image,name);
//			list.add(fy);
//			}
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	/**
//	 * 
//	 */
//	private void initView() {
//		sm =  ScreenManager.gerScreenManager();
//		sm.pushActivity(ShootProduct.this);
//		back= (ImageView) findViewById(R.id.imageView1);
//		back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				sm.popActivity(sm.currentActivity());
//			}
//		});
//		sp1 = (Spinner) findViewById(R.id.sp1);
//		sp2 = (Spinner) findViewById(R.id.sp2);
//		sp3 = (Spinner) findViewById(R.id.sp3);
//		gridview = (ListView) findViewById(R.id.gridview);
//		text = (TextView) findViewById(R.id.textView1);
//		aa =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner5));
//		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sp1.setAdapter(aa);
//		aa =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner4));
//		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sp2.setAdapter(aa);
//		aa =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner3));
//		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sp3.setAdapter(aa);
//		arrlist = new ArrayList<Myhome>();
//		arrlist1 = new ArrayList<Myhome>();
//		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				switch(arg2){
//				case 0:
//					hla =new Home_List_Adapter(arrlist, ShootProduct.this);
//					gridview.setAdapter(hla);
//					break;
//				case 1:
//					hla =new Home_List_Adapter(arrlist1, ShootProduct.this);
//					gridview.setAdapter(hla);
//					break;
//
//				}
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//		});
//		gridview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				Intent it = new Intent(ShootProduct.this,FindTwoFouractivity.class);
//				it.putExtra("key", list.get(arg2));
//				startActivity(it);
//			}
//		});
//
//	}
}
