package com.wenyu.Utils;

/**
 * 项目常量配置
 * @author huhaoran
 *
 */
public class ConstantClassField {
	/**
	 * 新加坡服务器地址
	 */
	public static final String TEST_URL_SG ="http://128.199.237.152/"; 
	
	/**
	 * 公司内部测试服务器地址
	 */
	public static final String TEST_URL_OET = "http://192.168.1.148/";
	
	/**
	 * 阿里云服务器地址
	 */
	public static final String PRODUCT_URL_ALIYUN ="http://101.200.175.143/";
	/**
	 * 阿里云服务器地址2
	 */
	public static final String PRODUCT_URL_ALIYUN1 ="http://101.200.175.143:8000/";
	
	/**
	 * 实际用的服务器地址
	 */
	public static final String SERVICE_URL = PRODUCT_URL_ALIYUN1; 
	/**
	 * 验证码
	 */
	public static String CHECK_CODE = "";
}
