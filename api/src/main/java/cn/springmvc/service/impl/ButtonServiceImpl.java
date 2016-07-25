package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.springmvc.model.Button;
import cn.springmvc.model.ButtonGroup;
import cn.springmvc.service.ButtonService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class ButtonServiceImpl implements ButtonService {

	/**
	 * get all button groups
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ButtonGroup> getButtonGroup() throws Exception {
		return null;
	}

	/**
	 * get all buttons by button group id and user level id
	 * 
	 * @param groupId
	 * @param levelId
	 * @return
	 * @throws Exception
	 */
	public List<Button> getButtons(int groupId, int levelId) throws Exception {
		return null;
	}
}
