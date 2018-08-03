package cn.edu.uestc.smgt.pojo.dto;

public class PwdChangeDTO {
    public String getLoginName() {
		return loginName;
	}
	public String getFirstPwd() {
		return firstPwd;
	}
	public String getSecondPwd() {
		return secondPwd;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setFirstPwd(String firstPwd) {
		this.firstPwd = firstPwd;
	}
	public void setSecondPwd(String secondPwd) {
		this.secondPwd = secondPwd;
	}
	private String loginName;
    private String firstPwd;
	private String originPwd;

	public String getOriginPwd() {
		return originPwd;
	}

	public void setOriginPwd(String originPwd) {
		this.originPwd = originPwd;
	}

	private String secondPwd;
}
