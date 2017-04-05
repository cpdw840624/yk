package com.ryb.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ApiOriginInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		inv.invoke();
		inv.getController().getResponse().addHeader("Access-Control-Allow-Origin","*");
	}

}
