package com.survey.model.paper;

import java.io.Serializable;
import java.util.Date;

public class ExamUserList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date askdate;
  private Long userid;
  private Long paperid;
  private Long ishidden;
  private Integer sex;
  private Integer age;
  private Integer educa;
  private Integer orgid;
  private Integer divisionage;
  private Integer postlevel;
  private String subjectids;
  private Integer agescope;
  private String answers;
  private String text;

  public ExamUserList()
  {
  }

  public ExamUserList(String text,Integer agescope,Integer orgid,Long id, Date askdate, Long userid, Long paperid, Long ishidden, Integer sex, Integer age, Integer educa, Integer divisionage, Integer postlevel, String subjectids, String answers)
  {
	  this.text = text;
	  this.agescope = agescope;
    this.id = id;
    this.askdate = askdate;
    this.userid = userid;
    this.paperid = paperid;
    this.ishidden = ishidden;
    this.sex = sex;
    this.age = age;
    this.educa = educa;
    this.orgid = orgid;
    this.divisionage = divisionage;
    this.postlevel = postlevel;
    this.subjectids = subjectids;
    this.answers = answers;
  }

  
  public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Integer getAgescope() {
	return agescope;
}

public void setAgescope(Integer agescope) {
	this.agescope = agescope;
}

public Long getId() {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Date getAskdate() {
    return this.askdate;
  }
  public void setAskdate(Date askdate) {
    this.askdate = askdate;
  }
  public Long getUserid() {
    return this.userid;
  }
  
  public Integer getOrgid() {
	return orgid;
}

public void setOrgid(Integer orgid) {
	this.orgid = orgid;
}

public void setUserid(Long userid) {
    this.userid = userid;
  }
  public Long getPaperid() {
    return this.paperid;
  }
  public void setPaperid(Long paperid) {
    this.paperid = paperid;
  }
  public Long getIshidden() {
    return this.ishidden;
  }
  public void setIshidden(Long ishidden) {
    this.ishidden = ishidden;
  }
  public Integer getSex() {
    return this.sex;
  }
  public void setSex(Integer sex) {
    this.sex = sex;
  }
  public Integer getAge() {
    return this.age;
  }
  public void setAge(Integer age) {
    this.age = age;
  }
  public Integer getEduca() {
    return this.educa;
  }
  public void setEduca(Integer educa) {
    this.educa = educa;
  }
  public Integer getDivisionage() {
    return this.divisionage;
  }
  public void setDivisionage(Integer divisionage) {
    this.divisionage = divisionage;
  }
  public Integer getPostlevel() {
    return this.postlevel;
  }
  public void setPostlevel(Integer postlevel) {
    this.postlevel = postlevel;
  }
  public String getSubjectids() {
    return this.subjectids;
  }
  public void setSubjectids(String subjectids) {
    this.subjectids = subjectids;
  }
  public String getAnswers() {
    return this.answers;
  }
  public void setAnswers(String answers) {
    this.answers = answers;
  }
}