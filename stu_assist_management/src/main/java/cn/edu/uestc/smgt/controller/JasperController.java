package cn.edu.uestc.smgt.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.uestc.smgt.dao.BuzhuMapper;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.pojo.Buzhu;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.dto.BuZhuDTO;
import cn.edu.uestc.smgt.utils.DateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/jasper/")
@Deprecated
public class JasperController {
	public static int BASIC_LENGTH = 9 * 256;
	/*
	 * @RequestMapping(value = "/report", method = RequestMethod.GET) public
	 * String report(Model model) { // 报表数据源 JRDataSource jrDataSource = new
	 * JRBeanCollectionDataSource( JavaBeanPerson.getList()); Map
	 * exporterParameters = new HashedMap(); exporterParameters.put("deptName",
	 * "test"); exporterParameters.put("kaoheDate", "2016年10月"); // 动态指定报表模板url
	 * model.addAttribute("url", "/WEB-INF/jasper/kaohe.jasper");
	 * model.addAttribute("format", "word"); // 报表格式
	 * model.addAttribute("jrMainDataSource", jrDataSource);
	 * model.addAttribute("exporterParameters", exporterParameters); return
	 * "iReportView"; // 对应jasper-defs.xml中的bean id }
	 */

	@Autowired
	private BuzhuMapper buzhuMapper;
	@Autowired
	private DeptMapper deptMapper;

	/**
	 * 学院打印报表到word中
	 * 
	 * @param request
	 * @param response
	 * @param helpDate
	 * @param deptId
	 * @param statusCode
	 * @throws IOException
	 */
	@RequestMapping(value = "/reporter", method = RequestMethod.GET)
	public void reporter(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {
		Map<String, Object> datamap = deptGetExportInfo(helpDate, deptId,
				statusCode, null);
		response.setContentType("application/msword;charset=utf-8");
		String defaultFilename = "download";
		String defaultname = null;
		if (defaultFilename.trim() != null && defaultFilename != null) {
			defaultname = defaultFilename + ".doc";
		} else {
			defaultname = "export.doc";
		}
		String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		// new DocumentHandler().createDoc(dataMap, "D:\\test.doc");

		// 使用freemarker导出word
		Configuration configuration = null;
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/uestc/smgt/freemarker");
		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("deptWord.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		// File outFile = new File(fileName);
		Writer out = null;
		// FileOutputStream fos = null;

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			// fos = new FileOutputStream(outFile);
			OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
					"UTF-8");
			// 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
			// out = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(outFile)));
			out = new BufferedWriter(oWriter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(datamap, out);
			out.close();
			ouputStream.flush();
			ouputStream.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param helpDate
	 * @param deptId
	 * @param statusCode
	 * @throws IOException
	 */
	@RequestMapping(value = "/reporterExcel", method = RequestMethod.GET)
	public void reporterExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = true) String deptId,
			@RequestParam(required = false) Byte statusCode,
			@RequestParam(required = false) Byte type) throws IOException {
		Map<String, Object> datamap = deptGetExportInfo(helpDate, deptId,
				statusCode, type);
		/*
		 * for (Entry<String, Object> entry : datamap.entrySet()) {
		 * System.out.println(entry.getKey() + "---" + entry.getValue()); }
		 */
		response.setContentType("application/x-msdownload;charset=utf-8");
		String defaultname = DateUtils.date2LongStringForFileName(new Date())
				+ "_download.xls";
		String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		// new DocumentHandler().createDoc(dataMap, "D:\\test.doc");

		// 使用freemarker导出word
		Configuration configuration = null;
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/uestc/smgt/freemarker");
		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("deptExcel.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		// File outFile = new File(fileName);
		Writer out = null;
		// FileOutputStream fos = null;

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			// fos = new FileOutputStream(outFile);
			OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
					"UTF-8");
			// 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
			// out = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(outFile)));
			out = new BufferedWriter(oWriter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(datamap, out);
			out.close();
			ouputStream.flush();
			ouputStream.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成要打印的数据(对于学院)
	 * 
	 * @param params
	 * @author ljd
	 * @return
	 */
	private Map<String, Object> deptGetExportInfo(String helpDate,
			String deptId, Byte statusCode, Byte type) {
		String newDate = DateUtils.date2String(new Date());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("year", DateUtils.getYear(newDate));
		dataMap.put("month", DateUtils.getMonth(newDate));
		// 获取相关数据

		// 获取年月（多选数组）
		String[] dates = null;
		if (!StringUtils.isEmpty(helpDate)) {
			dates = helpDate.split(",");
		}
		// 获取deptName
		String[] depts = null;
		if (!StringUtils.isEmpty(deptId)) {
			Dept dept = deptMapper.selectByDeptId(deptId);
			dataMap.put("deptName", dept.getDeptName());
			depts = new String[] { deptId };
		} else {
			// 没有传学院id表示，有错误
			throw new NullPointerException("学院信息不能为空");
		}

		// 添加参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deptId", depts);
		params.put("helpDate", dates);
		params.put("statusCode", statusCode);
		params.put("type", type);
		List<BuZhuDTO> buzhus = buzhuMapper
				.selectBuZhuAndStudentInfoWithSelectionsByMap(params);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < buzhus.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("stuId", buzhus.get(i).getStuId());
			map.put("name", buzhus.get(i).getStudentName());
			map.put("bankNo", buzhus.get(i).getBankNo());
			map.put("money", buzhus.get(i).getMoney());
			map.put("beizhu", buzhus.get(i).getRemark());
			list.add(map);

		}
		dataMap.put("lists", list);
		dataMap.put("size", list.size() + 10);
		return dataMap;
	}

	/**
	 * 将已审核的助管补助申请信息导出为word
	 * 
	 * @param request
	 * @param response
	 * @throws JRException
	 * @throws IOException
	 */
	@RequestMapping(value = "/zgWorddownload", method = RequestMethod.GET)
	public void zgWorddownload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {
		// String helpDate = buzhu.getHelpDate();
		String newDate = helpDate;
		if (helpDate == null
				|| com.alibaba.druid.util.StringUtils.isEmpty(helpDate)) {
			newDate = DateUtils.date2String(new Date());
		}
		// 获取相关数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("year", DateUtils.getYear(newDate));
		dataMap.put("month", DateUtils.getMonth(newDate));

		// 添加参数
		Buzhu buzhu = new Buzhu();
		buzhu.setHelpDate(helpDate);
		buzhu.setDeptId(deptId);
		buzhu.setStatusCode(statusCode);
		List<BuZhuDTO> buzhus = buzhuMapper
				.selectBuZhuAndStudentInfoWithSelections(buzhu);
		List list = new ArrayList();
		for (int i = 0; i < buzhus.size(); i++) {

			HashMap map = new HashMap();
			map.put("id", i + 1);
			map.put("stuId", buzhus.get(i).getStuId());
			map.put("name", buzhus.get(i).getStudentName());
			map.put("bankName", buzhus.get(i).getBankName());
			map.put("bankNo", buzhus.get(i).getBankNo());
			map.put("money", buzhus.get(i).getMoney());
			list.add(map);

		}
		dataMap.put("buzhus", list);

		response.setContentType("application/msword;charset=utf-8");
		String defaultFilename = "download";
		String defaultname = null;
		if (defaultFilename.trim() != null && defaultFilename != null) {
			defaultname = defaultFilename + ".doc";
		} else {
			defaultname = "export.doc";
		}
		String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		// new DocumentHandler().createDoc(dataMap, "D:\\test.doc");

		// 使用freemarker导出word
		Configuration configuration = null;
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/uestc/smgt/freemarker");
		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("managerWord.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		// File outFile = new File(fileName);
		Writer out = null;
		// FileOutputStream fos = null;

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			// fos = new FileOutputStream(outFile);
			OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
					"UTF-8");
			// 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
			// out = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(outFile)));
			out = new BufferedWriter(oWriter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(dataMap, out);
			out.close();
			ouputStream.flush();
			ouputStream.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将已审核的助管补助申请信息导出为excel
	 * 
	 * @param request
	 * @param response
	 * @param buzhu
	 * @throws JRException
	 * @throws IOException
	 */
	@RequestMapping(value = "/zgExceldownload1", method = RequestMethod.GET)
	public void zgExceldownload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {
		Map<String, Object> map = managerGetExportInfo(helpDate, deptId,
				statusCode);
		response.setContentType("application/x-msdownload;charset=utf-8");
		String defaultFilename = "download";
		String defaultname = null;
		if (defaultFilename.trim() != null && defaultFilename != null) {
			defaultname = defaultFilename + ".xls";
		} else {
			defaultname = "export.xls";
		}
		String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		// new DocumentHandler().createDoc(dataMap, "D:\\test.doc");

		// 使用freemarker导出excel
		Configuration configuration = null;
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(),
				"/cn/edu/uestc/smgt/freemarker");
		Template t = null;
		try {
			t = configuration.getTemplate("managerExcel.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		// File outFile = new File(fileName);
		Writer out = null;
		// FileOutputStream fos = null;

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			// fos = new FileOutputStream(outFile);
			OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
					"UTF-8");
			// 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
			// out = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(outFile)));
			out = new BufferedWriter(oWriter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(map, out);
			out.close();
			ouputStream.flush();
			ouputStream.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将已审核的助管补助申请信息导出为excel,使用POI
	 * 
	 * @param request
	 * @param response
	 * @param buzhu
	 * @throws JRException
	 * @throws IOException
	 */
	@RequestMapping(value = "/zgExceldownload", method = RequestMethod.GET)
	public void zgExceldownload_poi(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) Byte type,
			@RequestParam(required = false) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {

		response.setContentType("application/x-msdownload;charset=utf-8");
		String fileName = new String(
				(DateUtils.date2LongStringForFileName(new Date()) + "_download.xls")
						.getBytes("GBK"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		// new DocumentHandler().createDoc(dataMap, "D:\\test.doc");

		// 使用POI导出excel
		String newDate = DateUtils.date2String(new Date());
		String title = DateUtils.getYear(newDate) + "年"
				+ DateUtils.getMonth(newDate) + "月助管补助申请表";
		String[] headers = new String[] { "序号", "学号", "姓名", "设岗学院/部门名称", "银行",
				"银行卡号", "申请年月", "金额", "备注", "补助类型", "状态" };

		List<BuZhuDTO> buzhus = managerGetExportInfo1(helpDate, type, deptId,
				statusCode);

		// 输出文档路径及名称

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			/*
			 * OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
			 * "UTF-8");
			 */
			exportExcelToManager(title, headers, ouputStream, buzhus);
			// 这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
			// out = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(outFile)));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2016-12-25 根据参数查询数据(用于给POI导出使用)
	 * 
	 * @param helpDate
	 * @param type
	 * @param deptId
	 * @param statusCode
	 * @return
	 */
	private List<BuZhuDTO> managerGetExportInfo1(String helpDate, Byte type,
			String deptId, Byte statusCode) {

		// 获取相关数据

		// 获取年月（多选数组）
		String[] dates = null;
		if (!StringUtils.isEmpty(helpDate)) {
			dates = helpDate.split(",");
		}
		// 获取deptName
		String[] depts = null;
		if (!StringUtils.isEmpty(deptId)) {
			depts = deptId.split(",");
		}

		// 添加参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deptId", depts);
		params.put("helpDate", dates);
		params.put("statusCode", statusCode);
		params.put("type", type);
		// 由于按照数据库的数据导出，不合并同一个学生的不同申请
		// Calendar c = Calendar.getInstance();
		// c.add(Calendar.MONTH, 0);
		// c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		// params.put("submitTime", c.getTime());
		List<BuZhuDTO> buzhus = buzhuMapper
				.selectBuZhuAndStudentInfoWithSelectionsByMap(params);
		return buzhus;
	}

	/**
	 * 管理员获取所有补助信息，此时需要将deptId多选
	 * 
	 * @param helpDate
	 * @param deptId
	 * @param statusCode
	 * @return
	 */
	private Map<String, Object> managerGetExportInfo(String helpDate,
			String deptId, Byte statusCode) {
		String newDate = DateUtils.date2String(new Date());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*
		 * dataMap.put("year", DateUtils.getYear(newDate)); dataMap.put("month",
		 * DateUtils.getMonth(newDate));
		 */
		String title = DateUtils.getYear(newDate) + "年"
				+ DateUtils.getMonth(newDate) + "月助管补助申请表";
		dataMap.put("title", title);
		// 获取相关数据

		// 获取年月（多选数组）
		String[] dates = null;
		if (!StringUtils.isEmpty(helpDate)) {
			dates = helpDate.split(",");
		}
		// 获取deptName
		String[] depts = null;
		if (!StringUtils.isEmpty(deptId)) {
			depts = deptId.split(",");
		}

		// 添加参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deptId", depts);
		params.put("helpDate", dates);
		params.put("statusCode", statusCode);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		params.put("submitTime", c.getTime());
		List<BuZhuDTO> buzhus = buzhuMapper
				.selectBuZhuAndStudentInfoWithSelectionsByMap(params);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int index = 1;
		for (int i = 0; i < buzhus.size(); i++) {
			String stuId = buzhus.get(i).getStuId();
			double money = buzhus.get(i).getMoney();
			String beizhu = buzhus.get(i).getRemark();
			if (beizhu == null)
				beizhu = "";

			boolean exists = false;
			for (int j = 0; j < list.size(); j++) {
				Map temp = (Map) list.get(j);
				if (temp.get("stuId").equals(stuId)) {
					exists = true;
					money = money + (Double) temp.get("money");
					if (beizhu != null && !"".equals(beizhu)) {
						beizhu = beizhu + " " + temp.get("beizhu");
						temp.put("beizhu", beizhu);
					}
					temp.put("moeny", money);

					break;
				}
			}
			if (!exists) {
				HashMap map = new HashMap();
				map.put("id", index++);
				map.put("stuId", stuId);
				map.put("name", buzhus.get(i).getStudentName());
				map.put("bankNo", buzhus.get(i).getBankNo());
				map.put("money", money);
				map.put("beizhu", beizhu);
				list.add(map);
			}

		}
		dataMap.put("lists", list);

		dataMap.put("size", list.size() + 10);
		return dataMap;
	}

	/**
	 * 将已审核的助管补助申请信息导出为excel
	 * 
	 * @param request
	 * @param response
	 * @param buzhu
	 * @throws JRException
	 * @throws IOException
	 */
	/*
	 * @RequestMapping(value = "/zgExceldownload", method = RequestMethod.GET)
	 * public void zgExceldownload(HttpServletRequest request,
	 * HttpServletResponse response,
	 * 
	 * @RequestParam(required = false) String helpDate,
	 * 
	 * @RequestParam(required = false) String deptId,
	 * 
	 * @RequestParam(required = false) Byte statusCode) throws IOException { if
	 * (helpDate == null ||
	 * com.alibaba.druid.util.StringUtils.isEmpty(helpDate)) { helpDate =
	 * DateUtils.date2String(new Date()); } // 获取相关数据 String header =
	 * DateUtils.getYear(helpDate) + "年" + DateUtils.getMonth(helpDate) +
	 * "月助管补助申请表"; // 添加参数 Buzhu buzhu = new Buzhu();
	 * buzhu.setHelpDate(helpDate); buzhu.setDeptId(deptId);
	 * buzhu.setStatusCode(statusCode); List<BuZhuDTO> buzhus = buzhuMapper
	 * .selectBuZhuAndStudentInfoWithSelections(buzhu); List list = new
	 * ArrayList(); for (int i = 0; i < buzhus.size(); i++) {
	 * 
	 * HashMap map = new HashMap(); map.put("id", i + 1); String temp = null;
	 * 
	 * map.put("stuId", ((temp = buzhus.get(i).getStuId()) == null) ? "" :
	 * temp); map.put("name", ((temp = buzhus.get(i).getStudentName()) == null)
	 * ? "" : temp); map.put("bankName", ((temp = buzhus.get(i).getBankName())
	 * == null) ? "" : temp); map.put("bankNo", ((temp =
	 * buzhus.get(i).getBankNo()) == null) ? "" : temp); map.put("money", ((temp
	 * = buzhus.get(i).getMoney().toString()) == null) ? "" : temp);
	 * list.add(map);
	 * 
	 * }
	 * 
	 * response.setContentType("application/vnd.ms-excel;charset=utf-8"); String
	 * defaultFilename = "download"; String defaultname = null; if
	 * (defaultFilename.trim() != null && defaultFilename != null) { defaultname
	 * = defaultFilename + ".xls"; } else { defaultname = "export.xls"; } String
	 * fileName = new String(defaultname.getBytes("GBK"), "utf-8");
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * fileName);
	 * 
	 * Writer out = null; // FileOutputStream fos = null;
	 * 
	 * ServletOutputStream ouputStream = response.getOutputStream();
	 * 
	 * try { // fos = new FileOutputStream(outFile); OutputStreamWriter oWriter
	 * = new OutputStreamWriter(ouputStream, "UTF-8"); // 这个地方对流的编码不可或缺
	 * ，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误
	 * 。主要是编码格式不正确，无法解析。 // out = new BufferedWriter(new OutputStreamWriter(new
	 * // FileOutputStream(outFile))); out = new BufferedWriter(oWriter); }
	 * catch (Exception e1) { e1.printStackTrace(); }
	 * 
	 * try { exportExcel(header, new String[] { "学号", "姓名", "银行名称", "银行卡号",
	 * "补助金额" }, ouputStream, list); // out.close(); ouputStream.flush();
	 * ouputStream.close(); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcelToManager(String header, String[] headers,
			OutputStream out, List items) {
		// 声明一个工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = hssfWorkbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(0, BASIC_LENGTH - 4 * 256);
		sheet.setColumnWidth(1, 2 * BASIC_LENGTH);
		sheet.setColumnWidth(2, BASIC_LENGTH);
		sheet.setColumnWidth(3, 3 * BASIC_LENGTH);
		sheet.setColumnWidth(4, BASIC_LENGTH);
		sheet.setColumnWidth(5, 3 * BASIC_LENGTH);
		sheet.setColumnWidth(6, BASIC_LENGTH);
		sheet.setColumnWidth(7, BASIC_LENGTH - 2 * 256);
		sheet.setColumnWidth(8, BASIC_LENGTH);
		sheet.setColumnWidth(9, BASIC_LENGTH);
		sheet.setColumnWidth(10, 2 * BASIC_LENGTH);

		// 设置表格默认列宽度
		// sheet.setDefaultColumnWidth(15);

		int index = 0;
		HSSFCellStyle titleStyle = hssfWorkbook.createCellStyle();
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 自动换行
		titleStyle.setWrapText(true);
		// 设置居中
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 生成字体
		HSSFFont titleFont = hssfWorkbook.createFont();

		titleFont.setFontName("宋体");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short) 20);
		// 把字体应用到当前的样式
		titleStyle.setFont(titleFont);
		// 合并第一行单元格并赋值title
		HSSFRow row = sheet.createRow(index++);
		row.setHeight((short) (25 * 20));
		HSSFCell titleCell = row.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(header);
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headers.length - 1);
		sheet.addMergedRegion(cra);

		// 生成一个样式
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 自动换行
		style.setWrapText(true);
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 生成字体
		HSSFFont font = hssfWorkbook.createFont();
		/*
		 * font.setColor(HSSFColor.BLACK.index);
		 * font.setFontHeightInPoints((short) 12);
		 * font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 */
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 20);
		// 把字体应用到当前的样式
		style.setFont(font);

		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 11);
		// 产生表格标题行
		row = sheet.createRow(index++);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 生成字体
		/*
		 * font = hssfWorkbook.createFont();
		 * font.setColor(HSSFColor.BLACK.index);
		 * font.setFontHeightInPoints((short) 12);
		 * font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		 */
		// 把字体应用到当前的样式
		style.setFont(font);
		int i = 1;
		for (Object item1 : items) {
			BuZhuDTO item = (BuZhuDTO) item1;
			item.setId(i++);
			row = sheet.createRow(index++);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style);

			cell.setCellValue(item.getId() + "");
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(item.getStuId());
			cell = row.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue(item.getStudentName());
			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue(item.getDeptName());
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(item.getBankName());
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue(item.getBankNo());
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue(item.getHelpDate());
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue(item.getMoney());
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue(item.getRemark());
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue(item.getType() == 1 ? "补助发放" : "");
			cell = row.createCell(10);
			cell.setCellStyle(style);
			cell.setCellValue(item.getStatusCodeName());
		}
		try {
			hssfWorkbook.write(out);
		} catch (IOException e) {
			// logger.error(e);
		}
	}

	/**
	 * 创建excel表格
	 * 
	 * @param headers
	 * @param out
	 * @param items
	 */
	public static void exportExcel(String header, String[] headers,
			OutputStream out, List items) {
		// 声明一个工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = hssfWorkbook.createSheet();
		// 设置表格默认列宽度
		sheet.setDefaultColumnWidth(15);

		int index = 0;
		// 生成一个样式
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 自动换行
		style.setWrapText(true);
		// 生成字体
		HSSFFont font = hssfWorkbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(index++);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 生成字体
		font = hssfWorkbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);
		for (Object item1 : items) {
			HashMap item = (HashMap) item1;
			row = sheet.createRow(index++);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style);
			HSSFRichTextString richString = new HSSFRichTextString(item.get(
					"stuId").toString());
			cell.setCellValue(richString);
			cell = row.createCell(1);
			cell.setCellStyle(style);
			richString = new HSSFRichTextString(item.get("name").toString());
			cell.setCellValue(richString);
			cell = row.createCell(2);
			cell.setCellStyle(style);
			richString = new HSSFRichTextString(item.get("bankName").toString());
			cell.setCellValue(richString);
			cell = row.createCell(3);
			cell.setCellStyle(style);
			richString = new HSSFRichTextString(item.get("bankNo").toString());
			cell.setCellValue(richString);
			cell = row.createCell(4);
			cell.setCellStyle(style);
			richString = new HSSFRichTextString(item.get("money").toString());
			cell.setCellValue(richString);
			/*
			 * cell = row.createCell(5); richString = new
			 * HSSFRichTextString(item.get("phone").toString());
			 * cell.setCellValue(richString);
			 */
		}
		try {
			hssfWorkbook.write(out);
		} catch (IOException e) {
			// logger.error(e);
		}
	}
}
