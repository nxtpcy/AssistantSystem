package cn.edu.uestc.smgt.service.base;

import java.util.List;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.AssistStudent;

public interface AssistStudentService {

	// 增加助管信息
	int add(AssistStudent ast);

	// 批量增加助管信息
	String batchAdd(List<AssistStudent> list);

	// 修改助管信息
	int update(AssistStudent ast);

	// 删除助管信息
	int delete(AssistStudent ast);

	// 查询助管信息
	void query(QueryBase queryBase);

	// 将学生信息批量添加到助管列表中
	String batchAddStudentToAssist(String[] ids);

	// 批量导入excel中的助管信息
	public String batchAddAssistByExcel(List<AssistStudent> list);

	// 在职转离职
	public int currToHist(String stuId);

}