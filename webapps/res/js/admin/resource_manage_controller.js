app.directive('tree', function () { 
	  return { 
	    require: '?ngModel', 
	    restrict: 'A', 
	    link: function ($scope, element, attrs, ngModel) { 
	      //var opts = angular.extend({}, $scope.$eval(attrs.nlUploadify)); 
	      var setting = { 
	        data: { 
	    	  key:{
	    	  	name:"caption"
	      	  },
	          simpleData: { 
	            enable: true,
	            idKey:"ID",
	            pIdKey:"parent_id",
	            rootPid:"0"
	          } 
	        }, 
	        callback: { 
	          onClick: function (event, treeId, treeNode, clickFlag) { 
	        	$scope.queryInfos(treeNode.ID);
	            $scope.$apply(function () { 
	               ngModel.$setViewValue(treeNode); 
	            });
	          } 
	        } 
	      }; 
	      
	      $.fn.zTree.init($(element[0]), setting, $scope.cateTree); 
	    } 
	  }; 
	}); 
app.controller('resourceManage', function($scope) {
	$scope.toAdd=function(cate){
		console.log($scope.selectNode.ID);
		layer.open({
			  title:'新增课件',
			  type: 2,
			  area: ['700px', '600px'],
			  fixed: false, //不固定
			  maxmin: false,
			  scrollbar:false,
			  content: contextPath+"/admin/resource/toAddOrModify?cateId="+(cate?cate.ID:''),
			  btn:['保存','关闭'],
			  yes: function(index, layero){
			 	var body = layer.getChildFrame('body', index);
			    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			    var s=iframeWin.angular.element("#addOrModifyForm").scope();
			    s.toAddOrModify();
			  }
			  ,btn2: function(index, layero){
				  layer.close(index);
			    //按钮【按钮二】的回调
			    
			    //return false 开启该代码可禁止点击该按钮关闭
			  },end:function(){
				    $scope.queryInfos(cate.ID);
				    $scope.$apply();
			  }
		});
	};
	
	$scope.toModify=function(data,cate){
		console.log($scope.selectNode);
		layer.open({
			  title:'修改课件',
			  type: 2,
			  area: ['700px', '600px'],
			  fixed: false, //不固定
			  maxmin: false,
			  scrollbar:false,
			  content: contextPath+"/admin/resource/toAddOrModify?id="+data.id,
			  btn:['保存','关闭'],
			  yes: function(index, layero){
			 	var body = layer.getChildFrame('body', index);
			    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			    var s=iframeWin.angular.element("#addOrModifyForm").scope();
			    s.toAddOrModify();
			    $scope.queryInfos(cate.ID);
			    $scope.$apply();
			  }
			  ,btn2: function(index, layero){
				  layer.close(index);
			    //按钮【按钮二】的回调
			    
			    //return false 开启该代码可禁止点击该按钮关闭
			  }
		});
	};
	
	$scope.toDelete=function(data,cate){
		layer.confirm('确认要删除课件：'+data.info_name+'吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(index, layero){
				$scope.delete(data);
			    $scope.queryInfos(cate.ID);
			    $scope.$apply();
			    layer.close(index);
			}, function(index, layero){
				layer.close(index);
		});
		
	};
	
	$scope.delete=function(data){
		$scope.loadingIndex = layer.load(1, {
			shadeClose:false,
			  shade: [0.8, '#393D49'] //0.1透明度的白色背景
		});
		var url=contextPath+"/admin/resource/delete";
		var params=new Array();
		params.push("id="+data.id);
		var flag=false;
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
	          success: function (ret) {
				layer.close($scope.loadingIndex);  
				if(ret){
					if(ret.result){
						flag=true;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
	          },  
	          error: function (ret) {  
	        	  layer.close($scope.loadingIndex);
	        	  bootstrapQ.alert(ret);  
	          }
		});
		if(flag){
			$scope.queryInfos($scope.selectNode.ID);
		}
	};
	
	//查询分页列表数据
	$scope.queryInfos=function(cateId){
		$scope.loadingIndex = layer.load(1, {
			shadeClose:false,
			  shade: [0.8, '#393D49'] //0.1透明度的白色背景
		});
		var url=contextPath+"/admin/resource/queryInfos";
			var params=new Array();
			params.push("info.parentId="+cateId);
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
				layer.close($scope.loadingIndex);
				if(ret){
					if(ret.result){
						$scope.infosList=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			},
        	error: function (ret) {  
	        	  layer.close($scope.loadingIndex);
	        	  bootstrapQ.alert(ret);  
	          }
		});
	};
	
	//查询分页列表数据
	$scope.queryCateTree=function(page){
		var url=contextPath+"/admin/resource/queryCateTree";
			var params=new Array();
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

						$scope.cateTree=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			}
		});
	};
	
	$scope.init=function(){
		$scope.cateTree=[];
		$scope.selectNode={};
		$scope.queryCateTree();
	};
	
	$scope.init();
});

