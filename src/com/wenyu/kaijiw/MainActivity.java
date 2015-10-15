package com.wenyu.kaijiw;

import imsdk.data.IMMyself;
import imsdk.data.IMMyself.OnActionListener;
import imsdk.data.IMMyself.OnConnectionChangedListener;
import imsdk.data.IMMyself.OnReceiveTextListener;
import imsdk.data.IMSDK.OnDataChangedListener;
import imsdk.data.group.IMGroupInfo;
import imsdk.data.group.IMMyselfGroup;
import imsdk.data.group.IMMyselfGroup.OnGroupMessageListener;
import imsdk.data.group.IMSDKGroup;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;
import imsdk.data.relations.IMMyselfRelations;
import imsdk.data.relations.IMMyselfRelations.OnRelationsEventListener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.slidingmenu.lib.SlidingMenu;
import com.wenyu.Data.Customer;
import com.wenyu.Data.IMConfiguration;
import com.wenyu.Data.Message;
import com.wenyu.Utils.NotificationUtil;
import com.wenyu.Utils.UICommon;
import com.wenyu.application.MessagePushCenter;
import com.wenyu.application.MessagePushCenter.FriendRequstObserve;
import com.wenyu.db.DBManager;
import com.wenyu.kaijiw.fragment.FootFragment;
import com.wenyu.kaijiw.fragment.HomeFragment;
import com.wenyu.kaijiw.fragment.InfoFragment;
import com.wenyu.kaijiw.fragment.MineComFragment;
import com.wenyu.kaijiw.fragment.MineFragment;
import com.wenyu.kaijiw.fragment.MyFragment;
import com.wenyu.kaijiw.fragment.ReFindFragment;
import com.wenyu.kjw.adapter.HomeWatcher;
import com.wenyu.kjw.adapter.HomeWatcher.OnHomePressedListener;

public class MainActivity extends FragmentActivity {
	private SlidingMenu menu;
	private ListView listview;
	private RadioButton btn1,btn2,btn3,btn4,btn5;
	private ReFindFragment hf ;
	private FootFragment ff;
	private InfoFragment nf;
	private HomeFragment homef;
	private MyFragment mf;
	private MineFragment mine;
	private MineComFragment mineCom;
	private List<Fragment> li;
	private List<Customer> cus;
	private DBManager mgr;
	private Customer cu;
	private HomeWatcher mHomeWatcher;
	private String phone,password,infocustomer,message,imID ="";
	public static MainActivity sSingleton;
	private static int mNotificationID;
	private static Vibrator mNotificationVibrator;
	private static int mMessageID;
	private int notifyId;
	public LayoutInflater mInflater;
	private static SoundPool mSoundPool;
	private int height,flag,cflag,customer_id;
	private static final int NOTICE_ID = 1222;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mgr = new DBManager(this);
		sSingleton = this;
		mInflater = LayoutInflater.from(this);
		intData();
		initView();  
		initListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHomeWatcher.endWatch();
		mgr.closeDB();
	}
	/**
	 * ���õ�½����֤״̬
	 */
	private void intData() {
		//��ʼ��IM
		IMMyself.init(getApplicationContext(), IMConfiguration.sAppKey, null);
		imID = getIntent().getStringExtra("imID");
		String imPwd = getIntent().getStringExtra("imPwd");
		cus = mgr.query();
		for(int i=0;i<cus.size();i++){
			cu = cus.get(i);
			//	cflag = Certify 
			//	flag = Active
			if( cu.getActive()==1&&cu.getCertify()==0)
			{
				flag = 1;
				BaoyzApplication.getInstance().isLogined = true;
				customer_id = cu.getId();
				phone = cu.getPhonenumber();
				password = cu.getPassword();
			}else if(cu.getActive()==1&&cu.getCertify()==1){
				flag = 1;
				cflag =1;
				BaoyzApplication.getInstance().isLogined = true;
				customer_id = cu.getId();
				phone = cu.getPhonenumber();
				password = cu.getPassword();
				IMLogin(imID,imPwd);
				BaoyzApplication.getInstance().isApprove = true;

			}
			else if(cu.getActive()==1&&cu.getCertify()==2){
				flag = 1;
				cflag =2;
				customer_id = cu.getId();
				phone = cu.getPhonenumber();
				password = cu.getPassword();
				IMLogin(imID,imPwd);
				BaoyzApplication.getInstance().isLogined = true;
				BaoyzApplication.getInstance().isApprove = true;
			}else if(cu.getActive()==1&&cu.getCertify()==3){
				flag = 1;
				cflag =3;
				customer_id = cu.getId();
				phone = cu.getPhonenumber();
				password = cu.getPassword();
				IMLogin(imID,imPwd);
				BaoyzApplication.getInstance().isLogined = true;
				BaoyzApplication.getInstance().isApprove = false;
			}else if(cu.getActive()==1&&cu.getCertify()==4){
				flag = 1;
				cflag =4;
				customer_id = cu.getId();
				phone = cu.getPhonenumber();
				password = cu.getPassword();
				IMLogin(imID,imPwd);
				BaoyzApplication.getInstance().isLogined = true;
				BaoyzApplication.getInstance().isApprove = false;

			}		
		}	
		BaoyzApplication.getInstance().customer_id = customer_id;
		li = new ArrayList<Fragment>();
		hf = new ReFindFragment();
		//		if(flag==1){
		//			ff = new FootFragment().newInstance(customer_id, phone, password,cflag);
		//			homef = HomeFragment.newInstance(customer_id,phone,password,cflag);
		//		}else{
		ff = new FootFragment();
		homef =	new HomeFragment();
		//		}				
		nf = new InfoFragment();
		mf = new MyFragment();
		li.add(hf);
		li.add(ff);
		li.add(homef);
		li.add(nf);
		li.add(mf);
	}
	//��½IM �˺�
	private void IMLogin(String imID,String imPwd) {
		if(imID!=null&&imPwd!=null){
			IMMyself.setCustomUserID(imID);
			IMMyself.setPassword(imPwd);	  
		}else{
			IMMyself.setCustomUserID(phone+"a");
			IMMyself.setPassword("123456");
		}
		// ���ó�ʱʱ��Ϊ5��
		IMMyself.login(true, 10, new OnActionListener() {
			@Override
			public void onSuccess() {
				BaoyzApplication.getInstance().isLogined = true;
			}

			@Override
			public void onFailure(String error) {
				if (error.equals("Timeout")) {
					error = "һ����¼��ʱ";
				} else if (error.equals("Wrong Password")) {
					error = "�������";
				}

				Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
			}
		});
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		btn1 = (RadioButton)findViewById(R.id.radio0);
		btn2 = 	(RadioButton) findViewById(R.id.radio1);
		btn3 = 	(RadioButton) findViewById(R.id.radio2);
		btn4 =	 (RadioButton) findViewById(R.id.radio3);
		btn5=(RadioButton)findViewById(R.id.openimage);
		btn1.setOnClickListener(cl);
		btn1.setHeight(30);
		btn1.setPivotY(height-30);
		btn2.setOnClickListener(cl);
		btn3.setOnClickListener(cl);
		btn4.setOnClickListener(cl);
		btn5.setOnClickListener(cl);
		getSupportFragmentManager().beginTransaction().add(R.id.fragment, hf).hide(hf).add(R.id.fragment, ff).hide(ff).add(R.id.fragment, homef).show(homef).add(R.id.fragment, nf).hide(nf).commit();
	}

	OnClickListener cl=  new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){

			case R.id.radio0:
				replace(0);
				break;
			case R.id.radio1:
				replace(1);
				break;	
			case R.id.openimage:
				replace(2);
				break;
			case R.id.radio2:
				replace(3);
				break;

			case R.id.radio3:
				/**
				 * flag = 1; ��½�ɹ�
				 * flag = 0�� δ��¼
				 * cflag = 1 �����û�
				 * cflag = 2 ��ҵ�û�
				 * cflag = 3 ����δ��֤
				 * cflag = 4 ��ҵδ��֤
				 */
				if(flag==0){
					Intent it = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(it);
				}else if(flag==1&&cflag==0){

					if(mf == null || !mf.isVisible()){
						li.remove(4);
						mf = MyFragment.newInstance(customer_id,phone, password);
						li.add(mf);
						replace(4);
						getSupportFragmentManager().beginTransaction().add(R.id.fragment, mf).show(mf).commit();
					}

				}else if(flag==1&&(cflag==1||cflag==3)){
					//����
					if(mine == null || !mine.isVisible()){
						li.remove(4);
						if("".equals(imID)||imID ==null){
							imID = System.currentTimeMillis()+"";
						}
						mine =   MineFragment.newInstance(customer_id,phone, password,imID,cflag);
						mine = new MineFragment();
						li.add(mine);
						replace(4);
						getSupportFragmentManager().beginTransaction().add(R.id.fragment, mine).show(mine).commit();
					}

				}else if(flag==1&&(cflag==2||cflag==4)){
					//��˾
					if(mineCom == null || !mineCom.isVisible()){
						li.remove(4);
						if("".equals(imID)||imID ==null){
							imID = System.currentTimeMillis()+"";
						}
						mineCom = MineComFragment.newInstance(customer_id,phone, password,imID,cflag);
						li.add(mineCom);
						replace(4);
						getSupportFragmentManager().beginTransaction().add(R.id.fragment, mineCom).show(mineCom).commit();
					}

				}
				break;

			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void replace(int i) {
		for (int j = 0; j < li.size(); j++) {
			if(j==i){
				getSupportFragmentManager().beginTransaction().show(li.get(i)).commit();
			}else{
				getSupportFragmentManager().beginTransaction().hide(li.get(j)).commit();
			}
		}

	}
	//-------------------------------������Ϣ����--------------------------------
	/**
	 * ������Ϣ�ļ���
	 */

	private void initListener() {
		// ������Ϣ����
		IMMyself.setOnReceiveTextListener(new OnReceiveTextListener() {
			@Override
			public void onReceiveText(String text, String fromCustomUserID,
					long serverActionTime) {
				int unreadMessageCount = (int) IMMyselfRecentContacts
						.getUnreadChatMessageCount();

				// ����δ����Ϣ
				message = text;
				infocustomer = fromCustomUserID; 
				Message message = new Message();
				message.setUserID(fromCustomUserID);
				message.setUserName(fromCustomUserID);
				message.setMessageContent(text);
				NotificationUtil.getInstance(getApplicationContext()).notificationMsg(
						message);
			}

			@Override
			public void onReceiveSystemText(String text, long serverActionTime) {
			}
		});
		//��½�ɹ� ״̬����
		IMMyself.setOnConnectionChangedListener(new OnConnectionChangedListener() {
			@Override
			public void onDisconnected(boolean loginConflict) {
				if (loginConflict) {
					BaoyzApplication.getInstance().isLogined = false;
					// ��¼��ͻ
					Toast.makeText(MainActivity.this, "��¼��ͻ", Toast.LENGTH_SHORT)
					.show();
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					intent.putExtra("userName", IMMyself.getCustomUserID());
					intent.putExtra("password", IMMyself.getPassword());
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					MainActivity.this.startActivity(intent);
				} else {
					BaoyzApplication.getInstance().isLogined = false;
					// �������
					Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT)
					.show();
				}
			}

			@Override
			public void onReconnected() {
				// ���ߺ��Զ�����
				Toast.makeText(MainActivity.this, "�����ɹ�", Toast.LENGTH_SHORT).show();
			}
		});



		IMMyselfGroup.setOnGroupMessageListener(new OnGroupMessageListener() {
			@Override
			public void onReceiveText(String text, String groupID,
					String fromCustomUserID, long actionServerTime) {
				Message message = new Message();

				message.setMessageContent(text);
				message.setGroupID(groupID);

				IMGroupInfo group = IMSDKGroup.getGroupInfo(groupID);

				message.setGroupName(group.getGroupName());
				message.setMutiChat(true);
				message.setTimeSamp(actionServerTime);
				MessagePushCenter.notifyAllObservesMessageComing(message);
				playNotification(true);
			}

			@Override
			public void onReceiveCustomMessage(String customMessage, String groupID,
					String fromCustomUserID, long actionServerTime) {
				Message message = new Message();

				message.setMessageContent(customMessage);
				message.setGroupID(groupID);

				IMGroupInfo group = IMSDKGroup.getGroupInfo(groupID);

				message.setGroupName(group.getGroupName());
				message.setMutiChat(true);
				message.setTimeSamp(actionServerTime);
				MessagePushCenter.notifyAllObservesMessageComing(message);
				playNotification(true);
			}

			@Override
			public void onReceiveBitmapMessage(String messageID, String groupID,
					String fromCustomUserID, long serverActionTime) {
			}

			@Override
			public void onReceiveBitmap(Bitmap bitmap, String groupID,
					String fromCustomUserID, long serverActionTime) {
			}

			@Override
			public void onReceiveBitmapProgress(double progress, String groupID,
					String fromCustomUserID, long serverActionTime) {
			}
		});


		mHomeWatcher = new HomeWatcher(MainActivity.this);
		mHomeWatcher.startWatch();
		mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
			@Override
			public void onHomePressed() {
			}

			@Override
			public void onHomeLongPressed() {
			}
		});
	}

	public boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();

		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					return true; // "��̨"
				} else {
					return false; // "ǰ̨"
				}
			}
		}

		return false;
	}
	/** ��ʾ֪ͨ�������ת��ָ��Activity */
	public void showIntentActivityNotify(int notifyId, String content, String title,
			String user, Class<? extends Activity> clazz, Context context) {
		// Notification.FLAG_ONGOING_EVENT --���ó�פ
		// Flag;Notification.FLAG_AUTO_CANCEL ֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
		// notification.flags = Notification.FLAG_AUTO_CANCEL;
		// //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
		NotificationManager notificationManager = (NotificationManager) this
				.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		builder.setContentTitle(title).setContentText(content)
		// .setNumber(number)//��ʾ����
		.setTicker("����һ������Ϣ��")// ֪ͨ�״γ�����֪ͨ��������������Ч����
		.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
		.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
		.setAutoCancel(false)// ���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��
		// .setOngoing(false)//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
		.setDefaults(Notification.DEFAULT_ALL)// ��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
		// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND ������� //
		// requires VIBRATE permission
		.setAutoCancel(true)// �������֪ͨ����ʧ
		.setSmallIcon(R.drawable.ic_launcher);

		// �������ͼACTION����ת��Intent
		Intent resultIntent = new Intent(this, clazz);

		resultIntent.putExtra("CustomUserID", user);
		resultIntent.putExtra("notify", true);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentIntent(pendingIntent);
		notificationManager.notify(notifyId, builder.build());
	}

	// ����Ϣ���� - �����������ѡ�������
	public static void playNotification(boolean isMessage) {
		if (IMConfiguration.sSoundNotice) {
			if (isMessage) {
				mSoundPool.play(mMessageID, 1, 1, 0, 0, 1);
			} else {
				mSoundPool.play(mNotificationID, 1, 1, 0, 0, 1);
			}
		}

		if (IMConfiguration.sVibrateNotice) {
			mNotificationVibrator.vibrate(200);
		}
	}

	@Override
	public void onBackPressed() {
		//do something
		super.onBackPressed();
	}
}
