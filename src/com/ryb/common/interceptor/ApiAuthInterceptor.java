package com.ryb.common.interceptor;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.dw.base.bean.BaseJSONResult;
import com.dw.base.utils.date.DateUtil;
import com.dw.base.utils.file.FileUtils;
import com.dw.base.utils.memcached.MemcachedUtil;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.ryb.common.DemoConfig;
import com.ryb.common.controller.ApiController;

public class ApiAuthInterceptor implements Interceptor {
	private static Integer apiLimitTimelong;//api请求频率限制（秒），0不限制
	private static Long apiLimitCount;//api单位时间请求次数上限
	private static Integer apiLimitBlockTime;//api请求超限后，禁用秒数
	private static Boolean isWriteLog;
	
	static{
		apiLimitTimelong=Integer.valueOf(DemoConfig.p.getProperty("api_limit_timelong", "0"));
		apiLimitCount=Long.valueOf(DemoConfig.p.getProperty("api_limit_count", "5"));
		apiLimitBlockTime=Integer.valueOf(DemoConfig.p.getProperty("api_limit_block_time", "60"));
		isWriteLog=Boolean.valueOf(DemoConfig.p.getProperty("is_write_log", "false"));
	}
	
	public void intercept(Invocation inv) {
		inv.invoke();
	}

}
