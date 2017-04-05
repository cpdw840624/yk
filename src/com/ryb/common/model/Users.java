package com.ryb.common.model;

import com.dw.base.model.BaseModel;
import com.jfinalext.annotation.ModelAnno;

@SuppressWarnings("serial")
@ModelAnno(tableName = "tb_users", primaryKey = "operid")
public class Users extends BaseModel<Users> {
	public static final Users dao=new Users();
	
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	public Users getByUsername(String username){
		return this.findFirst("select * from tb_users where isfront=1 and dr=0 and opername=? ", username);
	}
	
	public Users getByAdminUsername(String username){
		return this.findFirst("select * from tb_users where isback=1 and dr=0 and opername=? ", username);
	}
}
