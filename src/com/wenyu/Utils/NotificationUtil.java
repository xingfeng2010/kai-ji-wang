package com.wenyu.Utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.wenyu.Data.Message;
import com.wenyu.kaijiw.IMChatActivity;
import com.wenyu.kaijiw.IMChatActivitys;
import com.wenyu.kaijiw.R;



/**
 * 应用状�?栏的消息提醒
 * 
 */
public class NotificationUtil {
	private static NotificationUtil instance = null;
	private static NotificationManager noManager = null;
	private static Context mContext;
	private Bitmap icon;
	private volatile int NOTIFICATION_ID = 0;

	/**
	 * 
	 * @param context
	 *            ��ҪgetApplicationContext()
	 * @return
	 */
	public static NotificationUtil getInstance(Context context) {
		if (instance == null || noManager == null) {
			instance = new NotificationUtil(context);
			mContext = context;
		}

		return instance;
	}

	private NotificationUtil(Context context) {
		noManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 *  ����״̬����Ϣ����
	 * @param message
	 */
	public void notificationMsg(Message message) {
//		if (checkIsRunningTopApp(mContext)) {
//
//			return;
//		}

		if (icon == null) {
			icon = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.cover);
		}

		Intent intent = new Intent(mContext, IMChatActivitys.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("CustomUserID", message.getUserID());

		PendingIntent pendingIntent = PendingIntent.getActivity(mContext,
				NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);

		Notification notification = new NotificationCompat.Builder(mContext)
				.setLargeIcon(icon)
				.setSmallIcon(R.drawable.sendmessage)
				.setTicker(message.getUserName() + "��" + message.getMessageContent())
				// 显示于屏幕顶端状态栏的文�?				// .setContentInfo("contentInfo")
				.setContentTitle(message.getUserName())
				.setContentText(message.getMessageContent())
				// .setNumber(++messageNum)
				.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(pendingIntent).build();

		noManager.notify(NOTIFICATION_ID, notification);
		NOTIFICATION_ID++;
	}

	private boolean checkIsRunningTopApp(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningTaskInfo> tasksInfo = am.getRunningTasks(1);
		String MY_PKG_NAME = context.getPackageName();

		if (tasksInfo.size() > 0) {
			// Ӧ�ó���λ�ڶ�ջ�Ķ���
			if (MY_PKG_NAME.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}

		return false;
	}
}
