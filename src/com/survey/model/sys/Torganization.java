package com.survey.model.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("all")
@Entity
@Table(name="sys_organization", schema="")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Torganization extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Date createdatetime;
  private String name;
  private String address;
  private String code;
  private String icon;
  private Integer seq;
  private Long percent;
  private Torganization organization;
  private Long deptotalnum;
  private Set<Torganization> organizations = new HashSet(0);

  public Torganization()
  {
  }

  public Long getDeptotalnum() {
	return deptotalnum;
}

public void setDeptotalnum(Long deptotalnum) {
	this.deptotalnum = deptotalnum;
}

public Torganization(Date createdatetime, String name, String address, String code, String icon, Integer seq, Long percent, Torganization organization, Set<Torganization> organizations)
  {
    this.createdatetime = createdatetime;
    this.name = name;
    this.address = address;
    this.code = code;
    this.icon = icon;
    this.seq = seq;
    this.percent = percent;
    this.organization = organization;
    this.organizations = organizations;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="pid")
  public Torganization getOrganization() {
    return this.organization;
  }

  public void setOrganization(Torganization organization) {
    this.organization = organization;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="createdatetime", length=19)
  public Date getCreatedatetime() {
    return this.createdatetime;
  }

  public void setCreatedatetime(Date createdatetime) {
    this.createdatetime = createdatetime;
  }

  @NotBlank
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public Long getPercent()
  {
    return this.percent;
  }

  public void setPercent(Long percent) {
    this.percent = percent;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="organization")
  public Set<Torganization> getOrganizations() {
    return this.organizations;
  }

  public void setOrganizations(Set<Torganization> organizations) {
    this.organizations = organizations;
  }
}