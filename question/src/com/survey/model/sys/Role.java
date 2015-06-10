package com.survey.model.sys;

import java.io.Serializable;

public class Role
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Integer seq;
  private Integer isdefault;
  private String description;
  private String resourceIds;
  private String resourceNames;

  public Long getId()
  {
    return this.id;
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

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public Integer getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(Integer isdefault) {
    this.isdefault = isdefault;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getResourceIds() {
    return this.resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getResourceNames() {
    return this.resourceNames;
  }

  public void setResourceNames(String resourceNames) {
    this.resourceNames = resourceNames;
  }
}