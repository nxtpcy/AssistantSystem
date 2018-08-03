package cn.edu.uestc.smgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.JinEMapper;
import cn.edu.uestc.smgt.pojo.JinE;
import cn.edu.uestc.smgt.service.JinEService;

@Service
public class JinEServiceImpl implements JinEService {
	@Autowired
	private JinEMapper jinEMapper;

	/* (non-Javadoc)
	 * @see cn.edu.uestc.smgt.service.impl.JinEService#add(java.lang.Integer)
	 */
	public int add(Integer je) {
		if(je < 0){
			return StatusType.MONEY_INVALID.getVal();
		}
		// 查询是否已经存在金额
		JinE jinE = jinEMapper.select();
		if (jinE != null) {
			// update;
			jinE.setJe(je);
			jinEMapper.updateByPrimaryKey(jinE);
		} else {
			jinE = new JinE();
			jinE.setJe(je);
			jinEMapper.insert(jinE);
		}
		return StatusType.SUCCESS.getVal();
	}
	
	public JinE getJE(){
		return jinEMapper.select();
	}
}
