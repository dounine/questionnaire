package com.survey.service.sys;

import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.model.sys.User;
import java.util.List;

public abstract interface UserServiceI
{
  public List<Object[]> getDivisionAge();//司龄
  public List<Object[]> getEducate();//教育
  public List<Object[]> getPostLevel();//职级
  public List<Object[]> getPost();//职位
  public List<Object[]> getAgescope();//年龄
  
  public abstract List<User> dataGrid(User paramUser, PageFilter paramPageFilter);

  public abstract Long count(User paramUser, PageFilter paramPageFilter);

  public abstract void add(User paramUser);

  public abstract void delete(Long paramLong);

  public abstract void edit(User paramUser);

  public abstract User get(Long paramLong);

  public abstract User login(User paramUser);

  public abstract List<String> resourceList(Long paramLong);

  public abstract boolean editUserPwd(SessionInfo paramSessionInfo, String paramString1, String paramString2);

  public abstract User getByLoginName(User paramUser);
}