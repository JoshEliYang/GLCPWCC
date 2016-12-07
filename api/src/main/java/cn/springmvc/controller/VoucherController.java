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

@Scope("prototype")
@Controller
@RequestMapping("/voucherBinding")
public class VoucherController {

	@Autowired
	public VoucherBuildingService voucherBuildingService;
	Logger logger = Logger.getLogger(VoucherController.class);

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody VoucherModel vmodel, HttpServletRequest request) {
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		// Map<String, String> result = null;

		List<UserParamModel> result;

		try {
			result = voucherBuildingService.getUser(vmodel, model);
			logger.error("tags--" + result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
}
