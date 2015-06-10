<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	var userGrid;
	var subid =  '${papermng.id}';
	$(function() {
		
		 
		
		userGrid = $('#userGrid').datagrid({
			url : '${ctx}' + '/subjectmng/alySubOrgId?subid='+subid,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			singleSelect: false,
			selectOnCheck: true,
			checkOnSelect: true,
			columns : [ [  {
				width : '50',
				title : '机构id',
				field : 'orgid'
			},{
				width : '150',
				title : '机构名称',
				field : 'orgName',
				sortable : true
			}, {
				width : '100',
				title : '机构答题占比',
				field : 'percent',
				sortable : true
			},{
				width : '100',
				title : '答题总数',
				field : 'askCnt'
			},{
				width : '100',
				title : '机构人数',
				field : 'userCnt'
			}, {
				width : '100',
				title : '是否达标',
				field : 'isFull',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case '不达标':
						return '<font color=\'red\'>不达标</font>';
					case '达标':
						return '<font color=\'green\'>达标</font>';
					}
				}
			}] ] 
		});
		
	});
 
</script>
<div class="easyui-layout" data-options="fit:true ">
	<div data-options="region:'center'" title="机构答题列表" style="width:680px; overflow: hidden;height: 480px; ">
		<table id="userGrid" data-options="fit:true "></table>
	</div>
</div>
