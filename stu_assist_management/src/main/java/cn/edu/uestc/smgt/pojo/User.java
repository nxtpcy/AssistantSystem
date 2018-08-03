package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class User {
    @Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", pwd=" + pwd
				+ ", modifyTime=" + modifyTime + ", createTime=" + createTime
				+ ", deptId=" + deptId + ", role=" + role + "]";
	}

	private Integer id;

    private String loginName;

    private String pwd;

    private Date modifyTime;

    private Date createTime;

    private String deptId;

    private Byte role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }
}