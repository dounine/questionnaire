package com.survey.service.paper.impl;

import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.dao.BaseDaoI;
import com.survey.model.paper.Papermng;
import com.survey.model.paper.Tpapermng;
import com.survey.model.sys.Tuser;
import com.survey.service.paper.PapermngServiceI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class PapermngServiceImpl
  implements PapermngServiceI
{

  @Autowired
  private BaseDaoI<Tuser> userDao;

  @Autowired
  private BaseDaoI<Tpapermng> papermngDao;

  public List<Papermng> treeGrid()
  {
    List lr = new ArrayList();
    List<Tpapermng> l = this.papermngDao
      .find("from Tpapermng t left join fetch t.papermng  order by t.seq");
    if ((l != null) && (l.size() > 0)) {
      for (Tpapermng t : l) {
        Papermng r = new Papermng();
        BeanUtils.copyProperties(t, r);
        if (t.getPapermng() != null) {
          r.setPid(t.getPapermng().getId());
          r.setPname(t.getPapermng().getName());
        }
        r.setIconCls(t.getIcon());
        lr.add(r);
      }
    }
    return lr;
  }

  public void add(Papermng org)
  {
    Tpapermng t = new Tpapermng();
    BeanUtils.copyProperties(org, t);
    if ((org.getPid() != null) && (!"".equals(org.getPid()))) {
      t.setPapermng((Tpapermng)this.papermngDao.get(Tpapermng.class, org.getPid()));
    }
    t.setCreatedatetime(new Date());
    this.papermngDao.save(t);
  }

  public void delete(Long id)
  {
    Tpapermng t = (Tpapermng)this.papermngDao.get(Tpapermng.class, id);
    del(t);
  }

  private void del(Tpapermng t) {
    if ((t.getPapermngs() != null) && (t.getPapermngs().size() > 0)) {
      for (Tpapermng r : t.getPapermngs()) {
        del(r);
      }
    }
    this.papermngDao.delete(t);
  }

  public void edit(Papermng r)
  {
    Tpapermng t = (Tpapermng)this.papermngDao.get(Tpapermng.class, r.getId());
    t.setLimitdate(r.getLimitdate());
    t.setFaildate(r.getFaildate());
    t.setIcon(r.getIcon());
    t.setName(r.getName());
    t.setCreatedatetime(r.getCreatedatetime());
    t.setSeq(r.getSeq());
    t.setDescription(r.getDescription());
    if ((r.getPid() != null) && (!"".equals(r.getPid()))) {
      t.setPapermng((Tpapermng)this.papermngDao.get(Tpapermng.class, r.getPid()));
    }
    this.papermngDao.update(t);
  }

  public Papermng get(Long id)
  {
    Tpapermng t = (Tpapermng)this.papermngDao.get(Tpapermng.class, id);
    Papermng r = new Papermng();
    BeanUtils.copyProperties(t, r);
    if (t.getPapermng() != null) {
      r.setPid(t.getPapermng().getId());
      r.setPname(t.getPapermng().getName());
    }
    return r;
  }

  public List<Tree> tree()
  {
    List<Tpapermng> l = null;
    List lt = new ArrayList();

    l = this.papermngDao.find("select distinct t from Tpapermng t order by t.seq");

    if ((l != null) && (l.size() > 0)) {
      for (Tpapermng r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getPapermng() != null) {
          tree.setPid(r.getPapermng().getId().toString());
        }
        tree.setText(r.getName());
        tree.setIconCls(r.getIcon());
        tree.setAttributes(r.getLimitdate());
        lt.add(tree);
      }
    }
    return lt;
  }

  public void grant(Papermng papermng)
  {
    Tpapermng t = (Tpapermng)this.papermngDao.get(Tpapermng.class, papermng.getId());
    if ((papermng.getUserIds() != null) && (!papermng.getUserIds().equalsIgnoreCase(""))) {
      String ids = "";
      boolean b = false;
      for (String id : papermng.getUserIds().split(",")) {
        if (b)
          ids = ids + ",";
        else {
          b = true;
        }
        ids = ids + id;
      }
      t.setUsers(new HashSet(this.userDao.find("select distinct t from Tuser t where t.id in (" + 
        ids + ")")));
    } else {
      t.setUsers(null);
    }
  }

  public List<Tree> treeByUser(SessionInfo sessionInfo)
  {
    List<Tpapermng> l = null;
    List lt = new ArrayList();

    Map params = new HashMap();
    if (sessionInfo != null) {
      if ("admin".equals(sessionInfo.getLoginname())) {
        l = this.papermngDao.find("select distinct t from Tpapermng t order by t.seq");
      //} else if ("匿名用户".equals(sessionInfo.getName())) {
      } else if (  sessionInfo.getRolesname().equals("匿名用户")) {
        params.put("userId", Long.valueOf(sessionInfo.getId().longValue()));
        l = this.papermngDao.find("select distinct t from Tpapermng t join fetch t.users user join user.papermngs paper where  user.id = :userId and t.faildate>=now() order by t.seq ", params);
      } else {
        params.put("userId", Long.valueOf(sessionInfo.getId().longValue()));
        l = this.papermngDao.find("select distinct t from Tpapermng t join fetch t.users user join user.papermngs paper where  user.id = :userId and t.faildate>=now()  and  not (user.id) in (select ulist.userid from TexamUserList ulist where ulist.paperid=t.id )  order by t.seq ", 
          params);
      }
    }
    else return null;

    if ((l != null) && (l.size() > 0)) {
      for (Tpapermng r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getPapermng() != null) {
          tree.setPid(r.getPapermng().getId().toString());
        }
        tree.setText(r.getName());
        tree.setIconCls(r.getIcon());
        tree.setAttributes(r.getLimitdate() + "|||" + r.getDescription());
        lt.add(tree);
      }
    }
    return lt;
  }
}