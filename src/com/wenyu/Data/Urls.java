package com.wenyu.Data;

import com.wenyu.Utils.ConstantClassField;

public interface Urls {
public final static String Url_Search = ConstantClassField.SERVICE_URL+"service/globalSearch";
public final static String Url_Register = ConstantClassField.SERVICE_URL+"service/registers";
public final static String Url_Certify = ConstantClassField.SERVICE_URL+"service/addAuthentication";
public final static String Url_Checkcode = ConstantClassField.SERVICE_URL+"service/applyVerificationCode";
public final static String Url_Resetpwd = ConstantClassField.SERVICE_URL+"/service/resetPwd";
public final static String Url_Enshrine =ConstantClassField.SERVICE_URL+ "service/myEnshrine";
public final static String Url_addEnshrine =ConstantClassField.SERVICE_URL+ "service/addEnshrine";
public final static String Url_CancelEnshrine = ConstantClassField.SERVICE_URL+"service/removeEnshrine";
public final static String Url_Footmark = ConstantClassField.SERVICE_URL+"service/myFootmark";
public final static String Url_addFootmark = ConstantClassField.SERVICE_URL+"service/addFootmark";
public final static String Url_cancelFootmark = ConstantClassField.SERVICE_URL+"service/removeEnshrine";
public static final String ProductionActivityTitle = ConstantClassField.SERVICE_URL+"service/equipstop";
public static final String ProductionActivityContent = ConstantClassField.SERVICE_URL+"service/storelist";
public static final String ProductionActivityContentequip = ConstantClassField.SERVICE_URL+"service/equiplist";
public static final String LightNearbyActivity = ConstantClassField.SERVICE_URL+"service/store";
public static final String LightNearbyActivityEquip = ConstantClassField.SERVICE_URL+"service/equip";
public static final String Url_Logins = ConstantClassField.SERVICE_URL+"service/logins";
public static final String Url_Details = ConstantClassField.SERVICE_URL+"service/detail";
public static final String Url_PersonInfoUpdate = ConstantClassField.SERVICE_URL+"service/updateCustomerInfo";
public static final String Url_photos = ConstantClassField.SERVICE_URL+"service/photo";
public static final String Url_getIMID = ConstantClassField.SERVICE_URL+"service/getIMIDBySendToID";
public static final String Url_getIMCustomerInfo = ConstantClassField.SERVICE_URL+"service/getCustomerInfoByIMID";
public static final String SimilarAc = ConstantClassField.SERVICE_URL+"service/findsimilar";

}
