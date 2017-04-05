package com.ryb.common.model;

import java.util.ArrayList;
import java.util.List;

import com.dw.base.model.BaseModel;
import com.dw.base.utils.string.StringUtil;
import com.jfinalext.annotation.ModelAnno;
import com.ryb.common.utils.ArrayUtils;
import com.ryb.common.utils.SpellQueryConditions;

@SuppressWarnings("serial")
@ModelAnno(tableName = "tb_categorys", primaryKey = "ID")
public class Categorys extends BaseModel<Categorys> {
	public static final Categorys dao=new Categorys();
	
	private String ids;//id列表
	private String parentIds;//父节点列表
	/**
	 * SELECT * FROM tb_categorys
WHERE dr=0;
	 */
	
	public List<Categorys> queryList(Categorys categorys){
		StringBuffer sql=new StringBuffer();
		List params=new ArrayList();
		sql.append("select * from tb_categorys where dr=0 ");
		if(categorys!=null){
			if(!StringUtil.stringIsNull(categorys.getIds())){
				String[] ids=ArrayUtils.converArrStrToStr(categorys.getIds(), ",");
				if(ids!=null && ids.length>0){
					sql.append(" and ( "+SpellQueryConditions.getConditionSqlByIds(null,"ID",ids.length));
					for(String id:ids){
						params.add(id);
					}
					sql.append(" ) ");
				}
			}
			if(!StringUtil.stringIsNull(categorys.getParentIds())){
				String[] ids=ArrayUtils.converArrStrToStr(categorys.getParentIds(), ",");
				if(ids!=null && ids.length>0){
					sql.append(" and ( "+SpellQueryConditions.getConditionSqlByIds(null,"parent_id",ids.length));
					for(String id:ids){
						params.add(id);
					}
					sql.append(" ) ");
				}
			}
		}
		sql.append(" order by lev_id,sort");
		return this.find(sql.toString(),params.toArray());
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
}
