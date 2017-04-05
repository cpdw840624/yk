package com.ryb.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.dw.base.bean.BaseJSONResult;
import com.dw.base.bean.DubboResult;
import com.dw.base.constants.DubboResultConstant;
import com.dw.base.controller.BaseController;
import com.dw.base.utils.memcached.MemcachedUtil;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.ryb.common.interceptor.ApiAuthInterceptor;

public class ApiController extends BaseController {
	
	@Override
	public void renderJsonMsgResult(String msg,Object jsonObject){
		String dt=this.getPara("dt");
		if(StringUtil.stringIsNull(dt)||!dt.equals("jsonp")){
			super.renderJsonMsgResult(msg, jsonObject);
		}else{
			BaseJSONResult result=null;
			if(msg==null){
				result=new BaseJSONResult(true, "",null,jsonObject);
			}else{
				result=new BaseJSONResult(false, msg,null,jsonObject,this.getAsyncTokenId());
			}
			this.renderText(this.getPara("callback")+"("+JsonKit.toJson(result)+");");
		}
	}
	
	public void renderSystemError(Exception e){
		this.removeUnionCheck();
		e.printStackTrace();
		//此处调用邮件组件，发送异常通知
		this.setAttr("exception_on", 1);
		BaseJSONResult result=new BaseJSONResult(false,"系统错误，请与管理员联系！",null,null,this.getAsyncTokenId());
		String dt=this.getPara("dt");
		if(StringUtil.stringIsNull(dt)||!dt.equals("jsonp")){
			this.renderJson(result);
		}else{
			this.renderText(this.getPara("callback")+"("+JsonKit.toJson(result)+");");
		}
	}

	/**
	 * 账号认证
	 * @return
	 */
	public DubboResult isTokenRight(){
		/**
		Account user=Account.dao.findById(this.getParaToInt("accountId"));
		if(user!=null&&user.getStr("aToken").equals(this.getPara("accountToken"))){
			return null;
		}
		return new DubboResult(99, "账号认证错误");
		*/
		return null;
	}

	/**
	 * 客户端重复提交验证码
	 * 每次进入信息发送页面后，需要更新unionCheck值；
	 * 如果因某些错误需要重复发送，或由于客户端本身原因导致错误，如网络超时、网络异常等，而没有接收到服务器返回信息时，此时再次发送前，无需更新unionCheck值
	 * @return 是否重复提交
	 */
	public DubboResult isDuplicateSubmit(){
		String unionCheck=this.getPara("unionCheck");
		System.out.println("unionCheck="+unionCheck);
		if(StringUtil.stringIsNull(unionCheck)){
			return null;
		}else{
			
			Object unionCheckObj=MemcachedUtil.getInstance().get("unionCheck_"+this.getParaToInt("accountId")+"_"+unionCheck);
			if(unionCheckObj==null){
				MemcachedUtil.getInstance().add("unionCheck_"+this.getParaToInt("accountId")+"_"+unionCheck, 1, 1);
				return null;
			}else{
				return new DubboResult(90, "当前操作已经提交服务器处理，请勿重复操作");
			}
		}
	}
	
	public void removeUnionCheck(){
		String unionCheck=this.getPara("unionCheck");
		if(!StringUtil.stringIsNull(unionCheck)){
			MemcachedUtil.getInstance().delete("unionCheck_"+this.getParaToInt("accountId")+"_"+unionCheck);
		}
	}

	public String getUnionCheck(){
		String unionCheck=this.getPara("unionCheck");
		return StringUtil.stringIsNull(unionCheck)?"":unionCheck;
	}
}
