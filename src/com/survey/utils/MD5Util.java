package com.survey.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("all")
public class MD5Util
{
  public static void main(String[] args)
  {
    String s = "admin";
    System.out.println(md5(s));
  }

  public static String md5(String str)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());
      byte[] byteDigest = md.digest();

      StringBuffer buf = new StringBuffer("");
      for (byte element : byteDigest) {
        int i = element;
        if (i < 0) {
          i += 256;
        }
        if (i < 16) {
          buf.append("0");
        }
        buf.append(Integer.toHexString(i));
      }

      return buf.toString();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }return null;
  }
}