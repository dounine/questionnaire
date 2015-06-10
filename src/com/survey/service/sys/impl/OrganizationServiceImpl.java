package com.survey.service.sys.impl;

import com.survey.base.ServiceException;
import com.survey.common.Tree;
import com.survey.dao.BaseDaoI;
import com.survey.model.sys.Organization;
import com.survey.model.sys.Torganization;
import com.survey.model.sys.Tuser;
import com.survey.service.sys.OrganizationServiceI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class OrganizationServiceImpl
  implements OrganizationServiceI
{

  @Autowired
  private BaseDaoI<Tuser> userDao;

  @Autowired
  private BaseDaoI<Torganization> organizationDao;

  public List<Organization> treeGrid()
  {
    List lr = new ArrayList();
    List<Torganization>  l = this.organizationDao
      .find("from Torganization t left join fetch t.organization  order by t.seq");
    if ((l != null) && (l.size() > 0)) {
      for (Torganization t : l) {
        Organization r = new Organization();
        BeanUtils.copyProperties(t, r);
        if (t.getOrganization() != null) {
          r.setPid(t.getOrganization().getId());
          r.setPname(t.getOrganization().getName());
        }
        r.setIconCls(t.getIcon());
        lr.add(r);
      }
    }
    return lr;
  }
  
  public List<Object[]> getOrganizations(){
	  String hql = "select id,name from Organization";
	  return userDao.findBySql(hql);
  }

  public void add(Organization org)
  {
    Torganization t = new Torganization();
    BeanUtils.copyProperties(org, t);
    if ((org.getPid() != null) && (!"".equals(org.getPid()))) {
      t.setOrganization((Torganization)this.organizationDao.get(Torganization.class, org.getPid()));
    }
    t.setCreatedatetime(new Date());
    this.organizationDao.save(t);
  }

  public void delete(Long id)
  {
    Torganization t = (Torganization)this.organizationDao.get(Torganization.class, id);
    del(t);
  }

  private void del(Torganization t) {
    List list = this.userDao.find("from Tuser t left join t.organization org where org.id=" + t.getId());
    if ((list != null) && (list.size() > 0)) {
      throw new ServiceException("该部门已经被用户使用");
    }
    if ((t.getOrganizations() != null) && (t.getOrganizations().size() > 0)) {
      for (Torganization r : t.getOrganizations()) {
        del(r);
      }
    }
    this.organizationDao.delete(t);
  }

  public void edit(Organization r)
  {
    Torganization t = (Torganization)this.organizationDao.get(Torganization.class, r.getId());
    t.setCode(r.getCode());
    t.setIcon(r.getIcon());
    t.setName(r.getName());
    t.setAddress(r.getAddress());
    t.setSeq(r.getSeq());
    t.setPercent(r.getPercent());
    t.setDeptotalnum(r.getDeptotalnum());
    if ((r.getPid() != null) && (!"".equals(r.getPid()))) {
      t.setOrganization((Torganization)this.organizationDao.get(Torganization.class, r.getPid()));
    }
    this.organizationDao.update(t);
  }

  public Organization get(Long id)
  {
    Torganization t = (Torganization)this.organizationDao.get(Torganization.class, id);
    Organization r = new Organization();
    BeanUtils.copyProperties(t, r);
    if (t.getOrganization() != null) {
      r.setPid(t.getOrganization().getId());
      r.setPname(t.getOrganization().getName());
    }
    return r;
  }

  public List<Tree> tree()
  {
    List<Torganization> l = null;
    List lt = new ArrayList();

    l = this.organizationDao.find("select distinct t from Torganization t order by t.seq");

    if ((l != null) && (l.size() > 0)) {
      for (Torganization r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getOrganization() != null) {
          tree.setPid(r.getOrganization().getId().toString());
        }
        tree.setText(r.getName());
        tree.setIconCls(r.getIcon());
        lt.add(tree);
      }
    }
    return lt;
  }
}