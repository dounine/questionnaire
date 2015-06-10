package com.survey.model.sys;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("all")
@Entity
@Table(name="sys_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trole extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String name;
  private Integer seq;
  private Integer isdefault;
  private String description;
  private Set<Tresource> resources = new HashSet(0);
  private Set<Tuser> users = new HashSet(0);

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

  public Integer getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(Integer isdefault) {
    this.isdefault = isdefault;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="sys_role_resource", joinColumns={@javax.persistence.JoinColumn(name="role_id", nullable=false, updatable=false)}, inverseJoinColumns={@javax.persistence.JoinColumn(name="resource_id", nullable=false, updatable=false)})
  @OrderBy("id ASC")
  public Set<Tresource> getResources() { return this.resources; }

  public void setResources(Set<Tresource> resources)
  {
    this.resources = resources;
  }
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="sys_user_role", joinColumns={@javax.persistence.JoinColumn(name="role_id", nullable=false, updatable=false)}, inverseJoinColumns={@javax.persistence.JoinColumn(name="user_id", nullable=false, updatable=false)})
  @OrderBy("id ASC")
  public Set<Tuser> getUsers() { return this.users; }

  public void setUsers(Set<Tuser> users)
  {
    this.users = users;
  }
}