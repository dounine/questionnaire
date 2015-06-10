package com.survey.utils;

public class StringUtil
{
  public static String formateString(String str, String[] params)
  {
    for (int i = 0; i < params.length; i++) {
      str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
    }
    return str;
  }
}