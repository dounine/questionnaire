package com.survey.model.sys;

import java.io.Serializable;

public class Dictionarytype
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String code;
  private String name;
  private Integer seq;
  private String description;
  private String pid;

  public Dictionarytype()
  {
  }

  public Dictionarytype(String code, String name, Integer seq, String description)
  {
    this.code = code;
    this.name = name;
    this.seq = seq;
    this.description = description;
  }

  public Long getId()
  {
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

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public String getDescription() {
    return this.description;
  }

  public String getPid() {
    return this.pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}