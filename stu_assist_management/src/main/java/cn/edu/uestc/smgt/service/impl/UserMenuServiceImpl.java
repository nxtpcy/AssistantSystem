package cn.edu.uestc.smgt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.HelpFunctionMenuMapper;
import cn.edu.uestc.smgt.dao.HelpUserMenuMapper;
import cn.edu.uestc.smgt.pojo.HelpFunctionMenu;
import cn.edu.uestc.smgt.pojo.HelpUserMenu;
import cn.edu.uestc.smgt.service.UserMenuService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;

@Service("UserMenuService")
public class UserMenuServiceImpl implements UserMenuService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TransactionTemplate txTemplate;
	@Autowired
	HelpUserMenuMapper helpUserMenuMapper;
	@Autowired
	HelpFunctionMenuMapper helpFunctionMenuMapper;
	@CacheEvict(value="menuCache")
	public int updateUserMenu(int userId, String menuIds) {
		int rows = 0;
		int length = 0;
		if(menuIds == null || StringUtils.isEmpty(menuIds)){
			return StatusType.OBJECT_NULL.getVal();
		}
		try{
			final String []ids = menuIds.split(",");
			length = ids.length;
			final int tempUserId = userId;
			rows = txTemplate.execute(new TransactionCallback<Integer>() {
				int totalRows = 0;
				public Integer doInTransaction(TransactionStatus arg0) {
					helpUserMenuMapper.deleteByUserId(tempUserId);
					
					for(String id : ids){
						HelpUserMenu helpUserMenu = new HelpUserMenu();
						helpUserMenu.setMenuId(Integer.parseInt(id));
						helpUserMenu.setUserId(tempUserId);
						totalRows += helpUserMenuMapper.insert(helpUserMenu);
					}
					return totalRows;
				}
			});
		}catch(Throwable e){
			logger.error(
					"调用UserMenuServiceImpl.updateUserMenu出错,rows={},userId={},menuIds={}",
					new Object[] { rows, userId, menuIds},e);
		}
		return DAOResultUtil.getBatchResult(rows, length).getVal();
	}

	@Cacheable(value="menuCache")
	public List<HelpFunctionMenu> getMenus(int userId) {
		logger.info("query from db userid="+userId);
		List<HelpUserMenu> menus = helpUserMenuMapper.selectByUserId(userId);
		Map<Integer,HelpFunctionMenu> localCache = new HashMap<Integer,HelpFunctionMenu>();
	    List<HelpFunctionMenu> roots = new ArrayList<HelpFunctionMenu>();
		for(HelpUserMenu helpUserMenu : menus){
			HelpFunctionMenu leaf = this.getMenuById(helpUserMenu.getMenuId());
			makeChildren(leaf,localCache,roots);
		}
		return roots;
	}
	private boolean exist(List<HelpFunctionMenu> roots,int id){
		for(HelpFunctionMenu root : roots){
			if(id == root.getId())
				return true;
		}
		return false;
	}
	private void makeChildren(HelpFunctionMenu leaf,Map<Integer,HelpFunctionMenu> localCache,List<HelpFunctionMenu> roots){
		if(leaf.getParentId() == null){
			if(!exist(roots,leaf.getId()))
			    roots.add(leaf);
			return;
		}
		HelpFunctionMenu parent = localCache.get(leaf.getParentId());
		if(parent == null){
			parent = new HelpFunctionMenu();
			BeanUtils.copyProperties(this.getMenuById(leaf.getParentId()), parent);
			localCache.put(parent.getId(), parent);
		}
		parent.addChild(leaf);
		makeChildren(parent,localCache,roots);
	}
//	public static void main(String []args) throws ExecutionException{
//		LoadingCache<String,String> cahceBuilder=CacheBuilder
//		        .newBuilder()
//		        .build(new CacheLoader<String, String>(){
//		            @Override
//		            public String load(String key) throws Exception {    
//		            	System.out.println("第一次");
//		                return key;
//		            }
//		            
//		        });     
//		System.out.println("jerry value:"+cahceBuilder.get("jerry"));
//		System.out.println("jerry value:"+cahceBuilder.get("jerry"));
//		Map<Integer,HelpFunctionMenu> localCache = new HashMap<Integer,HelpFunctionMenu>();
//		HelpFunctionMenu parent = new HelpFunctionMenu();
////		localCache.put(1, parent);
////		parent.addChild(new HelpFunctionMenu());
////		System.out.println(localCache.get(1));
//	}
	@Cacheable(value="menuCache",key="#id")
	private HelpFunctionMenu getMenuById(int id){
//		logger.info("query from db by key="+id);
		return helpFunctionMenuMapper.selectByPrimaryKey(id);
	}
	

}
