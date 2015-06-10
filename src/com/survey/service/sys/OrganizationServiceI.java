package com.survey.service.sys;

import com.survey.common.Tree;
import com.survey.model.sys.Organization;
import java.util.List;

public abstract interface OrganizationServiceI
{
	
	
  public abstract List<Organization> treeGrid();

  public abstract void add(Organization paramOrganization);

  public abstract void delete(Long paramLong);

  public abstract void edit(Organization paramOrganization);

  public abstract Organization get(Long paramLong);
  
  public List<Object[]> getOrganizations();

  public abstract List<Tree> tree();
}