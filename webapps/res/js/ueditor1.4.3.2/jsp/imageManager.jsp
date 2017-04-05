<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,com.qiniu.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%
	Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	RSFClient client = new RSFClient(mac);
	ListPrefixRet list = client.listPrifix("rybbase", "", "", 10);
	StringBuffer sb = new StringBuffer();
	for (ListItem item : list.results) {
		sb.append("/");
		sb.append("http://7xv9y6.com2.z0.glb.qiniucdn.com"+item.key);
		sb.append("ue_separate_ue");
	}
	String imgStr = sb.toString();
	if (imgStr != "") {
		imgStr = imgStr
				.substring(0, imgStr.lastIndexOf("ue_separate_ue"))
				.replace(File.separator, "/").trim();
	}
	out.print(imgStr);
%>
