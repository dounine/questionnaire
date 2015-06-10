package com.survey.model.paper;

import java.io.Serializable;

public class AlyOrgDto
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long orgid;
  private Long askCnt;
  private Long percent;
  private Long userCnt;
  private String orgName;
  private String isFull;

  public AlyOrgDto()
  {
  }

  public AlyOrgDto(Long orgid, Long askCnt, Long percent, Long userCnt, String orgName, String isFull)
  {
    this.orgid = orgid;
    this.askCnt = askCnt;
    this.percent = percent;
    this.userCnt = userCnt;
    this.orgName = orgName;
    this.isFull = isFull;
  }

  public Long getOrgid()
  {
    return this.orgid;
  }

  public void setOrgid(Long orgid)
  {
    this.orgid = orgid;
  }

  public Long getAskCnt()
  {
    return this.askCnt;
  }

  public void setAskCnt(Long askCnt)
  {
    this.askCnt = askCnt;
  }

  public Long getPercent()
  {
    return this.percent;
  }

  public void setPercent(Long percent)
  {
    this.percent = percent;
  }

  public Long getUserCnt()
  {
    return this.userCnt;
  }

  public void setUserCnt(Long userCnt)
  {
    this.userCnt = userCnt;
  }

  public String getOrgName()
  {
    return this.orgName;
  }

  public void setOrgName(String orgName)
  {
    this.orgName = orgName;
  }

  public String getIsFull()
  {
    return this.isFull;
  }

  public void setIsFull(String isFull)
  {
    this.isFull = isFull;
  }
}