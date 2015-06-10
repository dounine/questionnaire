<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	var userGrid;
	$(function() {
		
		$('#organizationId').combotree({
			url : '${ctx}/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : '400',
			panelWidth:'230'
		});
		
		userGrid = $('#userGrid').datagrid({
			url : '${ctx}' + '/user/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			fit:true,
			fitColumns:true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			singleSelect: false,
			selectOnCheck: true,
			checkOnSelect: true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},{
				width : '100',
				title : '登录名',
				field : 'id',
				hidden : true
			},{
				width : '100',
				title : '登录名',
				field : 'loginname',
				sortable : true
			}, {
				width : '100',
				title : '姓名',
				field : 'name',
				sortable : true
			},{
				width : '80',
				title : '部门ID',
				field : 'organizationId',
				hidden : true
			},{
				width : '260',
				title : '所属部门',
				field : 'organizationName'
			}, {
				width : '50',
				title : '性别',
				field : 'sex',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '男';
					case 1:
						return '女';
					}
				}
			}, {
				width : '50',
				title : '年龄',
				field : 'age',
				sortable : true
			}] ],
			onLoadSuccess:function(data){     
			 if(data){
				 $.each(data.rows, function(index, item){
			     var paperId=${papermng.id}; 
				 if(item.paperids != null && item.paperids.indexOf(paperId)>=0){
					 $('#userGrid').datagrid('checkRow', index);
			 		}
			 		});
				 }
			 }  
		});
		
	});
	
	// 查询用户
	function queryUserFun(){
		userGrid.datagrid('load', {
			loginname: $('#loginname').val(),
			name: $('#name').val(),
			organizationId:$('#organizationId').combo('getValue')//获取值  
		});
	}
 	
 	function resetFun(){
 		$('#loginname').val(''); 
 		$('#name').val(''); 
 		$('#organizationId').combo('setValue', '').combo('setText', '');
 	}
 	
 	
	$('#papermngGrantForm').form({
		url : '${ctx}/papermng/grant',
		onSubmit : function() {
			progressLoad();
			var isValid = $(this).form('validate');
			if (!isValid) {
				progressClose();
			}
			return isValid;
		},
		success : function(result) {
			progressClose();
			result = $.parseJSON(result);
			if (result.success) {
				parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为papermng.jsp页面预定义好了
				parent.$.modalDialog.handler.dialog('close');
			}
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true ">
	<div data-options="region:'north' " title="查询条件"   style="width:400px; overflow: hidden;height: 140px; " >
	  <form id="papermngGrantForm" method="post">
		<input name="id" type="hidden"  value="${papermng.id}" readonly="readonly">
		<input id="userIds" name="userIds" type="hidden" />
		<table class="grid">
				<tr>
					<td>登陆账号</td>
					<td colspan="1"><input name="loginname"  id="loginname" type="text" style="width: 134px;" >
					<td>员工姓名</td>
					<td colspan="1"><input name="name" id="name" type="text" style="width: 134px;" >
				</tr>
				<tr>
					<td>部门</td>
					<td colspan="3"><select id="organizationId" name="organizationId" style="width: 374px;" class="easyui-validatebox"  ></select></td>
				</tr>
				<tr>
					<td colspan="4"><a class="easyui-linkbutton" href="javascript:void(0)" onclick="queryUserFun()" >查询</a>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="resetFun()" >重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'" title="人员列表" style="width:400px; overflow: hidden;height: 200px; ">
		<table id="userGrid" data-options="fit:true "></table>
	</div>
</div>
