package cn.edu.uestc.smgt.pojo.dto;

import org.springframework.beans.BeanUtils;

import cn.edu.uestc.smgt.pojo.Buzhu;

public class BuZhuDTO extends Buzhu {

	@Override
	public String toString() {
		return "BuZhuDTO [studentName=" + studentName + ", bankName="
				+ bankName + ", deptName=" + deptName + ", bankNo=" + bankNo
				+ ", statusCodeName=" + statusCodeName + "]";
	}

	private String studentName;
	private String bankName;
	private String deptName;
	private String bankNo;
	private String statusCodeName;
    
	public String getStatusCodeName() {
		return statusCodeName;
	}

	public void setStatusCodeName(String statusCodeName) {
		this.statusCodeName = statusCodeName;
	}

	public BuZhuDTO() {
	}

	public BuZhuDTO(Buzhu buzhu) {
		BeanUtils.copyProperties(buzhu, this);
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

}
