package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.ButtonDao;
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
	@Autowired
	private ButtonDao buttonDao;

	/**
	 * get all button groups
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ButtonGroup> getButtonGroup() throws Exception {
		return buttonDao.getButtonGroup();
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
		return buttonDao.getButtons(groupId, levelId);
	}
}
