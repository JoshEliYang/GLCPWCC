package cn.springmvc;

import java.util.ResourceBundle;

/**
 * 全局常量
 * 
 * @author johsnon
 *
 */
public final class Consts {

	// token
//	public static String TOKEN="hKKl48yK4Jolq4dbX5R9RYE8YzYkNndb";
//	
//	// 测试账号
//	public static String APP_ID = "wx6953682e7b6eecdf";
//	public static String APP_SERCRET = "d0c2bf8805a8bf8824f31830edde8750";
//
//	// 微信账号
//	public static String WECHART_ACCOUNT="gh_96c813516423";
	
	public static String TOKEN=null;
	// 测试账号
	public static String APP_ID = null;
	public static String APP_SERCRET =null;
	// 微信账号
	public static String WECHART_ACCOUNT=null;
	
	// johsnon账号
	// public static String APP_ID = "wx54ab9837e1967990";
	// public static String APP_SERCRET = "b83f38ad4f7401ca24a1f16fabb0dd98";
	
	static{
		// 获得资源包  
	    ResourceBundle rb = ResourceBundle.getBundle("conf/wechart");
	    TOKEN=rb.getString("TOKEN");
	    APP_ID=rb.getString("APP_ID");
	    APP_SERCRET=rb.getString("APP_SERCRET");
	    WECHART_ACCOUNT=rb.getString("WECHART_ACCOUNT");
	}
}
