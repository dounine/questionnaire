<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		$('#pid').combotree({
			url : '${ctx}/papermng/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});
		
		$('#papermngAddForm').form({
			url : '${ctx}/papermng/add',
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
		
	});
</script>
<div style="padding: 3px;">
	<form id="papermngAddForm" method="post">
		<table class="grid">
			<tr>
				<td>问卷名称</td>
				<td><input name="name" type="text" style="width: 134px;" class="easyui-validatebox" data-options="required:true" ></td>
				<td>有效期</td>
				<td>
				<input type="text" id="faildate" name="faildate" class="easyui-datetimebox"  style="width:140px"/>
				</td>
			</tr>
			<tr>
				<td>问卷描述</td>
				<td colspan="3" ><textarea class="easyui-kindeditor"  rows="2" cols="3" id="description" name="description" style="width:700px;height:230px;"></textarea></td>
			</tr>
			<tr>
				<td>排序</td>
				<td><input name="seq" class="easyui-numberspinner" style="width: 140px;" data-options="editable:true,required:true" value="0"></td>
				<td>菜单图标</td>
				<td><input  name="icon" value="icon_folder" style="width: 134px;"/></td>
			</tr>
			<tr>
				<td>答题时限（分钟）</td>
				<td colspan="3" ><input name="limitdate" class="easyui-numberspinner" style="width: 140px;" data-options="editable:true,required:true" value="60"></td>
			</tr>
			
			<tr>
				<td>上级资源</td>
				<td colspan="3" ><select id="pid" name="pid" style="width:270px;"></select>&nbsp;
				<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
			</tr>
		</table>
	</form>
</div>