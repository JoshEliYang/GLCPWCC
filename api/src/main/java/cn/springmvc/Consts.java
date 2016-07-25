package cn.springmvc;

import cn.springmvc.model.BasicModel;

/**
 * 全局常量
 * 
 * @author johsnon
 *
 */
public final class Consts {
	static BasicModel BASIC_DATA;

	public static BasicModel getBASIC_DATA() {
		return BASIC_DATA;
	}

	public static void setBASIC_DATA(BasicModel bASIC_DATA) {
		BASIC_DATA = bASIC_DATA;
	}

}
