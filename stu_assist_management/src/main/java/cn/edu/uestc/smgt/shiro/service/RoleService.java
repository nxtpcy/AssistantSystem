package cn.edu.uestc.smgt.shiro.service;


import java.util.List;
import java.util.Set;

import cn.edu.uestc.smgt.shiro.entity.Role;

public interface RoleService {


    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Integer roleId);

    public Role findOne(Integer roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Integer... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Integer[] roleIds);
}
