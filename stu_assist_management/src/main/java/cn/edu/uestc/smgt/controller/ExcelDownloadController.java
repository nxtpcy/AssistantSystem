package cn.edu.uestc.smgt.controller;

import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.microsoft.schemas.office.visio.x2012.main.CellType;

import cn.edu.uestc.smgt.dao.BuzhuMapper;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.pojo.dto.BuZhuDTO;
import cn.edu.uestc.smgt.utils.DateUtils;

@Controller
@RequestMapping("/xls/")
public class ExcelDownloadController {
	public static int BASIC_LENGTH = 9 * 256;
	private static final String[] HeaderManager = new String[] { "序号", "学号",
			"姓名", "设岗学院/部门", "银行", "银行卡号", "申请年月", "金额", "备注", "补助类型", "审核状态" };
	private static final String[] HeaderTeacher = new String[] { "姓名", "学号",
			"银行卡号", "金额", "补助类型", "考核老师", "备注" };;
	@Autowired
	private BuzhuMapper buzhuMapper;
	@Autowired
	private DeptMapper deptMapper;

	/**
	 * 将已审核的助管补助申请信息导出为excel,使用POI
	 * 
	 * @param request
	 * @param response
	 * @param buzhu
	 * @throws JRException
	 * @throws IOException
	 */
	@RequiresRoles("0")
	@RequestMapping(value = "zg_xls_download_manager", method = RequestMethod.GET)
	public void zgExceldownloadForManager(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) Byte type,
			@RequestParam(required = false) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {

		response.setContentType("application/x-msdownload;charset=utf-8");
		String fileName = new String(
				(DateUtils.date2LongStringForFileName(new Date()) + "_助管补助导出.xls")
						.getBytes("utf-8"), "iso-8859-1");
		// String fileName = new String(
		// (DateUtils.date2LongStringForFileName(new Date()) + "_助管补助导出.xls")
		// .getBytes("iso-8859-1"), "utf-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);

		// 使用POI导出excel
		String newDate = DateUtils.date2String(new Date());
		String title = DateUtils.getYear(newDate) + "年"
				+ DateUtils.getMonth(newDate) + "月助管补助申请表";
		// String[] headers = new String[] { "序号", "学号", "姓名", "设岗学院/部门编号",
		// "设岗学院/部门名称", "银行", "银行卡号", "申请年月", "金额", "备注", "补助类型", "状态" };

		List<BuZhuDTO> buzhus = getExportInfo(helpDate, type, deptId,
				statusCode);

		// 输出文档路径及名称

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			/*
			 * OutputStreamWriter oWriter = new OutputStreamWriter(ouputStream,
			 * "UTF-8");
			 */
			// exportExcelToManager(title, headers, ouputStream, buzhus);
			exportExcelToManager(title, ouputStream, buzhus);
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
	private List<BuZhuDTO> getExportInfo(String helpDate, Byte type,
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

	public static void exportExcelToManager(String header, OutputStream out,
			List<BuZhuDTO> items) throws IOException {
		String[] headers = HeaderManager;// 拿到表头

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();// 声明一个工作薄

		HSSFSheet sheet = hssfWorkbook.createSheet();// 生成一个表格
		// 设置页脚
		HSSFFooter footer = sheet.getFooter();
		footer.setCenter(HSSFFooter.page());
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
		style.setWrapText(true);// 自动换行
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 生成字体
		HSSFFont font = hssfWorkbook.createFont();
		style.setFont(font);// 把字体应用到当前的样式
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 20);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 设置字体不加粗
		font.setFontHeightInPoints((short) 11);
		// 产生表格标题行
		row = sheet.createRow(index++);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		int i = 1;// 产生正文行id号
		for (BuZhuDTO item : items) {// 遍历正文数据
			item.setId(i++);
			row = sheet.createRow(index++);
			// 序号
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getId() + "");

			// 学号
			cell = row.createCell(1);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getStuId());

			// 姓名
			cell = row.createCell(2);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getStudentName());

			// 设岗学院/部门 (编号+名称)
			cell = row.createCell(3);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getDeptId() + "" + item.getDeptName());

			// 银行名称
			cell = row.createCell(4);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getBankName());

			// 银行卡号
			cell = row.createCell(5);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getBankNo());

			// 申请年月
			cell = row.createCell(6);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getHelpDate());

			// 补助金额
			cell = row.createCell(7);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getMoney());

			// 备注
			cell = row.createCell(8);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getRemark());

			// 补助发放类型：正常，非正常
			cell = row.createCell(9);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getType() == 1 ? "补助发放" : "");

			// 审核状态
			cell = row.createCell(10);
			cell.setCellStyle(style);// 设置样式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格内容为字符串格式
			cell.setCellValue(item.getStatusCodeNameWhenNull());
		}
		hssfWorkbook.write(out);
		hssfWorkbook.close();

	}

	// ---------------------------------------------------------------------

	@RequestMapping(value = "zg_xls_download_teacher", method = RequestMethod.GET)
	public void zgExceldownloadForTeacher(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String helpDate,
			@RequestParam(required = false) Byte type,
			@RequestParam(required = true) String deptId,
			@RequestParam(required = false) Byte statusCode) throws IOException {

		if (deptId == null) {
			throw new NullPointerException("部门编号不能为空");
		}

		response.setContentType("application/x-msdownload;charset=utf-8");
		// String fileName = new String(
		// (DateUtils.date2LongStringForFileName(new Date()) + "_助管补助申请.xls")
		// .getBytes("iso-8859-1"), "utf-8");
		String fileName = new String(
				(DateUtils.date2LongStringForFileName(new Date()) + "_助管补助申请.xls")
						.getBytes("utf-8"), "iso-8859-1");
		// String fileName = new String(
		// (DateUtils.date2LongStringForFileName(new Date()) + "_助管补助申请.xls"));

		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);

		// 使用POI导出excel
		String newDate = DateUtils.date2String(new Date());
		String deptName = deptMapper.selectByDeptId(deptId).getDeptName();
		String date = DateUtils.getYear(newDate) + "年"
				+ DateUtils.getMonth(newDate) + "月";
		String title = "研究生助管" + DateUtils.getMonth(newDate) + "月考核表";
		List<BuZhuDTO> buzhus = getExportInfo(helpDate, type, deptId,
				statusCode);

		// System.out.println(totalMoney);

		// 输出文档路径及名称

		ServletOutputStream ouputStream = response.getOutputStream();
		try {
			exportExcelToTeacher(title, deptName, date, buzhus, ouputStream);
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

	private void exportExcelToTeacher(String title, String deptName,
			String date, List<BuZhuDTO> buzhus, OutputStream ouputStream)
			throws IOException {
		String[] headers = HeaderTeacher;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// 设置页脚
		HSSFFooter footer = sheet.getFooter();
		footer.setCenter(HSSFFooter.page());
		// 设置列宽
		sheet.setColumnWidth(0, 10 * 256);// 姓名
		sheet.setColumnWidth(1, 14 * 256);// 学号
		sheet.setColumnWidth(2, 21 * 256);// 银行卡号
		sheet.setColumnWidth(3, 8 * 256);// 金额
		sheet.setColumnWidth(4, 10 * 256);// 补助类型
		sheet.setColumnWidth(5, 12 * 256);// 考核老师
		sheet.setColumnWidth(6, 12 * 256);// 备注

		// 设置字体和单元格样式
		HSSFCellStyle titleStyle = wb.createCellStyle();
		// 设置居中
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		titleStyle.setWrapText(true);// 设置自动换行
		HSSFFont titleFont = wb.createFont();
		titleStyle.setFont(titleFont);
		titleFont.setFontName("宋体");
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBold(true);

		int index = 0;// 行号
		// 创建title，并合并单元格
		HSSFRow row = sheet.createRow(index++);
		row.setHeight((short) (35.4 * 20));// 设置行高
		HSSFCell titleCell = row.createCell(0);
		writeToCell(wb, sheet, row, titleCell, title, titleStyle);// 将单元格填充
		// 合并单元格
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headers.length - 1);
		sheet.addMergedRegion(cra);

		// --------------------------------------------------------------------
		// 将第二行第一单元格到第三单元格合并

		CellRangeAddress cra_1 = new CellRangeAddress(1, 1, 0, 2);
		sheet.addMergedRegion(cra_1);
		row = sheet.createRow(index++);
		// 设置行高
		row.setHeight((short) (16 * 20));
		// 将数据写到该单元格中
		HSSFCell cell = row.createCell(0);
		// 构造第二行第一个单元格的样式
		HSSFCellStyle style1 = createCommonStyle(wb, sheet, false, false);
		writeToCell(wb, sheet, row, cell, "     单位：" + deptName, style1);
		// 将第二行第四单元格到第六单元格合并
		CellRangeAddress cra_2 = new CellRangeAddress(1, 1, 3, 5);
		sheet.addMergedRegion(cra_2);
		cell = row.createCell(3);
		// 构造第二行第四个单元格的样式
		style1 = createCommonStyle(wb, sheet, true, false);
		writeToCell(wb, sheet, row, cell, "考核月份：" + date, style1);

		// --------------------------------------------------------------------
		// 构造以后的样式--居中，有边框
		style1 = createCommonStyle(wb, sheet, true, true);
		// 写入头
		row = sheet.createRow(index++);
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			writeToCell(wb, sheet, row, cell, headers[i], style1);
		}

		int totalMoney = 0;// 定义合计金额
		// --------------------------------------------------------------------
		for (BuZhuDTO buZhuDTO : buzhus) {
			totalMoney += buZhuDTO.getMoney();// 计算合计金额
			row = sheet.createRow(index++);
			cell = row.createCell(0);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getStudentName(), style1);
			cell = row.createCell(1);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getStuId(), style1);
			cell = row.createCell(2);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getBankNo(), style1);
			cell = row.createCell(3);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getMoney().intValue()
					+ "", style1);
			cell = row.createCell(4);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getType() == 1 ? "补助发放"
					: "正常发放", style1);
			cell = row.createCell(5);
			writeToCell(wb, sheet, row, cell, "", style1);
			cell = row.createCell(6);
			writeToCell(wb, sheet, row, cell, buZhuDTO.getRemark(), style1);
		}

		// --------------------------------------------------------------------
		// 合计金额
		row = sheet.createRow(index);
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell, "合计金额：", style1);
		cell = row.createCell(1);
		writeToCell(wb, sheet, row, cell, "", style1);
		// 合并单元格
		CellRangeAddress cra_3 = new CellRangeAddress(index, index, 0, 1);
		sheet.addMergedRegion(cra_3);
		cell = row.createCell(2);
		writeToCell(wb, sheet, row, cell, totalMoney + "", style1);
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "", style1);
		cell = row.createCell(4);
		writeToCell(wb, sheet, row, cell, "", style1);
		cell = row.createCell(5);
		writeToCell(wb, sheet, row, cell, "", style1);
		cell = row.createCell(6);
		writeToCell(wb, sheet, row, cell, "", style1);
		CellRangeAddress cra_4 = new CellRangeAddress(index, index, 2,
				headers.length - 1);
		sheet.addMergedRegion(cra_4);

		// -------------------------------------------------------------------
		index++;
		// 注：
		style1 = createCommonStyle(wb, sheet, false, false);
		style1.getFont(wb).setFontHeightInPoints((short) 9);
		row = sheet.createRow(index);
		row.setHeight((short) (35.4 * 20));
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell,
				"注：1、此表请每月25日前报研究生管理处，以便核发补助，过时不予补发。\n"
						+ "2、补助可以根据指导老师考核情况调整，但不能超过规定的上限。", style1);
		CellRangeAddress cra_5 = new CellRangeAddress(index, index, 0,
				headers.length - 1);
		sheet.addMergedRegion(cra_5);

		// --------------------------------------------------------------------
		index += 2;
		style1 = createCommonStyle(wb, sheet, true, false);
		style1.getFont(wb).setBold(true);// 等价于font.setBold(true);
		row = sheet.createRow(index);
		row.setHeight((short) (16 * 20));
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell, "单位审批人：", style1);
		CellRangeAddress cra_6 = new CellRangeAddress(index, index, 0, 1);
		sheet.addMergedRegion(cra_6);
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "审批时间：", style1);
		CellRangeAddress cra_7 = new CellRangeAddress(index, index, 3, 4);
		sheet.addMergedRegion(cra_7);
		index++;
		row = sheet.createRow(index);
		row.setHeight((short) (16 * 20));
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "单位盖章：", style1);
		CellRangeAddress cra_8 = new CellRangeAddress(index, index, 3, 4);
		sheet.addMergedRegion(cra_8);
		// 将内容写出到输出流
		wb.write(ouputStream);
		wb.close();
	}

	/**
	 * 将单元格设置成某个值
	 * 
	 * @param wb
	 * @param sheet
	 * @param row
	 * @param cell
	 * @param value
	 * @param style
	 */
	private void writeToCell(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row,
			HSSFCell cell, String value, HSSFCellStyle style) {
		cell.setCellStyle(style);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	/**
	 * 根据原样式进行修改返回样式
	 * 
	 * @param wb
	 * @param sheet
	 * @return
	 */
	private HSSFCellStyle createCommonStyle(HSSFWorkbook wb, HSSFSheet sheet,
			boolean alignCenter, boolean border) {
		/**
		 * 设置字体和单元格样式
		 */
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		style.setFont(font);
		if (alignCenter)
			// 设置居中
			style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		style.setWrapText(true);// 设置自动换行
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);

		if (border) {
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		return style;
	}

	public static void main(String[] args) {

	}
}
