package com.yizhi.student.dao;

import com.yizhi.student.domain.StudentInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学生基础信息表
 * @author dunhf
 * @email 499345515@qq.com
 * @date 2019-08-01 09:45:46
 */
@Mapper
public interface StudentInfoDao {

	StudentInfoDO get(Integer id);
	
	List<StudentInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StudentInfoDO studentInfo);
	
	int update(StudentInfoDO studentInfo);
	
	int remove(@Param("id") Integer id);
	
	int batchRemove(@Param("ids") Integer[] ids);
}
