package cn.edu.uestc.smgt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.HelpFunctionMenu;
import cn.edu.uestc.smgt.service.UserMenuService;

@Deprecated
@Controller
@RequestMapping("/usr/")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserMenuService userMenuService;

	@SysControllerLogAnnotation(desc = "更新用户权限")
	@RequestMapping(value = "/menu/updateUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserMenu(HttpServletRequest request,
			@RequestParam int userId, @RequestParam String menuIds) {
		try {
			int status = userMenuService.updateUserMenu(userId, menuIds);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error(
					"调用MenuController.updateUserMenu出错,userid={},menuIds={},error={}",
					new Object[] { userId, menuIds, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "/menu/getUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public Object getUserMenu(HttpServletRequest request,
			@RequestParam int userId) {
		try {
			List<HelpFunctionMenu> menus = userMenuService.getMenus(userId);
			String message = StatusType.SUCCESS.getMessage();
			return new Response(StatusType.SUCCESS.getVal(), message, menus);
		} catch (Exception e) {
			logger.error(
					"调用MenuController.getUserMenu出错,userid={},userId={},error={}",
					new Object[] { userId, userId, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}
}
