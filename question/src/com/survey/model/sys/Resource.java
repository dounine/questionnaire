package com.survey.model.sys;

import java.io.Serializable;
import java.util.Date;

public class Resource
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long pid;
  private String pname;
  private Long id;
  private Date createdatetime;
  private String name;
  private String url;
  private String description;
  private String iconCls;
  private Integer seq;
  private Integer resourcetype;
  private Integer cstate;
  private String icon;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPid() {
    return this.pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public Date getCreatedatetime() {
    return this.createdatetime;
  }

  public void setCreatedatetime(Date createdatetime) {
    this.createdatetime = createdatetime;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIconCls() {
    return this.iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public Integer getResourcetype() {
    return this.resourcetype;
  }

  public void setResourcetype(Integer resourcetype) {
    this.resourcetype = resourcetype;
  }

  public String getPname() {
    return this.pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Integer getCstate() {
    return this.cstate;
  }

  public void setCstate(Integer cstate) {
    this.cstate = cstate;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}