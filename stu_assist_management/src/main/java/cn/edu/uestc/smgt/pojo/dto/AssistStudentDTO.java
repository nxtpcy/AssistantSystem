package cn.edu.uestc.smgt.pojo.dto;

import org.springframework.beans.BeanUtils;

import cn.edu.uestc.smgt.pojo.AssistStudent;

public class AssistStudentDTO extends AssistStudent {
	// 学生所在部门和科室信息
	private String deptName;
	private String officeName;
	// 学生相关信息（所在学院，导师信息）
	private String stuDeptId;
	private String stuDeptName;
	private String dsName;
	private String dsId;

	public AssistStudentDTO() {
		// TODO Auto-generated constructor stub
	}

	public AssistStudentDTO(AssistStudent ast) {
		BeanUtils.copyProperties(ast, this);
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getStuDeptId() {
		return stuDeptId;
	}

	public void setStuDeptId(String stuDeptId) {
		this.stuDeptId = stuDeptId;
	}

	public String getStuDeptName() {
		return stuDeptName;
	}

	public void setStuDeptName(String stuDeptName) {
		this.stuDeptName = stuDeptName;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

}
