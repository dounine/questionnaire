package com.survey.service.paper.impl;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
import com.survey.base.Alyxml;
import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.dao.BaseDaoI;
import com.survey.model.paper.AlyOrgDto;
import com.survey.model.paper.ExamUserList;
import com.survey.model.paper.QuestionType;
import com.survey.model.paper.Subjectmng;
import com.survey.model.paper.TexamAskList;
import com.survey.model.paper.TexamUserList;
import com.survey.model.paper.Tpapermng;
import com.survey.model.paper.Tsubjectmng;
import com.survey.model.sys.Tuser;
import com.survey.service.paper.SubjectmngServiceI;
import com.survey.service.sys.UserServiceI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class SubjectmngServiceImpl implements SubjectmngServiceI {

	@Autowired
	private BaseDaoI<Tuser> userDao;

	@Autowired
	private BaseDaoI<Tpapermng> papermngDao;

	@Autowired
	private BaseDaoI<Tsubjectmng> subjectmngDao;

	@Autowired
	private BaseDaoI<TexamUserList> examUserListDao;
	
	@Autowired
	private UserServiceI userService;

	@Autowired
	private BaseDaoI<TexamAskList> texamAskListDao;

	public List<Subjectmng> treeGrid(Long paperId, PageFilter ph) {
		String where = "";
		if ((paperId != null) && (!"".equals(paperId))) {
			where = where + " where t.papermng.id=" + paperId + " ";
		}
		List lr = new ArrayList();
		List<Tsubjectmng> l = this.subjectmngDao.find(
				"from Tsubjectmng t  left join fetch t.papermng   " + where
						+ orderHql(ph), ph.getPage(), ph.getRows());
		if ((l != null) && (l.size() > 0)) {
			for (Tsubjectmng t : l) {
				Subjectmng r = new Subjectmng();
				BeanUtils.copyProperties(t, r);
				if (t.getId() != null) {
					r.setId(t.getId());
					r.setName(t.getName());
				}
				r.setDescription(t.getDescription());
				r.setPaperid(t.getPapermng().getId());
				r.setType(t.getType());
				lr.add(r);
			}
		}
		return lr;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	public void add(Subjectmng u) {
		Tsubjectmng t = new Tsubjectmng();
		BeanUtils.copyProperties(u, t);
		if ((u.getPaperid() != null) && (!"".equals(u.getPaperid()))) {
			t.setPapermng((Tpapermng) this.papermngDao.get(Tpapermng.class,
					u.getPaperid()));
		}
		this.subjectmngDao.save(t);
	}

	public List<Object[]> getSubjectmngs(Integer parerIdTemp) {//  导出数据
		
		return subjectmngDao
				.findBySql("select ifnull(pm.name,'') as'试卷名称'," +
						"ifnull(sm.name,'') as '问题题目',  " +
						" ifnull(sm.questiontype,'') as '问题维度'," +
					 "  ifnull(sm.answerA,'') as 'A选项',"+
					 " ifnull(sm.answerB,'') as 'B选项',"+
					" ifnull(sm.answerC,'') as 'C选项',"+
					" ifnull(sm.answerD,'') as 'D选项',"+
					" ifnull(sm.answerE,'') as 'E选项',"+
					" ifnull(sm.answerF,'') as 'F选项' ,"+
					"  (case ifnull(eal.answer,'') when 'O' then '其他' else  ifnull(eal.answer,'')  end ) as '回答选择项',"+
					"   ifnull(eal.text,'') as '个人描述',"+
					"   ifnull((select su.name from sys_user as su where su.id=eul.userid),'') as '姓名', "+
					" ifnull((select so.name from sys_organization as so where so.id=eul.orgid),'') as '部门',"+
					" ifnull((case eul.sex when 0 then '男' when 1 then '女' end ) ,'')as '性别',"+
					" ifnull(eul.age,0) as '年龄',"+
					" ifnull((select sd_edu.text from sys_dictionary as sd_edu where sd_edu.id = eul.educa),'') as '学历',"+
					" ifnull((select sd_divage.text from sys_dictionary as sd_divage where sd_divage.id = eul.divisionage),'') as '司龄',"+
					" ifnull((select sd_post.text from sys_dictionary as sd_post where sd_post.id = eul.post),'')as '职位',"+
					" ifnull((select sd_postlevel.text from sys_dictionary as sd_postlevel where sd_postlevel.id = eul.postlevel),'') as '职级',"+
					 "   date_format(eul.askdate,'%Y-%c-%d %h:%i:%s')  as '答题时间'"+
					 " from exam_ask_list as eal "+
						" inner join 	(select * from subject_mng where subject_mng.paperid="+ parerIdTemp + ") as sm on sm.id=eal.subjectid "+
						" inner join exam_user_list as eul on eul.id=eal.examuserlistid"+
						" inner join paper_mng as pm on pm.id=sm.paperid");
	}


	public void delete(Long id) {
		Tsubjectmng t = (Tsubjectmng) this.subjectmngDao.get(Tsubjectmng.class,
				id);
		del(t);
	}

	private void del(Tsubjectmng t) {
		this.subjectmngDao.delete(t);
	}

	public void edit(Subjectmng u) {
		Tsubjectmng t = (Tsubjectmng) this.subjectmngDao.get(Tsubjectmng.class,
				u.getId());
		t.setName(u.getName());
		if ((u.getPaperid() != null) && (!"".equals(u.getPaperid()))) {
			t.setPapermng((Tpapermng) this.papermngDao.get(Tpapermng.class,
					u.getPaperid()));
		}
		t.setAnswerA(u.getAnswerA());
		t.setAnswerB(u.getAnswerB());
		t.setAnswerC(u.getAnswerC());
		t.setAnswerD(u.getAnswerD());
		t.setAnswerE(u.getAnswerE());
		t.setAnswerF(u.getAnswerF());
		t.setKind(u.getKind());
		t.setQuestiontype(u.getQuestiontype());// 维度
		t.setDescription(u.getDescription());
		this.subjectmngDao.update(t);
	}

	public Subjectmng get(Long id) {
		Tsubjectmng t = (Tsubjectmng) this.subjectmngDao.get(Tsubjectmng.class,
				id);
		Subjectmng r = new Subjectmng();
		BeanUtils.copyProperties(t, r);
		if (t.getPapermng() != null) {
			r.setPaperid(t.getPapermng().getId());
			r.setPaperName(t.getPapermng().getName());
		}
		return r;
	}

	public Long count(Long paperId, PageFilter ph) {
		String where = "";
		if ((paperId != null) && (!"".equals(paperId))) {
			where = where + " where t.papermng.id=" + paperId;
		}
		String hql = " from Tsubjectmng t ";
		return this.subjectmngDao.count("select count(*) " + hql + where);
	}

	public void addexamUser(SessionInfo sessionInfo, ExamUserList examUserList,Boolean isotherclick) {
		TexamUserList texamUserList = new TexamUserList();
		Tuser u = (Tuser) this.userDao.get(Tuser.class, sessionInfo.getId());
		Long ishidden = examUserList.getIshidden();
		if (("1".equals(ishidden)) || (ishidden.longValue() == 1L)) {
			BeanUtils.copyProperties(examUserList, texamUserList);
		} else {
			
			 /*Integer disage = -1;
			  List<Object[]> oo1 = userService.getDivisionAge();
			  for(Object[] jj : oo1){
				  String ageRound = jj[1].toString();
				  String ageStr = ageRound.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
				  String[] ss = ageStr.split(",");
				  
				  if(ss.length==1){
					  if(sessionInfo.getDisage()==Integer.parseInt(ageStr)){
						  disage = Integer.parseInt(ageStr);
						  break;
					  }
				  }else {
					  Integer begin = Integer.parseInt(ss[0]);
					  Integer end = Integer.parseInt(ss[1]);
					  
					  if(sessionInfo.getDisage()>=begin&&sessionInfo.getDisage()<=end){
						  disage = Integer.parseInt(jj[0].toString());
						  break;
					  }
				}
			  }*/
			
			texamUserList.setAge(u.getAge());
			if(examUserList.getDivisionage()!=null){
				texamUserList.setDivisionage(u.getDivisionage());
			}else{
				texamUserList.setDivisionage(sessionInfo.getDisage());///司龄
			}
			texamUserList.setEduca(u.getEduca());
			texamUserList.setPost(u.getPost());
			texamUserList.setPostlevel(u.getPostlevel());
			texamUserList.setSex(u.getSex());
			texamUserList.setAgescope(examUserList.getAgescope());
			texamUserList.setOrgid(examUserList.getOrgid());
			texamUserList.setPaperid(examUserList.getPaperid());
		}
		texamUserList.setAskdate(new Date());
		texamUserList.setIshidden(ishidden);
		texamUserList.setUserid(sessionInfo.getId());
		Long subid = (Long) this.examUserListDao.save(texamUserList);
		texamUserList.setId(subid);
		
		String[] answers = examUserList.getAnswers().split(",");
		List<Integer> ids = new ArrayList<Integer>();
		for(String string : answers){
			String _ids = string.substring(1);
			TexamAskList texamAskList = new TexamAskList();
			texamAskList.setSubjectid(Long.parseLong(_ids));
			texamAskList.setAnswer(string.substring(0,1));
			texamAskList.setExamUserList(texamUserList);
			this.texamAskListDao.save(texamAskList);
		}
		if(examUserList.getSubjectids()!=null&&examUserList.getSubjectids().trim().length()!=0){
			String[] ss = examUserList.getSubjectids().substring(3).split("。。。");
			for(String string : ss){
				String[] dd = string.split("，，，");
				TexamAskList texamAskList = new TexamAskList();
				texamAskList.setSubjectid(Long.parseLong(dd[0]));
				if(dd.length==1){
					texamAskList.setText("");
				}else{
					texamAskList.setText(dd[1]);
				}
				texamAskList.setAnswer("O");
				texamAskList.setExamUserList(texamUserList);
				this.texamAskListDao.save(texamAskList);
			}
		}
		

	}

	public String alyxmlfromdb(Long subid, int typeAlyId) {
		String xml = null;
		String sql = "";
		StringBuffer sb = new StringBuffer();
		String resultName = "";
		if (typeAlyId == 1) {
			resultName="部门信息统计";
			sb.append(" select name,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count ");
			sb.append(" from ");
			sb.append(" (select  ");
			sb.append(" sm.id as smid,so.id as handleid,so.name as name,eal.answer as answer,count(*) as count ");
			sb.append(" from  exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join  ");
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_organization as so on  ");
			sb.append(" so.id=eul.orgid where sm.id=");
			sb.append(subid);
			sb.append(" group by so.id,so.name,eal.answer) as  _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,an.answer order by answer,name");
			sql = sb.toString();
		} else if (typeAlyId == 2) {
		} else if (typeAlyId == 3) {
			resultName="职级信息统计";
			sb.append(" select text,an.answer,");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count");
			sb.append(" from");
			sb.append(" ( select sm.id as smid,sd.id as handleid,sd.text as text,eal.answer as answer,count(*) as count");
			sb.append(" from exam_ask_list as eal");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.postlevel");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp");
			sb.append(" inner join answer as an");
			sb.append(" group by handleid,text,an.answer order by answer,text");
			sql = sb.toString();
		} else if (typeAlyId == 4) {
			resultName="性别信息统计";
			sb.append(" select (case sex1 when 0 then '男'  when 1 then '女' end) as sex,(case an.answer when 'O' then '其它' else an.answer end ) as answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,eul.sex as sex1,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by eul.sex,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by sex,an.answer  order by answer asc,sex desc ");
			sql = sb.toString();
		} else if (typeAlyId == 5) {
			resultName="年龄信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,seq ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid, ");
			sb.append(" sd.id as handleid, ");
			sb.append(" sd.text as text, ");
			sb.append(" sd.seq as seq, ");
			sb.append(" eal.answer as answer, ");
			sb.append(" count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.agescope ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by sd.code,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by  answer,seq ");
			sql = sb.toString();
		} else if (typeAlyId == 6) {
			resultName="教育信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.educa ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,text ");
			sql = sb.toString();
		} else if(typeAlyId == 7) {
			resultName="司龄信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,seq ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,sd.seq as seq,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.divisionage ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,seq ");
			sql = sb.toString();
		}else if(typeAlyId == 8){
			resultName="系统信息统计";
			sb.append(" select comsysname,answer,sum(count) from ( ");
			sb.append(" select smid,handleid,getcompanyname(name) as comname,getcompanysysname(name) as comsysname,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count ");
			sb.append(" from ");
			sb.append("  (select  ");
			sb.append("  sm.id as smid,so.id as handleid,so.name as name,eal.answer as answer,count(*) as count ");
			sb.append("  from "); 
			sb.append(" exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join "); 
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_organization as so on "); 
			sb.append(" so.id=eul.orgid ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append("  group by so.id,so.name,eal.answer) as "); 
			sb.append("  _temp ");
			sb.append("  inner join answer as an ");
			sb.append(" group by handleid,an.answer order by answer,name ");
			sb.append("  )  as ___tt ");
			sb.append("  where comsysname <>'公司' ");
			sb.append("  group by comsysname,answer ");
			sb.append("  order by answer,comsysname ");
			sql = sb.toString();
		}else if(typeAlyId==9){
			resultName="公司信息统计";
			sb.append(" select comname,answer,sum(bb) from ( select comname,answer,sum(count) as bb from ( ");
			sb.append(" select smid,handleid,getcompanyname(name) as comname,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count ");
			sb.append(" from ");
			sb.append(" (select  ");
			sb.append(" sm.id as smid,so.id as handleid,so.name as name,eal.answer as answer,count(*) as count ");
			sb.append(" from "); 
			sb.append(" exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join "); 
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_organization as so on  ");
			sb.append(" so.id=eul.orgid ");
			sb.append(" where sm.id=");
			sb.append(subid);
			sb.append(" group by so.id,so.name,eal.answer) as "); 
			sb.append(" _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,an.answer order by answer,name ");
			sb.append("  )  as ___tt ");
			//sb.append(" where comsysname <>'公司' ");
			sb.append(" group by comname,answer ");
			sb.append(" order by answer,comname) as jj group by  comname,answer  order by answer,comname ");
			sql = sb.toString();
		}
		List<Object[]> ewsList = this.subjectmngDao.findBySql(sql);

		return new Alyxml().alyToXml(ewsList, typeAlyId,resultName);
	}

	public String alyxmlfromdbQuesionType(Long subid, String quesionType,
			Integer typeAlyId) {
		String xml = null;
		String sql = "";
		StringBuffer sb = new StringBuffer();
		String resultName = "";
		if (typeAlyId == 1) {//部门
			resultName="部门信息统计";
			sb.append(" select name,an.answer,");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from");
			sb.append(" (select ");
			sb.append(" sm.id as smid,so.id as handleid,so.name as name,sm.questiontype as questiontype,eal.answer as answer,count(*) as count");
			sb.append(" from ");
			sb.append(" exam_ask_list as eal");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid");
			sb.append(" inner join ");
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid");
			sb.append(" inner join sys_organization as so on ");
			sb.append(" so.id=eul.orgid");
			sb.append(" where sm.questiontype='" + quesionType + "'");
			sb.append("  and sm.paperid=");
			sb.append(subid);
			sb.append(" group by so.id,so.name,eal.answer) as ");
			sb.append(" _temp");
			sb.append(" inner join answer as an");
			sb.append(" group by handleid,an.answer order by answer,name");
			sql = sb.toString();
		} else if (typeAlyId == 2) {
		} else if (typeAlyId == 3) {//职级
			resultName="职级信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.postlevel ");
			sb.append(" where sm.questiontype='" + quesionType
					+ "' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,text ");
			sql = sb.toString();
		} else if (typeAlyId == 4) {//性别
			resultName="性别信息统计";
			sb.append(" select (case sex1 when 0 then '男'  when 1 then '女' end) as sex,(case an.answer when 'O' then '其它' else an.answer end ) as answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,eul.sex as sex1,eal.answer as answer,sm.questiontype as questiontype,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" where sm.questiontype='"+quesionType+"' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by eul.sex,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by sex,an.answer order by answer ");
			sql = sb.toString();
		} else if (typeAlyId == 5) {//年龄
			resultName="年龄信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.agescope ");
			sb.append(" where sm.questiontype='" + quesionType
					+ "' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,text");
			sql = sb.toString();
		} else if (typeAlyId == 6) {//教育
			resultName="教育信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.educa ");
			sb.append(" where sm.questiontype='" + quesionType
					+ "' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,text");
			sql = sb.toString();
		} else if(typeAlyId == 7){//司龄
			resultName="司龄信息统计";
			sb.append(" select text,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype ");
			sb.append(" from ");
			sb.append(" (select sm.id as smid,sd.id as handleid,sd.text as text,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_dictionary as sd on sd.id=eul.divisionage ");
			sb.append(" where sm.questiontype='" + quesionType
					+ "' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by sd.id,sd.text,eal.answer) as _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,text,an.answer order by answer,text");
			sql = sb.toString();
		}else if (typeAlyId == 8) {//系统
			resultName="系统统计";
			sb.append(" select comsysname,answer,sum(count),questiontype from ( ");
			sb.append(" select name ,getcompanyname(name) as comname,getcompanysysname(name) as comsysname,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype  ");
			sb.append(" from ");
			sb.append(" (select  ");
			sb.append(" sm.id as smid,so.id as handleid,so.name as name,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from  ");
			sb.append(" exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join  ");
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_organization as so on  ");
			sb.append(" so.id=eul.orgid ");
			sb.append(" where sm.questiontype='"+quesionType+"' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by so.id,so.name,eal.answer) as  ");
			sb.append(" _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,an.answer order by answer,name )  as ___tt ");
			sb.append(" where comsysname <> '公司' ");
			sb.append(" group by comsysname,answer,questiontype ");
			sb.append(" order by answer,comsysname ");
			sql = sb.toString();
		}else if(typeAlyId == 9){//公司统计
			resultName="公司统计";
			sb.append(" select comname,answer,sum(count) as cc,questiontype from ( ");
			sb.append(" select name ,getcompanyname(name) as comname,getcompanysysname(name) as comsysname,an.answer, ");
			sb.append(" sum(if(an.answer=_temp.answer,count,0)) as count,questiontype  ");
			sb.append(" from ");
			sb.append(" (select  ");
			sb.append(" sm.id as smid,so.id as handleid,so.name as name,sm.questiontype as questiontype,eal.answer as answer,count(*) as count ");
			sb.append(" from  ");
			sb.append(" exam_ask_list as eal ");
			sb.append(" inner join subject_mng as sm on sm.id=eal.subjectid ");
			sb.append(" inner join "); 
			sb.append(" exam_user_list as eul on eul.id=eal.examuserlistid ");
			sb.append(" inner join sys_organization as so on "); 
			sb.append(" so.id=eul.orgid ");
			sb.append(" where sm.questiontype='"+quesionType+"' and sm.paperid=");
			sb.append(subid);
			sb.append(" group by so.id,so.name,eal.answer) as "); 
			sb.append(" _temp ");
			sb.append(" inner join answer as an ");
			sb.append(" group by handleid,an.answer order by answer,name )  as ___tt ");
			//sb.append(" where comsysname <> '公司' ");
			sb.append(" group by comname,answer,questiontype ");
			sb.append(" order by answer,comname");
			sql = sb.toString();
		}
		List ewsList = this.subjectmngDao.findBySql(sql);
		return new Alyxml().alyToXml(ewsList, typeAlyId,resultName);
	}

	public List<AlyOrgDto> alyOrgDB(Long subid, PageFilter ph) {
		String sql = "";
		sql = " select distinct A.id,A.cnt,B.percent ,B.userCnt,B.name ,if(A.cnt >=( B.percent * B.userCnt/100) ,'达标','不达标') from  (  select sys_organization.id,count(exam_user_list.userid) as cnt  \t from exam_user_list  LEFT JOIN sys_user   on exam_user_list.userid=sys_user.id LEFT JOIN  sys_organization   on sys_user.organization_id=sys_organization.id  where  exam_user_list.paperid="
				+ subid
				+ " group by exam_user_list.userid ) A "
				+ " LEFT JOIN "
				+ " (select sys_organization.id,sys_organization.percent,sys_organization.name,sys_organization.deptotalnum as userCnt from sys_user   "
				+ " LEFT JOIN sys_organization  on sys_user.organization_id=sys_organization.id group by sys_organization.id) B "
				+ " on B.id=A.id ;";
		List ewsList = this.subjectmngDao.findBySql(sql);
		List listEws = new ArrayList();
		if (!ewsList.isEmpty()) {
			for (int i = 0; i < ewsList.size(); i++) {
				Object[] obj = (Object[]) ewsList.get(i);
				AlyOrgDto sheetQueryResultDto = new AlyOrgDto();
				if (obj[0] != null)
					sheetQueryResultDto
							.setOrgid(Long.valueOf(obj[0].toString()));
				if (obj[1] != null)
					sheetQueryResultDto.setAskCnt(Long.valueOf(obj[1]
							.toString()));
				if (obj[2] != null)
					sheetQueryResultDto.setPercent(Long.valueOf(obj[2]
							.toString()));
				if (obj[3] != null)
					sheetQueryResultDto.setUserCnt(Long.valueOf(obj[3]
							.toString()));
				if (obj[4] != null)
					sheetQueryResultDto.setOrgName(obj[4].toString());
				if (obj[5] != null)
					sheetQueryResultDto.setIsFull(obj[5].toString());
				listEws.add(sheetQueryResultDto);
			}
		}
		return listEws;
	}

	public List<Subjectmng> treeGrid(String name, Long paperId, PageFilter ph) {
		String where = "";
		if ((paperId != null) && (!"".equals(paperId))) {
			where = where + "  where t.papermng.id=" + paperId + " ";
			if ((name != null) && (!"".equals(name))) {
				where = where + "  and  t.name like '%" + name + "%' ";
			}
		} else if ((name != null) && (!"".equals(name))) {
			where = where + "  where  t.name like '%" + name + "%' ";
		}

		List lr = new ArrayList();
		List<Tsubjectmng> l = this.subjectmngDao.find(
				"from Tsubjectmng t   left join fetch t.papermng   " + where
						+ orderHql(ph), ph.getPage(), ph.getRows());
		if ((l != null) && (l.size() > 0)) {
			for (Tsubjectmng t : l) {
				Subjectmng r = new Subjectmng();
				BeanUtils.copyProperties(t, r);
				if (t.getId() != null) {
					r.setId(t.getId());
					r.setName(t.getName());
				}
				r.setDescription(t.getDescription());
				r.setPaperid(t.getPapermng().getId());
				r.setType(t.getType());
				lr.add(r);
			}
		}
		return lr;
	}

	public Long count(String name, Long paperId, PageFilter ph) {
		String where = "";
		if ((paperId != null) && (!"".equals(paperId))) {
			where = where + "  where t.papermng.id=" + paperId;
			if ((name != null) && (!"".equals(name))) {
				where = where + "  and  t.name like '%" + name + "%' ";
			}
		} else if ((name != null) && (!"".equals(name))) {
			where = where + "  where  t.name like '%" + name + "%' ";
		}

		String hql = " from Tsubjectmng t ";
		return this.subjectmngDao.count("select count(*) " + hql + where);
	}

	public void addByList(Subjectmng subjectmng) {
		try {
			Long paperId = subjectmng.getPaperid();
			for (String id : subjectmng.getSubjectIds().split(",")) {
				Tsubjectmng t1 = new Tsubjectmng();
				t1 = (Tsubjectmng) this.subjectmngDao.get(Tsubjectmng.class,
						Long.valueOf(id));
				Tsubjectmng t2 = new Tsubjectmng();
				t2.setPapermng((Tpapermng) this.papermngDao.get(
						Tpapermng.class, paperId));
				t2.setAnswerA(t1.getAnswerA());
				t2.setAnswerB(t1.getAnswerB());
				t2.setAnswerC(t1.getAnswerC());
				t2.setAnswerD(t1.getAnswerD());
				t2.setAnswerE(t1.getAnswerE());
				t2.setAnswerF(t1.getAnswerF());
				t2.setQuestiontype(t1.getQuestiontype());// 维度
				t2.setDescription(t1.getDescription());
				t2.setName(t1.getName());
				t2.setType(t1.getType());
				t2.setKind(t1.getKind());
				this.subjectmngDao.save(t2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@Override
	public List<QuestionType> treeQuestionType(Integer paperid) {
		System.out.println("页面id:" + paperid);
		String sql = "select questiontype from subject_mng where paperid="
				+ paperid + " group by questiontype";
		List objects = subjectmngDao.findBySql(sql);
		int length = objects.size();
		QuestionType questionType = null;
		System.out.println("数组长度：" + length);
		List<QuestionType> questionTypes = new ArrayList<QuestionType>();
		for (int i = 0; i < length; i++) {
			questionType = new QuestionType();
			questionType.setId(i);
			questionType.setText((String) objects.get(i));
			questionTypes.add(questionType);
		}
		return questionTypes;
	}

	@Override
	public List<Object[]> getQuestionTypeAnswer(Integer paperid,
			String questionType) {
		return subjectmngDao.findBySql("select sm.answerA,sm.answerB,sm.answerC,sm.answerD,sm.answerE,sm.answerF from subject_mng as sm where sm.questiontype='"+questionType+"' and paperid='"+paperid+"' limit 1");
	}

	@Override
    public List<Object[]> getAgeScopes() {
		List<Object[]> list = subjectmngDao.findBySql("select id,text from sys_dictionary where dictionarytype_id=9");
		return list;
	}

	@Override
	public List<Object[]> getQuestionTypeAnswerOne(Integer paperid) {
		return subjectmngDao.findBySql("select sm.questiontype,sm.name from subject_mng as sm where paperid="+paperid+" group by questiontype limit 1");
	}

	@Override
	public List<Object[]> getOragsList() {
		return subjectmngDao.findBySql("select id,pid,name from sys_organization");
	}
}