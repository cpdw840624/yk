<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" import="javax.servlet.ServletResponse" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/WEB-INF/view/include/header.inc"%>
    	<title></title>
		<script type="text/javascript" src="<%=cachePath %>/res/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){

		});
	</script>
	</head>
	<body ng-app="myApp" ng-controller="myCtrl">
		<table style="border: 0;">
			<tr>
				<td style="vertical-align: top;">
					<ul>
					<c:forEach items="${skList}" var="sk">
						<li ng-click="changeSystem('${sk.skId}','${sk.skFilterType}')">${sk.skSystemName }(${sk.skFilterType==0?'白名单':(sk.skFilterType==1?'黑名单':'未指定')})</li>
					</c:forEach>
					</ul>
				</td>
				<td style="vertical-align: top;">
					<div ng-if="schoolList.length!=0&&currentSkId!=''">
						<button ng-click="modifySystemSchool()">保存</button>
						<input type="text" ng-model="searchKey" />
					</div>
					<div id="schoolList">
						<ul>
							<li ng-repeat="sch in schoolList|filter:search">
								<input type="checkbox" schId="{{sch.schId}}" name="isSpecials" value="1" ng-checked="isShow(sch)" ng-click="checkChange(sch,$event)" />
								&nbsp;&nbsp;
								<span ng-bind="sch.schName"></span>
								（<span ng-bind="sch.schCode"></span>）
							</li>
						</ul>
					</div>
					<div ng-if="schoolList.length==0">
						<span ng-bind="errorMsg">未找到园所信息</span>
					</div>
				</td>
			</tr>
		</table>
<script type="text/javascript">
var accountId='${accountId}';
var accountToken='${accountToken}';
var key='${key}';
app.controller('myCtrl', function($scope) {
	$scope.isShow=function(sch){
		if(sch.skFilterType==0){
			return sch.isShow==1;
		}else if(sch.skFilterType==1){
			return sch.isShow==0;
		}
		return false;
	};

	$scope.checkChange=function(sch,$event){
		var checkbox = $event.target;
		if(sch.skFilterType==0){
			if(checkbox.checked){
				sch.isShow=1;
			}else{
				sch.isShow=0;
			}
		}else if(sch.skFilterType==1){
			if(checkbox.checked){
				sch.isShow=0;
			}else{
				sch.isShow=1;
			}
		}
		
	};

	$scope.search=function(e){
		if($scope.searchKey==''){
			return true;
		}
		if(e.schName.indexOf($scope.searchKey)!=-1
				||e.schCode.indexOf($scope.searchKey)!=-1){
			return true;
		}
	};
	$scope.changeSystem=function(skId,skFilterType){
		if(skFilterType==''){
			$scope.currentSkId='';
			$scope.schoolList=[];
			$scope.searchKey='';
			$scope.errorMsg='当前系统未指定过滤类型';
			return;
		}
		$scope.currentSkId=skId;
		var url=contextPath+"/base/admin/school/querySystemSchoolList";
		var params=new Array();
		params.push("accountId="+accountId);
		params.push("accountToken="+accountToken);
		params.push("school.skId="+skId);
		$scope.errorMsg='未找到园所信息';
		$scope.schoolList=[];
		$scope.searchKey='';
		$.ajax({
			async: false,
			// 后台处理程序
			url : url,
			// 数据发送方式
			type : "post",
			// 接受数据格式
			dataType : "json",
			// 要传递的数据
			data : params.join('&'),
			// 回传函数
			success : function(ret){
				if(ret){
					if(ret.result){
						$scope.schoolList=ret.json;
					}
				}
			}
		});
	};
	
	$scope.modifySystemSchool=function(){
		
		var url=contextPath+"/base/admin/school/modifySystemSchool";
		var params=new Array();
		var ids=new Array();
		var isSpecials=new Array();

		for(var i=0;i<$scope.schoolList.length;i++){
			var sch=$scope.schoolList[i];
			ids.push(sch.schId);
			isSpecials.push(sch.isShow);
		}
		
		params.push("accountId="+accountId);
		params.push("accountToken="+accountToken);
		params.push("skId="+$scope.currentSkId);
		params.push("ids="+ids.join(','))
		params.push("isSpecials="+isSpecials.join(','));
		$.ajax({
			async: false,
			// 后台处理程序
			url : url,
			// 数据发送方式
			type : "post",
			// 接受数据格式
			dataType : "json",
			// 要传递的数据
			data : params.join('&'),
			// 回传函数
			success : function(ret){
				if(ret){
					if(ret.result){
						alert('保存成功');
					}else{
						alert(ret.msg);
					}
				}
			}
		});
	};
	$scope.init=function(){
		$scope.errorMsg='未找到园所信息';
		$scope.currentSkId='';
		$scope.searchKey='';
		$scope.schoolList=[];
	};

	$scope.init();
});
</script>
	</body>
</html>