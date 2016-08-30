package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.TemplateMessageDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TemplateMessage;
import cn.springmvc.service.TemplateMessageService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {
	@Autowired
	private TemplateMessageDao templateDao;

	/**
	 * get template by basic id
	 * 
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public List<TemplateMessage> getTemplate(BasicModel basicModel) throws Exception {
		return templateDao.getTemplate(basicModel.getId());
	}
}
