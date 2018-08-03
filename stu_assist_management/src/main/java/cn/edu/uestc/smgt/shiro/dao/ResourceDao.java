package cn.edu.uestc.smgt.shiro.dao;


import java.util.List;

import cn.edu.uestc.smgt.shiro.entity.Resource;

public interface ResourceDao {

    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Integer resourceId);

    Resource findOne(Integer resourceId);
    List<Resource> findAll();

}
