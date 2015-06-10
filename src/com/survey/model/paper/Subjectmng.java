package com.survey.model.paper;

import java.io.Serializable;

public class Subjectmng
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long paperid;
  private String name;
  private String paperName;
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
  private String subjectIds;

  public Subjectmng()
  {
  }

  public Subjectmng(String kind,Long id, Long paperid, String name, String paperName, Integer type, String description, String answerA, String answerB, String answerC, String answerD, String answerE, String answerF, String subjectIds,String questiontype)
  {
	  this.kind = kind;
	this.questiontype = questiontype;//维度
    this.id = id;
    this.paperid = paperid;
    this.name = name;
    this.paperName = paperName;
    this.type = type;
    this.description = description;
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
    this.answerD = answerD;
    this.answerE = answerE;
    this.answerF = answerF;
    this.subjectIds = subjectIds;
  }

public String getKind() {
	return kind;
}

public void setKind(String kind) {
	this.kind = kind;
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

  public Long getPaperid()
  {
    return this.paperid;
  }

  public void setPaperid(Long paperid)
  {
    this.paperid = paperid;
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getPaperName()
  {
    return this.paperName;
  }

  public void setPaperName(String paperName)
  {
    this.paperName = paperName;
  }

  public String getSubjectIds()
  {
    return this.subjectIds;
  }

  public void setSubjectIds(String subjectIds)
  {
    this.subjectIds = subjectIds;
  }

	public String getQuestiontype() {
		return questiontype;
	}
	
	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

}