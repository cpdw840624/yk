package com.ryb.user.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ioc.Ioc;
import com.jfinalext.annotation.ControllerAnno;
import com.jfinalext.annotation.DataSource;
import com.jfinalext.annotation.DataSourceMap;
import com.ryb.common.controller.ApiController;
import com.ryb.common.interceptor.ApiAuthInterceptor;
import com.ryb.common.model.Users;
import com.ryb.user.service.IUserService;

@ControllerAnno(controllerkey="/api/users")
@Before({ApiAuthInterceptor.class})
public class UserController extends ApiController {

	@Ioc.BY_TYPE
	private IUserService userService;
	
	@DataSource(type=DataSourceMap.MASTER)
	public void login(){
		try{
			String schoolId=this.getPara("schoolId");
			String username=this.getPara("username");
			String password=this.getPara("password");
	        String msg=userService.login(schoolId,username, password);
	        Users user=null;
	        if(msg==null){
	        	user=Users.dao.getByUsername(username);
	        	user.put("password","");
	        }
			this.renderJsonMsgResult(msg, user);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}
	
	@DataSource(type=DataSourceMap.MASTER)
	public void loginAdmin(){
		try{
			String username=this.getPara("username");
			String password=this.getPara("password");
	        String msg=userService.loginAdmin(username, password);
	        Users user=null;
	        if(msg==null){
	        	user=Users.dao.getByAdminUsername(username);
	        	user.put("password","");
	        	setSessionAttr("adminUser", user);
	        }
			this.renderJsonMsgResult(msg, user);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}
	
	@DataSource(type=DataSourceMap.MASTER)
	public void modifyPassword(){
		try{
			Integer id=this.getParaToInt("id");
			String oldPassword=this.getPara("oldPassword");
			String newPassword=this.getPara("newPassword");
	        String msg=userService.modifyPassword(id,oldPassword, newPassword);
	        Users user=null;
	        if(msg==null){
	        	user=Users.dao.findById(id);
	        	user.put("password","");
	        }
			this.renderJsonMsgResult(msg, user);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}

	@DataSource(type=DataSourceMap.MASTER)
	public void logout(){
		try{
			Integer id=this.getParaToInt("id");
	        String msg=userService.logout(id);
	        setSessionAttr("frontUser", null);
	        setSessionAttr("adminUser", null);
			this.renderJsonMsgResult(msg,null);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}

}
