package cn.edu.uestc.smgt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.dao.HelpLogMapper;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.HelpLog;
import cn.edu.uestc.smgt.pojo.dto.HelpLogDTO;
import cn.edu.uestc.smgt.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private HelpLogMapper helpLogMapper;

	@Autowired
	private DeptMapper deptMapper;

	public void query(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setTotalRow(helpLogMapper.size(queryBase.getParameters()));// 获取总行数
		List<?> list = helpLogMapper.selectByPageAndSelections(queryBase);
		List<HelpLogDTO> outList = new ArrayList<HelpLogDTO>();
		Dept dept = null;
		HelpLog temp = null;
		HelpLogDTO dto = null;
		for (Object object : list) {
			temp = (HelpLog) object;
			dto = new HelpLogDTO(temp);
			if (temp.getUserDept() != null) {
				dept = deptMapper.selectByDeptId(temp.getUserDept());
				if (dept != null)
					dto.setDeptName(dept.getDeptName());
			}
			outList.add(dto);
		}
		queryBase.setResults(outList);// 获取需要返回的数据集
	}
}
