package com.survey.service.sys.impl;

import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.dao.BaseDaoI;
import com.survey.model.sys.Resource;
import com.survey.model.sys.Tresource;
import com.survey.service.sys.ResourceServiceI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class ResourceServiceImpl
  implements ResourceServiceI
{

  @Autowired
  private BaseDaoI<Tresource> resourceDao;

  public List<Resource> treeGrid()
  {
    List lr = new ArrayList();
    List<Tresource> l = this.resourceDao
      .find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
    if ((l != null) && (l.size() > 0)) {
      for (Tresource t : l) {
        Resource r = new Resource();
        BeanUtils.copyProperties(t, r);
        r.setCstate(t.getState());
        if (t.getResource() != null) {
          r.setPid(t.getResource().getId());
          r.setPname(t.getResource().getName());
        }
        r.setIconCls(t.getIcon());
        lr.add(r);
      }
    }
    return lr;
  }

  public void add(Resource r)
  {
    Tresource t = new Tresource();
    t.setCreatedatetime(new Date());
    t.setDescription(r.getDescription());
    t.setIcon(r.getIcon());
    t.setName(r.getName());
    if ((r.getPid() != null) && (!"".equals(r.getPid()))) {
      t.setResource((Tresource)this.resourceDao.get(Tresource.class, r.getPid()));
    }
    t.setResourcetype(r.getResourcetype());
    t.setSeq(r.getSeq());
    t.setState(r.getCstate());
    t.setUrl(r.getUrl());
    this.resourceDao.save(t);
  }

  public void delete(Long id)
  {
    Tresource t = (Tresource)this.resourceDao.get(Tresource.class, id);
    del(t);
  }

  private void del(Tresource t) {
    if ((t.getResources() != null) && (t.getResources().size() > 0)) {
      for (Tresource r : t.getResources()) {
        del(r);
      }
    }
    this.resourceDao.delete(t);
  }

  public void edit(Resource r)
  {
    Tresource t = (Tresource)this.resourceDao.get(Tresource.class, r.getId());
    t.setDescription(r.getDescription());
    t.setIcon(r.getIcon());
    t.setName(r.getName());
    if ((r.getPid() != null) && (!"".equals(r.getPid()))) {
      t.setResource((Tresource)this.resourceDao.get(Tresource.class, r.getPid()));
    }
    t.setResourcetype(r.getResourcetype());
    t.setSeq(r.getSeq());
    t.setState(r.getCstate());
    t.setUrl(r.getUrl());
    this.resourceDao.update(t);
  }

  public Resource get(Long id)
  {
    Tresource t = (Tresource)this.resourceDao.get(Tresource.class, id);
    Resource r = new Resource();
    BeanUtils.copyProperties(t, r);
    r.setCstate(t.getState());
    if (t.getResource() != null) {
      r.setPid(t.getResource().getId());
      r.setPname(t.getResource().getName());
    }
    return r;
  }

  public List<Tree> tree(SessionInfo sessionInfo)
  {
    List<Tresource>  l = null;
    List lt = new ArrayList();

    Map params = new HashMap();
    params.put("resourcetype", Integer.valueOf(0));

    if (sessionInfo != null) {
      if ("admin".equals(sessionInfo.getLoginname())) {
        l = this.resourceDao
          .find("select distinct t from Tresource t  where t.resourcetype = :resourcetype  order by t.seq", 
          params);
      } else {
        params.put("userId", Long.valueOf(sessionInfo.getId().longValue()));
        l = this.resourceDao
          .find("select distinct t from Tresource t join fetch t.roles role join role.users user where t.resourcetype = :resourcetype and user.id = :userId order by t.seq", 
          params);
      }
    }
    else return null;

    if ((l != null) && (l.size() > 0)) {
      for (Tresource r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getResource() != null)
          tree.setPid(r.getResource().getId().toString());
        else {
          tree.setState("closed");
        }
        tree.setText(r.getName());
        tree.setIconCls(r.getIcon());
        Map attr = new HashMap();
        attr.put("url", r.getUrl());
        tree.setAttributes(attr);
        lt.add(tree);
      }
    }
    return lt;
  }

  public List<Tree> allTree(boolean flag)
  {
    List<Tresource> l = null;
    List lt = new ArrayList();
    if (flag)
      l = this.resourceDao.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
    else {
      l = this.resourceDao.find("select distinct t from Tresource t left join fetch t.resource where t.resourcetype =0 order by t.seq");
    }
    if ((l != null) && (l.size() > 0)) {
      for (Tresource r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getResource() != null) {
          tree.setPid(r.getResource().getId().toString());
        }
        tree.setText(r.getName());
        tree.setIconCls(r.getIcon());
        Map attr = new HashMap();
        attr.put("url", r.getUrl());
        tree.setAttributes(attr);
        lt.add(tree);
      }
    }
    return lt;
  }

  public List<String> resourceAllList()
  {
    List resourceList = new ArrayList();
    List l = this.resourceDao.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
    for (int i = 0; i < l.size(); i++) {
      resourceList.add(((Tresource)l.get(i)).getUrl());
    }
    return resourceList;
  }
}