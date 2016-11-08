package cn.springmvc.service.function;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.templateMesg.TemplateMessage;

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
