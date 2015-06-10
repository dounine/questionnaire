<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/mapJs.js"></script>
<script type="text/javascript">
	var map = new HashMap();
	var qus = new HashMap();
	var index_layout;
	var currIndex=1; // 当前序号
	var subjectCount=0; // 试题总数
	var timeEvent; // 倒计时
	var askTimeTotle = 0;
	var subDescript;
	var paperid=0;
	var index_tabsMenu;
	var layout_west_tree;
	var layout_west_tree_url = '';
	var nowSubjectId = -1;//当前题目id
	var kindtype = -1;
	var isotherclick = false;
	 var qqOther=null;//文字对象
	var sessionInfo_userId = '${sessionInfo.id}';
	var interval = 1000; 
	
	function Question(){
		var box = new Object();
		box.id = -1;
		box.textan = '';
		box.A = false;
		box.B = false;
		box.C = false;
		box.D = false;
		box.E = false;
		box.F = false;
		box.O = false;
		box.kind = '';
		box.inputtype=true;//true 代表是单选框  false代表是多选框
		box.paperid = -1;
		box.text = '';
		return box;
	}
	
	
	function ShowCountDown(endDate)  { 
		var now = new Date(); 
	    
		var leftTime=endDate.getTime()-now.getTime(); 
		var leftsecond = parseInt(leftTime/1000); 
		var day1=Math.floor(leftsecond/(60*60*24)); 
		var hour=Math.floor((leftsecond-day1*24*60*60)/3600); 
		var minute=Math.floor((leftsecond-day1*24*60*60-hour*3600)/60); 
		var second=Math.floor(leftsecond-day1*24*60*60-hour*3600-minute*60); 
		if(minute <= 10){
			 $("#timeID").html("答卷时间:"+ askTimeTotle +"分钟，剩余时间:"+hour+"小时"+minute+"分"+second+"秒，剩余时间不足<font color='red' >10</font>分钟，请抓紧提交问卷");
		}else{
		    $("#timeID").html("答卷时间:"+ askTimeTotle +"分钟，剩余时间:"+hour+"小时"+minute+"分"+second+"秒");
		}
		
		if(hour <=0 && minute <=0 && second<=0){ //超时，考试作废
			clea();
			$.messager.alert("糟糕","答题时间已到，答题作废！","warning",function(){
				document.location.href = "hiddenAskindex";
			});
		}
		
	} 
	

	$(function() {
		

 	   $("#mainID").hide();
 	   
		index_layout = $('#index_layout').layout({
			fit : true
		});
		
		
		layout_west_tree = $('#layout_west_tree').tree({
				url : '${ctx}/papermng/treeByUser', // 用户能看到的问卷类型
				parentField : 'pid',
				lines : true,
				onClick : function(node) {
					var attr = node.attributes;
					var temp = attr.split("|||");
					askTimeTotle = temp[0];
					subDescript = temp[1];
					// 如果有map有值，提示是否离开考试
					if(map.size() > 0){
						$.messager.confirm('提示','确定要退出答题?',function(r){
							if (r){
								//clea();
								//queryCount(node.id);
								document.location.href = "hiddenAskindex";
							}
						});
					}else{
						clea();
						 $("#subjectMsgId").html(subDescript);
						queryCount(node.id);
					}
				}
			});
		

		//绑定字典:为用户类型赋值
		pubMethod.bind('educa', 'educa');
		pubMethod.bind('divisionage', 'divisionage');
		/* pubMethod.bind('post', 'post'); */
		pubMethod.bind('postlevel', 'postlevel');
		$('#orgid').combotree({
			url : '${ctx}/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : '400',
			panelWidth:'230'
		});
	});
	


	// 清空数据
	function clea(){
		map = new HashMap();
		qus = new HashMap();
		currIndex=1;
		subjectCount=0; // 试题总数
		window.clearInterval(timeEvent);
		 $("#mainID").hide();
		 $("#askID").hide();
		 $("#timeID").html('');
		 $( "#progressbar" ).hide();
	}
	
	function queryCount(id){
		// 查询是否有题目
		$.ajax({
           type: "post",
           url: '${ctx}' + '/subjectmng/paperSubjectCnt',
           cache: false,
           data: {paperid: id},
           dataType: "text",
           success: function(response){
        	   subjectCount = eval(response);
               if (subjectCount > 0) { //有试题
               		$("#startID").hide();
               		$("#mainID").show();
               		$('#btn').linkbutton({ // 使用js的方式能达到效果
               		    iconCls: 'easyui-linkbutton'
               		});
               		paperid = id;
               }else{
            	   $("#startID").show();
            	   $("#mainID").hide();
            	   $("#askID").hide();
            	   
               }
           }
       });
	}
	
	function askBeginFun(){
		// 
		
		var isValid = $('#useraskForm').form('validate');
		if (!isValid) {
			$.messager.alert('提示', "请先选择调查人信息，再开始答题", 'warning');
			return false;
		}
		
		$('#post').attr({readonly:true});
		$('#postlevel').attr({readonly:true});
		$('#orgid').attr({readonly:true});
		$('#educa').attr({readonly:true});
		$('#divisionage').attr({readonly:true});
		$('#sex').attr({readonly:true});
		$('#age').attr({readonly:true});
		$('#beginBtn').linkbutton({disabled:true}); 
		$.ajax({
	           type: "post",
	           url: '${ctx}' + '/subjectmng/paperSubjectDetail',
	           cache: false,
	           data: {paperid:paperid,currIndex:currIndex,subjectCount:subjectCount},
	           dataType: "text",
	           success: function(objdata){
	        	  // $("#mainID").hide();
	        	   $("#askID").show();
	        	   var data = eval('(' + objdata + ')');
	        	   nowSubjectId = data.id;
	        	   var mess = "";
	        	   var mess1 = "";
	        	   if(data.kind=='单选'){
	        		   kindtype = 1;
	        	   mess =  ""+currIndex+". "+data.name+" "+
						   		"<br><br><input type='radio' name='radioX' value='A,"+data.id+",true' id='quesA'  onclick='addToMapFun(this)'  />"+" <label for='quesA'>A: "+data.answerA+"</label>  "+
						   		"<br><br><input type='radio' name='radioX' value='B,"+data.id+",true' id='quesB'  onclick='addToMapFun(this)'  />"+" <label for='quesB'>B: "+data.answerB+"</label>  ";
						   		if(data.answerC != null && data.answerC != ''){
						   			mess += "<br><br><input type='radio' name='radioX' value='C,"+data.id+",true' id='quesC'  onclick='addToMapFun(this)'  />"+" <label for='quesC'>C: "+data.answerC+"</label>  ";
						   		}
						   		if(data.answerD != null && data.answerD != ''){
						   			mess += "<br><br><input type='radio' name='radioX' value='D,"+data.id+",true' id='quesD'  onclick='addToMapFun(this)'  />"+" <label for='quesD'>D: "+data.answerD+"</label>  ";
						   		}
						   		if(data.answerE != null && data.answerE != ''){
						   			mess += "<br><br><input type='radio' name='radioX' value='E,"+data.id+",true' id='quesE'  onclick='addToMapFun(this)'  />"+" <label for='quesE'>E: "+data.answerE+"</label>  ";
						   		}
						   		if(data.answerF != null && data.answerF != ''){
						   			mess += "<br><br><input type='radio' name='radioX' value='F,"+data.id+",true' id='quesF'  onclick='addToMapFun(this)'  />"+" <label for='quesF'>F: "+data.answerF+"</label>  ";
						   		}
						   		/*mess1 += "</br><br><a id='preBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 0)' >上一题</a> "+
						   		"<a id='nextBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 1)' >下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
						   		"&nbsp;&nbsp;&nbsp;&nbsp;<a id='subBtn'  class='lake_bottom easyui-linkbutton'  onclick='subFun()' >交卷</a>";*/
						   		mess += "<br><br><input type='radio' name='radioX' value='O,"+data.id+",true' id='otherInput'  onclick='addToMapFun(this)'  />"+" <label for='otherInput'>O: 其它</label>  ";
						   		mess += "<br><br><textarea style='width:500px;height:36px;display:none;width:0px;' id='otherInputValue' name='text' value='' ></textarea>";
						   		mess1 += "</br><br><a id='preBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 0)' >上一题</a> "+
						   		"<a id='nextBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 1)' >下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
						   		"&nbsp;&nbsp;&nbsp;&nbsp;<a id='subBtn'  class='lake_bottom easyui-linkbutton'  onclick='subFun()' >交卷</a>";
	        	   }else if(data.kind=='多选'){
	        		   kindtype = 2;
	        		   mess =  ""+currIndex+". "+data.name+" "+
						   		"<br><br><input type='text' value='yes' id='isMulti' style='display:none;' /><input type='checkbox' name='radioX' value='A,"+data.id+",false' id='quesA'  onclick='addToMapFun(this)'  />"+" <label for='quesA'>A: "+data.answerA+"</label>	  "+
						   		"<br><br><input type='checkbox' name='radioX' value='B,"+data.id+",false' id='quesB'  onclick='addToMapFun(this)'  />"+" <label for='quesB'>B: "+data.answerB+"</label>  ";
						   		if(data.answerC != null && data.answerC != ''){
						   			mess += "<br><br><input type='checkbox' name='radioX' value='C,"+data.id+",false' id='quesC'  onclick='addToMapFun(this)'  />"+" <label for='quesC'>C: "+data.answerC+"</label>  ";
						   		}
						   		if(data.answerD != null && data.answerD != ''){
						   			mess += "<br><br><input type='checkbox' name='radioX' value='D,"+data.id+",false' id='quesD'  onclick='addToMapFun(this)'  />"+" <label for='quesD'>D: "+data.answerD+"</label>  ";
						   		}
						   		if(data.answerE != null && data.answerE != ''){
						   			mess += "<br><br><input type='checkbox' name='radioX' value='E,"+data.id+",false' id='quesE'  onclick='addToMapFun(this)'  />"+" <label for='quesE'>E: "+data.answerE+"</label>  ";
						   		}
						   		if(data.answerF != null && data.answerF != ''){
						   			mess += "<br><br><input type='checkbox' name='radioX' value='F,"+data.id+",false' id='quesF'  onclick='addToMapFun(this)'  />"+" <label for='quesF'>F: "+data.answerF+"</label>  ";
						   		}
						   		/*mess1 += "</br><br><a id='preBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 0)' >上一题</a> "+
						   		"<a id='nextBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 1)' >下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
						   		"&nbsp;&nbsp;&nbsp;&nbsp;<a id='subBtn'  class='lake_bottom easyui-linkbutton'  onclick='subFun()' >交卷</a>";*/
						   		mess += "<br><br><input type='checkbox' name='radioX' value='O,"+data.id+",true' id='otherInput'  onclick='addToMapFun(this)'  />"+" <label for='otherInput'>O: 其它</label>  ";
						   		mess += "<br><br><textarea style='width:500px;height:36px;display:none;width:0px;' id='otherInputValue' name='text' value='' ></textarea>";
						   		mess1 += "</br><br><a id='preBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 0)' >上一题</a> "+
						   		"<a id='nextBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 1)' >下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
						   		"&nbsp;&nbsp;&nbsp;&nbsp;<a id='subBtn'  class='lake_bottom easyui-linkbutton'  onclick='subFun()' >交卷</a>";
	        	   }else if(data.kind=='文本'){
	        		   mess =  ""+currIndex+". "+data.name+" "+
				   		"<br><br><input type='radio' name='radioX' value='A,"+data.id+",true' class='noClickInput' id='quesA'  onclick='addToMapFun(this)'  />"+" <label for='quesA'>A: "+data.answerA+"</label>  "+
				   		"<br><br><input type='radio' name='radioX' value='B,"+data.id+",true' class='noClickInput' id='quesB'  onclick='addToMapFun(this)'  />"+" <label for='quesB'>B: "+data.answerB+"</label>  ";
				   		if(data.answerC != null && data.answerC != ''){
				   			mess += "<br><br><input type='radio' name='radioX' class='noClickInput' value='C,"+data.id+",true' id='quesC'  onclick='addToMapFun(this)'  />"+" <label for='quesC'>C: "+data.answerC+"</label>  ";
				   		}
				   		if(data.answerD != null && data.answerD != ''){
				   			mess += "<br><br><input type='radio' name='radioX' class='noClickInput' value='D,"+data.id+",true' id='quesD'  onclick='addToMapFun(this)'  />"+" D: <label for='quesD'>"+data.answerD+"</label>  ";
				   		}
				   		if(data.answerE != null && data.answerE != ''){
				   			mess += "<br><br><input type='radio' name='radioX' class='noClickInput' value='E,"+data.id+",true' id='quesE'  onclick='addToMapFun(this)'  />"+" E: <label for='quesE'>"+data.answerE+"</label>  ";
				   		}
				   		if(data.answerF != null && data.answerF != ''){
				   			mess += "<br><br><input type='radio' name='radioX' class='noClickInput' value='F,"+data.id+",true' id='quesF'  onclick='addToMapFun(this)'  />"+" F: <label for='quesF'>"+data.answerF+"</label>  ";
				   		}
				   		mess += "<br><br><input type='radio' name='radioX' value='O,"+data.id+",true' id='otherInput'  onclick='addToMapFun(this)'  />"+" <label for='otherInput'>O: 其它</label>  ";
				   		mess += "<br><br><textarea style='width:500px;height:36px;display:none;width:0px;' id='otherInputValue' name='text' value='' ></textarea>";
				   		mess1 += "</br><br><a id='preBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 0)' >上一题</a> "+
				   		"<a id='nextBtn'  class='lake_bottom easyui-linkbutton'  onclick='askNextFun("+data.id+" , 1)' >下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
				   		"&nbsp;&nbsp;&nbsp;&nbsp;<a id='subBtn'  class='lake_bottom easyui-linkbutton'  onclick='subFun()' >交卷</a>";
	        	   }
	        	   $("#askID").html(mess);
	        	   $("#askID1").html(mess1);
	        	   
		        	   if(!qus.containsKey(data.id)){
		        		  var ququ = new Question();
		        		  ququ.id = data.id;
		        		  qus.put(data.id,ququ);
		        	   }
		        	   selectQu();
	        	   
				   if(currIndex == 1){ //只有第一题才开始倒计时
		        	   var now = new Date(); 
		   			   var t=now.getTime();
		    			t+=askTimeTotle*60*1000;//askTimeTotle的毫秒数
		    			var endDate=new Date(t);
		        	   timeEvent = window.setInterval(function(){ShowCountDown(endDate);}, interval); 
						$( "#progressbar" ).show();
					 	//进度条
						showBar();
				   }
	        	   
	        	   $('#preBtn').linkbutton({ // 使用js的方式能达到效果
              		    iconCls: 'easyui-linkbutton'
              		});
	        	   $('#nextBtn').linkbutton({ // 使用js的方式能达到效果
             		    iconCls: 'easyui-linkbutton'
             		});
	        	   $('#subBtn').linkbutton({ // 使用js的方式能达到效果
            		    iconCls: 'easyui-linkbutton'
            		});
	        	   // 控制按钮是否可用
	        	   if(currIndex == 1){
	        		   $('#preBtn').linkbutton({disabled:true}); 
	        	   }else{
	        		   $('#preBtn').linkbutton({disabled:false}); 
	        	   }
	        	   // 控制按钮是否可用
	        	   if(currIndex == subjectCount){
	        		   	$('#nextBtn').linkbutton({disabled:true}); 
	        		}else{
	        			$('#nextBtn').linkbutton({disabled:false}); 
	        		}
	        	   var valr = (map.size() / subjectCount) * 100;
	       		   var valrs =  Math.round(valr*1)/1;
				   if(valrs!=100){
	        		   $('#subBtn').linkbutton({disabled:true}); 
				   }
	           }
	       });
	}

	// 交卷
	function subFun(){
		var valr = (map.size() / subjectCount) * 100;
		var valrs =  Math.round(valr*1)/1;
		 if(  valrs < 100){
			$.messager.alert("警告","还有题目没有选择答案，不能交卷！！！","warning",function(){
				return false;
			});
		}else{ 
			$.messager.confirm('提示','确定要交卷吗?',function(r){
				if (r){
					submitSubject();
				}
			});
		}
		
	}
	
    // 调用ajax交卷
	function submitSubject(){
		if(qqOther!=null){
			qqOther.text=$("#otherInputValue").val();
			qqOther = null;
		}
		var keys = map.keys();
    	var subjectids ='' ;
    	var answers ='';
    	/* if(kindtype==1){
    	 for(var i=0;i<keys.length;i++){
    		if(keys[i] != null && keys[i]!=''){
        		subjectids += ","+keys[i];
        		answers += ","+map.get(keys[i]);
    		}
    		} 
    	}else if(kindtype==2){ */
   		keys = qus.keys();
    	for(var i =0;i<keys.length;i++){
    		if(keys[i]!=null&&keys[i]!=''){
    			var ss = qus.get(keys[i]);
    			if(ss.A){
    				answers +=',A'+keys[i];
    			}
    			if(ss.B){
    				answers +=',B'+keys[i];
    			}
    			if(ss.C){
    				answers +=',C'+keys[i];
    			}
    			if(ss.D){
    				answers +=',D'+keys[i];
    			}
    			if(ss.E){
    				answers +=',E'+keys[i];
    			}
    			if(ss.F){
    				answers +=',F'+keys[i];
    			}
    			if(ss.O){
    				subjectids +='。。。'+keys[i]+'，，，'+qus.get(keys[i]).text;
    			}
    		}
    	}
    	//}
    	
    	answers = answers.substring(1);
    	var postlevel = $('#postlevel').combobox('getValue');
    	var orgid = $("#orgid").combobox("getValue");
    	var educa =$('#educa').combobox('getValue');
    	var divisionage = $('#divisionage').combobox('getValue');
    	var sex = $('#sex').combobox('getValue');
    	var age =$('#age').val();
    	//var text = $("#otherInputValue").val()+"，，，，"+$("#otherInput")[0].value.substring(2);
		
    	//alert(subjectids);
    	
		$.ajax({
	           type: "post",
	           url: '${ctx}' + '/subjectmng/submitSubByHidden',
	           cache: false,
	           data: {paperid:paperid,subjectids:subjectids,answers:answers,postlevel:postlevel,orgid:orgid,educa:educa,divisionage:divisionage,sex:sex,age:age,text:'',isotherclick:isotherclick},
	           dataType: "text",
	           success: function(objdata){
	        	   if(objdata == 'succ'){
	        		   $.messager.alert("恭喜","提交问卷成功！","info",function(){
	       					document.location.href = "hiddenAskindex";
	       				});
	        	   }
	           }
		});
	}
    
    function selectQu(){//多选框
    	if(arguments.length==0){
    		if(qus.containsKey(nowSubjectId)){
    			var qq = qus.get(nowSubjectId);
    			if(qq.A){
    				$("#quesA").attr("checked",true);
    			}
    			if(qq.B){
    				$("#quesB").attr("checked",true);
    			}
    			if(qq.C){
    				$("#quesC").attr("checked",true);
    			}
    			if(qq.D){
    				$("#quesD").attr("checked",true);
    			}
    			if(qq.E){
    				$("#quesE").attr("checked",true);
    			}
    			if(qq.F){
    				$("#quesF").attr("checked",true);
    			}
    			if(qq.O){
    				$("#otherInput").attr("checked",true);
    				$("#otherInputValue").show();
       				$("#otherInputValue").animate({
       					"width":"500",
       					"height":"36"
       				});
    				$("#otherInputValue").val(qq.text);
    			}
    		}
    	}else{
	    	var value = arguments[0].value;
	    	var values = new Array();
	    	values = value.split(",");
	    	if(qus.containsKey(values[1])){
	    		var qq = qus.get(values[1]);
	    		if(values[2]=="false"){
	    			qq.inputtype = false;
	    		}else{
	    			qq.inputtype = true;
	    		}
    			if(values[0]=='A'){
    				if(!qq.A||!qq.inputtype){
    					qq.A = !qq.A;
	    				if(qq.inputtype&&$("#isMulti").length==0){
							qq.B = false;
							qq.C = false;
							qq.D = false;
							qq.E = false;
							qq.F = false;
							qq.O = false;
	    				}
    				}
    			}else if(values[0]=='B'){
    				if(!qq.B||!qq.inputtype){
    					qq.B = !qq.B;
	    				if(qq.inputtype&&$("#isMulti").length==0){
	    				qq.A = false;
						qq.C = false;
						qq.D = false;
						qq.E = false;
						qq.F = false;
						qq.O = false;
	    				}
    				}
    			}else if(values[0]=='C'){
    				if(!qq.C||!qq.inputtype){
    					qq.C = !qq.C;
	    				if(qq.inputtype&&$("#isMulti").length==0){
		    				qq.A = false;
							qq.B = false;
							qq.D = false;
							qq.E = false;
							qq.F = false;
							qq.O = false;
	    				}
    				}
    			}else if(values[0]=='D'){
    				if(!qq.D||!qq.inputtype){
    					qq.D = !qq.D;
	    				if(qq.inputtype&&$("#isMulti").length==0){
		    				qq.A = false;
							qq.B = false;
							qq.C = false;
							qq.E = false;
							qq.F = false;
							qq.O = false;
	    				}
    				}
    			}else if(values[0]=='E'){
    				if(!qq.E||!qq.inputtype){
    					qq.E = !qq.E;
	    				if(qq.inputtype&&$("#isMulti").length==0){
		    				qq.A = false;
							qq.B = false;
							qq.C = false;
							qq.D = false;
							qq.F = false;
							qq.O = false;
	    				}
    				}
    			}else if(values[0]=='F'){
    				if(!qq.F||!qq.inputtype){
    					qq.F = !qq.F;
	    				if(qq.inputtype&&$("#isMulti").length==0){
		    				qq.A = false;
							qq.B = false;
							qq.C = false;
							qq.D = false;
							qq.E = false;
							qq.O = false;
	    				}
    				}
    			}else if(values[0]=='O'){
    				if(!qq.O||!qq.inputtype||$("#isMulti").length==1){
    					qq.O = !qq.O;
	    				qqOther = qq;
	    				qq.text=$("#otherInputValue").val();
	    				if(qq.inputtype&&$("#isMulti").length==0){
	    					qq.A = false;
	    					qq.B = false;
	    					qq.C = false;
	    					qq.D = false;
	    					qq.E = false;
	    					qq.F = false;
	    				}
    				}
    			}
    			if(!qq.A&&!qq.B&&!qq.C&&!qq.D&&!qq.E&&!qq.F&&!qq.O){
    				map.remove(qq.id);
    			}
	    	}
    	}
    }
	
   
    function addToMapFun($1){
    		if($($1).val().substring(0,1)!="O"&&$("#otherInput").attr("checked")==null){
    			isotherclick = false;
    			$("#otherInputValue").animate({
        			"width":"0",
        			"height":"0"
        		},function(){
        			$(this).css({"display":"none"});
        		});
    		}
    		if($1.id=="otherInput"){
    			isotherclick = true;
    			if($("#otherInput").attr("checked")!=null){
	    			$(".progressbar-text").css({"margin-top":"20"});
	   				$("#otherInputValue").show();
	   				$("#otherInputValue").animate({
	   					"width":"500",
	   					"height":"36"
	   				});
    			}else{
    				$("#otherInputValue").animate({
	   					"width":"0",
	   					"height":"0"
	   				},function(){
	   					$("#otherInputValue").hide();
	   				});
    			}
    		}
    		
	    	var value = $1.value;
	    	var values = value.split(",");
	    	selectQu($1);
	    	var valueX= $("input[name=radioX]:checked").val();
			if(valueX != null && valueX != ''){
				 map.put(values[1],valueX);
			}
    	//}
 	   	//进度条
		showBar();
 	   
		  
		var valr = (map.size() / subjectCount) * 100;
		var valrs =  Math.round(valr*1)/1;
		if(valrs != 100){
 		   $('#subBtn').linkbutton({disabled:true}); 
		 }else{
			 $('#subBtn').linkbutton({disabled:false}); 
		 }
    }
    
	//上一页、下一页
	function askNextFun($1,$2){
		if(qqOther!=null){
			qqOther.text=$("#otherInputValue").val();
			qqOther = null;
		}
		//if(kindtype==2){
			selectQu();
		//}
		/* var valueX= $("input[name=radioX]:checked").val();
		if(valueX != null && valueX != ''){
			 map.put($1,valueX);
		}  */
		if($2 == 0){ //上一题
			currIndex = currIndex-1;
		}else{
			currIndex = currIndex + 1;
		}
		askBeginFun();
	}
	
	function logout(){
		$.messager.confirm('提示','确定要退出?',function(r){
			if (r){
				progressLoad();
				$.post( '${ctx}/admin/logout', function(result) {
					if(result.success){
						progressClose();
						window.location.href='${ctx}/admin/index';
					}
				}, 'json');
			}
		});
	}
	

	function editUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${ctx}/user/editPwdPage',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}

	
	function showBar(){
		var valr = (map.size() / subjectCount) * 100;
		var valrs =  Math.round(valr*1)/1;
	    $( "#progressbar" ).progressbar({

	      value: valrs

	    });
	}
	
</script>
</head>
<body>
	<div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
	<img src="${ctx}/style/images/index/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
	</div>
	<div id="index_layout">
		<div data-options="region:'north'" style=" overflow: hidden;" id="header">
			<img src="${ctx}/style/images/index/chasesun_white.png" style="position: absolute;top: 3;left: 0;right: 0;bottom: 0;margin: auto;"/>
			<span style="float: right; padding-right: 20px;">欢迎 <b>${sessionInfo.name}</b>&nbsp;&nbsp; <a href="javascript:void(0)" onclick="editUserPwd()" style="color: #fff">修改密码</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;
    		</span>
    		<span class="header"></span>
		</div>
		<div data-options="region:'west',split:true" title="问卷列表" style="width: 300px; overflow: hidden;overflow-y:auto;">
			<div class="well well-small" style="padding: 10px 5px 5px 5px;">
				<ul id="layout_west_tree"></ul>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
				<div title="问卷调查" data-options="border:false" style="overflow: hidden;padding:10px 0 20px 20px">
					<div id="startID" style="padding:10px 0 10px 10px">
						<h2>系统介绍</h2>
						<div  class="light-info">
							<div class="light-tip icon-tip"></div>
							<div>欢迎您使用问卷调查管理系统。</div>
						</div>
					</div>
					<div id="mainID" style="padding:10px 100px 10px 10px;"> 
						<form id="useraskForm" method="post">
							<table class="grid">
							<h2 id="subjectMsgId"></h2>
								<th colspan="6">匿名调查人员信息</th>
								<tr>
									<td>部门<font color="red">*</font></td>
									<td><select id="orgid" name="orgid" style="width: 200px;" class="easyui-validatebox" data-options="required:true"></select></td>
									<td>职级<font color="red">*</font></td>
									<td><input id="postlevel" name="postlevel"  style="width: 200px;" /></td>
									<td>学历<font color="red">*</font></td>
									<td><input id="educa" name="educa" style="width: 200px;"/></td>
								</tr>
								<tr>
									<td>司龄<font color="red">*</font></td>
									<td><input id="divisionage" name="divisionage"  style="width: 200px;" /></td>
									<td>性别<font color="red">*</font></td>
									<td><select name="sex" id="sex" class="easyui-combobox" data-options="width:200,editable:false,panelHeight:'auto'">
										<c:forEach items="${sexList}" var="sexList">
											<option value="${sexList.key}" >${sexList.value}</option>
										</c:forEach>
									</select></td>
									<td>年龄<font color="red">*</font></td>
									<td><input type="text" name="age" id="age" style="width: 194px;" value="30" data-options="required:true" /></td>
								</tr>
							</table>
						</form><br>
						<a id="beginBtn"  class="easyui-linkbutton"  onclick="askBeginFun()" >开始答题</a>
					
					</div> 
					<div id="askID" style="padding:0px 0 0px 0px;"> </div><br>
					<div id="askID1" style="position:absolute;bottom:80px;padding:0px 0 0px 0px;"> </div><br>
					<div id="timeID" style="position:absolute;bottom:50px;padding:0px 0 0px 0px;"> </div> <br>
					<div id="progressbar"  style="position:absolute;bottom:20px;padding:0 0 0 0;width: 400px;" ></div>
				</div>
		</div>
		<div data-options="region:'south',border:false" style="height: 25px; overflow: hidden;text-align: center;background-color: #daeef5" >问卷调查管理系统</div>
	</div>
	
</body>
</html>