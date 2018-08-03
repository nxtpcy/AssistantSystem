package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class Buzhu {
	private Integer id;

	private String helpDate;

	private String deptId;

	private String stuId;

	private Double money;

	private Integer szlbdm;

	private Integer shztm;

	private String remark;

	private Date createTime;

	private Date modifyTime;
	private Date submitTime;

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	private Byte statusCode;

	private Byte type;

	public Byte getType() {
		return type;
	}

	/**
	 * 根据statusCode获取状态名，
	 * 
	 * @return
	 */
	public String getStatusCodeNameWhenNull() {
		if (statusCode == 0) {
			return "未提交";
		}
		if (statusCode == 1) {
			return "已提交待审核";
		}
		if (statusCode == 2) {
			return "审核通过";
		}
		if (statusCode == 3) {
			return "审核未通过";
		}
		return null;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHelpDate() {
		return helpDate;
	}

	public void setHelpDate(String helpDate) {
		this.helpDate = helpDate == null ? null : helpDate.trim();
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId == null ? null : stuId.trim();
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getSzlbdm() {
		return szlbdm;
	}

	public void setSzlbdm(Integer szlbdm) {
		this.szlbdm = szlbdm;
	}

	public Integer getShztm() {
		return shztm;
	}

	public void setShztm(Integer shztm) {
		this.shztm = shztm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Byte getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Byte statusCode) {
		this.statusCode = statusCode;
	}
}