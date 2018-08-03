package cn.edu.uestc.smgt.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.service.base.DeptService;

@Controller
@RequestMapping("/base/")
public class DeptController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeptService deptService;

	@SysControllerLogAnnotation(desc = "增加部门信息")
	@RequestMapping(value = "dept/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Dept dept) {
		try {
			if (dept.getJobNum() < 0) {
				return new Response(StatusType.DATA_INVALID.getVal(),
						StatusType.DATA_INVALID.getMessage());
			}
			int status = deptService.add(dept);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用DeptController.add出错,dept={},error={}",
					new Object[] { dept, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "删除部门")
	@RequestMapping(value = "dept/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Dept dept) {
		try {
			int status = deptService.delete(dept.getDeptId());
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用DeptController.delete出错,dept={},error={}",
					new Object[] { dept.getDeptId(), e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "修改部门信息")
	@RequestMapping(value = "dept/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Dept dept) {
		try {
			if (dept.getJobNum() < 0) {
				return new Response(StatusType.DATA_INVALID.getVal(),
						StatusType.DATA_INVALID.getMessage());
			}
			int status = deptService.update(dept);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用DeptController.update出错,dept={},error={}",
					new Object[] { dept, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "dept/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("deptName", map.get("deptName"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			deptService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用DeptController.update出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "dept/list1", method = RequestMethod.POST)
	@ResponseBody
	public Object list1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map map) {
		try {
			QueryBase querybase = new QueryBase();
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("deptName", map.get("deptName"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			deptService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("rows", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用DeptController.update出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}
}
