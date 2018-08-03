package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.HelpLog;

public interface HelpLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HelpLog record);

    int insertSelective(HelpLog record);

    HelpLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HelpLog record);

    int updateByPrimaryKey(HelpLog record);

	Long size(Map<String, Object> parameters);

	List<? extends Object> selectByPageAndSelections(QueryBase queryBase);
    
    
    
    
}