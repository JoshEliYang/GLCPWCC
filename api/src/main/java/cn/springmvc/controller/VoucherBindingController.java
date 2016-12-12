package cn.springmvc.controller;

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
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;
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
	public Map<String, Object> create(@RequestBody VoucherModel vmodel, HttpServletRequest request) {
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		// Map<String, String> result = null;

		List<UserParamModel> result;

		String count;

		try {
			result = voucherBuildingService.getUser(vmodel, model);
			count = voucherBuildingService.getUserCount(vmodel, model);
			logger.error("tags--" + result);
			return HttpUtils.generateResponseFour("0", "success", result, count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}

	/**
	 * 剩余优惠券查询
	 * 
	 * @author summ
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAll() {
		try {
			return HttpUtils.generateResponse("0", "get vouche success", voucherSevice.getVouvher());
		} catch (Exception e) {
			// TODO: handle exception
			return HttpUtils.generateResponse("1", "get vouvher failed", "0");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/binding", method = RequestMethod.POST)
	public Map<String, Object> bindingAllUser(@RequestBody VoucherModel vmodel, HttpServletRequest request) {
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		// Map<String, String> result = null;

		// List<UserParamModel> result;

		String count;

		try {
			count = voucherBuildingService.getBindingCount(vmodel, model);
			// count = voucherBuildingService.getUserCount(vmodel, model);
			// logger.error("tags--" + result);
			return HttpUtils.generateResponseFour("0", "success", null, count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}

}
