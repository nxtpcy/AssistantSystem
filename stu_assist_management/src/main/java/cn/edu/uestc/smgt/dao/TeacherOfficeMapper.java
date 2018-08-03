package cn.edu.uestc.smgt.dao;

import cn.edu.uestc.smgt.pojo.TeacherOffice;

public interface TeacherOfficeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherOffice record);

    int insertSelective(TeacherOffice record);

    TeacherOffice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherOffice record);

    int updateByPrimaryKey(TeacherOffice record);
}