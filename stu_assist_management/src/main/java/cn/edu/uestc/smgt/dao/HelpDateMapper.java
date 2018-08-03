package cn.edu.uestc.smgt.dao;

import java.util.Date;
import java.util.List;

import cn.edu.uestc.smgt.pojo.HelpDate;

public interface HelpDateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HelpDate record);

    int insertSelective(HelpDate record);

    HelpDate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HelpDate record);

    int updateByPrimaryKey(HelpDate record);
    
    List<HelpDate> getAll();
    
    int exist(HelpDate helpDate);

    HelpDate getDeadlineTimeByYearMonth(HelpDate helpDate);
}