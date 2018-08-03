package cn.edu.uestc.smgt.service;

import cn.edu.uestc.smgt.common.QueryBase;

public interface LogService {
	/**
	 * 根据条件分页批量查询
	 * @param queryBase
	 */
	void query(QueryBase queryBase);
}
