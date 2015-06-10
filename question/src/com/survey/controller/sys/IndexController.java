package com.survey.controller.sys;

import com.survey.base.BaseController;
import com.survey.common.GlobalConstant;
import com.survey.common.Json;
import com.survey.common.SessionInfo;
import com.survey.model.sys.User;
import com.survey.service.paper.SubjectmngServiceI;
import com.survey.service.sys.ResourceServiceI;
import com.survey.service.sys.UserServiceI;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/admin"})
public class IndexController extends BaseController
{

  @Autowired
  private UserServiceI userService;

  @Autowired
  private ResourceServiceI resourceService;
  
  @Autowired
  private UserServiceI userServiceI;

  @RequestMapping({"/index"})
  public String index(HttpServletRequest request)
  {
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
      return "/index";
    }
    return "/login";
  }

  @RequestMapping({"/nomalindex"})
  public String nomalindex(HttpServletRequest request) {
    return "/nomalindex";
  }

  @RequestMapping({"/hiddenAskindex"})
  public String hiddenAskindex(HttpServletRequest request) {
    request.setAttribute("sexList", GlobalConstant.sexlist);
    return "/hiddenAskindex";
  }
  @ResponseBody
  @RequestMapping({"/login"})
  public void login(User user, HttpSession session, PrintWriter printWriter) {
	  User sysuser = this.userService.login(user);
	  Integer agescope = -1;
	  if(sysuser!=null){
	  List<Object[]> oo = userServiceI.getAgescope();
	  for(Object[] jj : oo){
		  String ageRound = jj[1].toString();
		  String ageStr = ageRound.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
		  String[] ss = ageStr.split(",");
		  if(ss.length==1){
			  if(sysuser.getAge()==Integer.parseInt(ageStr)){
				  agescope = Integer.parseInt(ageStr);
				  break;
			  }
		  }else{
			  Integer begin = Integer.parseInt(ss[0]);
			  Integer end = Integer.parseInt(ss[1]);
			  
			  if(sysuser.getAge()>=begin&&sysuser.getAge()<=end){
				  agescope = Integer.parseInt(jj[0].toString());
				  break;
			  }
		  }
	  }
	  
	  Integer disage = -1;
	  List<Object[]> oo1 = userServiceI.getDivisionAge();
	  for(Object[] jj : oo1){
		  if(jj!=null){
			  String ageRound = jj[1].toString();
			  String ageStr = ageRound.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
			  String[] ss = ageStr.split(",");
			  if(ss.length==1){
				  if(sysuser.getDivisionage()==Integer.parseInt(ageStr)){
					  disage = Integer.parseInt(jj[0].toString());
					  break;
				  }
			  }else{
				  Integer begin = Integer.parseInt(ss[0]);
				  Integer end = Integer.parseInt(ss[1]);
				  if(sysuser.getDivisionage()>=begin&&sysuser.getDivisionage()<=end){
					  disage = Integer.parseInt(jj[0].toString());
					  break;
				  }
			  }
		  }
	  }
	  
	  
	    if (sysuser != null) {
	    User _tempuser = this.userService.get(sysuser.getId());
	      SessionInfo sessionInfo = new SessionInfo();
	      sessionInfo.setId(_tempuser.getId());
	      sessionInfo.setLoginname(_tempuser.getLoginname());
	      sessionInfo.setName(_tempuser.getName());
	      sessionInfo.setOrgid(Integer.valueOf(_tempuser.getOrganizationId().toString()));//部门临时id
	      sessionInfo.setAgescope(agescope);//年龄临时id
	      sessionInfo.setDisage(disage);//司龄
	      sessionInfo.setResourceList(this.userService.resourceList(_tempuser.getId()));
	      sessionInfo.setResourceAllList(this.resourceService.resourceAllList());
	      sessionInfo.setRolesname(_tempuser.getRoleNames());
	      		System.out.println(	sysuser.getRoleNames());
	      session.setAttribute("sessionInfo", sessionInfo);

	      sysuser = this.userService.get(sysuser.getId());
	      String roles = sysuser.getRoleNames();
	      if (roles.equals("超级管理员"))
	        printWriter.write("admin");
	      else if ("匿名用户".equals(roles))
	    	 //System.out.println(roles); 
	        //printWriter.write("normal");*/
	      /*else if ( (roles!=null ) &&(roles.indexOf("匿名用户") >=0))*/
	    	  //  printWriter.write(sysuser.getLoginname()); // sysuser.getId()
	          printWriter.write("normal");     
    }
	  }
    else {
      printWriter.write("error");
    }
    printWriter.flush();
    printWriter.close();
  }
  @ResponseBody
  @RequestMapping({"/logout"})
  public Json logout(HttpSession session) {
    Json j = new Json();
    if (session != null) {
      session.invalidate();
    }
    j.setSuccess(true);
    j.setMsg("注销成功！");
    return j;
  }
}