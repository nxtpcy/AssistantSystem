package cn.edu.uestc.smgt.shiro.service;


import java.util.List;
import java.util.Set;

import cn.edu.uestc.smgt.shiro.entity.Resource;

public interface ResourceService {

    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Integer resourceId);

    Resource findOne(Integer resourceId);
    List<Resource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(Set<Integer> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Resource> findMenus(Set<String> permissions);
}
