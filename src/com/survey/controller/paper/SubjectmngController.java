package com.survey.controller.paper;

import com.alibaba.fastjson.JSON;
import com.survey.base.BaseController;
import com.survey.base.ServiceException;
import com.survey.common.Grid;
import com.survey.common.Json;
import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.model.paper.ExamUserList;
import com.survey.model.paper.Papermng;
import com.survey.model.paper.QuestionType;
import com.survey.model.paper.Subjectmng;
import com.survey.model.sys.Dictionary;
import com.survey.service.paper.PapermngServiceI;
import com.survey.service.paper.SubjectmngServiceI;
import com.survey.service.sys.DictionaryServiceI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.spi.LoggerFactory;
import org.apache.poi.poifs.poibrowser.ExtendableTreeCellRenderer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("all")
@Controller
@RequestMapping({"/subjectmng"})
public class SubjectmngController extends BaseController
{
  static final Logger logger = org.slf4j.LoggerFactory.getLogger(SubjectmngController.class);

  @Autowired
  private SubjectmngServiceI subjectmngService;

  @Autowired
  private DictionaryServiceI dictionaryService;

  @Autowired
  private PapermngServiceI papermngService;

  @RequestMapping({"/manager"})
  public String manager(HttpServletRequest request)
  {
    request.setAttribute("subjecttypeJson", JSON.toJSONString(this.dictionaryService.combox("subjecttype")));
    return "/paper/subjectmng";
  }
  
  @RequestMapping({"/questiontypecombox"})
  @ResponseBody
  public List<QuestionType> combox(Integer paperid) {
    return this.subjectmngService.treeQuestionType(paperid);
  }
  
  @RequestMapping({"/kindcombox"})
  @ResponseBody
  public List<QuestionType> kindcombox(Integer paperid) {
	  List<QuestionType> list = new ArrayList<QuestionType>();
	  list.add(new QuestionType(1, "单选"));
	  list.add(new QuestionType(2, "多选"));
	  list.add(new QuestionType(3, "文本"));
	  
	  return list;
  }

  @RequestMapping({"/subjectAly"})
  public String subjectAly(HttpServletRequest request) {
    return "/paper/subjectAly";
  }

  @RequestMapping({"/AddbyList"})
  @ResponseBody
  public Json AddbyList(Subjectmng subjectmng) {
    Json j = new Json();
    try {
      this.subjectmngService.addByList(subjectmng);
      j.setMsg("添加试题成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/dataGrid"})
  @ResponseBody
  public Grid dataGrid(Long paperid, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.subjectmngService.treeGrid(paperid, ph));
    grid.setTotal(this.subjectmngService.count(paperid, ph));
    return grid;
  }
  @RequestMapping({"/dataGridByQuery"})
  @ResponseBody
  public Grid dataGridByQuery(String name, Long paperid, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.subjectmngService.treeGrid(name, paperid, ph));
    grid.setTotal(this.subjectmngService.count(name, paperid, ph));
    return grid;
  }

  @RequestMapping({"/paperSubjectCnt"})
  @ResponseBody
  public void paperSubjectCnt(Long paperid, PageFilter ph, PrintWriter printWriter) {
    //int cnt = (int)this.subjectmngService.count(paperid, ph);
	Long cnt = subjectmngService.count(paperid, ph);
	logger.info("打印的值为："+cnt);
	printWriter.write((new StringBuilder()).append(cnt).toString());
	printWriter.flush();
	printWriter.close(); 
  }

  @RequestMapping({"/alySubjectId"})
  @ResponseBody
  public void alySubjectId(Long subid, int typeAlyId, HttpServletResponse res)
  {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter printWriter = null;
    try {
      printWriter = res.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String xml = this.subjectmngService.alyxmlfromdb(subid, typeAlyId);
    if(xml!=null){
    	printWriter.write(xml);
    	printWriter.flush();
    	printWriter.close();
    }
  }
  
  @RequestMapping({"/alySubjectId1"})
  @ResponseBody
  public void alySubjectId1(Long subid,String quesionType,Integer typeAlyId,HttpServletResponse res)
  {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter printWriter = null;
    try {
      printWriter = res.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String xml = this.subjectmngService.alyxmlfromdbQuesionType(subid,quesionType,typeAlyId);
    Subjectmng subjectmng = new Subjectmng();
	subjectmng.setId(subid);
	subjectmng.setName("");
	List<Object[]> lists = subjectmngService.getQuestionTypeAnswer(Integer.valueOf(subid+""), quesionType);
	logger.info("查询长度："+lists.size());
	for(Object[] oo : lists){
		subjectmng.setAnswerA((oo[0]==null?"":oo[0]).toString());
		subjectmng.setAnswerB((oo[1]==null?"":oo[1]).toString());
		subjectmng.setAnswerC((oo[2]==null?"":oo[2]).toString());
		subjectmng.setAnswerD((oo[3]==null?"":oo[3]).toString());
		subjectmng.setAnswerE((oo[4]==null?"":oo[4]).toString());
		subjectmng.setAnswerF((oo[5]==null?"":oo[5]).toString());
	}
    logger.info("返回页面的数据为："+xml);
	printWriter.write(xml+"，，，"+com.survey.utils.Json.toString(lists));
	printWriter.flush();
	printWriter.close();
  }

  @RequestMapping({"/alySubOrgId"})
  @ResponseBody
  public Grid alySubjectId(Long subid, PageFilter ph)
  {
    Grid grid = new Grid();
    List list = this.subjectmngService.alyOrgDB(subid, ph);
    grid.setRows(list);
    grid.setTotal(Long.valueOf(list.size()));
    return grid;
  }

  @RequestMapping({"/paperSubjectDetail"})
  @ResponseBody
  public void paperSubjectCnt(Long paperid, int currIndex, Long subjectCount, HttpServletResponse res) {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter printWriter = null;
    try {
      printWriter = res.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    PageFilter ph = new PageFilter();
    ph.setRows(1);
    ph.setPage(currIndex);
    ph.setOrder("asc");
    ph.setSort("id");
    List treeGrid = this.subjectmngService.treeGrid(paperid, ph);
    String text = "";
    if ((treeGrid != null) && (!treeGrid.isEmpty())) {
      Subjectmng subjectmng = (Subjectmng)treeGrid.get(0);
      text = JSON.toJSONString(subjectmng);
    }
    printWriter.write(text);
    printWriter.flush();
    printWriter.close();
  }
  @RequestMapping({"/submitSub"})
  @ResponseBody
  public void submitSub(HttpServletRequest request, Long paperid, String subjectids, String answers, Boolean isotherclick,PrintWriter printWriter) {
	  SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    ExamUserList examUserList = new ExamUserList();
    examUserList.setPaperid(paperid);
    examUserList.setSubjectids(subjectids);
    examUserList.setAnswers(answers);
    examUserList.setOrgid(sessionInfo.getOrgid());
    examUserList.setAgescope(sessionInfo.getAgescope());
    examUserList.setIshidden(Long.valueOf(0L));
    this.subjectmngService.addexamUser(sessionInfo, examUserList,isotherclick);
    printWriter.write("succ");
    printWriter.flush();
    printWriter.close();
  }

  @RequestMapping({"/submitSubByHidden"})
  @ResponseBody
  public void submitSubByHidden(HttpServletRequest request, Long paperid,Integer orgid, String subjectids, String answers, int postlevel, int educa, int divisionage, int sex, int age,String text,Boolean isotherclick, PrintWriter printWriter)
  {
	  List<Object[]> oo = subjectmngService.getAgeScopes();
	  Integer agescope = -1;
	  for(Object[] jj : oo){
		  String ageRound = jj[1].toString();
		  String ageStr = ageRound.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
		  String[] ss = ageStr.split(",");
		  
		  if(ss.length==1){
			  if(age==Integer.parseInt(ageStr)){
				  agescope = Integer.parseInt(jj[0].toString());
				  break;
			  }else if(age>Integer.parseInt(ageStr)){
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
	  
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    ExamUserList examUserList = new ExamUserList();
    examUserList.setPaperid(paperid);
    examUserList.setSubjectids(subjectids);
    examUserList.setAnswers(answers);
    examUserList.setIshidden(Long.valueOf(1L));
    examUserList.setAge(Integer.valueOf(age));
    examUserList.setEduca(Integer.valueOf(educa));
    examUserList.setOrgid(orgid);
    examUserList.setAgescope(agescope);
    examUserList.setPostlevel(Integer.valueOf(postlevel));
    examUserList.setSex(Integer.valueOf(sex));
    examUserList.setText(text);
    examUserList.setDivisionage(Integer.valueOf(divisionage));
    this.subjectmngService.addexamUser(sessionInfo, examUserList,isotherclick);
    printWriter.write("succ");
    printWriter.flush();
    printWriter.close();
  }

  @RequestMapping({"/addPage"})
  public ModelAndView addPage(Integer parerIdTemp)
  {
	  ModelAndView mo = new ModelAndView();
	  mo.addObject("parerIdTemp", parerIdTemp);
	  mo.setViewName("/paper/subjectmngAdd");
    return mo;
  }

  @RequestMapping({"/addFromListPage"})
  public String addFromListPage(HttpServletRequest request, Long parerIdTemp) {
    Subjectmng subjectmng = new Subjectmng();
    subjectmng.setPaperid(parerIdTemp);
    request.setAttribute("subjectmng", subjectmng);
    return "/paper/subjectmngAddFromList";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Subjectmng papermng) {
    Json j = new Json();
    try {
      this.subjectmngService.add(papermng);
      j.setSuccess(true);
      j.setMsg("添加成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Subjectmng get(Long id) {
    return this.subjectmngService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Subjectmng o = this.subjectmngService.get(id);
    request.setAttribute("subjectmng", o);
    return "/paper/subjectmngEdit";
  }

  @RequestMapping({"/showAly"})
  public String showAly(HttpServletRequest request, Long id)
  {
	Subjectmng o = null;
	o = this.subjectmngService.get(id);
    request.setAttribute("subjectmng", o);
    request.setAttribute("isquestionType", false);
    return "/paper/showAly";
  }
  
  
  @RequestMapping({"/showAlyQT"})
  public String showAly1(String questionType,HttpServletRequest request, Long id)
  {
	Subjectmng subjectmng = new Subjectmng();
	subjectmng.setId(id);
	subjectmng.setName("");
	List<Object[]> lists = subjectmngService.getQuestionTypeAnswer(Integer.valueOf(id+""), questionType);
	logger.info("查询长度："+lists.size());
	for(Object[] oo : lists){
		subjectmng.setAnswerA((oo[0]==null?"":oo[0]).toString());
		subjectmng.setAnswerB((oo[1]==null?"":oo[1]).toString());
		subjectmng.setAnswerC((oo[2]==null?"":oo[2]).toString());
		subjectmng.setAnswerD((oo[3]==null?"":oo[3]).toString());
		subjectmng.setAnswerE((oo[4]==null?"":oo[4]).toString());
		subjectmng.setAnswerF((oo[5]==null?"":oo[5]).toString());
	}
    request.setAttribute("subjectmng", subjectmng);
    request.setAttribute("isquestionType", true);
    return "/paper/showAly";
  }
  

  @RequestMapping({"/showOrgAly"})
  public String showOrgAly(HttpServletRequest request, Long id)
  {
    Papermng o = this.papermngService.get(id);
    request.setAttribute("papermng", o);
    return "/paper/showOrgAly";
  }
  
  @RequestMapping({"/showQuesTypeFirst"})
  @ResponseBody
  public void showQuesTypeFirst(HttpServletRequest request,Integer paperid,HttpServletResponse res)
  {
	res.setContentType("text/html;charset=UTF-8");
	PrintWriter printWriter = null;
    List<Object[]> oo = subjectmngService.getQuestionTypeAnswerOne(paperid);
    if(oo!=null&&oo.size()>0){
    	try {
			printWriter = res.getWriter();
			if(oo.get(0)[0]==null){
				printWriter.write("");
			}else {
				printWriter.write(oo.get(0)[0].toString());
			}
		} catch (IOException e) {
			printWriter.write("错误");
		}
    	printWriter.flush();
    	printWriter.close();
    	
    }
  }

  @RequestMapping({"/answermngPage"})
  public String answermngPage(HttpServletRequest request, Long id) {
    Subjectmng o = this.subjectmngService.get(id);
    request.setAttribute("papermng", o);
    return "/paper/subjectmngEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Subjectmng org) throws InterruptedException {
    Json j = new Json();
    try {
      this.subjectmngService.edit(org);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/delete"})
  @ResponseBody
  public Json delete(Long id) {
    Json j = new Json();
    try {
      this.subjectmngService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (ServiceException e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  public List<Object[]> getSubjectmngs(Integer parerIdTemp) {
	return subjectmngService.getSubjectmngs(parerIdTemp);
}

}