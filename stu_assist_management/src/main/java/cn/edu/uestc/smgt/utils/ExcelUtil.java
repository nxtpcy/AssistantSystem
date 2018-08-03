package cn.edu.uestc.smgt.utils;

/*     */
/*     */import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/*     */
import java.text.DecimalFormat;
/*     */
import java.text.SimpleDateFormat;
/*     */
import java.util.Date;
/*     */
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/*     */
import org.apache.poi.ss.usermodel.Cell;
/*     */
import org.apache.poi.ss.usermodel.DateUtil;
/*     */
import org.apache.poi.ss.usermodel.RichTextString;
/*     */
import org.apache.poi.ss.usermodel.Row;
/*     */
import org.apache.poi.ss.usermodel.Sheet;
/*     */
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.edu.uestc.smgt.pojo.Buzhu;

/*     */
/*     */public class ExcelUtil
/*     */{

	static Logger logger = Logger.getLogger("ExcelUtil");
	/* 20 */private static String DATEFOMAT = "yyyy-MM-dd";

	/*     */
	/*     */public static HashMap<String, Object> getSheet(InputStream is,
			int rowNum)
	/*     */{
		/* 30 */HashMap ret = new HashMap();
		/*     */
		/* 32 */Workbook wb = null;
		/* 33 */Sheet sheet = null;
		/*     */try {
			/* 35 */wb = WorkbookFactory.create(is);
			/*     */} catch (Exception e) {
			/* 37 */ret.put("msg", "涓婁紶鏂囦欢鏍煎紡閿欒锛屽彧鑳芥槸excel 鏂囦欢 .xls鍜�xlsx");
			/* 38 */return ret;
			/*     */}
		/* 40 */sheet = wb.getSheetAt(0);
		/* 41 */int rowSize = sheet.getLastRowNum();
		/* 42 */if (rowSize < rowNum) {
			/* 43 */ret.put("msg", "nijajjfalkjhgkl");
			/* 44 */return ret;
			/*     */}
		/* 46 */ret.put("sheet", sheet);
		/* 47 */return ret;
		/*     */}

	/*     */
	/*     */public static String getCellValue(Sheet sheet, int col, int row)
	/*     */{
		/* 59 */String cellValue = "";
		/* 60 */Row rows = sheet.getRow(row);
		/* 61 */if ((row > sheet.getLastRowNum()) || (rows == null)) {
			/* 62 */return cellValue;
			/*     */}
		/* 64 */Cell cell = rows.getCell(col);
		/* 65 */if (cell == null) {
			/* 66 */return cellValue;
			/*     */}
		/* 68 */if (col == 1) {
			/* 69 */cell.setCellType(1);
			/* 70 */return cell.getStringCellValue();
			/*     */}
		/*     */
		/* 73 */switch (cell.getCellType()) {
		/*     */case 1:
			/* 75 */cellValue = cell.getRichStringCellValue().getString()
					.trim();
			/* 76 */break;
		/*     */case 0:
			/* 78 */if (DateUtil.isCellDateFormatted(cell)) {
				/* 79 */SimpleDateFormat sdf = new SimpleDateFormat(DATEFOMAT);
				/* 80 */Date date = cell.getDateCellValue();
				/* 81 */cellValue = sdf.format(date);
				/*     */} else {
				/* 83 */DecimalFormat df = new DecimalFormat("#.00");
				/* 84 */cellValue = df.format(cell.getNumericCellValue())
						.toString();
				/*     */}
			/*     */
			/* 89 */break;
		/*     */case 4:
			/* 91 */cellValue = String.valueOf(cell.getBooleanCellValue())
					.trim();
			/* 92 */break;
		/*     */case 2:
			/* 94 */cellValue = cell.getCellFormula();
			/* 95 */break;
		/*     */case 3:
			/*     */
		default:
			/* 97 */cellValue = "";
			/*     */}
		/* 99 */return cellValue.trim();
		/*     */}

	/*     */
	/*     */public static boolean isNullOrEmpty(Object cell)
	/*     */{
		/* 108 */if (cell == null) {
			/* 109 */return true;
			/*     */}
		/* 111 */if (cell.toString().equals("")) {
			/* 112 */return true;
			/*     */}
		/* 114 */return false;
		/*     */}

	public static void exportAgentMonthExcel(String[] headers,
			OutputStream out, List<HashMap<Object, Object>> items) {
		// 澹版槑涓�釜宸ヤ綔钖�		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 鐢熸垚涓�釜琛ㄦ牸
		HSSFSheet sheet = hssfWorkbook.createSheet();
		// 璁剧疆琛ㄦ牸榛樿鍒楀搴�		sheet.setDefaultColumnWidth(20);
		// 鐢熸垚涓�釜鏍峰紡
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		// 璁剧疆鏍峰紡
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 鐢熸垚瀛椾綋
		HSSFFont font = hssfWorkbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 鎶婂瓧浣撳簲鐢ㄥ埌褰撳墠鐨勬牱寮�		style.setFont(font);
		// 浜х敓琛ㄦ牸鏍囬琛�		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		int index = 0;
		for (HashMap<Object, Object> item : items) {
			index++;
			row = sheet.createRow(index);
			HSSFCell cell = row.createCell(0);
			HSSFRichTextString richString = new HSSFRichTextString(item.get(
					"recharge").toString());
			cell.setCellValue(richString);
			cell = row.createCell(1);
			richString = new HSSFRichTextString(item.get("rate").toString());
			cell.setCellValue(richString);
			cell = row.createCell(2);
			richString = new HSSFRichTextString(item.get("percentage")
					.toString());
			cell.setCellValue(richString);
			cell = row.createCell(3);
			richString = new HSSFRichTextString(item.get("pid").toString());
			cell.setCellValue(richString);
			cell = row.createCell(4);
			richString = new HSSFRichTextString(item.get("nicheng").toString());
			cell.setCellValue(richString);
			cell = row.createCell(5);
			richString = new HSSFRichTextString(item.get("phone").toString());
			cell.setCellValue(richString);
		}
		try {
			hssfWorkbook.write(out);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	

}
