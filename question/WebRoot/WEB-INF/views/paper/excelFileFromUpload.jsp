<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
$(function() {
	
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
		frozenColumns : [ [ {
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
	  <form id="excelFileUpload" action="${ctx}/excel/import.html" method="post"
		enctype="multipart/form-data">
		<table class="grid" id="excelFileUpload" style="height:100px;margin:16px 4px 12px 4px;">
				<tr>
					<td>文件</td>
					<td><input name="file"  id="name" type="file" style="width: 200px;" ></td>
					<input name="parerIdTemp"  type="text" value="${parerIdTemp}" style="width: 200px;display:none;" >
				</tr>
			</table>
		</form>
</div>