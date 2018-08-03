package cn.edu.uestc.smgt.service;

import java.util.List;

import cn.edu.uestc.smgt.pojo.HelpFunctionMenu;

public interface UserMenuService {
     int updateUserMenu(int userId,String menuIds);
     
     List<HelpFunctionMenu> getMenus(int userId);
}
