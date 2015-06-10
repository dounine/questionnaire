package com.survey.model.sys;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="sys_dictionarytype", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tdictionarytype extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String code;
  private String name;
  private Integer seq;
  private String description;
  private Tdictionarytype dictionarytype;

  public Tdictionarytype()
  {
  }

  public Tdictionarytype(String code, String name, Integer seq, String description)
  {
    this.code = code;
    this.name = name;
    this.seq = seq;
    this.description = description;
  }

  @NotBlank
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @NotBlank
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

  public void setDescription(String description) {
    this.description = description;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="pid")
  public Tdictionarytype getDictionarytype() {
    return this.dictionarytype;
  }

  public void setDictionarytype(Tdictionarytype dictionarytype) {
    this.dictionarytype = dictionarytype;
  }
}