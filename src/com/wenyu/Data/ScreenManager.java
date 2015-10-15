package com.wenyu.Data;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class ScreenManager {
	private static Stack<Activity> activityStack;
	private static  ScreenManager instance;
	public ScreenManager(){

	}
	public  static ScreenManager gerScreenManager(){
		if(instance==null){
			instance = new ScreenManager();
		}
		return  instance;
	}

	//�˳�ջ��Activity

	public void popActivity(Activity activity){

		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}

	//��õ�ǰջ��Activity

	public Activity currentActivity(){


		Activity activity= activityStack.lastElement();

		return activity;

	}
	//����ǰActivity����ջ��

	public void pushActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * ������ǰActivity����ջ�����һ��ѹ��ģ�
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		popActivity(activity);
	}

	/**
	 * ����ָ��������Activity
	 */
	public void popAllActivityExceptOne(Class cls){

		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				popActivity(activity);
			}
		}
	}
	/**
	 * ��������Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
			if (null != activityStack.get(i)){
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	/**
	 * �˳�Ӧ�ó���
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {        }
	}
	public static Stack<Activity> getActivityStack() {
		return activityStack;
	}
	public static void setActivityStack(Stack<Activity> activityStack) {
		ScreenManager.activityStack = activityStack;
	}
	
}







