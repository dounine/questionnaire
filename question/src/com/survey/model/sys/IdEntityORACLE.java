package com.survey.model.sys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class IdEntityORACLE
{
  protected Long id;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserSequence")
  @SequenceGenerator(name="UserSequence", sequenceName="SEQ_USER", allocationSize=20)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}