package cn.edu.uestc.smgt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.HelpDate;
import cn.edu.uestc.smgt.service.ValidDateService;
@Controller
@RequestMapping("/usr/")
public class ValidDateController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ValidDateService validDateService;

	
	@SysControllerLogAnnotation(desc = "增加日期控制")
	@RequestMapping(value = "/date/add", method = RequestMethod.POST) 
	@ResponseBody
	public Object save(HttpServletRequest request,@RequestBody HelpDate helpDate) {
		try{
			int status = validDateService.save(helpDate);
			String message = StatusType.value(status).getMessage();
			return new Response(status,message);
		}catch(Exception e){
			logger.error("调用ValidDateController.save出错,HelpDate={}",new Object[]{helpDate},e);
			return new Response(StatusType.EXCEPTION.getVal(),StatusType.EXCEPTION.getMessage());
		}
	}
	
	@SysControllerLogAnnotation(desc = "删除日期控制")
	@RequestMapping(value = "/date/remove", method = RequestMethod.POST)
	@ResponseBody
	public Object remove(HttpServletRequest request,@RequestParam int id) {
		try{
			int status = validDateService.remove(id);
			String message = StatusType.value(status).getMessage();
			return new Response(status,message);
		}catch(Exception e){
			logger.error("调用ValidDateController.remove出错,id={}",id,e);
			return new Response(StatusType.EXCEPTION.getVal(),StatusType.EXCEPTION.getMessage());
		}
	}
	@RequestMapping(value = "/date/getAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllDates(HttpServletRequest request) {
		try{
			List<HelpDate> dates  = validDateService.getValidDates();
			return new Response(StatusType.SUCCESS.getVal(),StatusType.SUCCESS.getMessage(),dates);
		}catch(Exception e){
			logger.error("调用ValidDateController.getAllDates出错",e);
			return new Response(StatusType.EXCEPTION.getVal(),StatusType.EXCEPTION.getMessage());
		}
	}
}
