package cn.edu.uestc.smgt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.JinE;
import cn.edu.uestc.smgt.service.JinEService;

@Controller
@RequestMapping("/mgr/")
public class JinEController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JinEService jinEService;

	@SysControllerLogAnnotation(desc = "金额控制修改")
	@RequestMapping(value = "je/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody JinE je) {
		try {
			int status = jinEService.add(je.getJe());
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用JinEController.add出错,je={},error={}", new Object[] {
					je, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "je/get", method = RequestMethod.POST)
	@ResponseBody
	public Object getJE(HttpServletRequest request, HttpServletResponse response) {
		try {
			JinE je = jinEService.getJE();
			if (je == null) {
				return new Response(StatusType.NOT_EXISTS.getVal(),
						StatusType.NOT_EXISTS.getMessage());
			} else {
				return new Response(StatusType.SUCCESS.getVal(), je);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用JinEController.getJE出错,error={}",
					new Object[] { e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}
}
