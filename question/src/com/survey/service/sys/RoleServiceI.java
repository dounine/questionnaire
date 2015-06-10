package com.survey.service.sys;

import com.survey.common.PageFilter;
import com.survey.common.Tree;
import com.survey.model.sys.Role;
import java.util.List;

public abstract interface RoleServiceI
{
  public abstract List<Role> dataGrid(Role paramRole, PageFilter paramPageFilter);

  public abstract Long count(Role paramRole, PageFilter paramPageFilter);

  public abstract void add(Role paramRole);

  public abstract void delete(Long paramLong);

  public abstract void edit(Role paramRole);

  public abstract Role get(Long paramLong);

  public abstract void grant(Role paramRole);

  public abstract List<Tree> tree();
}