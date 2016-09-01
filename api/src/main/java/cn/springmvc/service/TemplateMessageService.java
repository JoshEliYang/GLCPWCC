package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TemplateMessage;

/**
 * 
 * @author johnson
 *
 */
public interface TemplateMessageService {

	/**
	 * get template by basic id
	 * 
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public List<TemplateMessage> getTemplate(BasicModel basicModel) throws Exception;
}
