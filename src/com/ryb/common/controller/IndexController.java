package com.ryb.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.dw.base.controller.BaseController;
import com.dw.base.model.BaseModel;
import com.jfinal.aop.Before;
import com.jfinalext.annotation.ControllerAnno;
import com.ryb.common.interceptor.AdminTokenInterceptor;
import com.ryb.common.interceptor.SSOLoginInterceptor;
import com.ryb.common.model.Users;

@ControllerAnno(controllerkey="/")
//@Before(SSOLoginInterceptor.class)
public class IndexController extends BaseController {
	
	public void index(){
		renderBaseJsp("toLogin.jsp");
	}
	
	public void toLogin(){
		renderBaseJsp("toLogin.jsp");
	}
	
	@Before({AdminTokenInterceptor.class})
	public void main(){
		List<BaseModel> list=new ArrayList<BaseModel>();
		BaseModel m=new Users();
		m.put("menuAlias","resource_manage");
		m.put("menuHtmlUrl","/view/resource_manage.html");
		m.put("menuNgController","resourceManage");
		m.put("menuJsUrl","/res/js/admin/resource_manage_controller.js");
		
		list.add(m);
		setAttr("menuList", list);
		renderBaseJsp("base/main.jsp");
	}
}
