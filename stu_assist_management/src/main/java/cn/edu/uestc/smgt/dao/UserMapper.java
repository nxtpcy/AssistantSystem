package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	int login(User user);

	User selectByLogin(User user);

	int updatePwdByLoginName(User user);

	/**
	 * @author ljd
	 */
	User selectByLoginName(String loginName);

	/**
	 * @author ljd
	 */
	int deleteByLoginName(String loginName);

	/**
	 * 获取参数条件的查询总记录条数
	 * @author ljd
	 */
	Long size(Map<String, Object> parameters);

	/**
	 * @author ljd
	 */
	List<User> selectByPageAndSelections(QueryBase querybase);
	
	 String findRoles(String username);
	 List<String> findPermissions(List<String> resourceIds);
	 String findResources(String roleName);
}