package com.qiniu;

import java.util.UUID;
import org.json.JSONException;
import com.qiniu.Mac;
import com.qiniu.Config;
import com.qiniu.PutPolicy;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class Uptoken {
	public final static String makeUptoken() throws AuthException,
			JSONException {
		Auth auth = Auth.create(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 可以根据自己需要设置过期时间,sdk默认有设置，具体看源码
		// putPolicy.expires = getDeadLine();
		//putPolicy.returnUrl = "http://localhost:8080/rybEmpAuth/QiNiuCallback.jsp";
		//putPolicy.returnBody = "{\"name\": $(fname),\"size\": \"$(fsize)\",\"w\": \"$(imageInfo.width)\",\"h\": \"$(imageInfo.height)\",\"key\":$(etag)}";
		String uptoken = auth.uploadToken(Config.bucketName);
		//String uptoken = auth.uploadToken(bucketName,null,3600,new StringMap()
        //.put("callbackUrl","http://localhost:8080/rybEmpAuth/QiNiuCallback.jsp")
        //.put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
		return uptoken;
	}

	/**
	 * 生成32位UUID 并去掉"-"
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
