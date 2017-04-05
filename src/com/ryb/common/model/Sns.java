package com.ryb.common.model;

import com.dw.base.model.BaseModel;
import com.jfinalext.annotation.ModelAnno;

@SuppressWarnings("serial")
@ModelAnno(tableName = "tb_sns", primaryKey = "id")
public class Sns extends BaseModel<Sns> {
	public static final Sns dao=new Sns();
	
	/**
	 * states状态(1-未发放,2-已发放,3-已激活)
	 */
	
	public Sns getByCaption(String caption){
		return this.findFirst("select * from tb_sns where dr=0 and caption=? ", caption);
	}
}
