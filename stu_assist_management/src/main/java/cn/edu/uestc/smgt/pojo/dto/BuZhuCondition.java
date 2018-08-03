package cn.edu.uestc.smgt.pojo.dto;

public class BuZhuCondition {
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
	@Override
	public String toString() {
		return "BuZhuCondition [helpDate=" + helpDate + ", deptId=" + deptId
				+ ", statusCode=" + statusCode + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + ", type=" + type + ", stuNo="
				+ stuNo + "]";
	}
	public void setStatusCode(Byte statusCode) {
		this.statusCode = statusCode;
	}
	private String deptId;
	private Byte statusCode = null;
	private Long currentPage = null;
	private Long pageSize = null;
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
	public Long getCurrentPage() {
		return currentPage;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
}
