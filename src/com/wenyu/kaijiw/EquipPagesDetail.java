package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.wenyu.Data.MyMessage;
import com.wenyu.kaijiw.fragment.Home_IntroFragment;
import com.wenyu.kaijiw.fragment.Home_MatingFragment;
import com.wenyu.kjw.adapter.Message_List_Adapter;
import com.wenyu.kjw.adapter.MySadapter;

public class EquipPagesDetail  extends FragmentActivity{
	private Button havelook;
	private List<Fragment> frags;
	private Home_IntroFragment hl;
	private Home_MatingFragment hmf;
	private MySadapter mzf ;
	private ImageView image;
	private List<MyMessage> lists;
	private Message_List_Adapter hla;
	private ListView listview;
	private String[] arr1={"��Ʒ����","�洢����","��ͷ","��Դ����"};
	private String[] arr2={"5.0kg������","MS�����","16MM,24MM,32MM","BAT�ӿ�"};
	private Context context;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equips_details);
		context =EquipPagesDetail.this;
		initView();
		shareview();
		hla =new Message_List_Adapter(lists, EquipPagesDetail.this);
		listview.setAdapter(hla);
	}

	private void shareview() {
		// ���÷�������
		mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
		//����������ʱ
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		image.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        // �Ƿ�ֻ���ѵ�¼�û����ܴ򿪷���ѡ��ҳ
		        mController.openShare(EquipPagesDetail.this, false);
		    }
		});
		
		//����΢��
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(EquipPagesDetail.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq�ռ�
		//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(EquipPagesDetail.this, "1104528162",
		                "S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//��������SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//������Ѷ΢��SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		
		
		
	}

	private void initView() {
		
//		simimar = (ImageView) findViewById(R.id.equipDetailImageView003);
		image = (ImageView) findViewById(R.id.equipDetailImageView2);
		listview = (ListView) findViewById(R.id.equipDetailListview);
		havelook =(Button) findViewById(R.id.equipDetailButton1);
		lists = new ArrayList<MyMessage>();
		for(int i=0;i<arr1.length;i++){
			MyMessage mm = new MyMessage(arr1[i], arr2[i]);
			lists.add(mm);			
		}
		frags = new ArrayList<Fragment>();
		hl = new Home_IntroFragment();
		hmf = new Home_MatingFragment();
		frags.add(hl);
		frags.add(hmf);
		mzf =  new MySadapter(getSupportFragmentManager(),frags);
//		simimar.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(EquipPagesDetail.this,SimilarActivity.class);
//				startActivity(intent);
//				
//			}
//		});
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(EquipPagesDetail.this ,HomeShareActivity.class);
				startActivity(it);
			}
		});
		havelook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(EquipPagesDetail.this ,ProductActivity.class);
				it.putExtra("store", "1");
				startActivity(it);
				
			}
		});
	}
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**ʹ��SSO��Ȩ����������´��� */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
}
