package com.wenyu.kaijiw;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.renn.rennsdk.http.HttpRequest.Base64;
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.CropHandler;
import com.wenyu.Utils.CropHelper;
import com.wenyu.Utils.CropParams;
import com.wenyu.db.DBManager;

public class PersonCustomerActivity extends Activity implements CropHandler{
	private Button ensure;
	private ImageView back,upload,idcard;
	private Calendar calendar;
	private DatePickerDialog dialog;
	private Map<String,String> paramsValue;
	private TextView getTime,firstP,secondP,uploadtext,idcardtext;
	private Customer customer,query;
	private DBManager mgr;
	private CropParams mCropParams;
	private TextView gendar,word,award;
	private EditText mineTextView06,name;
	private String position,position2,phone,picPath,img64="",idcard64="",jsonData,url=Urls.Url_Certify;
	private int customer_id,relativeType;
//	private RelativeLayout pcbtn1,pcbtn2,uploadRelative,idcardRelative,gendarRelative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_three);
		initView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			//第一职业
			if(data!=null){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
					position = bundle.getString("production");	
					firstP.setText(position);
				}
			}
			break;
		case 2:
			//第二职业
			if(data!=null){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
					position2 = bundle.getString("production2");	
					secondP.setText(position2);
				}
			}
			break;
		default:
			if(data!=null){
				if(data.getData()!=null){
					System.out.println("结果"+data.getData().getPath());
					Toast.makeText(PersonCustomerActivity.this, "请上传jpg或png形式的图片 ", 1000).show();
					return;		    
				} 
			}

			this.onPhotoCropped(mCropParams.temUri);
			break;
		}


	}

	private void initView() {
		phone=getIntent().getStringExtra("phone");
		customer_id = BaoyzApplication.getInstance().customer_id;
		name =  (EditText) findViewById(R.id.mineTextView01);
		gendar = (TextView) findViewById(R.id.mineTextView02);
//		pcbtn2 =(RelativeLayout) findViewById(R.id.minerelative03);
		getTime = (TextView) findViewById(R.id.mineTextView03);
		firstP = (TextView) findViewById(R.id.mineTextView04);
		secondP = (TextView) findViewById(R.id.mineTextView05);
		word = (TextView) findViewById(R.id.mineTextView07);
//		award = (EditText) findViewById(R.id.mineTextView08);
		back =   (ImageView) findViewById(R.id.pcimageView1);
		uploadtext = (TextView) findViewById(R.id.mineTextView09);
		idcardtext = (TextView) findViewById(R.id.mineTextView10);
		mineTextView06= (EditText) findViewById(R.id.mineTextView06);
		ensure = (Button) findViewById(R.id.pcbuttonView);
//		pcbtn1.setOnClickListener(ol);	
//		pcbtn2.setOnClickListener(ol);
		getTime.setOnClickListener(ol);
		back.setOnClickListener(ol);
		uploadtext.setOnClickListener(ol);
		idcardtext.setOnClickListener(ol);
		gendar.setOnClickListener(ol);
		name.setOnClickListener(ol);
		firstP.setOnClickListener(ol);
		secondP.setOnClickListener(ol);
//		gendarRelative.setOnClickListener(ol);
		ensure.setOnClickListener(ol);
	}
	private boolean checkEdit(){
		if(name.getText().toString().trim().equals("")){
			Toast.makeText(PersonCustomerActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
		}else if(gendar.getText().toString().equals("")){
			Toast.makeText(PersonCustomerActivity.this, "性别必须为男或女", Toast.LENGTH_SHORT).show();
		}else if(getTime.getText().toString().equals("")){
			Toast.makeText(PersonCustomerActivity.this, "生日不能为空", Toast.LENGTH_SHORT).show();
		}else if(firstP.getText().toString().equals("")){
			Toast.makeText(PersonCustomerActivity.this, "第一职业不能为空", Toast.LENGTH_SHORT).show();
		}
		else{
			return true;
		}
		return false;
	}
	private boolean checkPic(){
		if(img64.isEmpty()){
			Toast.makeText(PersonCustomerActivity.this, "个人照片不能为空", Toast.LENGTH_SHORT).show();
		} if(idcard64.isEmpty()){
			Toast.makeText(PersonCustomerActivity.this, "身份证不能为空", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	private void httpCertify() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("customer_id",customer_id+"");
					paramsValue.put("type","个人");
					paramsValue.put("name",name.getText().toString());
					paramsValue.put("gender",gendar.getText().toString());
					paramsValue.put("birthday", getTime.getText().toString());
					paramsValue.put("foccupation", firstP.getText().toString());
					paramsValue.put("soccupation", secondP.getText().toString());
					paramsValue.put("image", img64);
					paramsValue.put("telephone",phone );
					paramsValue.put("idcard", idcard64);
					if(NetWorkUtil.isNetAvailable(PersonCustomerActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						String ss =getParse(jsonData);
						if(("").equals(jsonData)){
							Toast.makeText(PersonCustomerActivity.this, "网络连接异常 ", 1000).show();
						}else if(("fail").equals(jsonData)){
							Toast.makeText(PersonCustomerActivity.this, "验证信息失败，请检查 ", 1000).show();
							return;
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();
	}


	OnClickListener ol = new OnClickListener() {

		private Builder setMessage;

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.mineTextView02:
				showSexChooseDialog();
				break;
			case R.id.mineTextView04:
				Intent it = new Intent(PersonCustomerActivity.this,FirstPositionActivity.class);
				it.putExtra("position", "第一职业");
				PersonCustomerActivity.this.startActivityForResult(it, 1);
				break;
			case R.id.mineTextView05:
				Intent it1 = new Intent(PersonCustomerActivity.this,FirstPositionActivity.class);
				it1.putExtra("position", "第二职业");
				PersonCustomerActivity.this.startActivityForResult(it1, 2);	
				break;
			case R.id.mineTextView09:
				mCropParams = new CropParams(
						CacheUtils.getCacheDirectory(PersonCustomerActivity.this),
						"infoPhoto.jpg");
				relativeType = 0;
				startActivityForResult(
						CropHelper.buildCropFromGalleryIntent(mCropParams),
						CropHelper.REQUEST_CROP);			    
				break;
			case R.id.mineTextView10:
				mCropParams = new CropParams(
						CacheUtils.getCacheDirectory(PersonCustomerActivity.this),
						"idcardPhoto.jpg");
				relativeType = 1;
				startActivityForResult(
						CropHelper.buildCropFromGalleryIntent(mCropParams),
						CropHelper.REQUEST_CROP);		
				break;

			case R.id.pcimageView1:

				PersonCustomerActivity.this.finish();

				break;
			case R.id.mineTextView03:
				calendar = Calendar.getInstance();
				dialog = new DatePickerDialog(PersonCustomerActivity.this, new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int year, int month, int day) {
						//									System.out.println("年->"+year+"月->"+month+"日->"+day);
						getTime.setText(year+"-"+month+"-"+day);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				dialog.show();
				break;
				//------------------------------------------
				//			        	8888888
			case R.id.pcbuttonView:
				if(checkEdit()){
					if(checkPic()){	
						httpCertify();
						new AlertDialog.Builder(PersonCustomerActivity.this).setMessage("确定你填写的信息真实有效")
						.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {					
							@Override
							public void onClick(DialogInterface dialog, int which) {
								new AlertDialog.Builder(PersonCustomerActivity.this).setMessage("感谢您提供的信息,我们会在48小时内给您回复").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
									@Override
									public void onClick(DialogInterface dialog, int which) {
										PersonCustomerActivity.this.finish();
									}
								}).show();

							}
						}).show();
					}
				}
				break;
			default:
				break;

			}
		}


	};
	
	 //选择性别
	private void showSexChooseDialog() {
		AlertDialog.Builder builder = new Builder(PersonCustomerActivity.this);
		View view = LayoutInflater.from(PersonCustomerActivity.this).inflate(
				R.layout.dialog_gender, null);
		LinearLayout maleLayout = (LinearLayout) view.findViewById(R.id.layout_male);
		LinearLayout femaleLayout = (LinearLayout) view
				.findViewById(R.id.layout_female);
		ImageView maleImageView = (ImageView) view.findViewById(R.id.iv_male);
		ImageView femaleImageView = (ImageView) view.findViewById(R.id.iv_female);

	


			if (gendar.getText().toString().equals("女")) {
				maleImageView.setVisibility(View.GONE);
				femaleImageView.setVisibility(View.VISIBLE);
			} else {
				maleImageView.setVisibility(View.VISIBLE);
				femaleImageView.setVisibility(View.GONE);
			}	 

		builder.setView(view);

		final AlertDialog dialog = builder.create();

		dialog.show();
		maleLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				if (!gendar.getText().toString().equals("男")) {
					gendar.setText("男");
				}
			}
		});
		femaleLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				if (!gendar.getText().toString().equals("女")) {
					gendar.setText("女");
				}
			}
		});
		
	}

	private  String getParse(String st){
		String name="";
		try {
			JSONObject jo= new JSONObject(st);
			name = jo.optString("message");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
	@Override
	public void onPhotoCropped(Uri uri) {

		Bitmap bitmap = null;

		try {
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			if(relativeType==0){
				img64 += Base64.encodeBytes(ba);
				uploadtext.setText("图片已上传,点击更换图片");
//				upload.setAlpha(0);

			}else{
				idcard64 += Base64.encodeBytes(ba); 
				idcardtext.setText("图片已上传,点击更换图片");
//				idcard.setAlpha(0);
			}


		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void onCropCancel() {

	}

	@Override
	public void onCropFailed(String message) {

	}

	@Override
	public CropParams getCropParams() {
		return mCropParams;
	}

	@Override
	public Activity getContext() {
		return null;
	}

}
