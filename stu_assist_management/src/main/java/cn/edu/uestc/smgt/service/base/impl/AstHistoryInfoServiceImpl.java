package cn.edu.uestc.smgt.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.dao.AstHistoryInfoMapper;
import cn.edu.uestc.smgt.pojo.AstHistoryInfo;
import cn.edu.uestc.smgt.service.base.AstHistoryInfoService;

@Service("astHistoryInfoService")
public class AstHistoryInfoServiceImpl implements AstHistoryInfoService {

	@Autowired
	private AstHistoryInfoMapper astHistoryInfoMapper;

	public void query(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setTotalRow(astHistoryInfoMapper.size(queryBase
				.getParameters()));// 获取总行数
		List<AstHistoryInfo> list = astHistoryInfoMapper
				.selectByPageAndSelections(queryBase);
		queryBase.setResults(list);// 获取需要返回的数据集
	}

}
