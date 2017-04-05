package com.ryb.common.interceptor;

import com.baomidou.kisso.web.handler.SSOHandlerInterceptor;
import com.baomidou.kisso.web.interceptor.KissoAbstractInterceptor;
import com.ryb.common.handler.DefaultSSOHandler;

public class DefaultSSOInterceptor extends KissoAbstractInterceptor {
	/* SSO 拦截控制器 */
	private SSOHandlerInterceptor handlerInterceptor;


	public SSOHandlerInterceptor getHandlerInterceptor() {
		if (handlerInterceptor == null) {
			return DefaultSSOHandler.getInstance();
		}
		return handlerInterceptor;
	}


	public void setHandlerInterceptor(SSOHandlerInterceptor handlerInterceptor) {
		this.handlerInterceptor = handlerInterceptor;
	}

}
