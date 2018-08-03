package cn.edu.uestc.smgt.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.service.base.AstHistoryInfoService;
import cn.edu.uestc.smgt.utils.DateUtils;

@Controller
@RequestMapping("/base/ast/history/")
public class AstHistoryInfoController {
	@Autowired
	private AstHistoryInfoService astHistoryInfoService;

	/**
	 * 条件分页查询当前的离职助管easyui版 stuId:学号 stuName:姓名 startTime:离职记录创建时间
	 * endTime:离职记录最近一次修改时间
	 * 
	 * @author ljd 2016-10-25
	 */
	@RequiresRoles("0")//只有管理员才能查看离职人员信息
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		try {
			QueryBase querybase = new QueryBase();
			querybase.addParameter("stuId", map.get("stuId"));
			querybase.addParameter("name", map.get("stuName"));
			querybase.addParameter("startTime", map.get("startTime"));
			querybase.addParameter("endTime", map.get("endTime"));
			if (!StringUtils.isEmpty((CharSequence) map.get("startTime")))
				querybase.addParameter("startTime", DateUtils.long2Date(Long
						.parseLong(map.get("startTime").toString())));
			if (!StringUtils.isEmpty((CharSequence) map.get("endTime")))
				querybase.addParameter("endTime", DateUtils.long2Date(Long
						.parseLong(map.get("endTime").toString())));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			astHistoryInfoService.query(querybase);
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
