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
@Table(name="exam_ask_list", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TexamAskList extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long subjectid;
  private String answer;
  private String text;
  private TexamUserList examUserList;

  public TexamAskList()
  {
  }

  public TexamAskList(String text,Long subjectid, String answer, TexamUserList examUserList)
  {
	  this.text = text;
    this.subjectid = subjectid;
    this.answer = answer;
    this.examUserList = examUserList;
  }

  public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Long getSubjectid()
  {
    return this.subjectid;
  }

  public void setSubjectid(Long subjectid)
  {
    this.subjectid = subjectid;
  }

  public String getAnswer()
  {
    return this.answer;
  }

  public void setAnswer(String answer)
  {
    this.answer = answer;
  }

  @NotNull
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="examuserlistid")
  public TexamUserList getExamUserList() {
    return this.examUserList;
  }

  public void setExamUserList(TexamUserList examUserList) {
    this.examUserList = examUserList;
  }
}