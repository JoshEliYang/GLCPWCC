package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.VoucherService;

@Scope("prototype")
@Controller
@RequestMapping("/voucher")

public class VoucherController {
	@Autowired
	VoucherService voucherSevice;
	
	/**
	 * 剩余优惠券查询
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


}
