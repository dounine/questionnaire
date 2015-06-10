package com.survey.service.sys.impl;

import com.survey.common.GlobalConstant;
import com.survey.common.PageFilter;
import com.survey.dao.BaseDaoI;
import com.survey.model.sys.Dictionary;
import com.survey.model.sys.Tdictionary;
import com.survey.model.sys.Tdictionarytype;
import com.survey.service.sys.DictionaryServiceI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class DictionaryServiceImpl
  implements DictionaryServiceI
{

  @Autowired
  private BaseDaoI<Tdictionary> dictionaryDao;

  @Autowired
  private BaseDaoI<Tdictionarytype> dictionarytypeDao;

  public void add(Dictionary r)
  {
    Tdictionary t = new Tdictionary();
    t.setIsdefault(GlobalConstant.NOT_DEFAULT);
    t.setState(GlobalConstant.ENABLE);
    t.setCode(r.getCode());
    t.setText(r.getText());
    t.setSeq(r.getSeq());
    t.setDictionarytype((Tdictionarytype)this.dictionarytypeDao.get(Tdictionarytype.class, r.getDictionarytypeId()));
    this.dictionaryDao.save(t);
  }

  public void delete(Long id)
  {
    Tdictionary t = (Tdictionary)this.dictionaryDao.get(Tdictionary.class, id);
    this.dictionaryDao.delete(t);
  }

  public void edit(Dictionary r)
  {
    Tdictionary t = (Tdictionary)this.dictionaryDao.get(Tdictionary.class, r.getId());
    t.setText(r.getText());
    t.setSeq(r.getSeq());
    t.setCode(r.getCode());
    t.setState(r.getState());
    t.setDictionarytype((Tdictionarytype)this.dictionarytypeDao.get(Tdictionarytype.class, r.getDictionarytypeId()));
    this.dictionaryDao.update(t);
  }

  public Dictionary get(Long id)
  {
    Tdictionary t = (Tdictionary)this.dictionaryDao.get(Tdictionary.class, id);
    Dictionary r = new Dictionary();
    r.setId(t.getId());
    r.setIsdefault(t.getIsdefault());
    r.setText(t.getText());
    r.setSeq(t.getSeq());
    r.setCode(t.getCode());
    if (t.getDictionarytype() != null) {
      r.setDictionarytypeId(t.getDictionarytype().getId());
      r.setDictionarytypeName(t.getDictionarytype().getName());
    }
    r.setState(t.getState());
    return r;
  }

  public List<Dictionary> dataGrid(Dictionary dictionary, PageFilter ph)
  {
    List ul = new ArrayList();
    Map params = new HashMap();
    String hql = " from Tdictionary t ";
    List<Tdictionary>  l = this.dictionaryDao.find(hql + whereHql(dictionary, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
    for (Tdictionary t : l) {
      Dictionary u = new Dictionary();
      BeanUtils.copyProperties(t, u);
      if (t.getDictionarytype() != null) {
        u.setDictionarytypeId(t.getDictionarytype().getId());
        u.setDictionarytypeName(t.getDictionarytype().getName());
      }
      ul.add(u);
    }
    return ul;
  }


  public Long count(Dictionary dictionary, PageFilter ph)
  {
    Map params = new HashMap();
    String hql = " from Tdictionary t ";
    return this.dictionaryDao.count("select count(*) " + hql + whereHql(dictionary, params), params);
  }

  private String whereHql(Dictionary dictionary, Map<String, Object> params) {
    String hql = "";
    if (dictionary != null) {
      hql = hql + " where 1=1 ";
      if (dictionary.getText() != null) {
        hql = hql + " and t.name like :name";
        params.put("name", "%%" + dictionary.getText() + "%%");
      }
      if (dictionary.getDictionarytypeId() != null) {
        hql = hql + " and t.dictionarytype.id = :dictionarytypeId";
        params.put("dictionarytypeId", dictionary.getDictionarytypeId());
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

  public List<Dictionary> combox(String code)
  {
    List ld = new ArrayList();
    List lt = this.dictionaryDao.find("from Tdictionary t where t.state=0 and t.dictionarytype.code='" + code + "' order by t.seq");
    if ((lt != null) && (lt.size() > 0)) {
      for (int i = 0; i < lt.size(); i++) {
        if (((Tdictionary)lt.get(i)).getState().intValue() == 0) {
          Dictionary d = new Dictionary();
          d.setId(((Tdictionary)lt.get(i)).getId());
          d.setText(((Tdictionary)lt.get(i)).getText());
          ld.add(d);
        }
      }
    }
    return ld;
  }

  public Dictionary checkUnique(Dictionary dictionary)
  {
    Tdictionary t = (Tdictionary)this.dictionaryDao.get("from Tdictionary t where t.code = '" + dictionary.getCode() + "' and t.dictionarytype.id=" + dictionary.getDictionarytypeId());
    if (t != null) {
      Dictionary r = new Dictionary();
      r.setId(t.getId());
      r.setIsdefault(t.getIsdefault());
      r.setText(t.getText());
      r.setSeq(t.getSeq());
      r.setCode(t.getCode());
      if (t.getDictionarytype() != null) {
        r.setDictionarytypeId(t.getDictionarytype().getId());
        r.setDictionarytypeName(t.getDictionarytype().getName());
      }
      r.setState(t.getState());
      return r;
    }
    return null;
  }
}