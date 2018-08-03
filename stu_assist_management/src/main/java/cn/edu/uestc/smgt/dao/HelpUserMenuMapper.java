package cn.edu.uestc.smgt.dao;

import java.util.List;

import cn.edu.uestc.smgt.pojo.HelpUserMenu;

public interface HelpUserMenuMapper {


    int insert(HelpUserMenu record);

    int insertSelective(HelpUserMenu record);


    List<HelpUserMenu> selectByUserId(Integer userId);
    
    int deleteByUserId(Integer userId);
}