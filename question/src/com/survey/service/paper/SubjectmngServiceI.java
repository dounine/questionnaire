package com.survey.service.paper;

import java.util.List;

import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.model.paper.AlyOrgDto;
import com.survey.model.paper.ExamUserList;
import com.survey.model.paper.QuestionType;
import com.survey.model.paper.Subjectmng;

public abstract interface SubjectmngServiceI
{
	public List<Object[]> getOragsList();
	
public List<Object[]> getQuestionTypeAnswerOne(Integer paperid);
	
  public List<Object[]> getAgeScopes();
	
  public List<Object[]> getQuestionTypeAnswer(Integer paperid,String questionType);
  
  public abstract List<QuestionType> treeQuestionType(Integer paperid);
	
  public abstract List<Subjectmng> treeGrid(Long paramLong, PageFilter paramPageFilter);

  public abstract List<Subjectmng> treeGrid(String paramString, Long paramLong, PageFilter paramPageFilter);
  
  public abstract List<Object[]> getSubjectmngs(Integer parerIdTemp);

  public abstract void add(Subjectmng paramSubjectmng);

  public abstract void delete(Long paramLong);

  public abstract void edit(Subjectmng paramSubjectmng);

  public abstract void addByList(Subjectmng paramSubjectmng);

  public abstract Subjectmng get(Long paramLong);

  public abstract Long count(Long paramLong, PageFilter paramPageFilter);

  public abstract Long count(String paramString, Long paramLong, PageFilter paramPageFilter);

  public abstract void addexamUser(SessionInfo paramSessionInfo, ExamUserList paramExamUserList,Boolean isotherclick);

  public abstract String alyxmlfromdb(Long paramLong, int paramInt);
  
  public abstract String alyxmlfromdbQuesionType(Long paramLong, String quesionType,Integer typeAlyId);

  public abstract List<AlyOrgDto> alyOrgDB(Long paramLong, PageFilter paramPageFilter);
}