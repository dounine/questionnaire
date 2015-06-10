package com.survey.functional.sys;

import com.survey.functional.base.BaseTestTemplate;
import com.survey.model.sys.User;
import com.survey.service.sys.UserServiceI;
import javax.annotation.Resource;
import org.junit.Test;

@SuppressWarnings("all")
public class UserRestFT extends BaseTestTemplate
{

  @Resource
  private UserServiceI userService;

  @Test
  public void list()
  {
    User user = new User();
    user.setLoginname("admin");
    user.setPassword("admin");
    User u = this.userService.login(user);
  }
}