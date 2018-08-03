package cn.edu.uestc.smgt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.Status;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.HelpDateMapper;
import cn.edu.uestc.smgt.pojo.HelpDate;
import cn.edu.uestc.smgt.service.ValidDateService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;
@Service("ValidDateService")
public class ValidDateServiceImpl implements ValidDateService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	HelpDateMapper helpDateMapper;
	public int save(HelpDate date) {
		int rows = -1;
		try{
			if(date == null || date.getHelpYear() == null || date.getHelpMonth() == null)
				return StatusType.OBJECT_NULL.getVal();
			Integer month = date.getHelpMonth();
			Integer year = date.getHelpYear();
			if((month >= 1 &&  month <=12) && (year >= 1900 && year <= 2999)){
				int exist = helpDateMapper.exist(date);
				if(exist > 0){
					return StatusType.EXISTS.getVal();
				}
				rows = helpDateMapper.insertSelective(date);
				return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
			}
			else{
				return StatusType.DATE_ERROR.getVal();
			}
		}catch(Exception e){
			logger.error("调用ValidDateServiceImpl.save出错,传入参数HelpDate={},rows={}",new Object[]{date,rows},e);
			return StatusType.EXCEPTION.getVal();
		}
	}

	public List<HelpDate> getValidDates() {
		List<HelpDate> dates = null;
		try{
			dates = helpDateMapper.getAll();
			if(dates == null){
				dates = new ArrayList<HelpDate>();
			}
			return dates;
		}catch(Exception e){
			logger.error("调用ValidDateServiceImpl.getAllDates出错",e);
		    return new ArrayList<HelpDate>();
		}
	}

	public int remove(int id) {
		int rows = -1;
		try{
			rows = helpDateMapper.deleteByPrimaryKey(id);
			return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
		}catch(Exception e){
			logger.error("调用ValidDateServiceImpl.remove出错,传入参数id={},rows={}",new Object[]{id,rows},e);
			return StatusType.EXCEPTION.getVal();
		}
	}

}
