package com.ryb.resource.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.dw.base.model.BaseModel;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.aop.Before;
import com.jfinalext.annotation.ControllerAnno;
import com.jfinalext.annotation.DataSource;
import com.jfinalext.annotation.DataSourceMap;
import com.ryb.common.controller.ApiController;
import com.ryb.common.interceptor.ApiAuthInterceptor;
import com.ryb.common.model.Categorys;
import com.ryb.common.model.Info;
import com.ryb.common.model.Schools;
import com.ryb.resource.bean.ResourceTemplate;

@ControllerAnno(controllerkey="/api/resource")
@Before({ApiAuthInterceptor.class})
public class ResourceController extends ApiController {
	
	
	@DataSource(type=DataSourceMap.SLAVE)
	public void querySchoolResList(){
		try{
			String schoolId=this.getPara("schoolId");
			Schools schools=Schools.dao.findById(schoolId);
			String cateIds=schools.getStr("cate_power").replaceAll(";", ",");
			//cateIds="25,2502,250202,250203";
			Categorys params=new Categorys();
			params.setIds(cateIds);
			List<Categorys> cateList=Categorys.dao.queryList(params);
			//获取二级以下权限分类，因为权限分配只控制了2级
			String parentIds="";
			for(Categorys c:cateList){
				if(c.get("lev_id")!=null&&c.getInt("lev_id").intValue()==2){
					if(!parentIds.equals("")){
						parentIds+=",";
					}
					parentIds+=c.getStr("ID");
				}
			}
			params=new Categorys();
			params.setParentIds(parentIds);
			List<Categorys> cateListSub=Categorys.dao.queryList(params);
			cateList.addAll(cateListSub);
			//获取权限范围内的课件资源
			Info infoParam=new Info();
			infoParam.setSecCertIds(parentIds);
			infoParam.setType(2);//课件
			List<Info> fileList=Info.dao.queryList(infoParam);
			
			/**
			 * 转树形结构
			 */
	        List<Categorys> rootTrees = new ArrayList<Categorys>();
	        for(Categorys node1 : cateList){  
	        	//课件文件插入
	        	for(Info file:fileList){
	        		String cateId=null;
	        		String thirdId=file.getStr("catethird_id");
	        		String secondId=file.getStr("catesecond_id");
	        		String firstId=file.getStr("catefirst_id");
	        		if(!StringUtil.stringIsNull(thirdId)){
	        			cateId=thirdId;
	        		}else if(!StringUtil.stringIsNull(secondId)){
	        			cateId=thirdId;
	        		}else if(!StringUtil.stringIsNull(firstId)){
	        			cateId=thirdId;
	        		}
	        		if(cateId==null||!node1.getStr("ID").equals(cateId)){
	        			continue;
	        		}else{
	        			List<Info> files=(List<Info>)node1.get("files");
	        			if(files==null){
	        				files=new ArrayList<Info>();
	        				node1.put("files",files);
	        			}
	        			files.add(file);
	        		}
	        	}
	            boolean mark = false;  
	            for(Categorys node2 : cateList){  
	                if(!node1.getStr("parent_id").equals("0") && node1.getStr("parent_id").equals(node2.getStr("ID"))){  
	                    mark = true;  
	                    if(node2.get("childs") == null){
	                        node2.put("childs",new ArrayList<Categorys>());  
	                    }
	                    List<Categorys> childs=(List<Categorys>)node2.get("childs");
	                    childs.add(node1);
	                    break;  
	                }  
	            }  
	            if(!mark){  
//	            	BaseModel nodeTemplate=ResourceTemplate.getTemplateByCategoryId(node1.getStr("ID"));
//	            	if(nodeTemplate!=null){
//	            		node1.put("template",nodeTemplate);
//	            	}
	            	rootTrees.add(node1);
	            }
	        }
	        Categorys res=new Categorys();
	        res.put("ID", null);
	        res.put("lev_id", 0);
	        
	        res.put("template",ResourceTemplate.getTemplateByCategoryId("root"));
	        res.put("fileTemplate",ResourceTemplate.getTemplateByCategoryId("fileroot"));
	        res.put("img_newname","");
	        res.put("bg_url1","");
	        res.put("childs",rootTrees);
	        
	        //处理特殊节点
	        makeSpecialCatelog(null,res,rootTrees);
	        
			this.renderJsonMsgResult(null, res);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}

	private void makeSpecialCatelog(String rootId,Categorys parent,List<Categorys> childs){
		if(childs!=null&&childs.size()>0){
			for (int i=childs.size()-1;i>=0;i--) {
				Categorys cate = childs.get(i);
				List<Categorys> cc=(List<Categorys>)cate.get("childs");
				String currRootId=new String(rootId==null?cate.getStr("ID"):rootId);
				if(cc!=null&&cc.size()>0){
					makeSpecialCatelog(currRootId,cate,cc);
				}
	        	//插入模板
	        	BaseModel parentTemplate=ResourceTemplate.getParentTemplateByCategoryId(currRootId+"_"+cate.getInt("lev_id").toString());
	        	if(parentTemplate!=null){
	        		cate.put("template", parentTemplate);
	        	}
	        	BaseModel template=ResourceTemplate.getTemplateByCategoryId(cate.getStr("ID"));
	        	if(template!=null){
	        		cate.put("template", template);
	        	}
				BaseModel specialCate=ResourceTemplate.getSpecialByCategoryId(cate.getStr("ID"));
				if(specialCate!=null){
					childs.remove(i);
					//cate.setAttrs(specialCate);
					for (Entry<String, Object> e : (Set<Entry<String, Object>>)specialCate.getAttrsEntrySet()){
						cate.put(e.getKey(), e.getValue());
					}
					
//					BaseModel template=(BaseModel)parent.get("template");
//					if(parent.get("template")==null){
//						//根目录模板
//				        template=new Categorys();
//				        parent.put("template",template);
//					}
					List<BaseModel> specialBtns=(List<BaseModel>)parent.get("specialBtns");
					if(specialBtns==null){
						specialBtns=new ArrayList<BaseModel>();
						parent.put("specialBtns",specialBtns);						
					}
					specialBtns.add(cate);
				}
			}
		}
	}
}
