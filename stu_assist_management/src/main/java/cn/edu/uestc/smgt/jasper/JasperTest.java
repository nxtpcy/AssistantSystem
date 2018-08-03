/*package cn.edu.uestc.smgt.jasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class JasperTest {
	public static void main(String[] args) {
		String sourceFileName = "C:\\Users\\ljd\\JaspersoftWorkspace\\Tes\\zgkaohe.jasper";
		List beanSource = JavaBeanPerson.getList();
		JRBeanCollectionDataSource fields = new JRBeanCollectionDataSource(
				beanSource);
		Map parameters = new HashMap();
		parameters.put("deptName", "test");
		parameters.put("kaoheDate", "2015年10月");
		
		 * try { JasperFillManager.fillReportToFile(sourceFileName, parameters,
		 * fields); } catch (JRException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 
		try {
			InputStream is = new FileInputStream(sourceFileName);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, fields);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"BasicReport.pdf");
			// Or to view report in the JasperViewer
			JasperViewer.viewReport(jasperPrint);
			
			 * File file = new File("C:\\test.pdf"); if (!file.exists()) {
			 * file.createNewFile(); } OutputStream outputStream = new
			 * FileOutputStream(file);
			 * JasperExportManager.exportReportToPdfStream(jasperPrint,
			 * outputStream);
			 
			is.close();
			// outputStream.flush();
			// outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
*/