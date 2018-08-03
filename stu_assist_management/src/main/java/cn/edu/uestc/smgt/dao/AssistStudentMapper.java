package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.AssistStudent;

public interface AssistStudentMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AssistStudent record);

	int insertSelective(AssistStudent record);

	AssistStudent selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AssistStudent record);

	int updateByPrimaryKey(AssistStudent record);

	AssistStudent selectByStuId(String stuId);

	int updateByStuIdSelective(AssistStudent record);

	int deleteByStuId(String stuId);

	Long size(Map parameters);

	List selectByPageAndSelections(QueryBase queryBase);
}