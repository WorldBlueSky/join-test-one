package com.yizhi.student.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizhi.common.annotation.Log;
import com.yizhi.common.controller.BaseController;
import com.yizhi.common.domain.WeixinUserPrincipal;
import com.yizhi.common.redis.shiro.RedisCache;
import com.yizhi.common.redis.shiro.RedisManager;
import com.yizhi.common.redis.shiro.RedisSessionDAO;
import com.yizhi.common.redis.shiro.SerializeUtils;
import com.yizhi.common.utils.*;
import com.yizhi.student.domain.ClassDO;
import com.yizhi.student.service.ClassService;
import com.yizhi.student.service.CollegeService;
import com.yizhi.student.service.MajorService;
import com.yizhi.system.dao.UserDao;
import com.yizhi.system.domain.UserDO;
import com.yizhi.system.domain.UserOnline;
import com.yizhi.system.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 生基础信息表
 */
 
@Controller
@RequestMapping("/student/studentInfo")
public class StudentInfoController {

	@Autowired
	private StudentInfoService studentInfoService;

	@Autowired
	public SessionService sessionService;

    @Autowired
	public RedisManager redisManager;

	/**
	 * 查询当前登录用户 UserDo 对象
	 * @param sessionId 浏览器传递的cookie,用来找到redis中存储的session对象
	 * @return
	 */
	public UserDO getCurrentUser(String sessionId){
		String s = "yizhi_shiro_redis_session:"+sessionId;
		byte[] input = s.getBytes();

		byte[] output = redisManager.get(input);
		SimpleSession simpleSession = (SimpleSession) SerializeUtils.deserialize(output);

		// Session中 DefaultSubjectContext.PRINCIPALS_SESSION_KEY 对应的就是 UserDo 当前登录的userDo对象
		SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) simpleSession
				.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		Object PrimaryPrincipal=principalCollection.getPrimaryPrincipal();
		UserDO userDO = (UserDO) PrimaryPrincipal;

		return userDO;
	}

	/**
	 * 新增学生信息
	 * @param studentInfoDO
	 * @return
	 */
	@Log("学生信息保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("student:studentInfo:add")
	public R save(StudentInfoDO studentInfoDO,@CookieValue("yizhi.session.id") String sessionId){
        UserDO userDO = getCurrentUser(sessionId);

        int userId = userDO.getUserId().intValue();
        String name = userDO.getName();
		//System.out.println("执行新增操作的用户id是:======="+userId);
		//System.out.println("执行新增操作的真实姓名是:========"+name);

        studentInfoDO.setAddUserid(userId);
        studentInfoDO.setAddName(name);

		if(studentInfoService.save(studentInfoDO)>0){
	    	return R.ok();
		}
		return R.error();
	}

	/**
	 * 可分页查询
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("student:studentInfo:studentInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		if (params.get("sort")!=null) {
			params.put("sort",BeanHump.camelToUnderline(params.get("sort").toString()));
		}
		//查询列表数据
		Query query = new Query(params);
		List<StudentInfoDO> classList = studentInfoService.list(query);

//		System.out.println("经过连接分页之后得到的对象结果===========================");
//		for (int i = 0; i < classList.size(); i++) {
//			StudentInfoDO a =  classList.get(i);
//			System.out.println(a.toString());
//		}
//		System.out.println("=======================================================");

		int total = studentInfoService.count(query);
		PageUtils pageUtils = new PageUtils(classList, total,query.getCurrPage(),query.getPageSize());
		return pageUtils;
	}


	/**
	 * 修改学生信息
	 */
	@Log("学生基础信息表修改")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("student:studentInfo:edit")
	public R update(StudentInfoDO studentInfo,@CookieValue("yizhi.session.id") String sessionId){
		UserDO userDO = getCurrentUser(sessionId);

		int userId = userDO.getUserId().intValue();
		String name = userDO.getName();

		//System.out.println("执行修改操作的用户id是:======="+useId);
		//System.out.println("执行修改操作的真实姓名是:========"+name);

		studentInfo.setEditUserid(userId);
		studentInfo.setEditName(name);

        studentInfoService.update(studentInfo);
		return R.ok();
	}

	/**
	 * 根据id删除指定的学生信息
	 */
	@Log("学生基础信息表删除")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:remove")
	public R remove(Integer id){
		studentInfoService.remove(id);
		return R.ok();
	}
	
	/**
	 * 根据提供的id数组批量删除学生信息
	 */
	@Log("学生基础信息表批量删除")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
         studentInfoService.batchRemove(ids);
		return R.ok();
	}


	//前后端不分离 客户端 -> 控制器-> 定位视图
	/**
	 * 学生管理点击Tab标签 forward页面
	 */
	@GetMapping()
	@RequiresPermissions("student:studentInfo:studentInfo")
	String StudentInfo(){
		return "student/studentInfo/studentInfo";
	}

	/**
	 * 更新功能 弹出View定位
	 */
	@GetMapping("/edit/{id}")
	@RequiresPermissions("student:studentInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		StudentInfoDO studentInfo = studentInfoService.get(id);
		model.addAttribute("studentInfo", studentInfo);
		return "student/studentInfo/edit";
	}

	/**
	 * 学生管理 添加学生弹出 View
	 */
	@GetMapping("/add")
	@RequiresPermissions("student:studentInfo:add")
	String add(){
	    return "student/studentInfo/add";
	}
	
}//end class
