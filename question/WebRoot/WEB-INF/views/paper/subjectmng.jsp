<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/subjectmng/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/subjectmng/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
	<script type="text/javascript">
	var dataGrid;
	var organizationTree;
	var parerIdTemp ;
	var parerIdTempName;
	$(function() {
	
		organizationTree = $('#organizationTree').tree({
			url : '${ctx}/papermng/tree',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				parerIdTemp = node.id;
				parerIdTempName = node.text;
				dataGrid.datagrid('load', {
					paperid: node.id
				});
			}
		});
	
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/subjectmng/dataGrid',
			title:'',
		    striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			fit:true,			
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',			
			toolbar : '#toolbar',
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
			}, {
				width : '100',
				title : '维度',
				field : 'questiontype',
				sortable : true
			},{
				width : '100',
				title : '描述',
				field : 'description',
				sortable : true,
				hidden:true
			},{
				width : '100',
				title : '类别',
				field : 'kind',
				sortable : true
			},{
				width : '80',
				title : '题目类型',
				field : 'type',
				sortable : true,
				hidden:true,
				formatter : function(value, row, index) {
					var jsonObjs = $.parseJSON('${subjecttypeJson}');
					for(var i =0;i<jsonObjs.length;i++){
						var jsonobj = jsonObjs[i];
						if(jsonobj.id==value){
							return jsonobj.text;
						}
					}
					return "未知类型";
				}
			},{
				field : 'action',
				title : '操作',
				align:'center',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if(row.isdefault!=0){
						if ($.canEdit) {
							str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
						}
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if ($.canDelete) {
							str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
						}
					}
					return str;
				}
			}] ]
		}); 
	
	});
	
	function addFun() {
		if(parerIdTemp==null||parerIdTemp==""){
			$.messager.alert('提示', "请先选择试卷、再添加题目！", 'warning');
			return  false;
		}
		parent.$.modalDialog({
			title : '试卷  《'+parerIdTempName+'》添加 题目',
			width : 500,
			height : 394,
			href : '${ctx}/subjectmng/addPage?parerIdTemp='+parerIdTemp,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#subjectmngAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	
	
	function addFromList() {
		if (parerIdTemp == null || parerIdTemp == ''){
			$.messager.alert("提示","请先选择问卷类型！","info",function(){
				return false;
			});
		}else{
		parent.$.modalDialog({
			title : '从题库中选择',
			width : 700,
			height : 520,
			href : '${ctx}/subjectmng/addFromListPage?parerIdTemp='+parerIdTemp+'&parerIdTempName='+parerIdTempName,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#subjectmngAddFromListForm');
					var userGrid = parent.$.modalDialog.handler.find('#userGrid');
					var checkedItems = userGrid.datagrid('getChecked');
					var names = [];
					$.each(checkedItems, function(index, item){
						names.push(item.id);
					});   
					var subjectIds = parent.$.modalDialog.handler.find('#subjectIds');
					subjectIds.val(names.join(","));
					f.submit();
				}
			} ]
		});
		}
	}
	
	function importExcelDate(){
		if (parerIdTemp == null || parerIdTemp == ''){
			$.messager.alert("提示","请先选择问卷类型！","info",function(){
				return false;
			});
		}else{
		parent.$.modalDialog({
			title : '选择文件',
			width : 500,
			height : 200,
			href : '${ctx}/excel/import1.html?parerIdTemp='+parerIdTemp,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#excelFileUpload');
					f.submit();
				}
			} ]
		});
		}
	}
	

	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除试题？', function(b) {
			if (b) {
					progressLoad();
					$.post('${ctx}/subjectmng/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
					
			}
		});
	}
	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 394,
			href : '${ctx}/subjectmng/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#subjectmngEditForm');
					f.submit();
				}
			} ]
		});
	}
	

	</script>
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<div data-options="region:'center',border:false,fitColumns:false" title="题目列表"  >
		<table id="dataGrid" data-options="fit:true,border:false,fitColumns:false,striped:true" >
			
		</table>
	</div>
	<div data-options="region:'west',border:false,split:true" title="问卷列表" style="width:200px;overflow: hidden; ">
		<table id="organizationTree" style="width:180px;margin: 10px 10px 10px 10px"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/subjectmng/add')}">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_add'">添加</a>
<!-- 			<a onclick="addFromList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_add'">从题库中选择</a> -->
			<a onclick="importExcelDate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_add'">导入题目</a>
		</c:if>
	</div>
</body>
</html>