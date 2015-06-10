package com.survey.service.basic.impl;

import com.survey.common.PageFilter;
import com.survey.common.SessionInfo;
import com.survey.dao.BaseDaoI;
import com.survey.model.basic.Raw;
import com.survey.service.basic.RawService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class RawServiceImpl
  implements RawService
{

  @Autowired
  private BaseDaoI<Raw> rawDao;

  public void add(Raw r, HttpServletRequest request)
  {
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    r.setAddPerson(sessionInfo.getName());
    r.setAddTime(new Date());
    this.rawDao.save(r);
  }

  public void delete(Long id)
  {
    Raw t = (Raw)this.rawDao.get(Raw.class, id);
    this.rawDao.delete(t);
  }

  public void edit(Raw r, HttpServletRequest request)
  {
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
    r.setUpdatePerson(sessionInfo.getName());
    r.setUpdateTime(new Date());
    this.rawDao.update(r);
  }

  public Raw get(Long id)
  {
    Raw r = (Raw)this.rawDao.get(Raw.class, id);
    Raw t = new Raw();
    t.setId(r.getId());
    return r;
  }

  public String getMaxCode()
  {
    Map params = new HashMap();
    String hql = " from Raw t ";
    int rawCode = Integer.parseInt(this.rawDao.getCode("select max(t.rawCode) " + hql, params));
    return String.valueOf(rawCode + 1);
  }

  public List<Raw> dataGrid(Raw Raw, PageFilter ph)
  {
    List ul = new ArrayList();
    Map params = new HashMap();
    String hql = " from Raw t ";
    List<Raw> l = this.rawDao.find(hql + " order by t.id desc ", params, ph.getPage(), ph.getRows());
    for (Raw t : l) {
      Raw u = new Raw();
      BeanUtils.copyProperties(t, u);
      ul.add(u);
    }
    return ul;
  }

  public Long count(Raw Raw, PageFilter ph)
  {
    Map params = new HashMap();
    String hql = " from Raw t ";
    return this.rawDao.count("select count(*) " + hql, params);
  }
}