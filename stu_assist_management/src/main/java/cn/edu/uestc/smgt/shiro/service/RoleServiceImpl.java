package cn.edu.uestc.smgt.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.shiro.dao.RoleDao;
import cn.edu.uestc.smgt.shiro.entity.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;

    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public Role updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    public void deleteRole(Integer roleId) {
        roleDao.deleteRole(roleId);
    }

    public Role findOne(Integer roleId) {
        return roleDao.findOne(roleId);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Set<String> findRoles(Integer... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(Integer roleId : roleIds) {
            Role role = findOne(roleId);
            if(role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    public Set<String> findPermissions(Integer[] roleIds) {
        Set<Integer> resourceIds = new HashSet<Integer>();
        for(Integer roleId : roleIds) {
            Role role = findOne(roleId);
            if(role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
