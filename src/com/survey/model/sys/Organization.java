package com.survey.model.sys;

import java.io.Serializable;
import java.util.Date;

public class Organization
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date createdatetime;
  private String name;
  private String address;
  private String code;
  private String iconCls;
  private String icon;
  private Integer seq;
  private Long percent;
  private Long pid;
  private String pname;
  private Long deptotalnum;
  public Long getId()
  {
    return this.id;
  }

  public Long getDeptotalnum() {
	return deptotalnum;
}

public void setDeptotalnum(Long deptotalnum) {
	this.deptotalnum = deptotalnum;
}

public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIconCls() {
    return this.iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public Long getPid() {
    return this.pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public String getPname() {
    return this.pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Date getCreatedatetime() {
    return this.createdatetime;
  }

  public void setCreatedatetime(Date createdatetime) {
    this.createdatetime = createdatetime;
  }

  public Long getPercent() {
    return this.percent;
  }

  public void setPercent(Long percent) {
    this.percent = percent;
  }
}