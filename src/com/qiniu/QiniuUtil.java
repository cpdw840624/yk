package com.qiniu;

import java.io.File;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

public class QiniuUtil {
	
	public static JSONObject uploadFile(File file, String newFilename) {
		JSONObject result=new JSONObject();
		UploadManager uploadManager = new UploadManager();
		try {
			// 调用put方法上传
			Response res = uploadManager.put(file, newFilename, Uptoken.makeUptoken());
			// 响应的文本信息
			result.put("result", true);
			JSONObject fileJson=JSONObject.parseObject(res.bodyString());
			fileJson.put("key", Config.VIEW_HOST+"/"+fileJson.getString("key"));
			result.put("file", fileJson);
			return result;
		} catch (QiniuException e) {
			Response r = e.response;
		} catch (AuthException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("result", false);
		return result;
	}
	
	public static void main(String[] args) {
		QiniuUtil.uploadFile(new File("E:\\1.png"), "ttt.png");
	}
}
