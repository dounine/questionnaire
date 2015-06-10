package com.survey.model.paper;

import java.io.Serializable;
import java.util.Date;

public class Papermng
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date createdatetime;
  private String name;
  private String description;
  private Date faildate;
  private String iconCls;
  private String icon;
  private Integer seq;
  private Integer limitdate;
  private Long pid;
  private String pname;
  private String userIds;
  private String userNames;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Date getFaildate() {
    return this.faildate;
  }

  public void setFaildate(Date faildate) {
    this.faildate = faildate;
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

  public Integer getLimitdate() {
    return this.limitdate;
  }

  public void setLimitdate(Integer limitdate) {
    this.limitdate = limitdate;
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

  public String getUserIds() {
    return this.userIds;
  }

  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }

  public String getUserNames() {
    return this.userNames;
  }

  public void setUserNames(String userNames) {
    this.userNames = userNames;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}