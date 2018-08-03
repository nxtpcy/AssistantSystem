package cn.edu.uestc.smgt.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.AssistStudentMapper;
import cn.edu.uestc.smgt.dao.AstHistoryInfoMapper;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.dao.OfficeMapper;
import cn.edu.uestc.smgt.dao.StudentInSchoolMapper;
import cn.edu.uestc.smgt.pojo.AssistStudent;
import cn.edu.uestc.smgt.pojo.AstHistoryInfo;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.Office;
import cn.edu.uestc.smgt.pojo.StudentInSchool;
import cn.edu.uestc.smgt.pojo.dto.AssistStudentDTO;
import cn.edu.uestc.smgt.service.base.AssistStudentService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;
import cn.edu.uestc.smgt.utils.DateUtils;
import cn.edu.uestc.smgt.utils.ExcelUtil;

@Service("assistStudentService")
public class AssistStudentServiceImpl implements AssistStudentService {

	@Autowired
	private AssistStudentMapper assistStudentMapper;
	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private OfficeMapper officeMapper;
	@Autowired
	private StudentInSchoolMapper studentInSchoolMapper;
	@Autowired
	private AstHistoryInfoMapper astHistoryInfoMapper;

	// 增加助管信息
	public int add(AssistStudent ast) {
		if (ast == null || ast.getStuId() == null) {
			return StatusType.NOT_EXISTS.getVal();
		}
		if (assistStudentMapper.selectByStuId(ast.getStuId()) != null) {
			return StatusType.EXISTS.getVal();
		}
		ast.setCreateTime(new Date());
		int rows = assistStudentMapper.insertSelective(ast);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	// 修改助管信息
	public int update(AssistStudent ast) {
		if (ast == null || ast.getStuId() == null
				|| assistStudentMapper.selectByStuId(ast.getStuId()) == null) {
			return StatusType.NOT_EXISTS.getVal();
		}

		int rows = assistStudentMapper.updateByStuIdSelective(ast);

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	// 删除助管信息
	public int delete(AssistStudent ast) {
		if (ast == null || ast.getStuId() == null
				|| assistStudentMapper.selectByStuId(ast.getStuId()) == null) {
			return StatusType.NOT_EXISTS.getVal();
		}
		int rows = assistStudentMapper.deleteByStuId(ast.getStuId());

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();

	}

	// 查询助管信息
	public void query(QueryBase queryBase) {
		queryBase.setTotalRow(assistStudentMapper.size(queryBase
				.getParameters()));// 获取总行数
		List<AssistStudent> list = assistStudentMapper
				.selectByPageAndSelections(queryBase);
		List<AssistStudentDTO> listDto = new ArrayList<AssistStudentDTO>();
		for (AssistStudent ast : list) {
			AssistStudentDTO astDto = new AssistStudentDTO(ast);
			Dept dept;
			if (astDto.getDeptId() != null
					&& (dept = deptMapper.selectByDeptId(astDto.getDeptId())) != null)
				astDto.setDeptName(dept.getDeptName());

			Office office;
			if (astDto.getOfficeId() != null
					&& (office = officeMapper.selectByPrimaryKey(astDto
							.getOfficeId())) != null) {
				astDto.setOfficeName(office.getOfficeName());
			}

			astDto.setStuDeptId(ast.getStuCollegeId());
			astDto.setStuDeptName(ast.getStuCollegeName());
			astDto.setDsId(ast.getStuDsId());
			astDto.setDsName(ast.getStuDsName());
			listDto.add(astDto);
		}
		queryBase.setResults(listDto);// 获取需要返回的数据集
	}

	/**
	 * 批量增加助管信息
	 * 
	 * @author ljd
	 * @return 返回添加成功的记录条数
	 */
	public String batchAdd(List<AssistStudent> list) {
		// TODO Auto-generated method stub
		int successNumber = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			AssistStudent assistStudent = list.get(i);
			if (assistStudentMapper.selectByStuId(assistStudent.getStuId()) == null) {
				successNumber++;
				assistStudent.setCreateTime(new Date());
				assistStudentMapper.insertSelective(assistStudent);
			} else {
				// numbers.add("第" + i + "条数据在数据库中已经存在");
				sb.append("第" + i + "条数据在数据库中已经存在</br>");
			}
		}
		return sb.toString();
	}

	/**
	 * 超级管理员批量增加学生到助管列表，此时该助管只有学号，姓名等有值，所属的部门等信息需要添加完成后修改，默认没有
	 * 
	 * @author ljd
	 * @return 成功的记录条数
	 */
	public String batchAddStudentToAssist(String[] ids) {
		// TODO Auto-generated method stub
		List<AssistStudent> astList = new ArrayList<AssistStudent>();
		for (String string : ids) {
			StudentInSchool sis = studentInSchoolMapper.selectByStuId(string);
			if (sis == null)
				continue;
			AssistStudent ast = new AssistStudent();
			ast.setStuId(sis.getStuId());
			ast.setName(sis.getStuName());
			ast.setStuCollegeId(sis.getDeptId());
			ast.setStuCollegeName(sis.getDeptName());
			ast.setStuDsId(sis.getDsId());
			ast.setStuDsName(sis.getDsName());
			// ast.setDeptId("");
			// ast.setOfficeId(0);
			astList.add(ast);
		}
		return batchAdd(astList);
	}

	/**
	 * 根据excel表格批量导入主管信息
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class })
	public String batchAddAssistByExcel(List<AssistStudent> list) {
		// 首先判断这些数据在全校学生库中是否存在，
		// 然后修改存在的学生学院等身份信息为数据库中已经有的，防止错误上传某些字段
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			AssistStudent ast = list.get(i);
			StudentInSchool sis = studentInSchoolMapper.selectByStuId(ast
					.getStuId());
			// 有一种可能性：所插入的学生学号
			if (sis == null) {
				sb.append("第" + i + "条数据 学号 有错误</br>");
				continue;
			}
			ast.setName(sis.getStuName());
			ast.setStuCollegeId(sis.getDeptId());
			ast.setStuCollegeName(sis.getDeptName());
			ast.setStuDsId(sis.getDsId());
			ast.setStuDsName(sis.getDsName());
		}
		return sb.append(batchAdd(list)).toString();
	}

	/**
	 * 在职助管转离职 在职转离职：先判断当前学生的信息是否已经在离职表中存在。然后更新手机号码，银行信息，银行卡号。
	 * 然后获取startTime，endTime ，deptName ，然后与原来的info进行拼接。插入
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = RuntimeException.class)
	public int currToHist(String stuId) {
		// TODO Auto-generated method stub
		AssistStudent ast = assistStudentMapper.selectByStuId(stuId);
		if (ast == null) {
			return StatusType.NOT_EXISTS.getVal();
		}
		// 获取当前助管的信息,组成离职时间记录
		Date start = ast.getCreateTime();
		String startYM = "";
		if (start != null)
			startYM = DateUtils.date2String(start);
		Date now = new Date();
		String endYM = DateUtils.date2String(now);
		Dept dept = deptMapper.selectByDeptId(ast.getDeptId());
		String deptName = "";
		if (dept != null) {
			deptName = dept.getDeptName();
		}
		String addInfo = startYM + "," + endYM + "," + deptName;
		// 判断当前stuId是否在离职记录表中存在
		AstHistoryInfo astHist = astHistoryInfoMapper.selectByStuId(stuId);
		if (astHist == null) {
			astHist = new AstHistoryInfo();
			astHist.setStuId(stuId);
			astHist.setName(ast.getName());
			astHist.setStuCollegeId(ast.getStuCollegeId());
			astHist.setStuCollegeName(ast.getStuCollegeName());
			astHist.setStuDsId(ast.getStuDsId());
			astHist.setStuDsName(ast.getStuDsName());
			astHist.setTelephone(ast.getTelephone());
			astHist.setBankNo(ast.getBankNo());
			astHist.setBankName(ast.getBankName());
			astHist.setCreateTime(now);
			astHist.setModifyTime(now);
			astHist.setOnJobInfo(addInfo);
			astHistoryInfoMapper.insertSelective(astHist);
		} else {
			// 更新学生信息和离职info
			astHist.setName(ast.getName());
			astHist.setStuCollegeId(ast.getStuCollegeId());
			astHist.setStuCollegeName(ast.getStuCollegeName());
			astHist.setStuDsId(ast.getStuDsId());
			astHist.setStuDsName(ast.getStuDsName());
			astHist.setTelephone(ast.getTelephone());
			astHist.setBankNo(ast.getBankNo());
			astHist.setBankName(ast.getBankName());
			astHist.setModifyTime(now);
			addInfo = astHist.getOnJobInfo() + "|" + addInfo;
			astHist.setOnJobInfo(addInfo);
			astHistoryInfoMapper.updateByPrimaryKeySelective(astHist);
		}
		// 完成在职转离职的最后一步：将在职表中的信息删除
		assistStudentMapper.deleteByStuId(stuId);
		//全部执行成功返回success
		return StatusType.SUCCESS.getVal();
	}
}
