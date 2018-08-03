package cn.edu.uestc.smgt.shiro.dao;


import java.util.List;

import cn.edu.uestc.smgt.shiro.entity.Role;

public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Integer roleId);

    public Role findOne(Integer roleId);
    public List<Role> findAll();
}
