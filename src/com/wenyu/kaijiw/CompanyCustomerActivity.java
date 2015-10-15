package com.wenyu.kaijiw;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyCustomerActivity extends Activity implements CropHandler{
	private Button ensure;
	private ImageView back,uploadPic;
	private RelativeLayout comType,uploadRelative;
	private String phone,type,jsonData,license64,picPath,url=Urls.Url_Certify;
	private int customer_id;
	private Map<String,String> paramsValue;
	private DBManager mgr;
	private Customer query;
	private EditText name,companyname,companynumber,letter;
	private TextView category,license;
	private CropParams mCropParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_four);
		initView();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			//第一职业
			if(data!=null){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
				 type = bundle.getString("category");	
				 category.setText(type);
				}
			}
			break;
		 default :
	       	 if(data.getData()!=null){
	 			   System.out.println("结果"+data.getData().getPath());
	 				Toast.makeText(CompanyCustomerActivity.this, "请上传jpg或png形式的图片 ", 1000).show();
	 			    return;		    
	        	 }
	             this.onPhotoCropped(mCropParams.temUri);
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	private boolean checkEdit(){
	        if(name.getText().toString().trim().equals("")){
	            Toast.makeText(CompanyCustomerActivity.this, "公司名不能为空", Toast.LENGTH_SHORT).show();
	        }else if(category.getText().toString().equals("")){
	            Toast.makeText(CompanyCustomerActivity.this, "公司类别不能为空", Toast.LENGTH_SHORT).show();
	        }else if(license.getText().toString().equals("")){
	            Toast.makeText(CompanyCustomerActivity.this, "营业执照不能为空", Toast.LENGTH_SHORT).show();
	        }else if(companynumber.getText().toString().equals("")){
	            Toast.makeText(CompanyCustomerActivity.this, "营业执照注册号不能为空", Toast.LENGTH_SHORT).show();
	        }else{
	            return true;
	        }
	        return false;
	    }
	private void initView() {
		  phone=getIntent().getStringExtra("phone");
		  customer_id = getIntent().getIntExtra("customer_id", 0);
		   back =   (ImageView) findViewById(R.id.ccimageView1);
		   uploadRelative = (RelativeLayout) findViewById(R.id.mineCrelative02);
		   uploadPic = (ImageView) findViewById(R.id.mineCImageView05);
		   ensure = (Button) findViewById(R.id.ccbuttonView);
		   name =  (EditText) findViewById(R.id.mineCTextView01);
		   category =(TextView) findViewById(R.id.mineCTextView02);
			comType = (RelativeLayout) findViewById(R.id.mineCrelative01);
			companyname =  (EditText) findViewById(R.id.mineCTextView03);
			companynumber= (EditText) findViewById(R.id.mineCTextView06);
			letter = (EditText) findViewById(R.id.mineCTextView07);
		   license = (TextView) findViewById(R.id.mineCTextView05);
		  back.setOnClickListener(ol);
		  ensure.setOnClickListener(ol);
		  comType.setOnClickListener(ol);
		  uploadRelative.setOnClickListener(ol);
		}
	  private void httpCertify() {
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						paramsValue=new HashMap<String, String>(); 
						paramsValue.put("customer_id",customer_id+"");
						paramsValue.put("type","企业用户");
						paramsValue.put("name", name.getText().toString());
                        paramsValue.put("category", category.getText().toString());
						paramsValue.put("companyname", companyname.getText().toString());
                        paramsValue.put("license", license64);
						paramsValue.put("number", companynumber.getText().toString()); 
						paramsValue.put("telephone", phone);
						paramsValue.put("letter", letter.getText().toString());
						if(NetWorkUtil.isNetAvailable(CompanyCustomerActivity.this)){
							jsonData = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
							System.out.println("我应答的数据"+jsonData);
							if(("").equals(jsonData)){
								Toast.makeText(CompanyCustomerActivity.this, "网络连接异常 ", 1000).show();
							}else if(("fail").equals(jsonData)){
								Toast.makeText(CompanyCustomerActivity.this, "验证信息失败，请检查 ", 1000).show();
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
			
			@Override
			public void onClick(View arg0) {
				   switch (arg0.getId()) {
			        case R.id.ccimageView1:
			        	CompanyCustomerActivity.this.finish();
			    			break;
			        case R.id.mineCImageView02:
			        	Intent it = new Intent(CompanyCustomerActivity.this,CompanyTypeActivity.class);
						startActivityForResult(it, 1);
						break;
			        case R.id.mineCrelative02:
			        	mCropParams = new CropParams(
								CacheUtils.getCacheDirectory(CompanyCustomerActivity.this),
								"comPhoto.jpg");			
						startActivityForResult(
								CropHelper.buildCropFromGalleryIntent(mCropParams),
								CropHelper.REQUEST_CROP);	
			        	break;
			        case R.id.ccbuttonView:
			        	if(checkEdit()){
			        		httpCertify();
			        	 new AlertDialog.Builder(CompanyCustomerActivity.this).setMessage("确定你填写的信息真实有效")
					        .setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {					
								@Override
								public void onClick(DialogInterface dialog, int which) {
									new AlertDialog.Builder(CompanyCustomerActivity.this).setMessage("感谢您提供的信息,我们会在48小时内给您回复").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
										@Override
										public void onClick(DialogInterface dialog, int which) {
											CompanyCustomerActivity.this.finish();
										}
									}).show();

								}
							}).show();
			        	}
					        	    break;
					default:
						break;
				
			}
		}
	
   };
		@Override
		public void onPhotoCropped(Uri uri) {

			Bitmap bitmap = null;

			try {
				bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
				  ByteArrayOutputStream bao = new ByteArrayOutputStream();
		          bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		          byte[] ba = bao.toByteArray();

		          license64 += Base64.encodeBytes(ba);
		          license.setText("图片已上传,点击更换图片");
		          uploadPic.setAlpha(0);
		         
				
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		
			
		}
		@Override
		public void onCropCancel() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onCropFailed(String message) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public CropParams getCropParams() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Activity getContext() {
			// TODO Auto-generated method stub
			return null;
		}
}
