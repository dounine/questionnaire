package com.survey.model.paper;

import java.io.Serializable;

public class AlyxmlDto
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long userid;
  private Long paperid;
  private Long ishidden;
  private Integer sex;
  private String sexName;
  private Integer age;
  private Integer educa;
  private String educaName;
  private Integer divisionage;
  private String divisionageName;
  private Integer post;
  private String postName;
  private Integer postlevel;
  private String postlevelName;
  private Long subjectid;
  private String answer;
  private Long orgid;
  private String orgname;
  private Long cont;

  public AlyxmlDto()
  {
  }

  public AlyxmlDto(Long userid, Long paperid, Long ishidden, Integer sex, String sexName, Integer age, Integer educa, String educaName, Integer divisionage, String divisionageName, Integer post, String postName, Integer postlevel, String postlevelName, Long subjectid, String answer, Long orgid, String orgname, Long cont)
  {
    this.userid = userid;
    this.paperid = paperid;
    this.ishidden = ishidden;
    this.sex = sex;
    this.sexName = sexName;
    this.age = age;
    this.educa = educa;
    this.educaName = educaName;
    this.divisionage = divisionage;
    this.divisionageName = divisionageName;
    this.post = post;
    this.postName = postName;
    this.postlevel = postlevel;
    this.postlevelName = postlevelName;
    this.subjectid = subjectid;
    this.answer = answer;
    this.orgid = orgid;
    this.orgname = orgname;
    this.cont = cont;
  }

  public Long getUserid()
  {
    return this.userid;
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
  public Integer getPost() {
    return this.post;
  }
  public void setPost(Integer post) {
    this.post = post;
  }
  public Integer getPostlevel() {
    return this.postlevel;
  }
  public void setPostlevel(Integer postlevel) {
    this.postlevel = postlevel;
  }
  public Long getSubjectid() {
    return this.subjectid;
  }
  public void setSubjectid(Long subjectid) {
    this.subjectid = subjectid;
  }
  public String getAnswer() {
    return this.answer;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }
  public Long getOrgid() {
    return this.orgid;
  }
  public void setOrgid(Long orgid) {
    this.orgid = orgid;
  }
  public String getOrgname() {
    return this.orgname;
  }
  public void setOrgname(String orgname) {
    this.orgname = orgname;
  }

  public Long getCont()
  {
    return this.cont;
  }

  public void setCont(Long cont)
  {
    this.cont = cont;
  }

  public String getSexName()
  {
    return this.sexName;
  }

  public void setSexName(String sexName)
  {
    this.sexName = sexName;
  }

  public String getEducaName()
  {
    return this.educaName;
  }

  public void setEducaName(String educaName)
  {
    this.educaName = educaName;
  }

  public String getDivisionageName()
  {
    return this.divisionageName;
  }

  public void setDivisionageName(String divisionageName)
  {
    this.divisionageName = divisionageName;
  }

  public String getPostName()
  {
    return this.postName;
  }

  public void setPostName(String postName)
  {
    this.postName = postName;
  }

  public String getPostlevelName()
  {
    return this.postlevelName;
  }

  public void setPostlevelName(String postlevelName)
  {
    this.postlevelName = postlevelName;
  }
}