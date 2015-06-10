package com.survey.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Grid
  implements Serializable
{
  private Long total = Long.valueOf(0L);

  private List rows = new ArrayList();

  public Long getTotal() {
    return this.total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public List getRows()
  {
    return this.rows;
  }

  public void setRows(List rows)
  {
    this.rows = rows;
  }
}