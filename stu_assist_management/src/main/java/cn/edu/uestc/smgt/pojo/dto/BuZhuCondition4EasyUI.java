package cn.edu.uestc.smgt.pojo.dto;

public class BuZhuCondition4EasyUI {
	private String helpDate;
	public String getHelpDate() {
		return helpDate;
	}
	public String getDeptId() {
		return deptId;
	}
	public Byte getStatusCode() {
		return statusCode;
	}
	public void setHelpDate(String helpDate) {
		this.helpDate = helpDate;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setStatusCode(Byte statusCode) {
		this.statusCode = statusCode;
	}
	private String deptId;
	private Byte statusCode;
	private Long page = null;
	private Long rows = null;
	private Byte type = null;
	private String stuNo; //student no
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Long getPage() {
		return page;
	}
	@Override
	public String toString() {
		return "BuZhuCondition4EasyUI [helpDate=" + helpDate + ", deptId="
				+ deptId + ", statusCode=" + statusCode + ", page=" + page
				+ ", rows=" + rows + ", type=" + type + ", stuNo=" + stuNo + "]";
	}
	public Long getRows() {
		return rows;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public void setRows(Long rows) {
		this.rows = rows;
	}
}
