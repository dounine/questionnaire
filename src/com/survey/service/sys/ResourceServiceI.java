package com.survey.service.sys;

import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.model.sys.Resource;
import java.util.List;

public abstract interface ResourceServiceI
{
  public abstract List<Resource> treeGrid();

  public abstract void add(Resource paramResource);

  public abstract void delete(Long paramLong);

  public abstract void edit(Resource paramResource);

  public abstract Resource get(Long paramLong);

  public abstract List<Tree> tree(SessionInfo paramSessionInfo);

  public abstract List<Tree> allTree(boolean paramBoolean);

  public abstract List<String> resourceAllList();
}