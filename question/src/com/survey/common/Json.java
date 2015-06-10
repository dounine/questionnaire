package com.survey.common;

import java.io.Serializable;

public class Json
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private boolean success = false;

  private String msg = "";

  private Object obj = null;

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getObj() {
    return this.obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }
}