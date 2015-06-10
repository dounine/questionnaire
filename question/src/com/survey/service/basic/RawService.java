package com.survey.service.basic;

import com.survey.common.PageFilter;
import com.survey.model.basic.Raw;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public abstract interface RawService
{
  public abstract List<Raw> dataGrid(Raw paramRaw, PageFilter paramPageFilter);

  public abstract Long count(Raw paramRaw, PageFilter paramPageFilter);

  public abstract Raw get(Long paramLong);

  public abstract String getMaxCode();

  public abstract void add(Raw paramRaw, HttpServletRequest paramHttpServletRequest);

  public abstract void edit(Raw paramRaw, HttpServletRequest paramHttpServletRequest);

  public abstract void delete(Long paramLong);
}