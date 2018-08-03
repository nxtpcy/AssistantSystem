package cn.edu.uestc.smgt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.AssistStudentMapper;
import cn.edu.uestc.smgt.dao.BuzhuMapper;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.dao.HelpDateMapper;
import cn.edu.uestc.smgt.dao.JinEMapper;
import cn.edu.uestc.smgt.dict.BuzhuStatusCodeType;
import cn.edu.uestc.smgt.pojo.Buzhu;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.HelpDate;
import cn.edu.uestc.smgt.pojo.JinE;
import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition;
import cn.edu.uestc.smgt.pojo.dto.BuZhuDTO;
import cn.edu.uestc.smgt.service.BuZhuService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;
import cn.edu.uestc.smgt.utils.DateUtils;

@Service("BuZhuService")
public class BuZhuServiceImpl implements BuZhuService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TransactionTemplate txTemplate;
	@Autowired
	BuzhuMapper buzhuMapper;
	@Autowired
	HelpDateMapper helpDateMapper;
	@Autowired
	JinEMapper jinEMapper;
	@Autowired
	AssistStudentMapper assistStudentMapper;
	@Autowired
	DeptMapper deptMapper;

	/**
	 * 超额补助的增加
	 * @param buzhu 待保存的补助数据
	 * @return
	 */
	public int addBuZhuWithoutCheck(Buzhu buzhu){
		return add(buzhu);
	}

	/**
	 * 检查日期是否在控制日期内
	 * @param helpDate 受检日期
	 * @return
	 */
	private boolean validDate(String helpDate){
		//获取所有的有效月份
				
		List<HelpDate> helpValidDates = helpDateMapper.getAll();
		String year = helpDate.substring(0, 4);
		String month = helpDate.substring(4);
		final int yearInteger = DateUtils.yearString2Int(year);
		final int monthInteger = DateUtils.monthString2Int(month);

		for(HelpDate validDate : helpValidDates){
			if(yearInteger == validDate.getHelpYear() && monthInteger== validDate.getHelpMonth()){
				return true;
			}
		}
		logger.warn("调用BuZhuServiceImpl.validDate警告,不符合有效日期,year={},month={}",new Object[]{yearInteger,monthInteger});
		return false;
	}
	/**
	 * 检查补助金额是否超出控制
	 * @param checkMoney 控制金额
	 * @param helpMoney 受检金额
	 * @return
	 */
	private boolean validMoney(double checkMoney,double helpMoney){
		if(checkMoney < helpMoney){
			logger.warn("调用BuZhuServiceImpl.validMoney警告,不符合有效金额,checkMoney={},helpMoney={}",new Object[]{checkMoney,helpMoney});
			return false;
		}
		return true;
	}

	/**
	 *  正常补助的增加
	 * @param buzhu 待保存的补助数据
	 * @return
	 */
	public int addBuZhuCheck(Buzhu buzhu){
		if(buzhu == null)
			return StatusType.OBJECT_NULL.getVal();
		int statusByMoney = doCheckMoney(buzhu.getMoney());
		int statusByDate = doCheckDate(buzhu.getHelpDate());
		int statusByDeadlineTime = doCheckDeadlineTime(buzhu.getHelpDate());
		if(StatusType.SUCCESS.getVal() != statusByMoney)
			return statusByMoney;
		if(StatusType.SUCCESS.getVal() != statusByDate)
			return statusByDate;
		if(StatusType.SUCCESS.getVal() != statusByDeadlineTime)
			return statusByDeadlineTime;
		return add(buzhu);
	}

	private int doCheckDeadlineTime(String helpDate){
		String year = helpDate.substring(0, 4);
		String month = helpDate.substring(4);
		HelpDate condition = new HelpDate();
		condition.setHelpYear(Integer.valueOf(year));
		condition.setHelpMonth(Integer.valueOf(month));
		Date deadlineTimeDate = helpDateMapper.getDeadlineTimeByYearMonth(condition).getDeadlineTime();
		Date now = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date deadlineTimeDate = simpleDateFormat.parse(deadlineTime);
		if(now.after(deadlineTimeDate)){
            return StatusType.DEADLINE_SUBMIT.getVal();
		}
		return StatusType.SUCCESS.getVal();
	}
	private int doCheckDate(String helpDate){
		if(helpDate == null)
			return StatusType.OBJECT_NULL.getVal();
		//如果日期不是 201609 这种格式
		if(!DateUtils.dateValid(helpDate)){
			return StatusType.DATE_ERROR.getVal(); 
		}

		if(this.validDate(helpDate) == false){
			return StatusType.DATE_INVALID.getVal();
		}
		return StatusType.SUCCESS.getVal();
	}
	private int doCheckMoney(Double money){
		if(money == null)
			return StatusType.OBJECT_NULL.getVal();
		JinE validMoney = jinEMapper.select();
		if(validMoney.getJe() == null){
			logger.error("调用BuZhuServiceImpl.addBuZhuCheck出错，无法获取金额，JinE={}",new Object[]{validMoney});
			return StatusType.OBJECT_NULL.getVal();
		}
		boolean isValid = this.validMoney((double)validMoney.getJe(), money); 
		if(isValid == false){
			return StatusType.MONEY_INVALID.getVal();
		}
		return StatusType.SUCCESS.getVal();
	}


	/*
	 * 新增一条补助信息
	 * (non-Javadoc)
	 * @see cn.edu.uestc.smgt.service.BuZhuService#add(cn.edu.uestc.smgt.pojo.Buzhu)
	 */
	private int add(Buzhu buzhu) {
		if (buzhu == null || buzhu.getStuId() == null
				|| buzhu.getHelpDate() == null) {
			return StatusType.OBJECT_NULL.getVal();
		}
		int rows = 0, exist = 0;
		// judge if exist same key(student_id && help_date)
		try {
			exist = buzhuMapper.existByStuIdAndHelpDate(buzhu);
			if (exist > 0)
				return StatusType.EXISTS.getVal();
			buzhu.setStatusCode((byte) BuzhuStatusCodeType.NEW.getVal());
			rows = buzhuMapper.insertSelective(buzhu);
		} catch (Exception e) {
			logger.error(
					"调用BuZhuServiceImpl.batchUpdate出错,exist={},rows={}",
					new Object[] { exist, rows},e);
		}
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	private int batchAdd(List<Buzhu> buzhuList) {
		throw new UnsupportedOperationException("批量插入不支持");
	}

	private int batchUpdate(List<Buzhu> buzhuList) {
		if (buzhuList == null)
			return StatusType.OBJECT_NULL.getVal();
		final List<Buzhu> modifiedList = buzhuList;
		int rows = 0;
		try {
			rows = txTemplate.execute(new TransactionCallback<Integer>() {
				public Integer doInTransaction(TransactionStatus status) {
					int totalRows = 0;
					for (Buzhu buzhu : modifiedList) {
						totalRows += buzhuMapper
								.updateByPrimaryKeySelective(buzhu);
					}
					return totalRows;
				}
			});
		} catch (Exception e) {
			logger.error(
					"调用BuZhuServiceImpl.batchUpdate出错,rows={},buzhuList={}",
					new Object[] { rows, buzhuList},e);
		}
		return DAOResultUtil.getBatchResult(rows, modifiedList.size()).getVal();
	}

	/*
	 * 删除补助信息
	 * @see cn.edu.uestc.smgt.service.BuZhuService#remove(int)
	 */
	public int remove(int id) {
		int rows = 0;
		try {
			rows = this.buzhuMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("调用BuZhuServiceImpl.remove出错,id={},rows={}",
					new Object[] { id, rows},e);
		}
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	public QueryBase getBuzhuByPage(int startPage, int pageNum,
			int statusCode) {
		QueryBase queryBase = new QueryBase();
		queryBase.setCurrentPage((long)startPage);
		queryBase.setPageSize((long)pageNum);
		queryBase.addParameter("statusCode", statusCode);
		long totalRow = this.buzhuMapper.getBuZhuRows(queryBase);
		queryBase.setTotalRow(totalRow);

		List<BuZhuDTO> dtos = this.buzhuMapper.getBuZhuByPage(queryBase);
		processBuZhuDTO(dtos);
		queryBase.setResults(dtos);
		return queryBase;
	}

	private void processBuZhuDTO(List<BuZhuDTO> dtos){
		if(dtos == null)
			return;
		for(BuZhuDTO dto : dtos){
			Dept dept = deptMapper.selectByDeptId(dto.getDeptId());
			if(dept != null){
				dto.setDeptName(dept.getDeptName());
			}
			dto.setStatusCodeName(BuzhuStatusCodeType.value(dto.getStatusCode()).getDesc());
		}
	}

	private int batchUpdate(String ids,byte statusCode) {
		if(ids == null || StringUtils.isEmpty(ids))
			return StatusType.OBJECT_NULL.getVal();
		String[] idsArray = ids.split(",");
		if (idsArray == null || idsArray.length == 0)
			return StatusType.DATA_PARSE_ERROR.getVal();
		List<Buzhu> buzhuList = new ArrayList<Buzhu>();
		for (String id : idsArray) {
			Buzhu buzhu = new Buzhu();
			buzhu.setId(Integer.parseInt(id));
			buzhu.setStatusCode(statusCode);
			if(statusCode == BuzhuStatusCodeType.REVIEW.getVal()){
				buzhu.setSubmitTime(new Date());
			}
			buzhuList.add(buzhu);
		}
		return batchUpdate(buzhuList);
	}

	/*
	 * 批量同意
	 * @see cn.edu.uestc.smgt.service.BuZhuService#bacthAgree(java.lang.String)
	 */
	public int bacthAgree(String ids) {
		return batchUpdate(ids,(byte)BuzhuStatusCodeType.AGREE.getVal());
	}
	/*
	 * 批量不同意
	 * @see cn.edu.uestc.smgt.service.BuZhuService#bacthAgree(java.lang.String)
	 */
	public int batchDisagree(String ids) {
		return batchUpdate(ids,(byte)BuzhuStatusCodeType.REJECT.getVal());
	}

	/*
	 * 批量待审核
	 * @see cn.edu.uestc.smgt.service.BuZhuService#bacthAgree(java.lang.String)
	 */
	public int bacthReview(String ids) {
		return batchUpdate(ids,(byte)BuzhuStatusCodeType.REVIEW.getVal());
	}

	public QueryBase searchBuZhu(BuZhuCondition buzhuCondition,boolean isPage){
		QueryBase queryBase = new QueryBase();
		try{

			String deptIds = buzhuCondition.getDeptId();
			if(!StringUtils.isEmpty(deptIds)){
				String[] deptIdArray = deptIds.split(",");
				Set<String> deptSet = new HashSet<String>();
				Collections.addAll(deptSet, deptIdArray);
				queryBase.addParameter("deptId",deptSet);
			}
			String helpDate = buzhuCondition.getHelpDate();
			if(!StringUtils.isEmpty(helpDate)){
				String[] helpDateArray = helpDate.split(",");
				Set<String> helpDateSet = new HashSet<String>();
				Collections.addAll(helpDateSet, helpDateArray);
				queryBase.addParameter("helpDate",helpDateSet);
			}
			String studentId = buzhuCondition.getStuNo();
			if(!StringUtils.isEmpty(studentId)){
				queryBase.addParameter("studentId",studentId);
			}
			queryBase.addParameter("type",buzhuCondition.getType());
			queryBase.addParameter("statusCode", buzhuCondition.getStatusCode());
			List<BuZhuDTO> dtos = null;
			if(isPage){
				Long currentPage = buzhuCondition.getCurrentPage() == null?0:buzhuCondition.getCurrentPage();
				queryBase.setCurrentPage(currentPage+1);
				queryBase.setPageSize(buzhuCondition.getPageSize());
				long totalRow = this.buzhuMapper.getBuZhuRows(queryBase);
				queryBase.setTotalRow(totalRow);
			}
			dtos = this.buzhuMapper.getBuZhuByPage(queryBase);
			processBuZhuDTO(dtos);
			queryBase.setResults(dtos);
		}catch(Exception e){
			logger.error("调用BuZhuServiceImpl.searchBuZhu出错,buzhuCondition={}",new Object[]{buzhuCondition},e);
		}
		return queryBase;
	}

	public QueryBase searchBuZhuByPage(BuZhuCondition buzhuCondition) {
		return searchBuZhu(buzhuCondition,true);
	}

	public int batchWithdraw(String ids) {
		if(ids == null || StringUtils.isEmpty(ids))
			return StatusType.OBJECT_NULL.getVal();
		String[] idsArray = ids.split(",");
		if (idsArray == null || idsArray.length == 0)
			return StatusType.DATA_PARSE_ERROR.getVal();
		List<String> idList = Arrays.asList(idsArray);
		Map<String,Object> paras = new HashMap<String, Object>(1);
		paras.put("idList", idList);
		List<Buzhu> getBuzhuList = this.buzhuMapper.getBuzhuByIds(paras);
		StringBuilder idsBuilder = new StringBuilder();
		if(getBuzhuList != null && getBuzhuList.size() != 0){
			//过滤出已提交待审核的补助
			for(Buzhu buzhu : getBuzhuList){
				if(buzhu == null)
					continue;
				if(buzhu.getStatusCode() == BuzhuStatusCodeType.REVIEW.getVal()){
					idsBuilder.append(buzhu.getId()).append(",");
				}
			}
		}
		String filterIds = "";
		if(idsBuilder.length() > 1){
			filterIds = idsBuilder.substring(0, idsBuilder.length()-1);
		}
		else{
			filterIds = idsBuilder.substring(0, idsBuilder.length());
		}
		return batchUpdate(filterIds,(byte)BuzhuStatusCodeType.NEW.getVal());
	}

	public int updateUnsubmittedBuzhu(Buzhu buzhu,boolean isCheck) {
		if(buzhu == null || buzhu.getStatusCode() == null || buzhu.getId() == null){
			return StatusType.OBJECT_NULL.getVal();
		}

		if(buzhu.getStatusCode() != BuzhuStatusCodeType.NEW.getVal()){
			return StatusType.OPERATION_FORBID.getVal();
		}
		if(isCheck){
			int statusByMoney = doCheckMoney(buzhu.getMoney());
			int statusByDate = doCheckDate(buzhu.getHelpDate());
			int statusByDeadlineTime = doCheckDeadlineTime(buzhu.getHelpDate());
			if(StatusType.SUCCESS.getVal() != statusByMoney)
				return statusByMoney;
			if(StatusType.SUCCESS.getVal() != statusByDate)
				return statusByDate;
			if(StatusType.SUCCESS.getVal() != statusByDeadlineTime)
				return statusByDeadlineTime;
		}
		int rows = buzhuMapper.updateByPrimaryKeySelective(buzhu);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	public QueryBase searchBuzhu(BuZhuCondition buzhuCondition) {
		return searchBuZhu(buzhuCondition,false);
	}

}
