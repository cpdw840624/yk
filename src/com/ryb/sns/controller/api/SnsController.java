package com.ryb.sns.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ioc.Ioc;
import com.jfinalext.annotation.ControllerAnno;
import com.jfinalext.annotation.DataSource;
import com.jfinalext.annotation.DataSourceMap;
import com.ryb.common.controller.ApiController;
import com.ryb.common.interceptor.ApiAuthInterceptor;
import com.ryb.common.model.Sns;
import com.ryb.sns.service.ISnsService;

@ControllerAnno(controllerkey="/api/sns")
@Before({ApiAuthInterceptor.class})
public class SnsController extends ApiController {
	
	@Ioc.BY_TYPE
	private ISnsService snsService;
	
	@DataSource(type=DataSourceMap.MASTER)
	public void activeMachine(){
		try{
			String caption=this.getPara("caption");
			String schoolId=this.getPara("schoolId");
			String mac=this.getPara("mac");
			String ip=this.getPara("ip");
			
			String msg=null;
			msg=snsService.activeMachine(caption, schoolId, mac, ip);
			
			Sns sns=Sns.dao.getByCaption(caption);
			this.renderJsonMsgResult(msg, sns);
		} catch (Exception e) {
			renderSystemError(e);
		}
	}


}
