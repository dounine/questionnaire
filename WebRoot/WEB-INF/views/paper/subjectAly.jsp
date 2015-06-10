<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
	<style>
		.noDecor{text-decoration:none;}
	</style>
	<script src="${ctx}/jslib/containts.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	var dataGrid;
	var organizationTree;
	var subId;
	var questionTypeV = "";
	$(function() {
		organizationTree = $('#organizationTree').tree({
			url : '${ctx}/papermng/tree',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				subId = node.id;
				$.get("${ctx}/subjectmng/showQuesTypeFirst?paperid="+subId,function(data){
					questionTypeV = data;
				});
				dataGrid.datagrid('load', {
					paperid: node.id
				});
			}
		});
	
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/subjectmng/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [{
				width : '80',
				title : '问卷ID',
				field : 'paperid',
				hidden:true
			}, {
				width : '150',
				title : '题目',
				field : 'name',
				sortable : true
			},  {
				width : '100',
				title : '选项A',
				field : 'answerA',
				sortable : true
			}, {
				width : '100',
				title : '选项B',
				field : 'answerB',
				sortable : true
			}, {
				width : '100',
				title : '选项C',
				field : 'answerC',
				sortable : true
			}, {
				width : '100',
				title : '选项D',
				field : 'answerD',
				sortable : true
			}, {
				width : '100',
				title : '选项E',
				field : 'answerE',
				sortable : true
			}, {
				width : '100',
				title : '选项F',
				field : 'answerF',
				sortable : true
			},{
				width : '100',
				title : '维度',
				field : 'questiontype',
				sortable : true
			}, {
				width : '100',
				title : '类别',
				field : 'kind',
				sortable : true
			},{
				width : '100',
				title : '描述',
				field : 'description',
				sortable : true,
				hidden:true
			},{
				field : 'action',
				title : '操作',
				width : 80,
				align:'center',
				formatter : function(value, row, index) {
					var str = '';
					if(row.isdefault!=0){
						str += $.formatString('<a class=\'noDecor\' href="javascript:void(0)" onclick="alyFun(\'{0}\');" >答案统计</a>', row.id);
					}
					return str;
				}
			}] ],
			toolbar : '#toolbar'
		});
	});
	
	
	function alyFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '答案统计',
			width : 1000,
			height : 700,
			href : '${ctx}/subjectmng/showAly?id=' + id
		});
	}
	
	function orgFun(){
		if(subId == null || subId == ''){
			$.messager.alert("提示","请先选择左边的问卷类型！","info",function(){
				return false;
			});
		}else{
			parent.$.modalDialog({
				title : '部门统计',
				width : 1000,
				height : 700,
				href : '${ctx}/subjectmng/showOrgAly?id=' + subId
			});
		}
	}
	
	function exportExcel(){
		if(subId == null || subId == ''){
			$.messager.alert("提示","请先选择左边的问卷类型！","info",function(){
				return false;
			});
		}else{
			$.messager.confirm('提示','确定要导出数据吗?',function(r){
				if (r){
					window.location.href='${ctx}/excel/export.html?parerIdTemp='+subId;
				}
			});
		}
	}
	
	function questiontypeSum(){
		if(subId == null || subId == ''){
			$.messager.alert("提示","请先选择左边的问卷类型！","info",function(){
				return false;
			});
		}else{
			parent.$.modalDialog({
				title : '维度统计',
				width : 1000,
				height : 700,
				href : '${ctx}/subjectmng/showAlyQT?id='+subId+'&questionType='+questionTypeV
			});
		}
	}
 

	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center'" title="题目列表"  >
		<table id="dataGrid" data-options="fit:true,border:false,fitColumns:false,striped:true" ></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="return orgFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_add'">部门统计</a>
		<a onclick="return exportExcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_add'">导出数据</a>
		<a onclick="return questiontypeSum();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_dic'">维度统计</a>
	</div>
	
	<div data-options="region:'west',border:false,split:true" title="问卷列表" style="width:200px;overflow: hidden; ">
		<table id="organizationTree" style="width:180px;margin: 10px 10px 10px 10px"></table>
	</div>
</body>
</html>