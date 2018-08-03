package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class AstHistoryInfo {
    private Integer id;

    private String stuId;

    private String name;

    private String stuCollegeId;

    private String stuCollegeName;

    private String stuDsId;

    private String stuDsName;

    private String telephone;

    private String bankNo;

    private String bankName;

    private String onJobInfo;

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
        this.stuCollegeName = stuCollegeName == null ? null : stuCollegeName.trim();
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

    public String getOnJobInfo() {
        return onJobInfo;
    }

    public void setOnJobInfo(String onJobInfo) {
        this.onJobInfo = onJobInfo == null ? null : onJobInfo.trim();
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