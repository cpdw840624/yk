package com.ryb.sns.service.impl;

import java.util.Date;

import com.dw.base.utils.date.DateUtil;
import com.jfinal.plugin.ioc.Service;
import com.ryb.common.model.Sns;
import com.ryb.sns.service.ISnsService;

@Service.BY_TYPE
public class SnsServiceImpl implements ISnsService {

	public String activeMachine(String caption, String schoolId, String mac,
			String ip) {
		Sns sns=Sns.dao.getByCaption(caption);
		if(sns==null){
			return "激活码错误";
		}
		if(sns.getInt("states").intValue()==1){
			return "此激活码未发放，暂不能使用";
		}
		if(sns.getInt("is_lock").intValue()==1){
			return "此激活码已被禁用";
		}
		if(sns.getDate("outof_times").compareTo(DateUtil.getTodayDate().getTime())<0){
			return "此激活码已过期";
		}
		if(sns.getInt("states").intValue()==3){
			return "此激活码已被激活，不能重复使用";
		}
		sns.set("states", 3);
		sns.set("activation_time", new Date());
		sns.set("activation_mac", mac);
		sns.set("activation_ip", ip);
		sns.update();
		return null;
	}

}
