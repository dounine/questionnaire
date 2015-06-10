<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="${ctx}/jslib/containts.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/jslib/FusionCharts.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var subid =  '${subjectmng.id}';//选中试卷id
	var functionType = 1;
	var newValueForQuestion="";
	function clikTarget($1,$2){
		$('#orgId').html('维度：'+$2);
		var swfFile ;
		if(functionType == 1){
			swfFile =  "MSColumn3D.swf" ; // "MSColumn3D.swf";  // 柱形图
		}else if(functionType == 2){
			swfFile = "StackedColumn3D.swf"; // "MSLine.swf";  // 区域图
		}else if(functionType == 3){
			swfFile = "LogMSLine.swf" ; // "MSBar3D.swf";  // 环图；
		}
		var uurl = "alySubjectId";
		var questiontypeval = "";
		if(${isquestionType}){
			uurl = "alySubjectId1"
			$('#orgId').html('类型：'+$2);
			if(arguments.length==3){
				questiontypeval=arguments[2];
			}
		}
		// 查询xml
		$.ajax({
           type: "post",
           url: '${ctx}' + '/subjectmng/'+uurl,
           cache: false,
           data: {subid: subid,typeAlyId:$1,quesionType:questiontypeval==""?newValueForQuestion:questiontypeval},
           dataType: "text",
           success: function(xml){
               if (xml != null ) {
	       			var chart1 = new FusionCharts( "${ctx}/FusionCharts/"+swfFile, "showIda", "760", "480");
	       			chart1.setDataXML(xml.split("，，，")[0]); 
	       			chart1.render("showId");
	       			if(xml.split("，，，").length>1){
		       			if(xml.split("，，，")[1]!=""){
			       			var json = eval("("+xml.split("，，，")[1]+")");
			       			$("#subjectmng_answerA").text(json[0][0]);
			       			$("#subjectmng_answerB").text(json[0][1]);
			       			$("#subjectmng_answerC").text(json[0][2]);
			       			$("#subjectmng_answerD").text(json[0][3]);
			       			$("#subjectmng_answerE").text(json[0][4]);
			       			$("#subjectmng_answerF").text(json[0][5]);
		       			}
	       			}
               }else{
            	   $.messager.alert("提示","该题目还未有人提交问卷","warning",function(){
       			});
               }
           }
       });
	}
	
	$(function(){
		$("#ccType").animate({
			"right":"0px"
		}).find("li").click(function(){
			if($(this).index()==0){
				functionType = 1;//柱形纵
			}else if($(this).index()==1){
				functionType = 2;//柱形横
			}else if($(this).index()==2){
				functionType = 3;//拆线
			}
			clikTarget(9,'公司');
		});
	});
	var isFirstLoadQuestionType = true;
	 function questionTypeSelectShow(control,paperid){
		 $('#'+ control).combobox({
	            url: '${ctx}/subjectmng/questiontypecombox?paperid=' + paperid,
	            method: 'get',
	            valueField: 'text',
	            textField: 'text',
	            editable: false,
	            panelHeight: 'auto',
	            required:true,
	            onLoadSuccess:function(){
	            	var data = $('#questiontypeval').combobox('getData');
	            	if(data!=null&&data!=""){
	       		 		if(isFirstLoadQuestionType)$("#questiontypeval").combobox('select',data[0].text);
	       		 		isFirstLoadQuestionType = false;
	       		 		var chart1 = new FusionCharts( "${ctx}/FusionCharts/MSColumn3D.swf", "showIda", "680", "370");
	       		 		chart1.setDataXML(xml.split("，，，")[0]);
	       				chart1.render("showId");
	       				if(xml.split("，，，").length>1){
			       			if(xml.split("，，，")[1]!=""){
				       			var json = eval("("+xml.split("，，，")[1]+")");
				       			$("#subjectmng_answerA").text(json[0][0]);
				       			$("#subjectmng_answerB").text(json[0][1]);
				       			$("#subjectmng_answerC").text(json[0][2]);
				       			$("#subjectmng_answerD").text(json[0][3]);
				       			$("#subjectmng_answerE").text(json[0][4]);
				       			$("#subjectmng_answerF").text(json[0][5]);
			       			}
		       			}
	            	}
	            },
	            onChange:function(newValue, oldValue){
	            	var data = $('#questiontypeval').combobox('getData');
	            	if(data!=null&&data!=""){
		                newValueForQuestion = newValue;
		                clikTarget(9,newValue,newValue);
	            	}
	           }
	        });
	 }
	questionTypeSelectShow("questiontypeval",subid);
	clikTarget(9,'公司');
</script>
<style>
	#ccType{
		width:100px;
		height:auto;
		right:-80px;
		bottom:160px;
		position:absolute;
	}
	#ccType li{
		margin-top:2px;
		background:#ccc;
		padding-left:20px;
		list-style:none;
		width:100%;
		float:left;
		height:30px;
		line-height:30px;
		cursor:pointer;
	}
	#questiontypevaldiv{
		position:absolute;
		right:10px;
		top:56px;
		z-index:999;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false" style="position:relative;">
	<div data-options="region:'center',border:false" title="" style="padding: 0px;">
		 <table style="padding-left:10px;" width="100%" border="0"  cellspacing="0" cellpadding="0" >
			 <br>
			 <tr><td ><c:if test="${isquestionType==false}" >题目：${subjectmng.name}</c:if>  &nbsp;&nbsp; A：<span id="subjectmng_answerA">${subjectmng.answerA}</span> &nbsp;&nbsp; B：<span id="subjectmng_answerB">${subjectmng.answerB}</span> &nbsp;&nbsp; C：<span id="subjectmng_answerC">${subjectmng.answerC}</span> &nbsp;&nbsp; D：<span id="subjectmng_answerD">${subjectmng.answerD}</span> &nbsp;&nbsp; E：<span id="subjectmng_answerE">${subjectmng.answerE}</span> &nbsp;&nbsp; F：<span id="subjectmng_answerF">${subjectmng.answerF}</span> </td> </tr>   
			 <tr> <c:if test="${isquestionType==false}" ><td  id = "orgId">类型：</td></c:if></tr> 
			 <tr id="imgList">
				 <td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<input name="id" type="hidden" style="width: 134px;" value="${subjectmng.id}">
				 <a class="notd" id="clickImgFirst" href="javascript:clikTarget(9,'公司')"> 公司 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(8,'系统')"> 系统 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(1,'部门')"> 部门</a>&nbsp;&nbsp;|&nbsp;
				 <!-- <a class="notd" href="javascript:clikTarget(2,'职位')">职位</a>&nbsp;&nbsp;|&nbsp; -->
				 <a class="notd" href="javascript:clikTarget(3,'职位')"> 职 级 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(4,'性别')"> 性 别 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(5,'年龄')"> 年 龄 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(6,'学历')"> 学 历 </a>&nbsp;&nbsp;|&nbsp;
				 <a class="notd" href="javascript:clikTarget(7,'司龄')"> 司 龄 </a>&nbsp;&nbsp;&nbsp;
			 	 <font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			 	</td>
	 		</tr>  
	 		<c:if test="${isquestionType==true}" ><div id="questiontypevaldiv">维度：<select id="questiontypeval" name="questiontypeval" style="width: 200px;" class="easyui-validatebox" data-options="required:true"></select></div></c:if>
	 	</table>
	 	<div id='showId' style="height:530px;position:absolute;width:760px;margin-top:40px;"> </div>
		<div id="ccType">
			<li>柱形图</li>
			<li>堆栈图</li>
			<!-- <li>拆线图</li> -->
		</div>
	</div>
</div>