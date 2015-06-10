package com.survey.model.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
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
	private Long organizationId;
	private String organizationName;
	private String roleIds;
	private String roleNames;
	private String paperids;
	private String paperidNames;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
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

	public String getRoleIds() {
		return this.roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return this.roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getEduca() {
		return this.educa;
	}

	public void setEduca(Integer educa) {
		this.educa = educa;
	}

	public Integer getDivisionage() {
		return this.divisionage;
	}

	public void setDivisionage(Integer divisionage) {
		this.divisionage = divisionage;
	}

	public Integer getPost() {
		return this.post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public Integer getPostlevel() {
		return this.postlevel;
	}

	public void setPostlevel(Integer postlevel) {
		this.postlevel = postlevel;
	}

	public String getPaperids() {
		return this.paperids;
	}

	public void setPaperids(String paperids) {
		this.paperids = paperids;
	}

	public String getPaperidNames() {
		return this.paperidNames;
	}

	public void setPaperidNames(String paperidNames) {
		this.paperidNames = paperidNames;
	}
}