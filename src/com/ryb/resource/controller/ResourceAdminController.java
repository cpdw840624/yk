package com.ryb.resource.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateUtil;

import com.alibaba.fastjson.JSONObject;
import com.dw.base.controller.BaseController;
import com.dw.base.utils.file.FileUtils;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.jfinalext.annotation.ControllerAnno;
import com.jfinalext.annotation.DataSource;
import com.jfinalext.annotation.DataSourceMap;
import com.qiniu.QiniuUtil;
import com.ryb.common.interceptor.AdminTokenInterceptor;
import com.ryb.common.model.Categorys;
import com.ryb.common.model.Info;
import com.ryb.common.model.Users;
import com.ryb.common.utils.ArrayUtils;
import com.ryb.common.utils.FileDecodeUtil;
import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;

@ControllerAnno(controllerkey="/admin/resource")
@Before({AdminTokenInterceptor.class})
public class ResourceAdminController extends BaseController {

	public static Properties pathConfig=	FileUtils.getPropertys("/path.properties");
	
	public void toManage(){
		try{
			renderJsp("resource/toManage.jsp");
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	public void queryCateTree(){
		try{
			List<Categorys> cateTree=Categorys.dao.queryList(null);
			renderJsonMsgResult(null, cateTree);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	public void queryInfos(){
		try{
			List<Info> infoList=Info.dao.queryList(this.getCommonModel(Info.class));
			renderJsonMsgResult(null, infoList);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	public void toAddOrModify(){
		try{
			String id=this.getPara("id");
			String cateId=this.getPara("cateId");
			String addOrModify="add";
			Info info=new Info();
			info.put("id",UUID.randomUUID().toString().replaceAll("-", ""));
			Categorys cate=null;
			List<String> cateList=new ArrayList<String>();
			if(id!=null){
				addOrModify="modify";
				info=Info.dao.findById(id);
				if(!StringUtil.stringIsNull(info.getStr("catethird_id"))){
					cateId=info.getStr("catethird_id");
				}else if(!StringUtil.stringIsNull(info.getStr("catesecond_id"))){
					cateId=info.getStr("catesecond_id");
				}else if(!StringUtil.stringIsNull(info.getStr("catefirst_id"))){
					cateId=info.getStr("catefirst_id");
				}
			}else{
				info.put("sort",Info.dao.getNextOrder(cateId));
			}
			if(cateId!=null){
				cate=Categorys.dao.findById(cateId);
			}
			if(cate!=null){
				info.put("catethird_id", cateId);
				do{
					cateList.add(cate.getStr("caption"));
					cate=Categorys.dao.findById(cate.getStr("parent_id"));
				}while(cate!=null);
			}
			Collections.reverse(cateList);
			
			setAttr("addOrModify",addOrModify);
			setAttr("info", info);
			setAttr("cateList",cateList);
			setAttr("infoJson", JsonKit.toJson(info));
			renderBaseJsp("resource/toAddOrModify.jsp");
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	public void addOrModify(){
		try{
			Users sessionUser=getSessionAttr("adminUser");
			String newFilepath=DateUtil.formatDate(new Date(), "yyyyMMdd");
			File iconImgDir=new File(pathConfig.getProperty("resource_img_path_root")+newFilepath);
			if(!iconImgDir.exists()){
				iconImgDir.mkdirs();
			}
			List<UploadFile> files=this.getFiles(pathConfig.getProperty("resource_img_path_root")+newFilepath);
			UploadFile file=null;
			String filenameNew=null;
			Info info=this.getModel(Info.class);
			Info infoOld=Info.dao.findById(info.getStr("id"));
			if(files!=null&&files.size()>0){
				file=files.get(0);
				filenameNew=FileUtils.createFileName();
				file.rename("", filenameNew, null);
			}
			
			List<Categorys> cateList=new ArrayList<Categorys>();
			Categorys cate=null;
			cate=Categorys.dao.findById(info.getStr("catethird_id"));
			if(cate!=null){
				do{
					cateList.add(new Categorys().setAttrs(cate));
					cate=Categorys.dao.findById(cate.getStr("parent_id"));
				}while(cate!=null);
			}
			Collections.reverse(cateList);
			
			if(infoOld==null){
				info.put("school_id",sessionUser.getStr("school_id"));
				info.put("school_name",sessionUser.getStr("school_name"));
				info.put("grammar_name","");
				info.put("catefirst_id",cateList.get(0).getStr("ID"));
				info.put("catefirst_name",cateList.get(0).getStr("caption"));
				info.put("catesecond_id",cateList.size()>=2?cateList.get(1).getStr("ID"):"");
				info.put("catesecond_name",cateList.size()>=2?cateList.get(1).getStr("caption"):"");
				info.put("catethird_id",cateList.size()>=3?cateList.get(2).getStr("ID"):"");
				info.put("catethird_name",cateList.size()>=3?cateList.get(2).getStr("caption"):"");
				info.put("user_id",sessionUser.getStr("sid"));
				info.put("user_name",sessionUser.getStr("opername"));
				info.put("update_user_id",sessionUser.getStr("sid"));
				info.put("update_uname",sessionUser.getStr("opername"));
				info.put("info_type",2);
				info.put("file_update_time",new Date());
				info.put("teach_suggest","");
				info.put("img_name",file.getOriginalFileName());
				info.put("img_newname",DateUtil.formatDate(new Date(), "yyyyMMdd")+"/"+filenameNew+"."+file.getExtName());
				info.put("img_hash","");
				info.put("mu_file_name","");
				info.put("mu_file_size",0);
				info.put("mu_file_path","");
				info.put("mu_file_hash","");
				info.put("mu_file_udpate_time",new Date());
				info.put("mu_file_exname","");
				info.put("create_time",new Date());
				info.save();
			}else{
				infoOld.set("info_name",info.getStr("info_name"));
				infoOld.set("sort",info.getInt("sort"));
				infoOld.set("school_id",sessionUser.getStr("school_id"));
				infoOld.set("school_name",sessionUser.getStr("school_name"));
				infoOld.set("catefirst_id",cateList.get(0).getStr("ID"));
				infoOld.set("catefirst_name",cateList.get(0).getStr("caption"));
				infoOld.set("catesecond_id",cateList.size()>=2?cateList.get(1).getStr("ID"):"");
				infoOld.set("catesecond_name",cateList.size()>=2?cateList.get(1).getStr("caption"):"");
				infoOld.set("catethird_id",cateList.size()>=3?cateList.get(2).getStr("ID"):"");
				infoOld.set("catethird_name",cateList.size()>=3?cateList.get(2).getStr("caption"):"");
				infoOld.set("user_id",sessionUser.getStr("sid"));
				infoOld.set("user_name",sessionUser.getStr("opername"));
				infoOld.set("update_user_id",sessionUser.getStr("sid"));
				infoOld.set("update_uname",sessionUser.getStr("opername"));
				if(info.get("file_path")!=null&&!info.getStr("file_path").equals(infoOld.getStr("file_path"))){
					infoOld.set("file_update_time",new Date());
					infoOld.set("file_name",info.getStr("file_name")); 
					infoOld.set("file_size",info.getStr("file_size")); 
					infoOld.set("file_exname",info.getStr("file_exname")); 
					infoOld.set("file_hash",info.getStr("file_hash")); 
					infoOld.set("file_path",info.getStr("file_path")); 
				}
				if(file!=null){
					infoOld.set("img_name",file.getOriginalFileName());
					infoOld.set("img_newname",DateUtil.formatDate(new Date(), "yyyyMMdd")+"/"+filenameNew+"."+file.getExtName());
					infoOld.set("img_hash","");
				}
				infoOld.update();
			}
			renderJsonMsgResult(null, null);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	public void delete(){
		try{
			Info infoOld=Info.dao.findById(this.getPara("id"));
			infoOld.set("dr", 1);
			infoOld.update();
			renderJsonMsgResult(null, null);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	@DataSource(type=DataSourceMap.MASTER)
	public void uploadIcon(){
		try{
			UploadFile file=this.getFile(PathKit.getWebRootPath()+File.separator+"uploadTemp");
			String resId=this.getPara("id");
			String filenameNew=FileUtils.createFileName();
			String newFilepath=DateUtil.formatDate(new Date(), "yyyyMMdd")+File.separator+filenameNew+".wyn";
			File encodeFile=new File(PathKit.getWebRootPath()+File.separator+"uploadTemp"+File.separator+filenameNew+".wyn");
			
			FileDecodeUtil.encodeFile(file.getFile(), encodeFile, resId);
			JSONObject result=QiniuUtil.uploadFile(encodeFile, newFilepath);
			
			encodeFile.delete();
			file.getFile().delete();
			
			renderJsonMsgResult(null, result);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}
	}
	
	@DataSource(type=DataSourceMap.MASTER)
	public void uploadFile(){
		int maxInactiveIntervalNow=this.getSession().getMaxInactiveInterval();
		try{
			this.getSession().setMaxInactiveInterval(-1);
			UploadFile file=this.getFiles(PathKit.getWebRootPath()+File.separator+"uploadTemp").get(0);
			//      2017/20170302/20170302135739.wyn
			//    七牛key：  20170302/20170302135739.wyn qiniu
			String resId=this.getPara("id");
			String filenameNew=FileUtils.createFileName();
			String dateStr=DateUtil.formatDate(new Date(), "yyyyMMdd");
			String newFilepath=dateStr+"/"+filenameNew+".wyn";
			File encodeFile=new File(PathKit.getWebRootPath()+File.separator+"uploadTemp"+File.separator+filenameNew+".wyn");
			
			FileDecodeUtil.encodeFile(file.getFile(), encodeFile, resId);
			//JSONObject result=QiniuUtil.uploadFile(encodeFile, dateStr+"/"+filenameNew+".wyn");
//		    $scope.info.file_name=file.name;
//		    $scope.info.file_size=file.size;
//		    $scope.info.file_exname="."+file.ext
//		    $scope.info.file_hash=ret.json.file.hash;
//		    $scope.info.file_path=ret.json.qiniuPath;
			
			File ftpdir=new File(pathConfig.getProperty("resource_file_path_root")+DateUtil.formatDate(new Date(), "yyyy")+File.separator+dateStr);
			if(!ftpdir.exists()){
				ftpdir.mkdirs();
			}
			encodeFile.renameTo(new File(pathConfig.getProperty("resource_file_path_root")+DateUtil.formatDate(new Date(), "yyyy")+File.separator+newFilepath));
			
			//encodeFile.delete();
		    JSONObject result=new JSONObject();
		    result.put("fileName", file.getFileName());
		    result.put("fileSize", file.getFile().length()/1024);
		    result.put("fileExname", "."+file.getExtName());
		    result.put("fileHash", "");
			result.put("qiniuPath", DateUtil.formatDate(new Date(), "yyyy")+"/"+newFilepath);
			file.getFile().delete();
			
			renderJsonMsgResult(null, result);
		}catch(Exception e){
			e.printStackTrace();
			renderSystemError(e);
		}finally{
			this.getSession().setMaxInactiveInterval(maxInactiveIntervalNow);
		}
	}
}
