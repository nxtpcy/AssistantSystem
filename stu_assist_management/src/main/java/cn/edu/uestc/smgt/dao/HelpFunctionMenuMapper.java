package cn.edu.uestc.smgt.dao;

import java.util.List;

import cn.edu.uestc.smgt.pojo.HelpFunctionMenu;

public interface HelpFunctionMenuMapper {

    HelpFunctionMenu selectByPrimaryKey(Integer id);
    
    List<HelpFunctionMenu> getChildrenHelpFunctionMenus(List<Integer> ids);
    
    List<HelpFunctionMenu> getParentHelpFunctionMenus(List<Integer> ids);

}