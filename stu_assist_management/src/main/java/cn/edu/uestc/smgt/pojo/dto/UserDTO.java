package cn.edu.uestc.smgt.pojo.dto;

import org.springframework.beans.BeanUtils;

import cn.edu.uestc.smgt.pojo.User;

public class UserDTO extends User {
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	private String deptName;

	public UserDTO(User user) {
		BeanUtils.copyProperties(user, this);
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
