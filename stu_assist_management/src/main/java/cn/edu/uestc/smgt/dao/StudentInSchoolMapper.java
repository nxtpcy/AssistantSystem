package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.StudentInSchool;

public interface StudentInSchoolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(StudentInSchool record);

	int insertSelective(StudentInSchool record);

	StudentInSchool selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(StudentInSchool record);

	int updateByPrimaryKey(StudentInSchool record);

	// 自定义dao方法开始

	Long size(Map parameters);

	List selectByPageAndSelections(QueryBase queryBase);

	StudentInSchool selectByStuId(String stuId);
}