package com.survey.model.sys;

import com.survey.model.paper.Tpapermng;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("all")
@Entity
@Table(name="sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser extends IdEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String loginname;
  private String password;
  private String name;
  private Integer sex;
  private Integer age;
  private Date createdatetime;
  private Integer usertype;
  private Integer isdefault;
  private Integer state;
  private Integer educa;
  private Integer divisionage;
  private Integer post;
  private Integer postlevel;
  private Torganization organization;
  private Set<Trole> roles = new HashSet(0);
  private Set<Tpapermng> papermngs = new HashSet(0);

  public Tuser()
  {
  }

  public Tuser(String loginname, String password, String name, Integer sex, Integer age, Date createdatetime, Integer usertype, Integer isdefault, Integer state, Integer educa, Integer divisionage, Integer post, Integer postlevel)
  {
    this.loginname = loginname;
    this.password = password;
    this.name = name;
    this.sex = sex;
    this.age = age;
    this.createdatetime = createdatetime;
    this.usertype = usertype;
    this.isdefault = isdefault;
    this.state = state;
    this.educa = educa;
    this.divisionage = divisionage;
    this.post = post;
    this.postlevel = postlevel;
  }

  @NotBlank
  public String getLoginname()
  {
    return this.loginname;
  }

public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotBlank
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSex() {
    return this.sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Integer getAge() {
    return this.age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="CREATEDATETIME", length=19)
  public Date getCreatedatetime() {
    return this.createdatetime;
  }

  public void setCreatedatetime(Date createdatetime) {
    this.createdatetime = createdatetime;
  }

  public Integer getUsertype() {
    return this.usertype;
  }

  public void setUsertype(Integer usertype) {
    this.usertype = usertype;
  }

  public Integer getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(Integer isdefault) {
    this.isdefault = isdefault;
  }

  public Integer getState() {
    return this.state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getEduca()
  {
    return this.educa;
  }

  public void setEduca(Integer educa)
  {
    this.educa = educa;
  }

  public Integer getDivisionage()
  {
    return this.divisionage;
  }

  public void setDivisionage(Integer divisionage)
  {
    this.divisionage = divisionage;
  }

  public Integer getPost()
  {
    return this.post;
  }

  public void setPost(Integer post)
  {
    this.post = post;
  }

  public Integer getPostlevel()
  {
    return this.postlevel;
  }

  public void setPostlevel(Integer postlevel)
  {
    this.postlevel = postlevel;
  }

  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="sys_user_role", joinColumns={@JoinColumn(name="user_id", nullable=false, updatable=false)}, inverseJoinColumns={@JoinColumn(name="role_id", nullable=false, updatable=false)})
  public Set<Trole> getRoles()
  {
    return this.roles;
  }

  public void setRoles(Set<Trole> roles) {
    this.roles = roles;
  }

  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="paper_user_rel", joinColumns={@JoinColumn(name="userid", nullable=false, updatable=false)}, inverseJoinColumns={@JoinColumn(name="paperid", nullable=false, updatable=false)})
  public Set<Tpapermng> getPapermngs() {
    return this.papermngs;
  }

  public void setPapermngs(Set<Tpapermng> papermngs)
  {
    this.papermngs = papermngs;
  }

  @NotNull
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="organization_id")
  public Torganization getOrganization() {
    return this.organization;
  }

  public void setOrganization(Torganization organization) {
    this.organization = organization;
  }
}