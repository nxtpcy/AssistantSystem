package cn.edu.uestc.smgt.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.service.base.DeptService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper deptMapper;

	public int add(Dept dept) {
		if (dept == null || dept.getDeptId() == null
				|| dept.getDeptName() == null) {
			return StatusType.OBJECT_NULL.getVal();
		}
		// check if dept alreay has
		Dept ans = deptMapper.selectByDeptId(dept.getDeptId());
		if (ans != null) {
			return StatusType.EXISTS.getVal();
		}
		int rows = deptMapper.insertSelective(dept);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	public int delete(String deptId) {
		if (deptId == null || deptMapper.selectByDeptId(deptId) == null) {
			return StatusType.NOT_EXISTS.getVal();
		}
		int rows = deptMapper.deleteByDeptId(deptId);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	public int update(Dept dept) {
		if (dept.getDeptId() == null
				|| deptMapper.selectByDeptId(dept.getDeptId()) == null) {
			return StatusType.NOT_EXISTS.getVal();
		}
		int rows = deptMapper.updateByDeptIdSelective(dept);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	/**
	 * 根据单位id和单位名称分页批量查询
	 * 
	 * @param queryBase
	 */
	public void query(QueryBase queryBase) {
		queryBase.setTotalRow(deptMapper.size(queryBase.getParameters()));// 获取总行数
		queryBase.setResults(deptMapper.selectByPageAndSelections(queryBase));// 获取需要返回的数据集
	}
}
