package cn.edu.uestc.smgt.dao;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.Buzhu;
import cn.edu.uestc.smgt.pojo.dto.BuZhuDTO;

public interface BuzhuMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Buzhu record);

	int insertSelective(Buzhu record);

	Buzhu selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Buzhu record);

	int updateByPrimaryKey(Buzhu record);

	int existByStuIdAndHelpDate(Buzhu record);

	List<BuZhuDTO> getBuZhuByPage(QueryBase queryBase);

	long getBuZhuRows(QueryBase queryBase);
	List<Buzhu> getBuzhuByIds(Map<String,Object> paras);
	/**
	 * 根据条件获取BuZhuDTO
	 * 
	 * @param buzhu
	 * @return
	 * @author ljd
	 */
	List<BuZhuDTO> selectBuZhuAndStudentInfoWithSelections(Buzhu buzhu);

	/**
	 * 根据条件获取BuZhuDTO,map
	 * 
	 * @param buzhu
	 * @return
	 * @author ljd
	 */
	List<BuZhuDTO> selectBuZhuAndStudentInfoWithSelectionsByMap(
			Map<String, Object> params);
}