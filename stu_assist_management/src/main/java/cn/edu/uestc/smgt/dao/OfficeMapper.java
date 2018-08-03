package cn.edu.uestc.smgt.dao;

import cn.edu.uestc.smgt.pojo.Office;

public interface OfficeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Office record);

    int insertSelective(Office record);

    Office selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Office record);

    int updateByPrimaryKey(Office record);
}