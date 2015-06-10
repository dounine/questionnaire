package com.survey.model.paper;

import java.io.Serializable;

public class Answermng
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String code;
  private String name;
  private String description;
  private Long subjectid;

  public Answermng()
  {
  }

  public Answermng(Long id, String code, String name, String description, Long subjectid)
  {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.subjectid = subjectid;
  }
  public Long getId() {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getSubjectid() {
    return this.subjectid;
  }
  public void setSubjectid(Long subjectid) {
    this.subjectid = subjectid;
  }
}