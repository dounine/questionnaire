package com.survey.common;

import java.io.Serializable;
import java.util.List;

public class SessionInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String loginname;
  private String name;
  private String ip;
  private Integer orgid;
  private Integer agescope;//年龄
  private Integer disage;//司龄
  private List<String> resourceList;
  private List<String> resourceAllList;
  private String rolesname;
  public String getRolesname() {
	return rolesname;
}

public void setRolesname(String rolesname) {
	this.rolesname = rolesname;
}

public Integer getDisage() {
	return disage;
}

public void setDisage(Integer disage) {
	this.disage = disage;
}

public Integer getOrgid() {
	return orgid;
}

public void setOrgid(Integer orgid) {
	this.orgid = orgid;
}

public Integer getAgescope() {
	return agescope;
}

public void setAgescope(Integer agescope) {
	this.agescope = agescope;
}

public List<String> getResourceList()
  {
    return this.resourceList;
  }

  public void setResourceList(List<String> resourceList) {
    this.resourceList = resourceList;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLoginname() {
    return this.loginname;
  }

  public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  public List<String> getResourceAllList() {
    return this.resourceAllList;
  }

  public void setResourceAllList(List<String> resourceAllList) {
    this.resourceAllList = resourceAllList;
  }

  public String toString()
  {
    return this.name;
  }
}