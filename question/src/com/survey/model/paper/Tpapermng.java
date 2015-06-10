package com.survey.model.paper;

import com.survey.model.sys.IdEntity;
import com.survey.model.sys.Tuser;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("all")
@Entity
@Table(name="paper_mng", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tpapermng extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Date createdatetime;
  private String name;
  private Date faildate;
  private String icon;
  private Integer seq;
  private Integer limitdate;
  private String description;
  private Tpapermng papermng;
  private Set<Tpapermng> papermngs = new HashSet(0);

  private Set<Tuser> users = new HashSet(0);

  public Tpapermng()
  {
  }

  public Tpapermng(Date createdatetime, String name, Date faildate, String icon, Integer seq, Integer limitdate, String description, Tpapermng papermng, Set<Tpapermng> papermngs, Set<Tuser> users)
  {
    this.createdatetime = createdatetime;
    this.name = name;
    this.faildate = faildate;
    this.icon = icon;
    this.seq = seq;
    this.limitdate = limitdate;
    this.description = description;
    this.papermng = papermng;
    this.papermngs = papermngs;
    this.users = users;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="createdatetime", length=19)
  public Date getCreatedatetime()
  {
    return this.createdatetime;
  }

  public void setCreatedatetime(Date createdatetime)
  {
    this.createdatetime = createdatetime;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="faildate", length=19)
  public Date getFaildate() {
    return this.faildate;
  }

  public void setFaildate(Date faildate)
  {
    this.faildate = faildate;
  }

  public String getIcon()
  {
    return this.icon;
  }

  public void setIcon(String icon)
  {
    this.icon = icon;
  }

  public Integer getSeq()
  {
    return this.seq;
  }

  public void setSeq(Integer seq)
  {
    this.seq = seq;
  }

  public Integer getLimitdate()
  {
    return this.limitdate;
  }

  public void setLimitdate(Integer limitdate)
  {
    this.limitdate = limitdate;
  }

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="pid")
  public Tpapermng getPapermng() {
    return this.papermng;
  }

  public void setPapermng(Tpapermng papermng)
  {
    this.papermng = papermng;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="papermng")
  public Set<Tpapermng> getPapermngs()
  {
    return this.papermngs;
  }

  public void setPapermngs(Set<Tpapermng> papermngs)
  {
    this.papermngs = papermngs;
  }
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="paper_user_rel", joinColumns={@JoinColumn(name="paperid", nullable=false, updatable=false)}, inverseJoinColumns={@JoinColumn(name="userid", nullable=false, updatable=false)})
  @OrderBy("id ASC")
  public Set<Tuser> getUsers() {
    return this.users;
  }

  public void setUsers(Set<Tuser> users) {
    this.users = users;
  }
}