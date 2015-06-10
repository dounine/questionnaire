package com.survey.service.sys.impl;

import com.survey.common.Tree;
import com.survey.dao.BaseDaoI;
import com.survey.model.sys.Dictionarytype;
import com.survey.model.sys.Tdictionarytype;
import com.survey.service.sys.DictionarytypeServiceI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class DictionarytypeServiceImpl
  implements DictionarytypeServiceI
{

  @Autowired
  private BaseDaoI<Tdictionarytype> dictionarytypeDao;

  public void add(Dictionarytype r)
  {
    Tdictionarytype t = new Tdictionarytype();
    t.setName(r.getName());
    t.setSeq(r.getSeq());
    t.setDescription(r.getDescription());
    this.dictionarytypeDao.save(t);
  }

  public void delete(Long id)
  {
    Tdictionarytype t = (Tdictionarytype)this.dictionarytypeDao.get(Tdictionarytype.class, id);
    this.dictionarytypeDao.delete(t);
  }

  public void edit(Dictionarytype r)
  {
    Tdictionarytype t = (Tdictionarytype)this.dictionarytypeDao.get(Tdictionarytype.class, r.getId());
    t.setDescription(r.getDescription());
    t.setName(r.getName());
    t.setSeq(r.getSeq());
    this.dictionarytypeDao.update(t);
  }

  public Dictionarytype get(Long id)
  {
    Tdictionarytype t = (Tdictionarytype)this.dictionarytypeDao.get(Tdictionarytype.class, id);
    Dictionarytype r = new Dictionarytype();
    r.setDescription(t.getDescription());
    r.setId(t.getId());
    r.setName(t.getName());
    r.setSeq(t.getSeq());
    return r;
  }

  public List<Tree> tree()
  {
    List<Tdictionarytype>  l = null;
    List lt = new ArrayList();

    l = this.dictionarytypeDao.find("select distinct t from Tdictionarytype t order by t.seq");

    if ((l != null) && (l.size() > 0)) {
      for (Tdictionarytype r : l) {
        Tree tree = new Tree();
        tree.setId(r.getId().toString());
        if (r.getDictionarytype() != null) {
          tree.setPid(r.getDictionarytype().getId().toString());
          tree.setIconCls("icon_folder");
        } else {
          tree.setIconCls("icon_company");
        }
        tree.setText(r.getName());
        lt.add(tree);
      }
    }
    return lt;
  }
}