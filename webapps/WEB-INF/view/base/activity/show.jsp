<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" import="javax.servlet.ServletResponse" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<c:if test="${isMobileDevice}">
	     	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	    	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		</c:if>
    	<title>${activity.actTitle }</title>
	</head>
	<body>
		<c:if test="${!isMobileDevice}"><div style="margin: 0 auto;width:600px;"></c:if>
			<center><h1>${activity.actTitle }</h1></center>
			<div style="float:right;padding-bottom:20px;">
				<fmt:formatDate value="${activity.actCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
			<HR style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=1 />
			<pre>
				${activity.actContent }
			</pre>
		<c:if test="${!isMobileDevice}"></div></c:if>
	</body>
</html>