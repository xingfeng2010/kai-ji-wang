package com.wenyu.kjw.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Myhome;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kaijiw.EquipsNearby;
import com.wenyu.kaijiw.FindTwoFouractivity;
import com.wenyu.kaijiw.LightNearby;
import com.wenyu.kaijiw.Nearby;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home_List_Adapter  extends BaseAdapter{
	private  List<Myhome> strs;
	private Context context;
	private ViewHolder vh;
	private float dX;
	private float uX;
	private int customer_id;
	private Map<String,String> paramsValue,cancelValue;
	private String footscanCancel=ConstantClassField.SERVICE_URL+"service/removeFootmark";
	private String test,category;
	public Home_List_Adapter(List<Myhome> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;
		customer_id = BaoyzApplication.getInstance().customer_id;
	}
	public void setData(List<Myhome> strs){
		this.strs = strs;
	}
	@Override
	public int getCount() {
		if(strs.size()==0){
			return 0;
		}
		return strs.size();
	}
	public void setStrs(List<Myhome> strs){
		this.strs = strs;
	}

	@Override
	public Object getItem(int arg0) {
		if(strs.size()==0){
			return null;
		}
		return strs.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/*
	 * (non-Javadoc)
	 *getView������������Ҫ���ġ�
	 * */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		//		
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item
					, null);
			vh.text = 	(TextView) convertView.findViewById(R.id.ps);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView);
			vh.introduction = 	(TextView) convertView.findViewById(R.id.type);
			vh.cate = (TextView) convertView.findViewById(R.id.cate);
			vh.btn = (Button)convertView.findViewById(R.id.btn);
			vh.minerelative03 = (RelativeLayout)convertView.findViewById(R.id.minerelative03);
			convertView.setTag(vh);
		}else{
			vh= (ViewHolder) convertView.getTag();
		}
		Myhome str =strs.get(position);
		vh.text.setText(str.getName());
		vh.introduction.setText(str.getIntroduction());
		vh.cate.setText(str.getType());

		final String imageUrl = str.getImag();
		//��ImageView���ñ�ǣ�ͼƬ��url�����Է�ֹͼƬ���ػ���
		vh.image.setTag(imageUrl);
		ImageLoader imageloader  =ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		//���ؿ�ʼĬ�ϵ�ͼƬ
		.showStubImage(R.drawable.z1)
		//urlΪ��ʱ��ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.z2)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		//		.cacheOnDisc(false)
		//����ͼƬ����������ʾͼƬ
		.showImageOnFail(R.drawable.z3)
		.build();
		imageloader.displayImage(imageUrl, vh.image,defaultOptions);


		final Button deleteBu = vh.btn;
		final RelativeLayout showLay = vh.minerelative03;
		deleteBu.setVisibility(View.GONE);
		showLay.setVisibility(View.VISIBLE);
		convertView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(MotionEvent.ACTION_DOWN == event.getAction()){
					dX=event.getX();
				}
				if(MotionEvent.ACTION_UP == event.getAction()){
					uX=event.getX();
					if(Math.abs(uX-dX)<=20.f){

						if(deleteBu.getVisibility() == View.VISIBLE){
							deleteBu.setVisibility(View.GONE);
							showLay.setVisibility(View.VISIBLE);
						} else if (deleteBu.getVisibility() == View.GONE) {
							if(position<strs.size()){
								Myhome mh = strs.get(position);
//								if(mh.getType().contains("���㳡��")){
//									category="���㳡��";
//								}else if(mh.getType().contains("Ӱ���˲�")){
//								}else if(mh.getType().contains("Ӱ������")){
//									
//								}else if(mh.getType().contains("���㳡��")){
//									
//								}
								Intent it1 = targetActivity(mh);
								context.startActivity(it1);
							}

						}
					}
				}
				if(MotionEvent.ACTION_MOVE == event.getAction()){
					float mX = event.getX();
					if(Math.abs(uX-dX)>20.f && Math.abs(mX-dX)>20.f){
						if(deleteBu.getVisibility() == View.GONE){
							deleteBu.setVisibility(View.VISIBLE);
//							showLay.setVisibility(View.GONE);
							deleteBu.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									if(position<strs.size()){
										final String ids = strs.get(position).getRecord();
										strs.remove(position);
										new Thread(new Runnable(){
											@Override
											public void run() {
												try {
													cancelValue=new HashMap<String, String>(); 
													cancelValue.put("record_id",ids);
													cancelValue.put("customer_id",customer_id
															+"");
													if(NetWorkUtil.isNetAvailable(context

															)){				
														test = new HttpP
																().sendPOSTRequestHttpClient(footscanCancel,cancelValue);	

//														handler.sendEmptyMessage(1);		
													}else {
														//handler.sendEmptyMessage(0);
													}
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}).start();
										Home_List_Adapter.this.notifyDataSetChanged();
									}

								}
							});

						}else if(deleteBu.getVisibility() == View.VISIBLE){
							deleteBu.setVisibility(View.GONE);
							showLay.setVisibility(View.VISIBLE);
						}

						dX = 0.f;
						uX = 0.f;

					}

				}

				return true;

			}

		});


		return convertView;
	}


	private Intent targetActivity(Myhome mh) {
		Intent it1;
//		if("���".equals(mh.getType())){
//			it1 = new Intent(context,LightNearby.class); 
//			it1.putExtra("pic", mh.getImag());
//			it1.putExtra("id", mh.getId());//id��bug,
////			it1.putExtra("name", mh.getIntroduction().split(",")[0]);
////			it1.putExtra("category", "��������");
//			return it1;
//		}else	
			
			if("����".equals(mh.getType())){
			it1= new Intent(context,Nearby.class);
			it1.putExtra("picture",mh.getImag());
			it1.putExtra("id", mh.getId());
			return it1;
		}else if("����".equals(mh.getType())){
			it1= new Intent(context,EquipsNearby.class);
			it1.putExtra("pic",mh.getImag());
			it1.putExtra("id", mh.getId());
//			it1.putExtra("name", mh.getIntroduction().split(",")[0]);
//			it1.putExtra("category", category);
//			it1.putExtra("customer_id",customer_id+"");
			return it1;
		}else if("�˲�".equals(mh.getType())){
			it1= new Intent(context,FindTwoFouractivity.class);
			it1.putExtra("picture",mh.getImag());
			it1.putExtra("id", mh.getId());
//			it1.putExtra("name", mh.getIntroduction().split(",")[0]);
//			it1.putExtra("category", category);
//			it1.putExtra("customer_id",customer_id+"");
			return it1;
		}
		return null;
	}
	class ViewHolder{
		TextView text;
		ImageView image;
		TextView introduction,cate;
		Button btn;
		RelativeLayout minerelative03;
	}
}
