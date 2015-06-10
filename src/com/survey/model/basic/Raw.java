package com.survey.model.basic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="basic_raw")
public class Raw
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(name="rawType")
  private String rawType;

  @Column(name="remark")
  private String remark;

  @Column(name="addPerson")
  private String addPerson;

  @Column(name="updatePerson")
  private String updatePerson;

  @Column(name="addTime")
  private Date addTime;

  @Column(name="updateTime")
  private Date updateTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRawType() {
    return this.rawType;
  }

  public void setRawType(String rawType) {
    this.rawType = rawType;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getAddPerson() {
    return this.addPerson;
  }

  public void setAddPerson(String addPerson) {
    this.addPerson = addPerson;
  }

  public String getUpdatePerson() {
    return this.updatePerson;
  }

  public void setUpdatePerson(String updatePerson) {
    this.updatePerson = updatePerson;
  }

  public Date getAddTime() {
    return this.addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}