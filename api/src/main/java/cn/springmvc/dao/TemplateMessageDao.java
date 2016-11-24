package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.templateMesg.TemplateMessage;

/**
 * 
 * @author johnson
 *
 */
public interface TemplateMessageDao {

	/**
	 * get template by basic id
	 * 
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public List<TemplateMessage> getTemplate(int basicId) throws Exception;
}
