package cn.edu.uestc.smgt.freemarker;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.pojo.Buzhu;

public class Main {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("year", "2016");
		dataMap.put("month", "10");

		List persons = new ArrayList();

		for (int i = 0; i < 100; i++) {
			HashMap map = new HashMap();
			map.put("id", i + 1);
			map.put("stuId", "201521060213");
			map.put("name", "����");
			map.put("bankName", "中国建设银行");
			map.put("bankNo", "1234567895555555555");
			map.put("money", "400");
			persons.add(map);
		}
		dataMap.put("buzhus", persons);
		new DocumentHandler().createDoc(dataMap, "D:\\test.doc");
	}

}