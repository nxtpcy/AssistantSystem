package cn.edu.uestc.smgt.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition;
import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition4EasyUI;
import org.springframework.util.StringUtils;

public class ConvertUtil{
	private ConvertUtil(){

	}
	public static BuZhuCondition4EasyUI convert(Map<String,Object> paras) throws Exception{
		BuZhuCondition4EasyUI buZhuCondition4EasyUI = new BuZhuCondition4EasyUI();
		BeanUtils.copyProperties(buZhuCondition4EasyUI,paras);
		String strStatusCode = String.valueOf(paras.get("statusCode"));
		if(StringUtils.isEmpty(strStatusCode)){
			buZhuCondition4EasyUI.setStatusCode(null);
		}
		if(StringUtils.isEmpty(String.valueOf(paras.get("type")))){
			buZhuCondition4EasyUI.setType(null);
		}
		return buZhuCondition4EasyUI;
	}

	public static Object convert(Map<String,Object> paras,Class clz) throws Exception{
		Object obj = clz.newInstance();
		BeanUtils.copyProperties(obj, paras);
		return obj;
	}
	public static BuZhuCondition convertBuzhuCondition4EasyUI(BuZhuCondition4EasyUI buZhuCondition4EasyUI )throws Exception{
		BuZhuCondition buZhuCondition = new BuZhuCondition();
		buZhuCondition.setCurrentPage(buZhuCondition4EasyUI.getPage());
		buZhuCondition.setDeptId(buZhuCondition4EasyUI.getDeptId());
		buZhuCondition.setHelpDate(buZhuCondition4EasyUI.getHelpDate());
		buZhuCondition.setStatusCode(buZhuCondition4EasyUI.getStatusCode());
		buZhuCondition.setStuNo(buZhuCondition4EasyUI.getStuNo());
		buZhuCondition.setType(buZhuCondition4EasyUI.getType());
		Long page = buZhuCondition4EasyUI.getPage();
		if(page > 0){
			page--;  //为了和原系统的currentPage保持一致，原来第一页默认是0，现在easyUI是1
			buZhuCondition.setCurrentPage(page);
		}
		buZhuCondition.setPageSize(buZhuCondition4EasyUI.getRows());
		return buZhuCondition;
	}
	public static void main(String []args){
		Map<String,Object> paras = new HashMap<String,Object>();
		paras.put("page", 0L);
		paras.put("row", 10L);
		paras.put("deptId", "001");
		paras.put("helpDate", "201610");
		paras.put("statusCode", 1);
		paras.put("type", 1);
		paras.put("stuNo", "2015");
		try {
			BuZhuCondition4EasyUI obj =  (BuZhuCondition4EasyUI)ConvertUtil.convert(paras, BuZhuCondition4EasyUI.class);
			BuZhuCondition obj1 = ConvertUtil.convertBuzhuCondition4EasyUI(obj);
			System.out.println(obj1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
