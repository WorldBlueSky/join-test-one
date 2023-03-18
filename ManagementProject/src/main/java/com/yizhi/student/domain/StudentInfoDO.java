package com.yizhi.student.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 生基础信息表
 * 
 * @author dunhf
 * @email 499345515@qq.com
 * @date 2019-08-01 09:45:46
 */
@Data
public class StudentInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Integer id;
	//学号
	private String studentId;
	//考生号
	private String examId;
	//所属班级
	private Integer classId;
	//学生姓名
	private String studentName;
	//身份证号
	private String certify;
	//家庭住址
	private String mailAddress;
	//外语语种
	private String foreignLanaguage;
	//性别
	private String studentSex;
	//民族
	private String nation;
	//政治面貌
	private String political;
	//一卡通卡号
	private String cardId;
	//手机号
	private String telephone;
	//科类
	private Integer subjectType;
	//所属学院
	private Integer tocollege;
	//隶属校区*****
	private Integer tocampus;
	//所属专业
	private Integer tomajor;
	//生源地
	private String birthplace;
	//隶属层次
	private String grade;
	//在校状态
	private Integer isstate;
	//出生日期
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	//备注
	private String note;

	//添加时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date addTime;
	//添加人
	private Integer addUserid;
	// 添加用户的姓名
	private String addName;

	//修改时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date editTime;
	//修改人
	private Integer editUserid;
	// 修改人的姓名
	private String editName;


	// 班级名称
	private String className;
	// 学院名称
	private String collegeName;
	// 专业名称
	private String majorName;



}
