package com.survey.service.paper;

import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.model.paper.Papermng;
import java.util.List;

public abstract interface PapermngServiceI
{
  public abstract List<Papermng> treeGrid();

  public abstract void add(Papermng paramPapermng);

  public abstract void delete(Long paramLong);

  public abstract void edit(Papermng paramPapermng);

  public abstract Papermng get(Long paramLong);

  public abstract List<Tree> tree();

  public abstract List<Tree> treeByUser(SessionInfo paramSessionInfo);

  public abstract void grant(Papermng paramPapermng);
}