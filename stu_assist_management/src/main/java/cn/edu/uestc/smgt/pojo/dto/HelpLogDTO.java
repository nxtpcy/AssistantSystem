package cn.edu.uestc.smgt.pojo.dto;

import org.springframework.beans.BeanUtils;

import cn.edu.uestc.smgt.pojo.HelpLog;

public class HelpLogDTO extends HelpLog {

	private String deptName;

	public HelpLogDTO() {

	}

	public HelpLogDTO(HelpLog log) {
		BeanUtils.copyProperties(log, this);
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
