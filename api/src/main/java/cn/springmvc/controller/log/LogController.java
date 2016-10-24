package cn.springmvc.controller.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;
import com.springmvc.utils.mongodb.model.DebugLog;
import com.springmvc.utils.mongodb.model.DebugResponse;
import com.springmvc.utils.mongodb.model.ErrorLog;
import com.springmvc.utils.mongodb.model.ErrorResponse;
import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.OperateLog;
import com.springmvc.utils.mongodb.model.OperateResponse;

import cn.springmvc.Consts;
import cn.springmvc.model.log.DebugLogQuery;
import cn.springmvc.model.log.LogQuery;
import cn.springmvc.model.log.OperateLogQuery;
import cn.springmvc.service.log.DebugLogService;
import cn.springmvc.service.log.ErrorLogService;
import cn.springmvc.service.log.OperateLogService;

/**
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private OperateLogService operateService;

	@Autowired
	private DebugLogService debugService;

	@Autowired
	private ErrorLogService errorService;

	@Autowired
	private MongoConfig mongoConfig;

	/**
	 * get operate log
	 * 
	 * @param queryDat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operate", method = RequestMethod.POST)
	public Map<String, Object> getOperateLog(@RequestBody OperateLogQuery queryDat) {
		OperateResponse res = null;

		System.out.println(queryDat);
		try {
			res = operateService.getOperateLog(queryDat, mongoConfig);
			return HttpUtils.generateResponse("0", "获得操作日志成功", res);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "获得操作日志失败", null);
		}

	}

	/**
	 * clear operate log
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operate", method = RequestMethod.DELETE)
	public Map<String, Object> clearOperateLog() {
		try {
			operateService.clear(mongoConfig);
			return HttpUtils.generateResponse("0", "清空操作日志成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "清空操作日志失败", null);
		}
	}

	/**
	 * export operate log
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/operate/export", method = RequestMethod.GET)
	public void exportOperateLog(HttpServletResponse response) throws IOException {
		StringBuilder content = new StringBuilder("");
		try {
			List<OperateLog> res = operateService.getAll(mongoConfig);
			for (int i = 0; i < res.size(); i++) {
				content.append(res.get(i).toString());
				content.append("\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getOutputStream().print("内部服务器错误");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String fileName = "operateLog" + df.format(new Date()) + ".log";
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.getOutputStream().print(new String(content.toString().getBytes(), "ISO-8859-1"));
	}

	/**
	 * get debug log
	 * 
	 * @param queryDat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/debug", method = RequestMethod.POST)
	public Map<String, Object> getDebugLog(@RequestBody DebugLogQuery queryDat) {
		DebugResponse res = null;

		try {
			res = debugService.getLog(queryDat, mongoConfig);
			return HttpUtils.generateResponse("0", "获得debug日志成功", res);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "获得debug日志失败", null);
		}
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/debug", method = RequestMethod.DELETE)
	public Map<String, Object> clearDebugLog() {
		try {
			debugService.clear(mongoConfig);
			return HttpUtils.generateResponse("0", "清空debug日志成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "清空debug日志失败", null);
		}
	}

	/**
	 * get all debug logs
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/debug/export", method = RequestMethod.GET)
	public void exportDebugLog(HttpServletResponse response) throws IOException {
		StringBuilder content = new StringBuilder("");
		try {
			List<DebugLog> res = debugService.getAll(mongoConfig);
			for (int i = 0; i < res.size(); i++) {
				content.append(res.get(i).toString());
				content.append("\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getOutputStream().print("服务器内部错误");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String fileName = "debug" + df.format(new Date()) + ".log";
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.getOutputStream().print(new String(content.toString().getBytes(), "ISO-8859-1"));
	}

	/**
	 * get error log
	 * 
	 * @param queryDat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public Map<String, Object> getErrorLog(@RequestBody LogQuery queryDat) {
		ErrorResponse res = null;

		try {
			res = errorService.getLog(queryDat.getStartTime(), queryDat.getEndTime(), queryDat.getSkip(), mongoConfig);
			return HttpUtils.generateResponse("0", "获得异常日志成功", res);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "获得异常日志失败", null);
		}
	}

	/**
	 * clear error log
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/error", method = RequestMethod.DELETE)
	public Map<String, Object> clearErrorLog() {
		try {
			errorService.clear(mongoConfig);
			return HttpUtils.generateResponse("0", "清空异常日志成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "清空异常日志失败", null);
		}
	}

	/**
	 * export error log
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/error/export", method = RequestMethod.GET)
	public void exportErrorLog(HttpServletResponse response) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String fileName = "Error" + df.format(new Date()) + ".log";
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		StringBuilder content = new StringBuilder("");
		try {
			List<ErrorLog> res = errorService.getAll(mongoConfig);
			for (int i = 0; i < res.size(); i++) {
				content.append(res.get(i).toString());
				content.append("\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getOutputStream().print("服务器内部错误");
		}
		response.getOutputStream().print(new String(content.toString().getBytes(), "ISO-8859-1"));
	}

	/**
	 * set debug mode
	 * 
	 * @param debug
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setDebug/{debug}", method = RequestMethod.GET)
	public Map<String, Object> setDebugMode(@PathVariable boolean debug) {
		Consts.DEBUG_MODE = debug;
		return HttpUtils.generateResponse("0", "debug模式设置成功", Consts.DEBUG_MODE);
	}

}
