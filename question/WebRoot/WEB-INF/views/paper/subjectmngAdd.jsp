<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		
		$('#paperid').combotree({
			url : '${ctx}/papermng/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});
		
		
		$('#subjectmngAddForm').form({
			url : '${ctx}/subjectmng/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为subjectmng.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
		});
		//var kindisselect = false;
		/*  function kindSelectShow(control){
			 $('#'+ control).combobox({
		            url: '${ctx}/subjectmng/kindcombox',
		            method: 'get',
		            valueField: 'text',
		            textField: 'text',
		            editable: false,
		            panelHeight: 'auto',
		            required:true,
		            onLoadSuccess:function(){
		            	var data = $('#kind').combobox('getData');
		            },
		            onChange:function(newValue, oldValue){
		            	var data = $('#questiontypeval').combobox('getData');
		           }
		        });
		 } */
		
		//kindSelectShow("kind");
		//绑定字典:为用户类型赋值
		pubMethod.bind('subjecttype', 'subjecttype');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="subjectmngAddForm" method="post">
			<table class="grid">
				<tr>
					<td>题目</td>
					<td  colspan="3"><input name="name" style="width: 400px;" type="text" class="easyui-validatebox" data-options="required:true" value=""></td>
					<input type="text" name="paperid" style="display:none;" value="${parerIdTemp}" />
				</tr>
				<tr>
					<td>选项A</td>
					<td colspan="3" ><input name="answerA" style="width: 400px;"  class="easyui-validatebox" data-options="required:true"  type="text"  value=""></td>
				</tr>
				<tr>
					<td>选项B</td>
					<td colspan="3"><input name="answerB" style="width: 400px;"  class="easyui-validatebox" data-options="required:true"  type="text"  value=""></td>
				</tr>
				<tr>
					<td>选项C</td>
					<td colspan="3"><input name="answerC" style="width: 400px;"  class="easyui-validatebox"   type="text"  value=""></td>
				</tr>
				<tr>
					<td>选项D</td>
					<td colspan="3"><input name="answerD" style="width: 400px;"  class="easyui-validatebox"   type="text"  value=""></td>
				</tr>
				<tr>
					<td>选项E</td>
					<td colspan="3"><input name="answerE" style="width: 400px;"  class="easyui-validatebox"  type="text"  value=""></td>
				</tr>
				<tr>
					<td>选项F</td>
					<td colspan="3"><input name="answerF" style="width: 400px;"  class="easyui-validatebox"   type="text"  value=""></td>
				</tr>
				<tr>
					<td>维度</td>
					<td ><input name="questiontype" id="questiontype" style="width: 170px;" type="text"  value=""></td>
					<td>类型</td>
					<td><select id="kind" panelHeight="auto" class="easyui-combobox" name="kind" style="width:168px;height:auto;">
						    <option value="单选">单选</option>
						    <option value="多选">多选</option>
						    <option value="文本">文本</option>
						</select></td>
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3" ><input name="description" style="width:400px;" type="text"  value=""></td>
				</tr>
			</table>
		</form>
	</div>
</div>