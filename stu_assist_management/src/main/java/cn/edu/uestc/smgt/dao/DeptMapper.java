package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.Dept;

public interface DeptMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Dept record);

	int insertSelective(Dept record);

	Dept selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Dept record);

	int updateByPrimaryKey(Dept record);

	Dept selectByDeptId(String deptId);

	List<Dept> selectSelective(Dept dept);

	int deleteByDeptId(String deptId);

	int updateByDeptIdSelective(Dept record);

	List selectByPageAndSelections(QueryBase queryBase);

	Long size(Map<String, Object> parameters);
}