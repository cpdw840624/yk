package com.ryb.common.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.web.handler.SSOHandlerInterceptor;

public class DefaultSSOHandler implements SSOHandlerInterceptor {
	private static DefaultSSOHandler handler;

	/**
	 * new 当前对象
	 */
	public static DefaultSSOHandler getInstance() {
		if (handler == null) {
			handler = new DefaultSSOHandler();
		}
		return handler;
	}

	public boolean preTokenIsNullAjax(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=" + SSOConfig.getSSOEncoding());
			response.setStatus(401);/* 401 未登录授权访问提示 */
			PrintWriter out = response.getWriter();
			out.print("prompt login exception, please login again.");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean preTokenIsNull(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

}
