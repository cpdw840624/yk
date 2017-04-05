package com.ryb.common.model;

import java.util.ArrayList;
import java.util.List;

import com.dw.base.model.BaseModel;
import com.dw.base.utils.string.StringUtil;
import com.jfinalext.annotation.ModelAnno;
import com.ryb.common.utils.ArrayUtils;
import com.ryb.common.utils.SpellQueryConditions;

@SuppressWarnings("serial")
@ModelAnno(tableName = "tb_infos", primaryKey = "id")
public class Info extends BaseModel<Info> {
	public static final Info dao=new Info();
	
	private Integer type;//info_type资源类型（1-教案，2-课件，3-资料，4-教具）
	private String parentId;//父级分类id列表
	private String secCertIds;//第二集分类id列表
	private String thirdCertIds;//第三集分类id列表
	/**
	 SELECT * FROM tb_infos
WHERE dr=0;

	 */
	
	public int getNextOrder(String catethird_id){
		StringBuffer sql=new StringBuffer();
		List params=new ArrayList();
		sql.append("select * from tb_infos where dr=0 ");
		sql.append(" and catethird_id=? ");
		params.add(catethird_id);
		sql.append(" order by sort desc");
		Info info=this.findFirst(sql.toString(), params.toArray());
		return info==null?1:(info.getInt("sort")+1);
	}
	
	public List<Info> queryList(Info info){
		StringBuffer sql=new StringBuffer();
		List params=new ArrayList();
		sql.append("select * from tb_infos where dr=0 ");
		if(info!=null){
			if(info.getType()!=null){
				sql.append(" and info_type=? ");
				params.add(info.getType());
			}
			if(!StringUtil.stringIsNull(info.getParentId())){
				sql.append(" and (catefirst_id=? or catesecond_id=? or catethird_id=?) ");
				params.add(info.getParentId());
				params.add(info.getParentId());
				params.add(info.getParentId());
			}
			if(!StringUtil.stringIsNull(info.getSecCertIds())){
				String[] ids=ArrayUtils.converArrStrToStr(info.getSecCertIds(), ",");
				if(ids!=null && ids.length>0){
					sql.append(" and ( "+SpellQueryConditions.getConditionSqlByIds(null,"catesecond_id",ids.length));
					for(String id:ids){
						params.add(id);
					}
					sql.append(" ) ");
				}
			}
			if(!StringUtil.stringIsNull(info.getThirdCertIds())){
				String[] ids=ArrayUtils.converArrStrToStr(info.getThirdCertIds(), ",");
				if(ids!=null && ids.length>0){
					sql.append(" and ( "+SpellQueryConditions.getConditionSqlByIds(null,"catethird_id",ids.length));
					for(String id:ids){
						params.add(id);
					}
					sql.append(" ) ");
				}
			}
		}
		sql.append("order by sort");
		return this.find(sql.toString(),params.toArray());
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSecCertIds() {
		return secCertIds;
	}
	public void setSecCertIds(String secCertIds) {
		this.secCertIds = secCertIds;
	}
	public String getThirdCertIds() {
		return thirdCertIds;
	}
	public void setThirdCertIds(String thirdCertIds) {
		this.thirdCertIds = thirdCertIds;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
