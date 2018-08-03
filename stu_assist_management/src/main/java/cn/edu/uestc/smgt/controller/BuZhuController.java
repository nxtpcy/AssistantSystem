package cn.edu.uestc.smgt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import cn.edu.uestc.smgt.annotation.UserAnnotation;
import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.Buzhu;
import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition;
import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition4EasyUI;
import cn.edu.uestc.smgt.service.BuZhuService;
import cn.edu.uestc.smgt.utils.ConvertUtil;

@Controller
@RequestMapping("/usr/")
public class BuZhuController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BuZhuService buzhuService;

	@SysControllerLogAnnotation(desc = "增加补助信息（控制相关信息）")
	@RequestMapping(value = "/buzhu/addBuZhuCheck", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:addBuZhuCheck")
	public Object addBuZhuCheck(HttpServletRequest request,
			@RequestBody Buzhu buzhu) {
		try {
			int status = buzhuService.addBuZhuCheck(buzhu);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.addBuZhuCheck出错,Buzhu={}",
					new Object[] { buzhu }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "增加补助信息（不控制相关信息）")
	@RequestMapping(value = "/buzhu/addBuZhuWithoutCheck", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:addBuZhuWithoutCheck")
	public Object addBuZhuWithoutCheck(HttpServletRequest request,
			@RequestBody Buzhu buzhu) {
		try {
			int status = buzhuService.addBuZhuWithoutCheck(buzhu);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.addBuZhuWithoutCheck出错,Buzhu={}",
					new Object[] { buzhu }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "删除补助记录")
	@RequestMapping(value = "/buzhu/remove", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:remove")
	public Object remove(HttpServletRequest request, @RequestParam int id) {
		try {
			int status = buzhuService.remove(id);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.remove出错,id={}", id, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "修改未提交审核的补助记录(补发)")
	@RequestMapping(value = "/buzhu/updateUnsubmittedBuzhuWithoutCheck", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:updateUnsubmittedBuzhuWithoutCheck")
	public Object updateUnsubmittedBuzhuWithoutCheck(
			HttpServletRequest request, @RequestBody Buzhu buzhu) {
		try {
			int status = buzhuService.updateUnsubmittedBuzhu(buzhu, false);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.updateUnsubmittedBuzhu出错,buzhu={}",
					buzhu, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "修改未提交审核的补助记录(正常发放)")
	@RequestMapping(value = "/buzhu/updateUnsubmittedBuzhuWithCheck", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:updateUnsubmittedBuzhuWithCheck")
	public Object updateUnsubmittedBuzhuWithCheck(HttpServletRequest request,
			@RequestBody Buzhu buzhu) {
		try {
			int status = buzhuService.updateUnsubmittedBuzhu(buzhu, true);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.updateUnsubmittedBuzhu出错,buzhu={}",
					buzhu, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "撤回已提交待审核的补助申请")
	@RequestMapping(value = "/buzhu/withdraw", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:withdraw")
	public Object withdraw(HttpServletRequest request,
			@RequestParam String buzhuList) {
		try {
			int status = buzhuService.batchWithdraw(buzhuList);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.withdraw出错,buzhuList={}",
					new Object[] { buzhuList }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "提交补助申请")
	@RequestMapping(value = "/buzhu/submit", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:submit")
	public Object submit(HttpServletRequest request,
			@RequestParam String buzhuList) {
		try {
			int status = buzhuService.bacthReview(buzhuList);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.submit出错,buzhuList={}",
					new Object[] { buzhuList }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "补助审核通过")
	@RequestMapping(value = "/buzhu/agree", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:agree")
	public Object agree(HttpServletRequest request,
			@RequestParam String buzhuList) {
		try {
			int status = buzhuService.bacthAgree(buzhuList);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.agree出错,buzhuList={}",
					new Object[] { buzhuList }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "补助审核拒绝")
	@RequestMapping(value = "/buzhu/disagree", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("buzhu:disagree")
	public Object disagree(HttpServletRequest request,
			@RequestParam String buzhuList) {
		try {
			int status = buzhuService.batchDisagree(buzhuList);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用BuZhuController.disagree出错,buzhuList={}",
					new Object[] { buzhuList }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "/buzhu/searchBuzhuByPage", method = RequestMethod.POST)
	@ResponseBody
	// @RequiresPermissions("buzhu:searchBuzhuByPage")
	public Object searchBuzhuByPage(HttpServletRequest request,
			@RequestBody BuZhuCondition buzhuCondition) {
		try {
			QueryBase queryBase = buzhuService
					.searchBuZhuByPage(buzhuCondition);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", queryBase.getTotalRow());
			result.put("result", queryBase.getResults());
			return new Response(StatusType.SUCCESS.getVal(),
					StatusType.SUCCESS.getMessage(), result);
		} catch (Exception e) {
			logger.error(
					"调用BuZhuController.searchBuzhuByPage出错,buzhuCondition={}",
					new Object[] { buzhuCondition }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * ljd修改，用于easyui数据获取
	 * 
	 * @param request
	 * @param buzhuCondition
	 * @return
	 */
	@RequestMapping(value = "/buzhu/searchBuzhuByPage1", method = RequestMethod.POST)
	@ResponseBody
	// @RequiresPermissions("buzhu:searchBuzhuByPage1")
	public Object searchBuzhuByPage1(HttpServletRequest request,
			@RequestParam Map<String, Object> paras) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			BuZhuCondition4EasyUI buZhuCondition4EasyUI = ConvertUtil
					.convert(paras);
			BuZhuCondition buzhuCondition = ConvertUtil
					.convertBuzhuCondition4EasyUI(buZhuCondition4EasyUI);
			logger.info("buzhuCondition={},buZhuCondition4Easy={}",
					buzhuCondition, buZhuCondition4EasyUI);
			QueryBase queryBase = buzhuService
					.searchBuZhuByPage(buzhuCondition);
			result.put("total", queryBase.getTotalRow());
			result.put("rows", queryBase.getResults());
			return result;
		} catch (Exception e) {
			logger.error(
					"调用BuZhuController.searchBuzhuByPage出错,buzhuCondition={}",
					new Object[] { paras }, e);
			return result;
		}
	}

	@RequestMapping(value = "/buzhu/searchBuzhu", method = RequestMethod.POST)
	@ResponseBody
	// @RequiresPermissions("buzhu:searchBuzhu")
	public Object searchBuzhu(HttpServletRequest request,
			@RequestBody BuZhuCondition buzhuCondition) {
		try {
			QueryBase queryBase = buzhuService.searchBuzhu(buzhuCondition);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", queryBase.getResults());
			return new Response(StatusType.SUCCESS.getVal(),
					StatusType.SUCCESS.getMessage(), result);
		} catch (Exception e) {
			logger.error("调用BuZhuController.searchBuzhu出错,buzhuCondition={}",
					new Object[] { buzhuCondition }, e);
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}
}
