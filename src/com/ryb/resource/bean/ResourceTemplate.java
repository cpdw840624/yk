package com.ryb.resource.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.dw.base.model.BaseModel;
import com.ryb.common.model.Categorys;

public class ResourceTemplate {
	
	private static Map<String, BaseModel> templateMap=new HashMap<String, BaseModel>();//当前页模板
	private static Map<String, BaseModel> parentTemplateMap=new HashMap<String, BaseModel>();//按父级分类模板（此节点的子级按此模板）
	private static Map<String, BaseModel> specialCatelogMap=new HashMap<String, BaseModel>();
	private static Map<String, BaseModel> specialFileMap=new HashMap<String, BaseModel>();
	
	static{
		//根目录模板
        BaseModel template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",500);
        template.put("startleft",117);
        template.put("colspan",81);
        template.put("rowspan",83);
        template.put("pagesize",12);
        template.put("btnwidth",200);
        template.put("btnheight",65);
        template.put("rowsize",4);
        template.put("fontcolor","#578948");
        template.put("bgUrl","bg/20170103/folder_root_default.jpg");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        List<Categorys> specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        templateMap.put("root", template);
        BaseModel fileRootTemplate=getNewFrom(template);
        fileRootTemplate.put("starttop",238);
        fileRootTemplate.put("startleft",114);
        fileRootTemplate.put("colspan",81);
        fileRootTemplate.put("rowspan",84);
        fileRootTemplate.put("pagesize",12);
        fileRootTemplate.put("btnwidth",201);
        fileRootTemplate.put("btnheight",65);
        fileRootTemplate.put("rowsize",4);
        fileRootTemplate.put("fontcolor","#578948");
        fileRootTemplate.put("bgUrl","bg/20170103/files_bg_litikecheng.jpg");
        fileRootTemplate.put("btnType", 3);
        templateMap.put("fileroot", fileRootTemplate);
        
        //24:托班立体课程
        //25:小班立体课程
        //26:中班立体课程
        //27:大班立体课程
        template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",400);
        template.put("startleft",267);
        template.put("colspan",80);
        template.put("rowspan",52);
        template.put("pagesize",10);
        template.put("btnwidth",80);
        template.put("btnheight",90);
        template.put("rowsize",5);
        template.put("fontcolor","#f2926e");
        template.put("bgUrl","bg/20170103/folder_bg_tuoban.jpg");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        parentTemplateMap.put("24_1", template);
        template=getNewFrom(template);
        template.put("bgUrl","bg/20170103/folder_bg_xiaoban.jpg");
        template.put("fontcolor","#f2926e");
        parentTemplateMap.put("25_1", template);
        template=getNewFrom(template);
        template.put("bgUrl","bg/20170103/folder_bg_zhongban.jpg");
        template.put("fontcolor","#e79200");
        parentTemplateMap.put("26_1", template);
        template=getNewFrom(template);
        template.put("bgUrl","bg/20170103/folder_bg_daban.jpg");
        template.put("fontcolor","#177db5");
        parentTemplateMap.put("27_1", template);

        //28:立体课程研训
        template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",495);
        template.put("startleft",222);
        template.put("colspan",56);
        template.put("rowspan",52);
        template.put("pagesize",4);
        template.put("btnwidth",166);
        template.put("btnheight",48);
        template.put("rowsize",4);
        template.put("fontcolor","#f2926e");
        template.put("bgUrl","bg/20170103/folder_bg_tuoban.jpg");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        parentTemplateMap.put("28_1", template);
        template=getNewFrom(template);//二级
        template.put("startleft",427);
        template.put("colspan",92);
        template.put("pagesize",2);
        template.put("rowsize",2);
        parentTemplateMap.put("28_2", template);
        
        //31:小小工程师
        template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",313);
        template.put("startleft",324);
        template.put("colspan",118);
        template.put("rowspan",52);
        template.put("pagesize",6);
        template.put("btnwidth",130);
        template.put("btnheight",130);
        template.put("rowsize",3);
        template.put("fontcolor","#0574a9");
        template.put("bgUrl","bg/20170103/folder_bg_gongchengshi.jpg");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        parentTemplateMap.put("31_1", template);

        //31:小小工程师-2级
        template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",337);
        template.put("startleft",163);
        template.put("colspan",51);
        template.put("rowspan",93);
        template.put("pagesize",12);
        template.put("btnwidth",200);
        template.put("btnheight",60);
        template.put("rowsize",4);
        template.put("fontcolor","#0574a9");
        template.put("bgUrl","");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        parentTemplateMap.put("31_2", template);

//        template=new Categorys();
//        //starttop:0,colspan:100,rowspan:100,rowsize:4
//        template.put("starttop",456);
//        template.put("startleft",336);
//        template.put("colspan",56);
//        template.put("rowspan",54);
//        template.put("pagesize",6);
//        template.put("btnwidth",166);
//        template.put("btnheight",48);
//        template.put("rowsize",5);
//        template.put("fontcolor","#f2926e");
//        template.put("bgUrl","bg/20170103/folder_bg_gongchengshi.jpg");
//        template.put("btnFolderBgUrl","");
//        template.put("btnFileBgUrl","");
//        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
//        specialBtns=new ArrayList<Categorys>();
//        template.put("specialBtns",specialBtns);
//        parentTemplateMap.put("24_2", template);

        //24:托班立体课程-2级
        //25:小班立体课程-2级
        //26:中班立体课程-2级
        //27:大班立体课程-2级
        template=new Categorys();
        //starttop:0,colspan:100,rowspan:100,rowsize:4
        template.put("starttop",361);
        template.put("startleft",223);
        template.put("colspan",56);
        template.put("rowspan",87);
        template.put("pagesize",12);
        template.put("btnwidth",166);
        template.put("btnheight",48);
        template.put("rowsize",4);
        template.put("fontcolor","#f2926e");
        template.put("bgUrl","");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        parentTemplateMap.put("24_2", template);
        template=getNewFrom(template);
        parentTemplateMap.put("25_2", template);
        template=getNewFrom(template);
        template.put("fontcolor","#e79200");
        parentTemplateMap.put("26_2", template);
        template=getNewFrom(template);
        template.put("fontcolor","#177db5");
        parentTemplateMap.put("27_2", template);

        //35\36:托班立体课程，上、下学期休闲一刻下级模板
        template=new Categorys();
        template.put("starttop",455);
        template.put("startleft",224);
        template.put("colspan",55);
        template.put("rowspan",52);
        template.put("pagesize",4);
        template.put("btnwidth",166);
        template.put("btnheight",48);
        template.put("rowsize",4);
        template.put("fontcolor","#f2926e");
        template.put("bgUrl","");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        templateMap.put("35", template);
        templateMap.put("36", template);

        //3104\3108:小小工程师、上下学期研训下级模板
        template=new Categorys();
        template.put("starttop",445);
        template.put("startleft",224);
        template.put("colspan",112);
        template.put("rowspan",52);
        template.put("pagesize",3);
        template.put("btnwidth",202);
        template.put("btnheight",65);
        template.put("rowsize",3);
        template.put("fontcolor","#0574a9");
        template.put("bgUrl","");
        template.put("btnFolderBgUrl","");
        template.put("btnFileBgUrl","");
        template.put("btnType",2);//按钮类型（1文字+背景按钮、2图标按钮、3图标+下方文字按钮）
        specialBtns=new ArrayList<Categorys>();
        template.put("specialBtns",specialBtns);
        templateMap.put("3104", template);
        templateMap.put("3108", template);
       
        //35\36:托班立体课程，上、下学期休闲一刻
        template=new Categorys();
        template.put("starttop",646);
        template.put("startleft",372);
        template.put("btnwidth",202);
        template.put("btnheight",69);
        template.put("fontcolor","#f2926e");
        specialCatelogMap.put("35", template);
        template=new Categorys();
        template.put("starttop",646);
        template.put("startleft",670);
        template.put("btnwidth",202);
        template.put("btnheight",69);
        template.put("fontcolor","#f2926e");
        specialCatelogMap.put("36", template);
        //3104：小小工程师-上学期研训
        template=new Categorys();
        template.put("starttop",686);
        template.put("startleft",389);
        template.put("btnwidth",202);
        template.put("btnheight",69);
        template.put("fontcolor","#0574a9");
        template.put("btnType",2);
        specialCatelogMap.put("3104", template);
        //3108：小小工程师-下学期研训
        template=new Categorys();
        template.put("starttop",686);
        template.put("startleft",687);
        template.put("btnwidth",202);
        template.put("btnheight",69);
        template.put("fontcolor","#0574a9");
        template.put("btnType",2);
        specialCatelogMap.put("3108", template);
	}
	
	public static BaseModel getParentTemplateByCategoryId(String id){
		return parentTemplateMap.get(id);
	}
	public static BaseModel getTemplateByCategoryId(String id){
		return templateMap.get(id);
	}
	public static BaseModel getSpecialByCategoryId(String id){
		return specialCatelogMap.get(id);
	}
	public static BaseModel getSpecialByFileId(String id){
		return specialFileMap.get(id);
	}
	
	private static BaseModel getNewFrom(BaseModel old){
		BaseModel newModel=new Categorys();
		for (Entry<String, Object> e : (Set<Entry<String, Object>>)old.getAttrsEntrySet()){
			newModel.put(e.getKey(), e.getValue());
		}
		return newModel;
	}
}
