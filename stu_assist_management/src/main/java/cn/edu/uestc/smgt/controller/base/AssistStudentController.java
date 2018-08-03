package cn.edu.uestc.smgt.controller.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.AssistStudent;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.service.base.AssistStudentService;
import cn.edu.uestc.smgt.service.base.StudentInSchoolService;
import cn.edu.uestc.smgt.utils.ExcelUtil;

@Controller
@RequestMapping("/base/stu/")
public class AssistStudentController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AssistStudentService assistStudentService;

	@Autowired
	private StudentInSchoolService studentInSchoolService;

	@SysControllerLogAnnotation(desc = "增加助管")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AssistStudent ast) {
		try {
			int status = assistStudentService.add(ast);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.add出错,assistStudent={},error={}",
					new Object[] { ast, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "修改助管信息")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(HttpServletRequest request,
			HttpServletResponse response, @RequestBody AssistStudent ast) {
		try {
			int status = assistStudentService.update(ast);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.update出错,assistStudent={},error={}",
					new Object[] { ast, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "删除助管")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response, @RequestBody AssistStudent ast) {
		try {
			int status = assistStudentService.delete(ast);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用AssistStudentController.delete出错,ast={},error={}",
					new Object[] { ast.getDeptId(), e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			String banks = (String) map.get("bankName");
			String[] bankArray = null;
			if (banks == null || "".equals(banks)) {
				bankArray = null;
			} else {
				bankArray = banks.split(",");
			}
			querybase.addParameter("bankName", bankArray);
			querybase.addParameter("stuId", map.get("stuId"));
			querybase.addParameter("name", map.get("name"));
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			String[] deptIdArray = null;
			if (user != null && user.getRole() == 1) {
				deptIdArray = new String[] { user.getDeptId() };
			} else {
				String deptIds = (String) map.get("deptId");
				if (deptIds == null || "".equals(deptIds)) {
					deptIdArray = null;
				} else {
					deptIdArray = deptIds.split(",");
				}
			}
			querybase.addParameter("deptId", deptIdArray);
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			assistStudentService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用AssistStudentController.list1出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "list1", method = RequestMethod.POST)
	@ResponseBody
	public Object list1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		try {
			QueryBase querybase = new QueryBase();
			String banks = (String) map.get("bankName");
			String[] bankArray = null;
			if (banks == null || "".equals(banks)) {
				bankArray = null;
			} else {
				bankArray = banks.split(",");
			}
			querybase.addParameter("bankName", bankArray);
			querybase.addParameter("stuId", map.get("stuId"));
			querybase.addParameter("name", map.get("name"));
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			String[] deptIdArray = null;
			if (user != null && user.getRole() == 1) {
				deptIdArray = new String[] { user.getDeptId() };
			} else {
				String deptIds = (String) map.get("deptId");
				if (deptIds == null || "".equals(deptIds)) {
					deptIdArray = null;
				} else {
					deptIdArray = deptIds.split(",");
				}
			}
			querybase.addParameter("deptId", deptIdArray);
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			assistStudentService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("rows", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用AssistStudentController.list1出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据条件查询当前在校生
	 * 
	 * 查询条件：学号，姓名，院系（编号+名称）[下拉列表形式]，导师代码（手动输入），导师姓名（ 手动输入）
	 * stuId，stuName，deptId，dsId，dsName
	 * 
	 * @author ljd
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "searchStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object searchStudentInSchool(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			QueryBase querybase = new QueryBase();

			querybase.addParameter("stuId", map.get("stuId"));
			querybase.addParameter("stuName", map.get("stuName"));
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("bankName", map.get("dsId"));
			querybase.addParameter("dsId", map.get("dsId"));
			querybase.addParameter("dsName", map.get("dsName"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			studentInSchoolService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.searchStudentInSchool出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据条件查询当前在校生
	 * 
	 * 查询条件：学号，姓名，院系（编号+名称）[下拉列表形式]，导师代码（手动输入），导师姓名（ 手动输入）
	 * stuId，stuName，deptId，dsId，dsName
	 * 
	 * @author ljd
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "searchStudent1", method = RequestMethod.POST)
	@ResponseBody
	public Object searchStudentInSchool1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		try {
			QueryBase querybase = new QueryBase();

			querybase.addParameter("stuId", map.get("stuId"));
			querybase.addParameter("stuName", map.get("stuName"));
			querybase.addParameter("deptId", map.get("deptId"));
			querybase.addParameter("bankName", map.get("dsId"));
			querybase.addParameter("dsId", map.get("dsId"));
			querybase.addParameter("dsName", map.get("dsName"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			studentInSchoolService.query(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("rows", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.searchStudentInSchool出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据前端传递的学号集合，批量添加学生信息到助管列表中
	 * 
	 * @author ljd
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "添加学生库中学生为助管")
	@RequestMapping(value = "insertStuToAst", method = RequestMethod.POST)
	@ResponseBody
	public Object insertStudentToAssist(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			String stuIds = (String) map.get("stuIds");
			if (stuIds == null) {
				return new Response(StatusType.PARAMETER_IS_NULL.getVal(),
						StatusType.PARAMETER_IS_NULL.getMessage());
			}
			// 将stuId分割
			String[] ids = stuIds.split(",");
			String info = assistStudentService.batchAddStudentToAssist(ids);
			if (!StringUtils.isEmpty(info)) {
				return new Response(-1, "导入失败信息如下</br>" + info);
			} else {
				return new Response(0, "添加成功");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.searchStudentInSchool出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 批量上传助管信息，使用xls文件
	 * 
	 */
	@SysControllerLogAnnotation(desc = "excel批量上传助管信息")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("uploadExcel") CommonsMultipartFile files) {
		System.out.println(files.getOriginalFilename());
		if (files != null) {
			InputStream is = null;
			try {
				// 将excel文件转为List，然后调用service层方法批量插入
				is = files.getInputStream();
				HashMap<String, Object> sheetMap = ExcelUtil.getSheet(is, 1);
				if (sheetMap.get("sheet") != null) {
					Sheet sheet = (Sheet) sheetMap.get("sheet");
					List<AssistStudent> list = readAststuData(sheet);
					if (null != list) {
						// int fileSize = list.size();
						String info = assistStudentService
								.batchAddAssistByExcel(list);
						if (StringUtils.isEmpty(info)) {
							return new Response(0, "添加成功");
						} else {
							return new Response(-1, "导入失败信息如下</br>" + info);
						}
					} else {
						return new Response(-1, "数据为空");
					}

				} else {
					return new Response(-1, "数据为空");
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.error(
						"调用AssistStudentController.upload出错,files={},error={}",
						new Object[] { files, e });
				return new Response(StatusType.EXCEPTION.getVal(),
						StatusType.EXCEPTION.getMessage());

			} finally {
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return new Response(-1, "未选择文件！");
	}

	/**
	 * 下载填写的模板用于上传使用，用户必须按照这个模板上传
	 */
	@RequestMapping("downloadMB")
	public void downloadMB(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		byte[] body = null;
		InputStream is = request.getSession().getServletContext()
				.getResourceAsStream("/moban/模板.xlsx");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("模板" + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 在职助管转为离职状态 在职转离职：先判断当前学生的信息是否已经在离职表中存在。然后更新手机号码，银行信息，银行卡号。
	 * 然后获取startTime，endTime ，deptName ，然后与原来的info进行拼接。插入
	 * 
	 * 批量助管转离职
	 * 
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "在职助管转离职")
	@RequestMapping(value = "currToHist", method = RequestMethod.POST)
	@ResponseBody
	public Object currentToHistory(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String stuIds) {
		try {
			if (stuIds == null) {
				return new Response(StatusType.PARAMETER_IS_NULL.getVal(),
						StatusType.PARAMETER_IS_NULL.getMessage());
			}
			// 将stuId分割
			String[] ids = stuIds.split(",");
			int length = ids.length;
			int successCount = 0;
			for (int i = 0; i < ids.length; i++) {
				try {
					int ans = assistStudentService.currToHist(ids[i]);
					if (ans == StatusType.SUCCESS.getVal())
						successCount++;
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(
							"调用AssistStudentController.currentToHistory出错,map={},error={}",
							new Object[] { ids[i], e });
				}
			}
			if (successCount == length) {
				return new Response(0, "转离职成功");
			} else {
				return new Response(-1, "失败数据条数:" + (length - successCount));
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用AssistStudentController.currentToHistory出错,map={},error={}",
					new Object[] { stuIds, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 将excel转为list
	 * 
	 * @param sheet
	 * @return
	 */
	private List<AssistStudent> readAststuData(Sheet sheet) {
		// TODO Auto-generated method stub
		List<AssistStudent> list = new ArrayList<AssistStudent>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			AssistStudent ast = new AssistStudent();
			int j = 0;
			int nullCount = 0;
			String stuId = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(stuId)) {
				ast.setStuId(stuId);
			} else {
				nullCount++;
			}
			String name = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(name)) {
				ast.setName(name);
			} else {
				nullCount++;
			}
			String stuCollId = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(stuCollId)) {
				ast.setStuCollegeId(stuCollId);
			} else {
				nullCount++;
			}
			String stuCollName = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(stuCollName)) {
				ast.setStuCollegeName(stuCollName);
			} else {
				nullCount++;
			}
			String stuDsId = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(stuDsId)) {
				ast.setStuDsId(stuDsId);
			} else {
				nullCount++;
			}
			String stuDsName = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(stuDsName)) {
				ast.setStuDsName(stuDsName);
			} else {
				nullCount++;
			}
			String deptId = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(deptId)) {
				ast.setDeptId(deptId);
			} else {
				nullCount++;
			}
			String deptName = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(deptName)) {
				// map.put("deptName", deptName);
			} else {
				nullCount++;
			}
			String bankNo = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(bankNo)) {
				ast.setBankNo(bankNo);
			} else {
				nullCount++;
			}
			String bankName = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(bankName)) {
				ast.setBankName(bankName);
			} else {
				nullCount++;
			}
			String tel = ExcelUtil.getCellValue(sheet, j++, i);
			if (!ExcelUtil.isNullOrEmpty(tel)) {
				ast.setTelephone(tel);
			} else {
				nullCount++;
			}
			if (nullCount != j) {
				list.add(ast);
			}
		}
		return list;
	}

}
