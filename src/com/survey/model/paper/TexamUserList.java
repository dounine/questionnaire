package com.survey.model.paper;

import com.survey.model.sys.IdEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="exam_user_list", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TexamUserList extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Date askdate;
  private Long userid;
  private Long paperid;
  private Long ishidden;
  private Integer sex;
  private Integer age;
  private Integer educa;
  private Integer divisionage;
  private Integer post;
  private Integer postlevel;
  private Integer orgid;
  private Integer agescope;

  public TexamUserList()
  {
  }

  public TexamUserList(Integer agescope,Integer orgid,Date askdate, Long userid, Long paperid, Long ishidden, Integer sex, Integer age, Integer educa, Integer divisionage, Integer post, Integer postlevel)
  {
	  this.agescope = agescope;//年龄
	  this.orgid = orgid;//部门 
    this.askdate = askdate;
    this.userid = userid;
    this.paperid = paperid;
    this.ishidden = ishidden;
    this.sex = sex;
    this.age = age;
    this.educa = educa;
    this.divisionage = divisionage;
    this.post = post;
    this.postlevel = postlevel;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="askdate", length=19)
  public Date getAskdate() {
    return this.askdate;
  }

  public Integer getAgescope() {
	return agescope;
}

public void setAgescope(Integer agescope) {
	this.agescope = agescope;
}

public void setAskdate(Date askdate) {
    this.askdate = askdate;
  }

  public Integer getOrgid() {
	return orgid;
}

public void setOrgid(Integer orgid) {
	this.orgid = orgid;
}

public Long getUserid()
  {
    return this.userid;
  }

  public void setUserid(Long userid)
  {
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
}