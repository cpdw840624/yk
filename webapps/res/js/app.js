/**
 * ajax请求错误信息处理
 * @param json 返回json对象
 * @return
 */
function jsonReturnErrorMsg(json,returnCall){
	var id='_'+Math.random();
	if(json.msg=='nologin'){
		bootstrapQ.alert({
			id:id,
			title:'登录超时',
			msg:'用户身份已过期，请重新登陆'
		},function () { 
			window.location.href = contextPath+json.jumpUrl;
		});
	}else{
		alert(json.msg);
		/**
		bootstrapQ.alert({
			id:id,
			title:'警告',
			msg:json.msg
		});
		*/
		
		if(json.asyncTokenId!=null&&json.asyncTokenId!=''){
			sync_token_from_session(json.asyncTokenId);
		}
	}
}

// 自定义数组删除
Array.prototype.del = function(n)
{
if (n<0) return this;
return this.slice(0,n).concat(this.slice(n+1,this.length));
}
var isEmpty=function(val){
		if(val==undefined||val==null||val==""){
			return true;
		}
		return false;
	};
	
	var initPcd=function(){
		var url=contextPath+"/api/base/allPcdList";
		var params=new Array();
		params.push("key="+key);
		var result=[];
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
						result=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			}
		});
		return result;
	};
	
	var initProvince=function(){
		var url=contextPath+"/api/base/provinceList";
		var params=new Array();
		params.push("key="+key);
		var result=[];
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
						result=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			}
		});
		return result;
	};
	
	var initSchool=function(){
		var url=contextPath+"/api/base/schoolList";
		var params=new Array();
		params.push("key="+key);
		var result=[];
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
						result=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			}
		});
		return result;
	};
	
	var pcdChange=function(pcdId){
		var url=contextPath+"/api/base/pcdListByParent";
		var params=new Array();
		params.push("key="+key);
		var result=[];
		params.push("parentCode="+pcdId);
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
						result=ret.json;
					}else{
						jsonReturnErrorMsg(ret);
					}
				}
			}
		});
		return result;
	};
	
	var isPCity=function(pcdId){
		var s=pcdId.substring(0,2);
		var sm=pcdId.substring(2,4);
		if(s=="11"||s=="12"||s=="31"||s=="50"||sm=="90"){
			return true;
		}else{
			return false;
		}
	};

	
var app = angular.module('myApp',['ng.ueditor']);
app.directive('datePicker', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        scope: {
        },
        link: function (scope, element, attr, ngModel) {

            element.val(ngModel.$viewValue);

            function onpicking(dp) {
                var date = dp.cal.getNewDateStr();
                scope.$apply(function () {
                    ngModel.$setViewValue(date);
                });
            }

            element.bind('click', function () {
                WdatePicker({
                    onpicking: onpicking,
                    dateFmt: attr.fmt?attr.fmt:'yyyy-MM-dd',
                    minDate: attr.minDate?attr.minDate:'',
                });
            });
        }
    };
});
/**
 * 公共导出全部数据方法
 * @param _exportUrl 导出方法请求地址
 * @param _exportColumns 列对象[{name=姓名,value='name',isdict=0}]
 * @param _params 导出选中是确定的唯一表示
 * @return
 */
function exportAll(scope,_exportUrl,_exportColumns,_params,_join$,queryParams){
	var param="page=1";
	var param="&pagesize=3000";
	param+="&"+queryParams;
	if(_exportColumns.length>0){
		$.each(_exportColumns,function(i,item){
			param+='&exportColumnNames='+item.name;
			param+='&exportColumns='+item.value;
			param+='&exportColumnIsDict='+(item.isdict==1?1:0);
		});
	}
	if(param==null){
		alert('请选择要导出的数据!!!');
		return;
	}
	$.ajax({
		// 后台处理程序
		url : _exportUrl,
		// 数据发送方式
		type : "post",
		// 接受数据格式
		dataType : "json",
		// 要传递的数据
		data : param,
		// 回传函数
		success : function(ret){
			if(ret){
				if(ret.result){
					bootstrapQ.alert('导出成功：<a href="'+contextPath+ret.json+'" target="_blank">点击下载</a>');
				}else{
					jsonReturnErrorMsg(ret);
				}
			}
		}
	});
}
