package com.ryb.user.service.impl;

import java.util.Date;

import com.dw.base.utils.encrypt.MD5;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.plugin.ioc.Service;
import com.ryb.common.model.Users;
import com.ryb.user.service.IUserService;

@Service.BY_TYPE
public class UserServiceImpl implements IUserService {

	public String login(String schoolId,String username, String password) {
		if(StringUtil.stringIsNull(username)){
			return "用户名不能为空";
		}
		if(StringUtil.stringIsNull(password)){
			return "密码不能为空";
		}
		Users user=Users.dao.getByUsername(username);
		if(user==null){
			return "用户名不存在";
		}
		if(!MD5.getMD5(user.getStr("password")).equals(password)){
			return "密码不正确";
		}
		if(user.getInt("states").intValue()!=1){
			return "当前账号未启用";
		}
		String userSchoolId=user.getStr("school_id");
		if(!StringUtil.stringIsNull(userSchoolId)&&!StringUtil.stringIsNull(schoolId)
				&&!schoolId.equals(userSchoolId)){
			return "当前账号不属于本机激活园所，无法登陆";
		}
		if(user.getInt("login_states").intValue()==1){
			return "当前账号已在其他设备登陆，请先退出";
		}
		user.set("login_states", 1);
		user.set("lastlogin_time", new Date());
		user.update();
		return null;
	}
	
	public String loginAdmin(String username, String password) {
		if(StringUtil.stringIsNull(username)){
			return "用户名不能为空";
		}
		if(StringUtil.stringIsNull(password)){
			return "密码不能为空";
		}
		Users user=Users.dao.getByAdminUsername(username);
		if(user==null){
			return "用户名不存在";
		}
		if(!MD5.getMD5(user.getStr("password")).equals(password)){
			return "密码不正确";
		}
		if(user.getInt("states").intValue()!=1){
			return "当前账号未启用";
		}
		user.set("lastlogin_time", new Date());
		user.update();
		return null;
	}
	
	public String modifyPassword(Integer id, String oldPassword,
			String newPassword) {
		if(id==null){
			return "ID不能为空";
		}
		if(StringUtil.stringIsNull(oldPassword)){
			return "原始密码不能为空";
		}
		if(StringUtil.stringIsNull(newPassword)){
			return "新密码不能为空";
		}
		Users user=Users.dao.findById(id);
		if(user==null){
			return "未找到指定ID用户数据";
		}
		if(!user.getStr("password").equals(oldPassword)){
			return "原始密码不正确";
		}
		user.set("password", newPassword);
		user.update();
		return null;
	}
	
	public String logout(Integer id){
		if(id==null){
			return "ID不能为空";
		}
		Users user=Users.dao.findById(id);
		user.set("login_states", 0);
		user.update();
		return null;
	}

}
