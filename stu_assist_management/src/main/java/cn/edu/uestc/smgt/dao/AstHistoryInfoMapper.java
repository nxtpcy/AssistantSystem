package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.AstHistoryInfo;

public interface AstHistoryInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AstHistoryInfo record);

	int insertSelective(AstHistoryInfo record);

	AstHistoryInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AstHistoryInfo record);

	int updateByPrimaryKey(AstHistoryInfo record);

	/**
	 * 根据学号查询离职信息
	 * 
	 * @param stuId
	 * @return
	 * @author ljd
	 */
	AstHistoryInfo selectByStuId(String stuId);

	/**
	 * 根据查询参数获取总行数
	 * 
	 * @author ljd
	 */
	Long size(Map<String, Object> parameters);

	/**
	 * 根据参数获取查询结果
	 * 
	 * @param queryBase
	 * @return
	 * @author ljd
	 */
	List<AstHistoryInfo> selectByPageAndSelections(QueryBase queryBase);
}