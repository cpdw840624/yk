package com.ryb.common.utils;

import com.dw.base.utils.string.StringUtil;

public class SpellQueryConditions {
	/**
	 * 描述：将数组ID转换成id串，用于in查询
	 * wang 2014-1-6 上午10:56:53
	 * @param prx  前缀  表的别名
	 * @param column  列
	 * @param size  选中数组大小
	 * @return
	 */
	public static String getConditionSqlByIds(String prx,String column,Integer size){
		StringBuffer ids=new StringBuffer();
		for(int i=0;i<size;i++){
			if(i==0){
				if (!StringUtil.stringIsNull(prx)) {
					ids.append(prx);
					ids.append(".");
				}
				ids.append(column);
				ids.append(" in(?");
			}
			if(i==(size-1)){
				ids.append(") ");
			}else{
				ids.append(",?");
			}
		}
		return ids.toString();
	}
}
