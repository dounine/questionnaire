package com.survey.model.paper;

import com.survey.model.sys.IdEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="subject_mng", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tsubjectmng extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String name;
  private Integer type;
  private String description;
  private String answerA;
  private String answerB;
  private String answerC;
  private String answerD;
  private String answerE;
  private String answerF;
  private String questiontype;//维度
  private String kind;//类型
  private Tpapermng papermng;

  public Tsubjectmng()
  {
  }

  public Tsubjectmng(String kind,String questiontype,String name, Integer type, String description, String answerA, String answerB, String answerC, String answerD, String answerE, String answerF)
  {
	this.kind = kind;
    this.name = name;
    this.type = type;
    this.description = description;
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
    this.answerD = answerD;
    this.answerE = answerE;
    this.answerF = answerF;
  }


public String getKind() {
	return kind;
}

public void setKind(String kind) {
	this.kind = kind;
}

public String getQuestiontype() {
	return questiontype;
}

public void setQuestiontype(String questiontype) {
	this.questiontype = questiontype;
}

public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAnswerA()
  {
    return this.answerA;
  }

  public void setAnswerA(String answerA)
  {
    this.answerA = answerA;
  }

  public String getAnswerB()
  {
    return this.answerB;
  }

  public void setAnswerB(String answerB)
  {
    this.answerB = answerB;
  }

  public String getAnswerC()
  {
    return this.answerC;
  }

  public void setAnswerC(String answerC)
  {
    this.answerC = answerC;
  }

  public String getAnswerD()
  {
    return this.answerD;
  }

  public void setAnswerD(String answerD)
  {
    this.answerD = answerD;
  }

  public String getAnswerE()
  {
    return this.answerE;
  }

  public void setAnswerE(String answerE)
  {
    this.answerE = answerE;
  }

  public String getAnswerF()
  {
    return this.answerF;
  }

  public void setAnswerF(String answerF)
  {
    this.answerF = answerF;
  }

  @NotNull
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="paperid")
  public Tpapermng getPapermng()
  {
    return this.papermng;
  }

  public void setPapermng(Tpapermng papermng)
  {
    this.papermng = papermng;
  }
}