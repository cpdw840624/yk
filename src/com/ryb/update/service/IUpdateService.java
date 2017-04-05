package com.ryb.update.service;

public interface IUpdateService {
	
	public String refreshServerUpdateJson(String rootPath);
	
	public String refreshUpdateJson(String rootPath,String clientJson);
}
