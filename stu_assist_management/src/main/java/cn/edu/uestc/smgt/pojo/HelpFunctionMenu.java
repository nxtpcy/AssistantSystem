package cn.edu.uestc.smgt.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelpFunctionMenu {
	private List<HelpFunctionMenu> children = new ArrayList<HelpFunctionMenu>();

	public void addChild(HelpFunctionMenu helpFunctionMenu){
		this.children.add(helpFunctionMenu);
	}
	
	public void removeChild(HelpFunctionMenu helpFunctionMenu){
		this.children.remove(helpFunctionMenu);
	}
	public List<HelpFunctionMenu> getChildren(){
		return children;
	}

	@Override
	public String toString() {
		return "HelpFunctionMenu [children=" + children + ", id=" + id
				+ ", menuCode=" + menuCode + ", menuName=" + menuName
				+ ", parentId=" + parentId + ", menuUrl=" + menuUrl
				+ ", statusCode=" + statusCode + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}

	private Integer id;

	private String menuCode;

	private String menuName;

	private Integer parentId;

	private String menuUrl;

	private Integer statusCode;

	private Date createTime;

	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
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