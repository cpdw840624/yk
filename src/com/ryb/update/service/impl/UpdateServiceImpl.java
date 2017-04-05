package com.ryb.update.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dw.base.utils.file.FileUtils;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.plugin.ioc.Service;
import com.ryb.update.service.IUpdateService;

@Service.BY_TYPE
public class UpdateServiceImpl implements IUpdateService {
	
	public String refreshServerUpdateJson(String rootPath) {
		String serverJson=null;
		String updateDir=rootPath+File.separator+"WEB-INF"+File.separator+"update"+File.separator;
		File dir=new File(updateDir);
		if(!dir.exists()){
			FileUtils.createPath(updateDir);// 创建文件目录
		}
		
		String filepath=rootPath+File.separator+"WEB-INF"+File.separator+"update"+File.separator+"data.json";
		try {
			File file=new File(filepath);
			if(file.exists()){
				serverJson=FileUtils.readLineFile(filepath);
			}
		} catch (IOException e) {
		}
		if(StringUtil.stringIsNull(serverJson)){
			serverJson="{lastVer:0,files:[]}";
		}
		JSONObject server=JSONObject.parseObject(serverJson);
		boolean isChange=false;
		File filesDir=new File(updateDir+"files");
		List<File> files=new ArrayList<File>();
		readFiles(filesDir,files);
		
		outter:for(File file:files){
			JSONArray ja=server.getJSONArray("files");
			for(int i=0;i<ja.size();i++){
				JSONObject fj=ja.getJSONObject(i);
				if(fj.getString("path").equals(getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath()))){
					if(file.lastModified()!=fj.getLongValue("lastModified")){
						fj.put("ver", fj.getIntValue("ver")+1);
						fj.put("lastModified", file.lastModified());
						isChange=true;						
					}
					continue outter;
				}
			}
			JSONObject jo=JSONObject.parseObject("{path:\""+getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath())+"\",ver:1,lastModified:"+file.lastModified()+"}");
			ja.add(jo);
			isChange=true;
		}
		
		JSONArray ja=server.getJSONArray("files");
		outter:for(int i=0;i<ja.size();i++){
			JSONObject fj=ja.getJSONObject(i);
			for(File file:files){
				if(fj.getString("path").equals(getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath()))){
					continue outter;
				}
			}
			ja.remove(fj);
			isChange=true;
		}
		
		if(isChange){
			server.put("lastVer", server.getIntValue("lastVer")+1);
		}
		try {
			FileUtils.deleteFile(filepath);
			FileUtils.writeFile(filepath, server.toJSONString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String refreshUpdateJson(String rootPath,String clientJson) {
		JSONObject result=JSONObject.parseObject("{client:{},deleteList:[]},updateZip:\"\"");
		JSONObject client=JSONObject.parseObject(clientJson);
		String serverJson=null;
		String updateDir=rootPath+File.separator+"WEB-INF"+File.separator+"update"+File.separator;
		File dir=new File(updateDir);
		if(!dir.exists()){
			FileUtils.createPath(updateDir);// 创建文件目录
		}
		
		String filepath=rootPath+File.separator+"WEB-INF"+File.separator+"update"+File.separator+"data.json";
		try {
			File file=new File(filepath);
			if(file.exists()){
				serverJson=FileUtils.readLineFile(filepath);
			}
		} catch (IOException e) {
		}
		if(StringUtil.stringIsNull(serverJson)){
			serverJson="{lastVer:0.,files:[]}";
		}
		JSONObject server=JSONObject.parseObject(serverJson);
		
		
		
		boolean isChange=false;
		File filesDir=new File(updateDir+"files");
		List<File> files=new ArrayList<File>();
		readFiles(filesDir,files);
		
		outter:for(File file:files){
			JSONArray ja=server.getJSONArray("files");
			for(int i=0;i<ja.size();i++){
				JSONObject fj=ja.getJSONObject(i);
				if(fj.getString("path").equals(getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath()))){
					if(file.lastModified()!=fj.getLongValue("lastModified")){
						fj.put("ver", fj.getIntValue("ver")+1);
						fj.put("lastModified", file.lastModified());
						isChange=true;						
					}
					continue outter;
				}
			}
			JSONObject jo=JSONObject.parseObject("{path:\""+getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath())+"\",ver:1,lastModified:"+file.lastModified()+"}");
			ja.add(jo);
			isChange=true;
		}
		
		JSONArray ja=server.getJSONArray("files");
		outter:for(int i=0;i<ja.size();i++){
			JSONObject fj=ja.getJSONObject(i);
			for(File file:files){
				if(fj.getString("path").equals(getRelativePath(filesDir.getAbsolutePath(),file.getAbsolutePath()))){
					continue outter;
				}
			}
			ja.remove(fj);
			isChange=true;
		}
		
		if(isChange){
			server.put("lastVer", server.getIntValue("lastVer")+1);
		}
		try {
			FileUtils.deleteFile(filepath);
			FileUtils.writeFile(filepath, server.toJSONString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getRelativePath(String rootPath,String path){
		return path.replaceAll("\\\\", "/").replaceAll(rootPath.replaceAll("\\\\", "/"), "");
	}
	
	public static void main(String[] args) {
		UpdateServiceImpl us=new UpdateServiceImpl();
		us.refreshServerUpdateJson("E:\\data\\workspace7.5\\yeyBaiban\\webapps");
	}
	
	private void readFiles(File dir,List<File> files){
		for(File item:dir.listFiles()){
			if(item.isFile()){
				files.add(item);
			}else{
				readFiles(item, files);
			}
		}
	}

}
