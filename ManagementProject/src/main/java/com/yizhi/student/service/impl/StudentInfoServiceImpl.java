package com.yizhi.student.service.impl;

import com.yizhi.common.redis.shiro.RedisCache;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;



@Service
public class StudentInfoServiceImpl implements StudentInfoService {

//    @Autowired
//	RedisCache<String, Object> redisCache;

	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Override
	public StudentInfoDO get(Integer id){
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){

//		System.out.println("map 传递的数据到底是什么?????????????????????");
//		for (Map.Entry<String,Object> entry:map.entrySet()) {
//			System.out.println(entry.getKey() +":"+ entry.getValue());
//		}

		return studentInfoDao.list(map);
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}
	
	@Override
	public int save(StudentInfoDO studentInfo){
		return studentInfoDao.save(studentInfo);
	}
	
	@Override
	public int update(StudentInfoDO studentInfo){
		return studentInfoDao.update(studentInfo);
	}
	
	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return studentInfoDao.batchRemove(ids);
	}
	
}
