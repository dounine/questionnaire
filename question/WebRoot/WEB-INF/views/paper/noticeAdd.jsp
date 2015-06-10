<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/kindeditor-4.1.2/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindeditor-4.1.2/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/kindeditor-4.1.2/plugins/code/prettify.js"></script>
<script type="text/javascript" src="${ctx}/kindeditor-4.1.2/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/kindeditor-4.1.2/themes/default/default.css">

<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var editor;
	$(document).ready(function (){
		//渲染编辑器
		KindEditor.ready(function(K) {
			editor = K.create('#noticeContent',{
				items:[
					'preview','fontname','fontsize','forecolor','hilitecolor','bold','italic','underline','strikethrough','justifyleft','justifycenter','justifyright','hr','fullscreen','image'
				],
				uploadJson : '${ctx}/upload.action'
			});
		});
	});	
	
	function file_upload(){
		var imgval = $('#fileField').val();
		if ("" == imgval) {
			Common.showBySite('温馨提示', '请选择文件！');
			return;
		}
		
		$("form").ajaxSubmit({
			type:"post",
			url:"${ctx}/notice/upload",
			dataType:"json",
			success:function(data){
				$.messager.alert('提示', data.msg, 'info');
				$("#fileAddress").val(data.filePath);
			}
		});
	}	

	$(function() {
		$('#noticeAddForm').form({
			url : '${ctx}/notice/add',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				$("#noticeContent").val(editor.html());
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
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="noticeAddForm" method="post" enctype="multipart/form-data">
		<input name="fileAddress" id="fileAddress" type="hidden">
			<table class="grid">
				<tr>
					<td width="100px;">公告标题</td>
					<td><input name="noticeTitle" style="width: 300px;"  class="easyui-validatebox span2" data-options="required:true"></td>
				</tr>
				<tr>
					<td>公告内容</td>
					<td><textarea id="noticeContent" name="noticeContent" style="width: 450px;height: 300px;" data-options="required:true"></textarea>
				</tr>
				<tr>
					<td>公告来源</td>
					<td><input name="noticeFrom" style="width: 277px;" type="text" class="easyui-validatebox span2"></td>
				</tr>
				<tr>
					<td>附件上传</td>
					<td>
						<input type="file" name="upload" class="file" id="fileField" size="28" style="height: 21px;"/> 
						<input type="button" class="button" id="save" style="height: 21px;" onclick="return file_upload()" value="上 传" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>