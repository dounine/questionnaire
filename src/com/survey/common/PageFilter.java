package com.survey.common;

import java.io.Serializable;

public class PageFilter
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int page;
  private int rows;
  private String sort;
  private String order;

  public int getPage()
  {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getRows() {
    return this.rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public String getSort() {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}