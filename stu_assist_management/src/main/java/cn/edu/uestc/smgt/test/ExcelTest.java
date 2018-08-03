package cn.edu.uestc.smgt.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.record.FooterRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ResourceUtils;

public class ExcelTest {
	public static void main(String[] args) throws IOException {
		ExcelTest test = new ExcelTest();
		File outFile = new File("d:/out.xls");
		test.createWorkbook(new FileOutputStream(outFile));
	}

	private void createWorkbook(OutputStream os) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// 设置excel的全局设定
		commonSet(wb, sheet);

		int index = 0;// 行号
		// 第一行，title，合并单元格，
		HSSFRow row = sheet.createRow(index++);
		HSSFCell titleCell = row.createCell(0);
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 5);
		sheet.addMergedRegion(cra);
		// 设置该行内容
		createTitle(wb, sheet, row, titleCell, "研究生助管12月考核表 ");

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
		HSSFCellStyle style = createCommonStyle(wb, sheet, false, false);
		writeToCell(wb, sheet, row, cell, "     单位：计算机科学与工程学院", style);
		// 将第二行第四单元格到第六单元格合并
		CellRangeAddress cra_2 = new CellRangeAddress(1, 1, 3, 5);
		sheet.addMergedRegion(cra_2);
		cell = row.createCell(3);
		// 构造第二行第四个单元格的样式
		style = createCommonStyle(wb, sheet, true, false);
		writeToCell(wb, sheet, row, cell, "考核月份：2010年12月", style);

		// --------------------------------------------------------------------
		// 构造以后的样式--居中，有边框
		style = createCommonStyle(wb, sheet, true, true);
		// 写入头
		row = sheet.createRow(index++);
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell, "姓名", style);
		cell = row.createCell(1);
		writeToCell(wb, sheet, row, cell, "学号", style);
		cell = row.createCell(2);
		writeToCell(wb, sheet, row, cell, "银行卡号", style);
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "金额", style);
		cell = row.createCell(4);
		writeToCell(wb, sheet, row, cell, "考核老师", style);
		cell = row.createCell(5);
		writeToCell(wb, sheet, row, cell, "备注", style);

		// --------------------------------------------------------------------

		// --------------------------------------------------------------------
		// 合计金额
		row = sheet.createRow(index);
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell, "合计金额：", style);
		cell = row.createCell(1);
		writeToCell(wb, sheet, row, cell, "", style);
		// 合并单元格
		CellRangeAddress cra_3 = new CellRangeAddress(index, index, 0, 1);
		sheet.addMergedRegion(cra_3);
		cell = row.createCell(2);
		writeToCell(wb, sheet, row, cell, "", style);
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "", style);
		cell = row.createCell(4);
		writeToCell(wb, sheet, row, cell, "", style);
		cell = row.createCell(5);
		writeToCell(wb, sheet, row, cell, "", style);
		CellRangeAddress cra_4 = new CellRangeAddress(index, index, 2, 5);
		sheet.addMergedRegion(cra_4);

		// -------------------------------------------------------------------
		index++;
		// 注：
		style = createCommonStyle(wb, sheet, false, false);
		row = sheet.createRow(index);
		row.setHeight((short) (35.4 * 20));
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell,
				"注：1、此表请每月25日前报研究生管理处，以便核发补助，过时不予补发。\n"
						+ "2、补助可以根据指导老师考核情况调整，但不能超过规定的上限。", style);
		CellRangeAddress cra_5 = new CellRangeAddress(index, index, 0, 5);
		sheet.addMergedRegion(cra_5);

		// --------------------------------------------------------------------
		index += 2;
		style = createCommonStyle(wb, sheet, true, false);
		style.getFont(wb).setBold(true);
		row = sheet.createRow(index);
		row.setHeight((short) (16 * 20));
		cell = row.createCell(0);
		writeToCell(wb, sheet, row, cell, "单位审批人：", style);
		CellRangeAddress cra_6 = new CellRangeAddress(index, index, 0, 1);
		sheet.addMergedRegion(cra_6);
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "审批时间：", style);
		CellRangeAddress cra_7 = new CellRangeAddress(index, index, 3, 4);
		sheet.addMergedRegion(cra_7);
		index++;
		row = sheet.createRow(index);
		row.setHeight((short) (16 * 20));
		cell = row.createCell(3);
		writeToCell(wb, sheet, row, cell, "单位盖章：", style);
		CellRangeAddress cra_8 = new CellRangeAddress(index, index, 3, 4);
		sheet.addMergedRegion(cra_8);
		// 将内容写出到输出流
		wb.write(os);
	}

	/**
	 * 全局设定
	 */
	private void commonSet(HSSFWorkbook wb, HSSFSheet sheet) {
		// 设置页脚
		HSSFFooter footer = sheet.getFooter();
		footer.setCenter(HSSFFooter.page());
		// 设置列宽
		sheet.setColumnWidth(0, 8 * 256);// 姓名
		sheet.setColumnWidth(1, 18 * 256);// 学号
		sheet.setColumnWidth(2, 23 * 256);// 银行卡号
		sheet.setColumnWidth(0, 8 * 256);// 金额
		sheet.setColumnWidth(0, 10 * 256);// 考核老师
		sheet.setColumnWidth(0, 11 * 256);// 备注

	}

	/**
	 * 设置title
	 * 
	 * @param wb
	 * @param rowId
	 *            =0
	 * @param cellId
	 *            =0
	 */
	private void createTitle(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row,
			HSSFCell cell, String value) {
		/**
		 * 设置字体和单元格样式
		 */
		HSSFCellStyle style = wb.createCellStyle();
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		style.setWrapText(true);// 设置自动换行
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 18);
		font.setBold(true);
		style.setFont(font);
		// 设置行高
		row.setHeight((short) (35.4 * 20));
		/*
		 * cell.setCellStyle(style); cell.setCellValue(value);
		 */
		writeToCell(wb, sheet, row, cell, value, style);
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
		cell.setCellValue(value);
	}

	/**
	 * 返回样式
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
		if (alignCenter)
			// 设置居中
			style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		style.setWrapText(true);// 设置自动换行
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);
		font.setBold(false);
		style.setFont(font);
		if (border) {
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		return style;
	}
}
