package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class StudentInSchool {
    private Integer id;

    private String stuId;

    private String stuName;

    private String gender;

    private String deptId;

    private String deptName;

    private String lqzyId;

    private String lqzyName;

    private String lqlb;

    private String dsId;

    private String dsName;

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

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getLqzyId() {
        return lqzyId;
    }

    public void setLqzyId(String lqzyId) {
        this.lqzyId = lqzyId == null ? null : lqzyId.trim();
    }

    public String getLqzyName() {
        return lqzyName;
    }

    public void setLqzyName(String lqzyName) {
        this.lqzyName = lqzyName == null ? null : lqzyName.trim();
    }

    public String getLqlb() {
        return lqlb;
    }

    public void setLqlb(String lqlb) {
        this.lqlb = lqlb == null ? null : lqlb.trim();
    }

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId == null ? null : dsId.trim();
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName == null ? null : dsName.trim();
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