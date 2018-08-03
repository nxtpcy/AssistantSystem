package cn.edu.uestc.smgt.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/")
public class TestController {

	@RequestMapping("/test1")
	@ResponseBody
	public String test1(HttpSession session) {
		session.setAttribute("name", "liangjidong");
		session.setAttribute("password", "222222");
		return "test1";
	}

	@RequestMapping("/test2")
	@ResponseBody
	public String test2(HttpSession session) {
		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
		return "test2,username=[" + name + "],password=[" + password + "]";
	}

}
