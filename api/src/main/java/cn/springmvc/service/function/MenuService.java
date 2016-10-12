package cn.springmvc.service.function;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johsnon
 *
 */
public interface MenuService {

	// 设置菜单
	public boolean setMenu(String jsonStr, BasicModel basicModel) throws Exception;
}
