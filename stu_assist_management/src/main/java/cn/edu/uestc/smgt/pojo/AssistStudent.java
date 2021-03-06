package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class AssistStudent {
	private Integer id;
	// 学生基础信息
	private String stuId;

	private String name;

	private String stuCollegeId;// 学生所在学院id

	private String stuCollegeName;

	private String stuDsId;// 学生导师id

	private String stuDsName;

	// 学生作为助管信息

	private String deptId;

	private Integer officeId;

	private String telephone;

	private String bankNo;

	private String bankName;

	private Byte decision;

	private Date createTime;

	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId == null ? null : stuId.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getStuCollegeId() {
		return stuCollegeId;
	}

	public void setStuCollegeId(String stuCollegeId) {
		this.stuCollegeId = stuCollegeId == null ? null : stuCollegeId.trim();
	}

	public String getStuCollegeName() {
		return stuCollegeName;
	}

	public void setStuCollegeName(String stuCollegeName) {
		this.stuCollegeName = stuCollegeName == null ? null : stuCollegeName
				.trim();
	}

	public String getStuDsId() {
		return stuDsId;
	}

	public void setStuDsId(String stuDsId) {
		this.stuDsId = stuDsId == null ? null : stuDsId.trim();
	}

	public String getStuDsName() {
		return stuDsName;
	}

	public void setStuDsName(String stuDsName) {
		this.stuDsName = stuDsName == null ? null : stuDsName.trim();
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo == null ? null : bankNo.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public Byte getDecision() {
		return decision;
	}

	public void setDecision(Byte decision) {
		this.decision = decision;
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
}