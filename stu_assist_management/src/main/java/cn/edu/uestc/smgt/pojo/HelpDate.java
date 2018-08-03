package cn.edu.uestc.smgt.pojo;

import java.util.Date;

public class HelpDate {
    private Integer id;

    private Integer helpYear;

    private Integer helpMonth;

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    private Date deadlineTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHelpYear() {
        return helpYear;
    }

    public void setHelpYear(Integer helpYear) {
        this.helpYear = helpYear;
    }

    public Integer getHelpMonth() {
        return helpMonth;
    }

    public void setHelpMonth(Integer helpMonth) {
        this.helpMonth = helpMonth;
    }
}