package cn.edu.uestc.smgt.dao;

import cn.edu.uestc.smgt.pojo.JinE;

public interface JinEMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JinE record);

    int insertSelective(JinE record);

    JinE selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JinE record);

    int updateByPrimaryKey(JinE record);
    
    JinE select();
}