package cn.edu.uestc.smgt.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.dao.StudentInSchoolMapper;
import cn.edu.uestc.smgt.pojo.AssistStudent;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.Office;
import cn.edu.uestc.smgt.pojo.dto.AssistStudentDTO;
import cn.edu.uestc.smgt.service.base.StudentInSchoolService;

@Service("studentInSchoolService")
public class StudentInSchoolServiceImpl implements StudentInSchoolService {

	@Autowired
	private StudentInSchoolMapper studentInSchoolMapper;

	public void query(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setTotalRow(studentInSchoolMapper.size(queryBase
				.getParameters()));// 获取总行数
		List<AssistStudent> list = studentInSchoolMapper
				.selectByPageAndSelections(queryBase);
		queryBase.setResults(list);// 获取需要返回的数据集
	}

}
