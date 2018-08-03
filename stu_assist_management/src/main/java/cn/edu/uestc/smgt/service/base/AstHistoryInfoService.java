package cn.edu.uestc.smgt.service.base;

import cn.edu.uestc.smgt.common.QueryBase;

public interface AstHistoryInfoService {
	/**
	 * 根据条件分页批量查询
	 * 
	 * @param queryBase
	 */
	void query(QueryBase queryBase);
}
