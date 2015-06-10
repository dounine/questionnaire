package com.survey.service.sys.impl;

import com.survey.common.PageFilter;
import com.survey.common.Tree;
import com.survey.dao.BaseDaoI;
import com.survey.model.sys.Role;
import com.survey.model.sys.Tresource;
import com.survey.model.sys.Trole;
import com.survey.service.sys.RoleServiceI;
import java.util.ArrayList;
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
public class RoleServiceImpl
  implements RoleServiceI
{

  @Autowired
  private BaseDaoI<Trole> roleDao;

  @Autowired
  private BaseDaoI<Tresource> resourceDao;

  public void add(Role r)
  {
    Trole t = new Trole();
    t.setIsdefault(Integer.valueOf(1));
    t.setName(r.getName());
    t.setSeq(r.getSeq());
    t.setDescription(r.getDescription());
    this.roleDao.save(t);
  }

  public void delete(Long id)
  {
    Trole t = (Trole)this.roleDao.get(Trole.class, id);
    this.roleDao.delete(t);
  }

  public void edit(Role r)
  {
    Trole t = (Trole)this.roleDao.get(Trole.class, r.getId());
    t.setDescription(r.getDescription());
    t.setName(r.getName());
    t.setSeq(r.getSeq());
    this.roleDao.update(t);
  }

  public Role get(Long id)
  {
    Trole t = (Trole)this.roleDao.get(Trole.class, id);
    Role r = new Role();
    r.setDescription(t.getDescription());
    r.setId(t.getId());
    r.setIsdefault(t.getIsdefault());
    r.setName(t.getName());
    r.setSeq(t.getSeq());
    Set<Tresource> s = t.getResources();
    if ((s != null) && (!s.isEmpty())) {
      boolean b = false;
      String ids = "";
      String names = "";
      for (Tresource tr : s) {
        if (b) {
          ids = ids + ",";
          names = names + ",";
        } else {
          b = true;
        }
        ids = ids + tr.getId();
        names = names + tr.getName();
      }
      r.setResourceIds(ids);
      r.setResourceNames(names);
    }
    return r;
  }

  public List<Role> dataGrid(Role role, PageFilter ph)
  {
    List ul = new ArrayList();
    Map params = new HashMap();
    String hql = " from Trole t ";
    List<Trole>  l = this.roleDao.find(hql + whereHql(role, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
    for (Trole t : l) {
      Role u = new Role();
      BeanUtils.copyProperties(t, u);
      ul.add(u);
    }
    return ul;
  }

  public Long count(Role role, PageFilter ph)
  {
    Map params = new HashMap();
    String hql = " from Trole t ";
    return this.roleDao.count("select count(*) " + hql + whereHql(role, params), params);
  }

  private String whereHql(Role role, Map<String, Object> params) {
    String hql = "";
    if (role != null) {
      hql = hql + " where 1=1 ";
      if (role.getName() != null) {
        hql = hql + " and t.name like :name";
        params.put("name", "%%" + role.getName() + "%%");
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

  public void grant(Role role)
  {
    Trole t = (Trole)this.roleDao.get(Trole.class, role.getId());
    if ((role.getResourceIds() != null) && (!role.getResourceIds().equalsIgnoreCase(""))) {
      String ids = "";
      boolean b = false;
      for (String id : role.getResourceIds().split(",")) {
        if (b)
          ids = ids + ",";
        else {
          b = true;
        }
        ids = ids + id;
      }
      t.setResources(new HashSet(this.resourceDao.find("select distinct t from Tresource t where t.id in (" + 
        ids + ")")));
    } else {
      t.setResources(null);
    }
  }

  public List<Tree> tree()
  {
    List<Trole> l = null;
    List lt = new ArrayList();

    l = this.roleDao.find("select distinct t from Trole t order by t.seq");

    if ((l != null) && (l.size() > 0)) {
      for (Trole r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        tree.setText(r.getName());
        lt.add(tree);
      }
    }
    return lt;
  }
}