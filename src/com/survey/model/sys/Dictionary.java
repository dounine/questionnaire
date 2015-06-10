package com.survey.model.sys;

import java.io.Serializable;

public class Dictionary
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String code;
  private String text;
  private Long dictionarytypeId;
  private String dictionarytypeName;
  private Integer seq;
  private Integer state;
  private Integer isdefault;

  public Dictionary()
  {
  }

  public Dictionary(String code, String text, Integer seq, Integer state, Integer isdefault)
  {
    this.code = code;
    this.text = text;
    this.seq = seq;
    this.state = state;
    this.isdefault = isdefault;
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

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public Integer getState() {
    return this.state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(Integer isdefault) {
    this.isdefault = isdefault;
  }

  public Long getDictionarytypeId() {
    return this.dictionarytypeId;
  }

  public void setDictionarytypeId(Long dictionarytypeId) {
    this.dictionarytypeId = dictionarytypeId;
  }

  public String getDictionarytypeName() {
    return this.dictionarytypeName;
  }

  public void setDictionarytypeName(String dictionarytypeName) {
    this.dictionarytypeName = dictionarytypeName;
  }
}