package com.ryb.user.service;

public interface IUserService {

	public String login(String schoolId,String username,String password);
	public String loginAdmin(String username,String password);
	public String modifyPassword(Integer id,String oldPassword,String newPassword);
	public String logout(Integer id);
}
