<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		
		$('#organizationId').combotree({
			url : '${ctx}/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : '400',
			panelWidth:'230'
		});
		
		$('#roleIds').combotree({
		    url: '${ctx}/role/tree',
		    multiple: true,
		    required: true,		    
		    panelHeight : 'auto'
		});
		
		$('#userAddForm').form({
			url : '${ctx}/user/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
		});
		
		//绑定字典:为用户类型赋值
		pubMethod.bind('usertype', 'usertype');
		pubMethod.bind('educa', 'educa');
		pubMethod.bind('divisionage', 'divisionage');
		pubMethod.bind('post', 'post');
		pubMethod.bind('postlevel', 'postlevel');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="userAddForm" method="post">
			<table class="grid">
				<tr>
					<td>登录名</td>
					<td><input name="loginname" style="width: 134px;" type="text" class="easyui-validatebox" data-options="required:true" value=""></td>
					<td>姓名</td>
					<td><input name="name" type="text" style="width: 134px;" class="easyui-validatebox" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input name="password" type="password" style="width: 134px;" class="easyui-validatebox" data-options="required:false"></td>
					<td>性别</td>
					<td><select name="sex" class="easyui-combobox" data-options="width:140,editable:false,panelHeight:'auto'">
						<c:forEach items="${sexList}" var="sexList">
							<option value="${sexList.key}" >${sexList.value}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input type="text" name="age" style="width: 134px;" class="easyui-validatebox" data-options="required:true"/></td>
					<td>用户类型</td>
					<td><input id="usertype" name="usertype"  style="width: 140px;" /></td>
				</tr>
				<tr>
					<td>部门</td>
					<td><select id="organizationId" name="organizationId" style="width: 140px;" class="easyui-validatebox" data-options="required:true"></select></td>
					<td>角色</td>
					<td><select id="roleIds"  name="roleIds"   style="width: 140px;"></select></td>
				</tr>
				<tr>
					<td>学历</td>
					<td><input id="educa" name="educa" style="width: 140px;"/></td>
					<td>司龄</td>
					<td><input id="divisionage" name="divisionage"  style="width: 140px;" /></td>
				</tr>
				<tr>
					<td>职位</td>
					<td><input id="post" name="post" style="width: 140px;"/></td>
					<td>职级</td>
					<td><input id="postlevel" name="postlevel"  style="width: 140px;" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>