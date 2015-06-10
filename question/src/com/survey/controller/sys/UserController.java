package com.survey.controller.sys;

import com.alibaba.fastjson.JSON;
import com.survey.base.BaseController;
import com.survey.base.ServiceException;
import com.survey.common.GlobalConstant;
import com.survey.common.Grid;
import com.survey.common.Json;
import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.model.paper.Subjectmng;
import com.survey.model.sys.Organization;
import com.survey.model.sys.User;
import com.survey.service.sys.DictionaryServiceI;
import com.survey.service.sys.OrganizationServiceI;
import com.survey.service.sys.UserServiceI;
import com.survey.utils.ExcelRead;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@SuppressWarnings("all")
@Controller
@RequestMapping({"/user"})
public class UserController extends BaseController
{

	private Logger logger = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserServiceI userService;

  @Autowired
  private DictionaryServiceI dictionaryService;

  @Autowired
  private OrganizationServiceI organizationService;

  @RequestMapping({"/manager"})
  public String manager(HttpServletRequest request)
  {
	    request.setAttribute("usertypeJson", JSON.toJSONString(this.dictionaryService.combox("usertype")));
	    request.setAttribute("educaJson", JSON.toJSONString(this.dictionaryService.combox("educa")));
	    request.setAttribute("divisionageJson", JSON.toJSONString(this.dictionaryService.combox("divisionage")));
	    request.setAttribute("postJson", JSON.toJSONString(this.dictionaryService.combox("post")));
	    request.setAttribute("postlevelJson", JSON.toJSONString(this.dictionaryService.combox("postlevel")));
	    return "/admin/user";
  }
  @RequestMapping({"/dataGrid"})
  @ResponseBody
  public Grid dataGrid(User user, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.userService.dataGrid(user, ph));
    grid.setTotal(this.userService.count(user, ph));
    return grid;
  }

  @RequestMapping({"/editPwdPage"})
  public String editPwdPage(HttpServletRequest request)
  {
    return "/admin/userEditPwd";
  }
  @RequestMapping({"/editUserPwd"})
  @ResponseBody
  public Json editUserPwd(HttpServletRequest request, String oldPwd, String pwd) {
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    Json j = new Json();
    try {
      this.userService.editUserPwd(sessionInfo, oldPwd, pwd);
      j.setSuccess(true);
      j.setMsg("密码修改成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  @RequestMapping({"/addPage"})
  public String addPage(HttpServletRequest request) {
    request.setAttribute("sexList", GlobalConstant.sexlist);
    return "/admin/userAdd";
  }

  @RequestMapping({"/importUser"})
  public String importUser(HttpServletRequest request, Long orgId) {
    Organization org = this.organizationService.get(orgId);
    request.setAttribute("organization", org);
    return "/admin/userImport";
  }

  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(User user) {
    Json j = new Json();
    User u = this.userService.getByLoginName(user);
    if (u != null)
      j.setMsg("用户名已存在!");
    else {
      try {
        this.userService.add(user);
        j.setSuccess(true);
        j.setMsg("添加成功！");
      } catch (Exception e) {
        j.setMsg(e.getMessage());
      }
    }

    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public User get(Long id) {
    return this.userService.get(id);
  }
  @RequestMapping({"/delete"})
  @ResponseBody
  public Json delete(Long id) {
    Json j = new Json();
    try {
      this.userService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    User u = this.userService.get(id);
    request.setAttribute("user", u);
    request.setAttribute("sexList", GlobalConstant.sexlist);
    return "/admin/userEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(User user) {
    Json j = new Json();
    try {
      this.userService.edit(user);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (ServiceException e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/importUserSubmit"})
  @ResponseBody
  public Json importUserSubmit(MultipartHttpServletRequest multipartRequest, Organization o) {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");

    String logoPathDir = "/files" + dateformat.format(new Date());

    String logoRealPathDir = multipartRequest.getSession().getServletContext().getRealPath(logoPathDir);

    File logoSaveFile = new File(logoRealPathDir);
    if (!logoSaveFile.exists()) {
      logoSaveFile.mkdirs();
    }
    MultipartFile multipartFile = multipartRequest.getFile("file");

    String logImageName = multipartFile.getOriginalFilename();

    String fileName = logoRealPathDir + File.separator + logImageName;
    File file = new File(fileName);
    try {
      multipartFile.transferTo(file);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Json j = new Json();
    try {
      readExcel(fileName, o);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (ServiceException e) {
      e.printStackTrace();
      j.setMsg(e.getMessage());
    }
    return j;
  }

  public void readExcel(String excelName, Organization o)
  {
	  
	  	ExcelRead excelRead = new ExcelRead();
	  	excelRead.importExcel(excelName);
		List<Object> objects = null;
		logger.info(excelRead.getSumRow().toString());
		for (int i = 1; i < excelRead.getSumRow(); i++) {
			objects = excelRead.readRow(0, i);
			User user = new User();
			if(objects.get(0)!=null) user.setLoginname(objects.get(0).toString());//登录名
			if(objects.get(1)!=null) user.setName(objects.get(1).toString());//用户名
			if(objects.get(2)!=null){//性别
				
				 if(objects.get(2).toString().equals("男"))					 
       			      user.setSex(0);
       		      else 
       		    	  user.setSex(1);
			}
			if(objects.get(3)!=null)user.setAge((int)Double.parseDouble(objects.get(3).toString()));//年龄
			if(objects.get(4)!=null){//学历
				 List<Object[]> oo = userService.getEducate();
	              String edu = objects.get(4).toString();
	        	  Integer agescope = -1;
	        	  for(Object[] jj : oo){
	        		  String ageRound = jj[1].toString();
	        		  if(edu.equals(ageRound)){
	        			  agescope = Integer.parseInt(jj[0].toString());
	        			  break;
	        		  }
	        	  }
	              user.setEduca(agescope);
			}
			if(objects.get(5)!=null){//司龄
					List<Object[]> oo = userService.getDivisionAge();
	          	  Integer agescope = -1;
	          	  String dage = objects.get(5).toString();
	          	  Integer age = (int)Double.parseDouble(dage);
	          	  for(Object[] jj : oo){
	          		  String ageRound = jj[1].toString();
	          		  String ageStr = ageRound.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
	          		  String[] ss = ageStr.split(",");
	          		  if(ss.length==1){
	          			 Integer begin = Integer.parseInt(ss[0]);
	          			 if(age==begin){
	          				 agescope = Integer.parseInt(jj[0].toString());
	          				 break;
	          			 }
	          		  }else{
	          			  Integer begin = Integer.parseInt(ss[0]);
	          			  Integer end = Integer.parseInt(ss[1]);
	          			  
	          			  if(age>=begin&&age<=end){
	          				  agescope = Integer.parseInt(jj[0].toString());
	          				  break;
	          			  }
	          		  }
	          	  }
	          	  user.setDivisionage(agescope);
			}
			
			if (objects.get(6)!=null) { // 职位
            	 List<Object[]> oo = userService.getPost();
                 String edu = objects.get(6).toString();
	           	  Integer agescope = -1;
	           	  for(Object[] jj : oo){
	           		  String ageRound = jj[1].toString();
	           		  if(edu.equals(ageRound)){
	           			  agescope = Integer.parseInt(jj[0].toString());
	           			  break;
	           		  }
	           	  }
                 user.setPost(agescope);
	          }
	          if (objects.get(7)!=null){ // 职级
	        	  List<Object[]> oo = userService.getPostLevel();
	              String edu = objects.get(7).toString();
		           	  Integer agescope = -1;
		           	  for(Object[] jj : oo){
		           		  String ageRound = jj[1].toString();
		           		  if(edu.equals(ageRound)){
		           			  agescope = Integer.parseInt(jj[0].toString());
		           		  }
		           	  }
	              user.setPostlevel(agescope);
	          }
	          user.setOrganizationId(o.getId());
	          user.setPassword("123");
	          user.setRoleIds("4");
	          user.setRoleNames("普通用户");
	          this.userService.add(user);
	         // System.out.println("user id "+user.getId().toString());
	           
		}
  }
}