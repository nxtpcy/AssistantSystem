package cn.edu.uestc.smgt.service.base;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.Dept;

public interface DeptService {

	int add(Dept dept);

	int delete(String deptId);

	int update(Dept dept);

	/**
	 * 根据单位id和单位名称分页批量查询
	 * @param queryBase
	 */
	void query(QueryBase queryBase);

}