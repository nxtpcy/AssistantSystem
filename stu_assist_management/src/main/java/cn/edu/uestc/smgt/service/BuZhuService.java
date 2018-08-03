package cn.edu.uestc.smgt.service;


import java.util.List;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.Buzhu;
import cn.edu.uestc.smgt.pojo.dto.BuZhuCondition;
import cn.edu.uestc.smgt.pojo.dto.BuZhuDTO;

public interface BuZhuService {
    int remove(int id);
    QueryBase getBuzhuByPage(int startPage,int pageNum,int statusCode);
    /**
     * 批量审核通过
     * @param ids 补助记录id集合
     * @return 执行状态
     */
    int bacthAgree(String ids);
    /**
     * 批量审核不通过
     * @param ids 补助记录id集合
     * @return 执行状态
     */
    int batchDisagree(String ids);
    
    /**
     * 批量提交
     * @param ids 补助记录id集合
     * @return 执行状态
     */
    int bacthReview(String ids);
    
    /**
     * 批量撤回
     * @param ids 补助记录id集合
     * @return 执行状态
     */
    int batchWithdraw(String ids);
    
    /**
     * 添加补发补助
     * @param buzhu 补助信息
     * @return 执行状态
     */
    int addBuZhuWithoutCheck(Buzhu buzhu);
    /**
     * 添加正常补助
     * @param buzhu 补助信息
     * @return 执行状态
     */
    int addBuZhuCheck(Buzhu buzhu);
    /**
     * 补助查询
     * @param buzhuCondition 补助条件
     * @return 执行状态
     */
    QueryBase searchBuZhuByPage(BuZhuCondition buzhuCondition);
    
    QueryBase searchBuzhu(BuZhuCondition buzhuCondition);
    
    /**
     * 修改未提交的补助记录 
     * @param buzhu 补助信息
     * @return 执行状态
     */
    int updateUnsubmittedBuzhu(Buzhu buzhu,boolean isCheck);
    
}
