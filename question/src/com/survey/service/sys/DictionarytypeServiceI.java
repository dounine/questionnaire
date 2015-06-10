package com.survey.service.sys;

import com.survey.common.Tree;
import com.survey.model.sys.Dictionarytype;
import java.util.List;

public abstract interface DictionarytypeServiceI
{
  public abstract void add(Dictionarytype paramDictionarytype);

  public abstract void delete(Long paramLong);

  public abstract void edit(Dictionarytype paramDictionarytype);

  public abstract Dictionarytype get(Long paramLong);

  public abstract List<Tree> tree();
}