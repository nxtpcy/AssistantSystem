package cn.edu.uestc.smgt.service;

import java.util.List;

import cn.edu.uestc.smgt.pojo.HelpDate;

public interface ValidDateService {
    int save(HelpDate date);
    
    List<HelpDate> getValidDates();
    
    int remove(int id);
}
