package com.ryb.common.model;

import com.dw.base.model.BaseModel;
import com.jfinalext.annotation.ModelAnno;

@SuppressWarnings("serial")
@ModelAnno(tableName = "tb_schools", primaryKey = "ID")
public class Schools extends BaseModel<Schools> {
	public static final Schools dao=new Schools();
	
	/**
	 * cate_power:园所课程资源权限
	 */
}
