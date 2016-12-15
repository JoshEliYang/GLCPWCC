package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucheModel;
import cn.springmvc.model.VoucherModel;
import cn.springmvc.model.voucher.BindingMessageModel;
import cn.springmvc.mq.MqSender;
import cn.springmvc.service.VoucherBuildingService;
import cn.springmvc.service.VoucherService;

@Scope("prototype")
@Controller
@RequestMapping("/voucher")
public class VoucherBindingController {
	@Autowired
	private VoucherBuildingService voucherBuildingService;
	@Autowired
	private VoucherService voucherSevice;

	Logger logger = Logger.getLogger(VoucherBindingController.class);

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody VoucherModel vmodel,
			HttpServletRequest request) {
		List<UserParamModel> result;
		String count;
		try {
			result = voucherBuildingService.getUser(vmodel);
			count = voucherBuildingService.getUserCount(vmodel);
			logger.error("tags--" + result);
			return HttpUtils
					.generateResponseFour("0", "success", result, count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}

	/**
	 * 剩余优惠券查询
	 * 
	 * @return
	 * @author summ
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAll() {
		try {
			return HttpUtils.generateResponse("0", "get vouche success",
					voucherSevice.getVouvher());
		} catch (Exception e) {
			return HttpUtils.generateResponse("1", "get vouvher failed", "0");
		}

	}

	/**
	 * 测试获得卡券code
	 * 
	 * @return
	 * @author summ
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> getVoucherCode(
			@RequestBody VoucheModel voucheModel) {
		System.out.println(voucheModel.getPromotionIdList());
		System.out.println(voucheModel.getVoucherNum());
		try {
			return HttpUtils.generateResponse(
					"0",
					"get vouche success",
					voucherSevice.getVoucherCode(
							voucheModel.getPromotionIdList(),
							voucheModel.getVoucherNum()));
		} catch (Exception e) {
			return HttpUtils.generateResponse("1", "get vouvher failed", "0");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/bindingall", method = RequestMethod.POST)
	public Map<String, Object> bindingAllUser(@RequestBody VoucherModel vmodel,
			HttpServletRequest request) {
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		User adminInfo = (User) request.getAttribute("admin");
		
		List<String> user = vmodel.getUsers();
		
		BindingMessageModel bmm = new BindingMessageModel();
		
		if(user != null && user.size() > 0){
			
			List<UserParamModel> customerIdUser;
			customerIdUser = voucherBuildingService
					.getCustomerIdByUser(vmodel.getUsers());
			
			List<String> vouList = voucherSevice.getVoucherCode(vmodel.getPromotionIds(),
					vmodel.getCustomerCount());
			bmm.setBasicModel(model);
			bmm.setUserList(customerIdUser);
			bmm.setVoucherList(vouList);
			bmm.setTemplateId("8Umia-WustHVtjQ3qSz9dN0toMEYYj8bKndQGPsQCeI");
			
		}else{
			// 获取总的用户
			String count = "0";
			List<UserParamModel> userList = new ArrayList<UserParamModel>();
			try {
				userList = voucherBuildingService.getUser(vmodel);
				count = voucherBuildingService.getUserCount(vmodel);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			List<String> vouList = voucherSevice.getVoucherCode(vmodel.getPromotionIds(),
					Integer.parseInt(count));
			
			bmm.setBasicModel(model);
			bmm.setUserList(userList);
			bmm.setVoucherList(vouList);
			bmm.setTemplateId("8Umia-WustHVtjQ3qSz9dN0toMEYYj8bKndQGPsQCeI");
			
		}
		
		
		String parameter = String.valueOf(bmm);
		
		TaskRequest taskrequest = new TaskRequest();
		taskrequest.setMethod("VoucherBindingMessage");
		taskrequest.setAdmin(adminInfo);
		taskrequest.setTaskTimeStamp("");
		taskrequest.setParameter(parameter);
		
		//MqSender
		
		MqSender mqSender = new MqSender();
		mqSender.sender(taskrequest);

		// Map<String, String> result = null;

		// List<UserParamModel> result;
		if(user != null && user.size() > 0){
			return HttpUtils.generateResponse("1", "部分Success", null);
		}else{
			return HttpUtils.generateResponse("1", "全部Success", null);
		}
		
	}

}
