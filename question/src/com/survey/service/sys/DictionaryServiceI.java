package com.survey.service.sys;

import com.survey.common.PageFilter;
import com.survey.model.sys.Dictionary;
import java.util.List;

public abstract interface DictionaryServiceI
{
  public abstract List<Dictionary> dataGrid(Dictionary paramDictionary, PageFilter paramPageFilter);

  public abstract Long count(Dictionary paramDictionary, PageFilter paramPageFilter);

  public abstract void add(Dictionary paramDictionary);

  public abstract void delete(Long paramLong);

  public abstract void edit(Dictionary paramDictionary);

  public abstract Dictionary get(Long paramLong);

  public abstract List<Dictionary> combox(String paramString);

  public abstract Dictionary checkUnique(Dictionary paramDictionary);
}