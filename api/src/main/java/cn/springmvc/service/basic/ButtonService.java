package cn.springmvc.service.basic;

import java.util.List;

import cn.springmvc.model.Button;
import cn.springmvc.model.ButtonGroup;

/**
 * 
 * @author johsnon
 *
 */
public interface ButtonService {
	/**
	 * get all button groups
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ButtonGroup> getButtonGroup(int levelId) throws Exception;

	/**
	 * get all buttons by button group id and user level id
	 * 
	 * @param groupId
	 * @param levelId
	 * @return
	 * @throws Exception
	 */
	public List<Button> getButtons(int groupId) throws Exception;
}
