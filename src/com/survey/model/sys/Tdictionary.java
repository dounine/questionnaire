package com.survey.model.sys;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="sys_dictionary", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tdictionary extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String code;
  private String text;
  private Tdictionarytype dictionarytype;
  private Integer seq;
  private Integer state;
  private Integer isdefault;

  public Tdictionary()
  {
  }

  public Tdictionary(String code, String text, Tdictionarytype dictionarytype, Integer seq, Integer state, Integer isdefault)
  {
    this.code = code;
    this.text = text;
    this.dictionarytype = dictionarytype;
    this.seq = seq;
    this.state = state;
    this.isdefault = isdefault;
  }

  @NotBlank
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @NotBlank
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }
  @NotNull
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="dictionarytype_id")
  public Tdictionarytype getDictionarytype() { return this.dictionarytype; }

  public void setDictionarytype(Tdictionarytype dictionarytype)
  {
    this.dictionarytype = dictionarytype;
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
}