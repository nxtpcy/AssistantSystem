package cn.edu.uestc.smgt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.service.LogService;
import cn.edu.uestc.smgt.utils.DateUtils;

@Controller
@RequestMapping("/usr/")
public class LogController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LogService logService;

	/**
	 * 条件分页查询当前的用户日志
	 * 
	 * @author ljd 2016-10-25
	 */
	@RequestMapping(value = "log/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("loginName", map.get("loginName"));
			querybase.addParameter("logDesc", map.get("logDesc"));

			querybase.addParameter("startTime", DateUtils.long2Date(Long
					.parseLong(map.get("startTime").toString())));
			querybase.addParameter("endTime", DateUtils.long2Date(Long
					.parseLong(map.get("endTime").toString())));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			logService.query(querybase);
			HashMap result = new HashMap();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用LogController.list出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 条件分页查询当前的用户日志easyui版
	 * 
	 * @author ljd 2016-10-25
	 */
	@RequestMapping(value = "log/list1", method = RequestMethod.POST)
	@ResponseBody
	public Object list1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map map) {
		try {
			QueryBase querybase = new QueryBase();
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("loginName", map.get("loginName"));
			querybase.addParameter("logDesc", map.get("logDesc"));
			System.out.println(map.get("startTime"));
			if (!StringUtils.isEmpty((CharSequence) map.get("startTime")))
				querybase.addParameter("startTime", DateUtils.long2Date(Long
						.parseLong(map.get("startTime").toString())));
			if (!StringUtils.isEmpty((CharSequence) map.get("endTime")))
				querybase.addParameter("endTime", DateUtils.long2Date(Long
						.parseLong(map.get("endTime").toString())));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			logService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("rows", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			/*
			 * logger.error("调用LogController.list出错,map={},error={}", new
			 * Object[] { map, e });
			 */
			e.printStackTrace();
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}
}
