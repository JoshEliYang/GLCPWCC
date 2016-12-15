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
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucheModel;
import cn.springmvc.model.VoucherModel;
import cn.springmvc.model.voucher.BindingMessageModel;
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
		User adminName = (User) request.getAttribute("admin");
		String count = "0";

		List<UserParamModel> customerIdUser;

		// 根据customer获取用户
		List<String> customerIdList = new ArrayList<String>();

		customerIdList.add("a1065526cd8f4aaf9bbc78c5740a926d");
		customerIdList.add("54b8c760633e4df7852f2bfb0ae5bd64");

		customerIdUser = voucherBuildingService
				.getCustomerIdByUser(customerIdList);

		// 获取总的用户
		List<UserParamModel> userList = new ArrayList<UserParamModel>();
		try {
			userList = voucherBuildingService.getUser(vmodel);
			count = voucherBuildingService.getUserCount(vmodel);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<String> promotionIdList = new ArrayList<String>();
		promotionIdList.add("2c90808158dc7b520158f17b156500e5");
		promotionIdList.add("2c90808158f67abf0158f77c92720006");

		List<String> vouList = voucherSevice.getVoucherCode(promotionIdList,
				Integer.parseInt(count));

		// 拼接taskr中parameter
		BindingMessageModel bmm = new BindingMessageModel();
		bmm.setBasicModel(model);
		bmm.setUserList(userList);
		bmm.setVoucherList(vouList);

		// Map<String, String> result = null;

		// List<UserParamModel> result;

		try {
			// count = voucherBuildingService.getBindingCount(vmodel, model);
			// count = voucherBuildingService.getUserCount(vmodel, model);
			// logger.error("tags--" + result);
			// return HttpUtils.generateResponseFour("0", "success", null,
			// count);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}

}
