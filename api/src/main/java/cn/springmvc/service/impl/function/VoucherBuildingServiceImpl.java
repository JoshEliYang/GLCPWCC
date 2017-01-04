package cn.springmvc.service.impl.function;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.VoucherMsgConfigDao;
import cn.springmvc.daoLvdi.VoucherDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;
import cn.springmvc.model.voucher.VoucherMessageModel;
import cn.springmvc.service.function.VoucherBuildingService;
import cn.springmvc.websocket.ProgressSocket;

/**
 * 
 * @author xia0zhu
 *
 */
@Service
public class VoucherBuildingServiceImpl implements VoucherBuildingService {

	@Autowired
	private VoucherDao voucherDao;
	@Autowired
	private VoucherMsgConfigDao configDao;

	public List<UserParamModel> getUser(VoucherModel vmodel) throws Exception {
		return voucherDao.getUser(vmodel.getFilter(), vmodel.getOrder(), vmodel.getOffset(), vmodel.getCount());
	}

	public String getUserCount(VoucherModel vmodel) {
		return voucherDao.getUserCount(vmodel.getFilter());
	}

	public String getBindingCount(VoucherModel vmodel, BasicModel model) {
		List<UserParamModel> list = voucherDao.getBindingCount(vmodel.getFilter());
		return String.valueOf(list.size());
	}

	public List<UserParamModel> getCustomerIdByUser(List<String> customerIdList, String timestamp, User admin) {

		List<UserParamModel> userparamModl = new ArrayList<UserParamModel>();

		for (int i = 0; i < customerIdList.size(); i++) {
			UserParamModel upm = voucherDao.getCustomerIdByUser(customerIdList.get(i));
			userparamModl.add(upm);
			TaskResponse taskMessage = new TaskResponse(admin.getId(), timestamp, "优惠券绑定任务", "正在获取用户信息", true, i + 1,
					customerIdList.size());
			ProgressSocket.broadcast(taskMessage);
		}

		return userparamModl;
	}

	/**
	 * get the configuration of voucher message model
	 */
	public VoucherMessageModel getVoucherConfig() throws Exception {
		return configDao.getConfig();
	}

	/**
	 * set the configuration of voucher message model
	 */
	public void setVoucherConfig(VoucherMessageModel voucherConfig) throws Exception {
		configDao.setConfig(voucherConfig);
	}

}
