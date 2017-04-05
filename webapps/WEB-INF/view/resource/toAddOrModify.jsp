<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" import="javax.servlet.ServletResponse" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<%@include file="/WEB-INF/view/include/header.inc"%>
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <link href="<%=cachePath %>/res/js/ueditor1.4.3.2/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
	    <link href="<%=cachePath %>/res/js/zTree3.5.24/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet">
	    <link href="<%=cachePath %>/res/css/font-awesome.css" rel="stylesheet">
	    <!-- Custom styles for this template -->
	    <link href="<%=cachePath %>/res/css/style.css" rel="stylesheet">
	    <link href="<%=cachePath %>/res/css/style-responsive.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=cachePath %>/res/js/webuploader/webuploader.css">
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
	    <!--[if lt IE 9]>
	      <script src="<%=cachePath %>/res/js/html5shiv.js"></script>
	      <script src="<%=cachePath %>/res/js/respond.min.js"></script>
	    <![endif]-->
	    <script type="text/javascript" src="<%=cachePath %>/res/js/webuploader/webuploader.withoutimage.min.js"></script>
	    
	   	<title>课件维护</title>
	</head>
	<body ng-app="myApp" ng-controller="myCtrl">
		<div class="container">
			<form id="addOrModifyForm" class="form-horizontal">
			  <input type="hidden" ng-model="info.id" ng-value="info.id" />
			  <input type="hidden" ng-model="info.catethird_id" ng-value="info.catethird_id" />
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">所属分类：</label>
			    <div class="col-sm-10">
			    	<c:forEach items="${cateList}" var="cate" varStatus="status">
			    		<c:if test="${status.index>0}">
			    			&nbsp;&gt;&nbsp;
			    		</c:if>
			    		${cate}
			    	</c:forEach>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="info_name" class="col-sm-2 control-label">课件名称</label>
			    <div class="col-sm-10">
			    	<input type="text" class="form-control" id="info_name" placeholder="" ng-model="info.info_name" />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="infoSort" class="col-sm-2 control-label">序号</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="infoSort" placeholder="" ng-model="info.sort" />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="infoIcon" class="col-sm-2 control-label">图标地址</label>
			    <div class="col-sm-10" >
			      <div ng-if="isAddOrModify=='modify'&&info.img_newname.length>0">
					${info.img_name }
				  </div>
			      <input id="infoIcon" type="file"  />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="infoPath" class="col-sm-2 control-label">文件地址</label>
			    <div class="col-sm-10">
			      <div ng-if="isAddOrModify=='modify'&&info.file_path.length>0">${info.file_name }</div>
			      <input type="hidden" ng-model="info.file_name" ng-value="info.file_name" />
			      <input type="hidden" ng-model="info.file_size" ng-value="info.file_size" />
			      <input type="hidden" ng-model="info.file_exname" ng-value="info.file_exname" />
			      <input type="hidden" ng-model="info.file_hash" ng-value="info.file_hash" />
			      <input type="hidden" ng-model="info.file_path" ng-value="info.file_path" />
					<div id="uploader" class="wu-example">
					        <div id="thelist" class="uploader-list" style="width:100%;">
					        </div>
					    <!--用来存放文件信息-->
					    <div class="btns" style="clear:both;">
					        <div id="picker" style="float:left;">选择文件</div>
					        <%-- 
					        <button id="ctlBtn" class="btn btn-primary" style="float:left;height: 38px;margin-left: 5px;" ng-click="uploadFile()">开始上传</button>
					        --%>
					    </div>
					</div>
			    </div>
			  </div>
			</form>
		</div>
<script type="text/javascript">
app.controller('myCtrl', function($scope) {
	$scope.toAddOrModify=function(){
		$scope.loadingIndex = parent.layer.load(1, {
			shadeClose:false,
			  shade: [0.1, '#393D49'] //0.1透明度的白色背景
		});
		if($scope.isAddOrModify=='add'||
				($scope.isAddOrModify=='modify'&&$scope.uploader.getFiles().length>0)){
			if($scope.uploader.getFiles().length==0){
				alert("请选择资料文件");
				parent.layer.close($scope.winIndex);
			}
			$scope.uploader.upload();
	    }else{
	    	$scope.addOrModify();
		}
	};
	
	$scope.addOrModify=function(){
		var url=contextPath+"/admin/resource/addOrModify";
		var formData = new FormData(); 
		formData.append("info.id",$scope.info.id); 
		formData.append("info.catethird_id",$scope.info.catethird_id)
		formData.append("info.info_name",$scope.info.info_name); 
		formData.append("info.sort",$scope.info.sort); 
		formData.append("info.file_name",$scope.info.file_name); 
		formData.append("info.file_size",$scope.info.file_size); 
		formData.append("info.file_exname",$scope.info.file_exname); 
		formData.append("info.file_hash",$scope.info.file_hash); 
		formData.append("info.file_path",$scope.info.file_path); 
		formData.append("filedata",document.getElementById("infoIcon").files[0]);
		var flag=false;
		$.ajax({
			 url: url,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (ret) {
				parent.layer.close($scope.loadingIndex);  
				if(ret){
					if(ret.result){
						flag=true;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
	          },  
	          error: function (ret) {  
	        	  parent.layer.close($scope.loadingIndex);
	        	  bootstrapQ.alert(ret);  
	          }
		});
		if(flag){
			parent.layer.close($scope.winIndex);
		}
	};

	$scope.init=function(){
		$scope.winIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$scope.info=eval(${infoJson});
		$scope.isAddOrModify="${addOrModify}";
		$scope.thelist=$("#thelist");
		$scope.uploader = WebUploader.create({

		    // swf文件路径
		    swf: cachePath + '/res/js/webuploader/Uploader.swf',

		    // 文件接收服务端。
		    server: contextPath+"/admin/resource/uploadFile?id="+$scope.info.id,

		    // 选择文件的按钮。可选。
		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		    pick: '#picker',

		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		    resize: false
		});
		$scope.uploader.on( 'beforeFileQueued', function( file ) {
		    
		});
		$scope.uploader.on( 'fileQueued', function( file ) {
		    var $li = $(
		            '<div id="' + file.id + '" class="file-item thumbnail">' +
		                '<img>' +
		                '<div class="info">' + file.name + '</div>' +
		            '</div>'
		            ),
		        $img = $li.find('img');


		    // $list为容器jQuery实例
		    $scope.thelist.empty();
		    $scope.thelist.append( $li );

		});
		// 文件上传过程中创建进度条实时显示。
		$scope.uploader.on( 'uploadProgress', function( file, percentage ) {
		    var $li = $( '#'+file.id ),
		        $percent = $li.find('.progress-bar');
	        

		    // 避免重复创建
		    if ( !$percent.length ) {
		        $percent = $(
		                '<div class="progress">'+
		                '<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;width: 0%;">'+
		                '  0%'+
		                '</div>'+
		              '</div>'
				        )
		                .appendTo( $li )
		                .find('.progress-bar');
		    }
		    var p=Math.floor(percentage*100);
		    $percent.attr("aria-valuenow",p);
		    $percent.css( 'width', p + '%' );
		    $percent.text(p + '%');
		});

		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		$scope.uploader.on( 'uploadSuccess', function( file,ret ) {
		    $( '#'+file.id ).addClass('upload-state-done');
		    $scope.info.file_name=ret.json.fileName;
		    $scope.info.file_size=ret.json.fileSize;
		    $scope.info.file_exname=ret.json.fileExname
		    $scope.info.file_hash=ret.json.fileHash;
		    $scope.info.file_path=ret.json.qiniuPath;
		    $scope.$apply();
		    $scope.addOrModify();
		    console.log(file);
		    console.log(ret);
		});

		// 文件上传失败，显示上传出错。
		$scope.uploader.on( 'uploadError', function( file ) {
		    var $li = $( '#'+file.id ),
		        $error = $li.find('div.error');

		    // 避免重复创建
		    if ( !$error.length ) {
		        $error = $('<div class="error"></div>').appendTo( $li );
		    }
		    $error.text('上传失败');
		    parent.layer.close($scope.loadingIndex);
		});

		// 完成上传完了，成功或者失败，先删除进度条。
		$scope.uploader.on( 'uploadComplete', function( file ) {
		    $( '#'+file.id ).find('.progress').remove();
		});
	};

	$scope.init();
});
</script>
	</body>
</html>