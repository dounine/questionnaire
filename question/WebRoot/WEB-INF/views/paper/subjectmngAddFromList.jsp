<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
var userGrid;
$(function() {
	
	$('#paperidQry').combotree({
		url : '${ctx}/papermng/tree',
		parentField : 'pid',
		lines : true,
		panelHeight : 'auto'
	});
	
	
	userGrid = $('#userGrid').datagrid({
		url : '${ctx}' + '/subjectmng/dataGridByQuery',
		striped : true,
		rownumbers : true,
		pagination : true,
		singleSelect : true,
		idField : 'id',
		sortName : 'name',
		sortOrder : 'asc',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
		singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		},{
			width : '150',
			title : 'id',
			field : 'id',
			hidden : true
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
			title : '维度',
			field : 'questiontype',
			sortable : true,
			hidden:true
		}, {
			width : '100',
			title : '类型',
			field : 'kind',
			sortable : true,
			hidden:true
		}] ]  
		});
		
	});
	
// 查询用户
function queryUserFun(){
	userGrid.datagrid('load', {
		name: $('#name').val(),
		paperid:$('#paperidQry').combo('getValue')//获取值  
	});
}
	
	function resetFun(){
		$('#name').val(''); 
		$('#paperidQry').combo('setValue', '').combo('setText', '');
	}
	
	
$('#subjectmngAddFromListForm').form({
	url : '${ctx}/subjectmng/AddbyList',
	 
	success : function(result) {
		progressClose();
		result = $.parseJSON(result);
		if (result.success) {
			parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为subjectmng.jsp页面预定义好了
			parent.$.modalDialog.handler.dialog('close');
		} else {
			parent.$.messager.alert('提示', result.msg, 'warning');
		}
	}
});
</script>
<div class="easyui-layout" data-options="fit:true ">
	<div data-options="region:'north' " title="查询条件"   style="width:680px; overflow: hidden;height: 120px; " >
	  <form id="subjectmngAddFromListForm" method="post">
		<input name="paperid" id="paperid"  type="hidden"  value="${subjectmng.paperid}" readonly="readonly">
		<input id="subjectIds" name="subjectIds" type="hidden" />
		<table class="grid">
				<tr>
					<td>题目</td>
					<td colspan="1"><input name="name"  id="name" type="text" style="width: 200px;" >
					<td>问卷</td>
					<td><select id="paperidQry" name="paperidQry" style="width: 260px;" class="easyui-validatebox"  ></select></td>
				</tr>
				<tr>
					<td colspan="4"><a class="easyui-linkbutton" href="javascript:void(0)" onclick="queryUserFun()" >查询</a>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="resetFun()" >重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'" title="试题列表" style="width:680px; overflow: hidden;height: 390px; ">
		<table id="userGrid" data-options="fit:true "></table>
	</div>
</div>