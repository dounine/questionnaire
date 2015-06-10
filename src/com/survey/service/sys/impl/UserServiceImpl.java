package com.survey.service.sys.impl;

import com.survey.common.GlobalConstant;
import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.dao.BaseDaoI;
import com.survey.model.paper.Tpapermng;
import com.survey.model.sys.Torganization;
import com.survey.model.sys.Tresource;
import com.survey.model.sys.Trole;
import com.survey.model.sys.Tuser;
import com.survey.model.sys.User;
import com.survey.service.sys.UserServiceI;
import com.survey.utils.MD5Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class UserServiceImpl
  implements UserServiceI
{

  @Autowired
  private BaseDaoI<Tuser> userDao;

  @Autowired
  private BaseDaoI<Trole> roleDao;

  @Autowired
  private BaseDaoI<Torganization> organizationDao;
  
  

  public void add(User u)
  {
    Tuser t = new Tuser();
    BeanUtils.copyProperties(u, t);
    t.setIsdefault(Integer.valueOf(1));
    t.setOrganization((Torganization)this.organizationDao.get(Torganization.class, u.getOrganizationId()));

    List roles = new ArrayList();
    if (u.getRoleIds() != null) {
      for (String roleId : u.getRoleIds().split(",")) {
        roles.add((Trole)this.roleDao.get(Trole.class, Long.valueOf(roleId)));
      }
    }
    t.setRoles(new HashSet(roles));
    if (u.getPassword().equals("")||u.getPassword()==null ){
    	 t.setPassword(MD5Util.md5("123"));
    }else{
    	 t.setPassword(MD5Util.md5(u.getPassword()));
    }
   
    t.setState(GlobalConstant.ENABLE);
    t.setCreatedatetime(new Date());
    t.setDivisionage(u.getDivisionage());
    t.setEduca(u.getEduca());
    t.setPost(u.getPost());
    t.setPostlevel(u.getPostlevel());
    this.userDao.save(t);
  }

  public void delete(Long id)
  {
    Tuser t = (Tuser)this.userDao.get(Tuser.class, id);
    del(t);
  }

  private void del(Tuser t) {
    this.userDao.delete(t);
  }

  public void edit(User user)
  {
    Tuser t = (Tuser)this.userDao.get(Tuser.class, user.getId());
    t.setAge(user.getAge());
    t.setLoginname(user.getLoginname());
    t.setName(user.getName());
    t.setOrganization((Torganization)this.organizationDao.get(Torganization.class, user.getOrganizationId()));
    List roles = new ArrayList();
    if (user.getRoleIds() != null) {
      for (String roleId : user.getRoleIds().split(",")) {
        roles.add((Trole)this.roleDao.get(Trole.class, Long.valueOf(roleId)));
      }
    }
    t.setRoles(new HashSet(roles));
    t.setSex(user.getSex());
    t.setUsertype(user.getUsertype());
    t.setDivisionage(user.getDivisionage());
    t.setEduca(user.getEduca());
    t.setPost(user.getPost());
    t.setPostlevel(user.getPostlevel());
    if ((user.getPassword() != null) && (!"".equals(user.getPassword()))) {
      t.setPassword(MD5Util.md5(user.getPassword()));
    }
    this.userDao.update(t);
  }

  public User get(Long id)
  {
    Map params = new HashMap();
    params.put("id", id);
    Tuser t = (Tuser)this.userDao.get("from Tuser t  left join fetch t.roles role where t.id = :id", params);
    User u = new User();
    BeanUtils.copyProperties(t, u);

    if (t.getOrganization() != null) {
      u.setOrganizationId(t.getOrganization().getId());
      u.setOrganizationName(t.getOrganization().getName());
    }

    if ((t.getRoles() != null) && (!t.getRoles().isEmpty())) {
      String roleIds = "";
      String roleNames = "";
      boolean b = false;
      for (Trole role : t.getRoles()) {
        if (b) {
          roleIds = roleIds + ",";
          roleNames = roleNames + ",";
        } else {
          b = true;
        }
        roleIds = roleIds + role.getId();
        roleNames = roleNames + role.getName();
      }
      u.setRoleIds(roleIds);
      u.setRoleNames(roleNames);
    }
    return u;
  }

  public User login(User user)
  {
    Map params = new HashMap();
    params.put("loginname", user.getLoginname());
    params.put("password", MD5Util.md5(user.getPassword()));
    Tuser t = (Tuser)this.userDao.get("from Tuser t where t.loginname = :loginname and t.password = :password", params);
    if (t != null) {
      User u = new User();
      BeanUtils.copyProperties(t, u);
      u.setOrganizationId(t.getOrganization().getId());
      return u;
    }
    return null;
  }

  public List<String> resourceList(Long id)
  {
    List resourceList = new ArrayList();
    Map params = new HashMap();
    params.put("id", id);
    Tuser t = (Tuser)this.userDao.get(
      "from Tuser t join fetch t.roles role join fetch role.resources resource where t.id = :id", params);
    if (t != null) {
      Set<Trole> roles = t.getRoles();
      if ((roles != null) && (!roles.isEmpty())) {
        for (Trole role : roles) {
          Set<Tresource> resources = role.getResources();
          if ((resources != null) && (!resources.isEmpty())) {
            for (Tresource resource : resources) {
              if ((resource != null) && (resource.getUrl() != null)) {
                resourceList.add(resource.getUrl());
              }
            }
          }
        }
      }
    }
    return resourceList;
  }

  public List<User> dataGrid(User user, PageFilter ph)
  {
    List ul = new ArrayList();
    Map params = new HashMap();
    String hql = " from Tuser t ";
    List<Tuser> l = this.userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
    for (Tuser t : l) {
      User u = new User();
      BeanUtils.copyProperties(t, u);
      Set<Trole> roles = t.getRoles();
      if ((roles != null) && (!roles.isEmpty())) {
        String roleIds = "";
        String roleNames = "";
        boolean b = false;
        for (Trole tr : roles) {
          if (b) {
            roleIds = roleIds + ",";
            roleNames = roleNames + ",";
          } else {
            b = true;
          }
          roleIds = roleIds + tr.getId();
          roleNames = roleNames + tr.getName();
        }
        u.setRoleIds(roleIds);
        u.setRoleNames(roleNames);
      }

      Set<Tpapermng> papermngs = t.getPapermngs();
      if ((papermngs != null) && (!papermngs.isEmpty())) {
        String paperids = "";
        String paperNames = "";
        boolean b = false;
        for (Tpapermng tr : papermngs) {
          if (b) {
            paperids = paperids + ",";
            paperNames = paperNames + ",";
          } else {
            b = true;
          }
          paperids = paperids + tr.getId();
          paperNames = paperNames + tr.getName();
        }
        u.setPaperids(paperids);
        u.setPaperidNames(paperNames);
      }

      if (t.getOrganization() != null) {
        u.setOrganizationId(t.getOrganization().getId());
        u.setOrganizationName(t.getOrganization().getName());
      }
      ul.add(u);
    }
    return ul;
  }

  public Long count(User user, PageFilter ph)
  {
    Map params = new HashMap();
    String hql = " from Tuser t ";
    return this.userDao.count("select count(*) " + hql + whereHql(user, params), params);
  }

  private String whereHql(User user, Map<String, Object> params) {
    String hql = "";
    if (user != null) {
      hql = hql + " where 1=1 ";
      if ((user.getName() != null) && (!"".equals(user.getName()))) {
        hql = hql + " and t.name like :name";
        params.put("name", "%%" + user.getName() + "%%");
      }
      if ((user.getLoginname() != null) && (!"".equals(user.getLoginname()))) {
        hql = hql + " and t.loginname='" + user.getLoginname() + "'  ";
      }
      if ((user.getOrganizationId() != null) && (!"".equals(user.getOrganizationId()))) {
        hql = hql + " and t.organization.id =" + user.getOrganizationId();
      }
    }
    return hql;
  }

  private String orderHql(PageFilter ph) {
    String orderString = "";
    if ((ph.getSort() != null) && (ph.getOrder() != null)) {
      orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
    }
    return orderString;
  }

  public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd)
  {
    Tuser u = (Tuser)this.userDao.get(Tuser.class, sessionInfo.getId());
    if (u.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {
      u.setPassword(MD5Util.md5(pwd));
      return true;
    }
    return false;
  }

  public User getByLoginName(User user)
  {
    Tuser t = (Tuser)this.userDao.get("from Tuser t  where t.loginname = '" + user.getLoginname() + "'");
    User u = new User();
    if (t != null)
      BeanUtils.copyProperties(t, u);
    else {
      return null;
    }
    return u;
  }

@Override
public List<Object[]> getDivisionAge() {
	return userDao.findBySql("select id,text from sys_dictionary where dictionarytype_id =5 order by seq");
}

@Override
public List<Object[]> getEducate() {
	return userDao.findBySql("select id,text from sys_dictionary where dictionarytype_id = 4");
}

@Override
public List<Object[]> getPostLevel() {
	return userDao.findBySql("select id,text from sys_dictionary where dictionarytype_id = 7");
}

@Override
public List<Object[]> getPost() {
	return userDao.findBySql("select id,text from sys_dictionary where dictionarytype_id = 6");
}

@Override
public List<Object[]> getAgescope() {
	return userDao.findBySql("select id,text from sys_dictionary where dictionarytype_id = 9");
}
}