package com.ryb.common.interceptor;

import javax.servlet.http.HttpSession;

import com.dw.base.bean.BaseJSONResult;
import com.dw.base.utils.memcached.MemcachedUtil;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.JsonKit;

public class BaseAdminTokenInterceptor implements Interceptor {
	public void  intercept(Invocation ai){
		try {
			String accountId=ai.getController().getPara("accountId");
			String accountToken=ai.getController().getPara("accountToken");
			if(StringUtil.stringIsNull(accountId)
					||StringUtil.stringIsNull(accountToken)
					||!accountToken.equals((String)MemcachedUtil.getInstance().get("token_"+accountId))){
				if(ai.getController().getRequest().getHeader("x-requested-with")!= null  
		                && ai.getController().getRequest().getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {//
					BaseJSONResult result = new BaseJSONResult(Boolean.FALSE, "nologin", "/base");
					ai.getController().renderJson(result);
				}else{
					ai.getController().redirect("/base");
				}
				return;
			}
		} catch (Exception e) {
			
		}
//		StringBuffer log=new StringBuffer();
//		log.append("{");
//		log.append("type:admin,");
//		log.append("url:\"").append(ai.getActionKey()).append("\",params:");
//		log.append(JsonKit.toJson(ai.getController().getParaMap()));
//		log.append("}");
//		System.out.println(log.toString());
		ai.invoke();
	}
}
