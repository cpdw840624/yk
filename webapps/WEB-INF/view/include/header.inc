<%@ page language="java" import="java.util.*" import="javax.servlet.ServletResponse" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%String contextPath = request.getContextPath(); %>
<%String projectRoot = contextPath;
String cachePath = contextPath; 
%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<script src="<%=cachePath %>/res/js/jquery/jquery-1.11.3.min.js"></script>
    <script src="<%=cachePath %>/res/js/ie10-viewport-bug-workaround.js"></script>
	<script src="<%=cachePath %>/res/js/angular.min.js"></script>
	<script type="text/javascript" src="<%=cachePath %>/res/js/ueditor1.4.3.2/angular-ueditor.js"></script>
	<script src="<%=cachePath %>/res/js/bootstrap.min.js"></script>
	<script src="<%=cachePath %>/res/js/bootstrapQ.min.js"></script>
	<script src="<%=cachePath %>/res/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=cachePath %>/res/js/app.js"></script>
	<script src="<%=cachePath %>/res/js/layer/layer.js"></script>

    <link href="<%=cachePath %>/res/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=cachePath %>/res/css/bootstrap-reset.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=cachePath %>/res/css/dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="<%=cachePath %>/res/js/ie-emulation-modes-warning.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var cachePath = "<%=cachePath %>";
var async_token_static = "${asyncTokenId}";
var sync_token_from_session = function (asyncTokenId) {
	async_token_static = asyncTokenId;
	$("input[name='async_token_static']").attr("value", asyncTokenId);
};

$().ready(function(){
	if ($("input[name='async_token_static']").length > 0) {
		$("input[name='async_token_static']").attr("value", async_token_static);
	}
});
</script>