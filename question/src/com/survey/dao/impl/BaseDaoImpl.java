package com.survey.dao.impl;

import com.survey.dao.BaseDaoI;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public class BaseDaoImpl<T>  implements BaseDaoI<T>
{

  @Autowired
  private SessionFactory sessionFactory;

  public Session getCurrentSession()
  {
    return this.sessionFactory.getCurrentSession();
  }

  public Serializable save(T o)
  {
    if (o != null) {
      return getCurrentSession().save(o);
    }
    return null;
  }

  public T get(Class<T> c, Serializable id)
  {
    return (T) getCurrentSession().get(c, id);
  }

  public T get(String hql)
  {
    Query q = getCurrentSession().createQuery(hql);
    List l = q.list();
    if ((l != null) && (l.size() > 0)) {
      return (T) l.get(0);
    }
    return null;
  }
  
  public T get(String hql, Map<String, Object> params)
  {
    Query q = getCurrentSession().createQuery(hql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    List l = q.list();
    if ((l != null) && (l.size() > 0)) {
      return (T) l.get(0);
    }
    return null;
  }

  public void delete(T o)
  {
    if (o != null)
      getCurrentSession().delete(o);
  }

  public void update(T o)
  {
    if (o != null)
      getCurrentSession().update(o);
  }

  public void saveOrUpdate(T o)
  {
    if (o != null)
      getCurrentSession().saveOrUpdate(o);
  }

  public List<T> find(String hql)
  {
    Query q = getCurrentSession().createQuery(hql);
    return q.list();
  }

  public List<T> find(String hql, Map<String, Object> params)
  {
    Query q = getCurrentSession().createQuery(hql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.list();
  }

  public List<T> find(String hql, Map<String, Object> params, int page, int rows)
  {
    Query q = getCurrentSession().createQuery(hql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }

  public List<T> find(String hql, int page, int rows)
  {
    Query q = getCurrentSession().createQuery(hql);
    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }

  public Long count(String hql)
  {
    Query q = getCurrentSession().createQuery(hql);
    return (Long)q.uniqueResult();
  }

  public Long count(String hql, Map<String, Object> params)
  {
    Query q = getCurrentSession().createQuery(hql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return (Long)q.uniqueResult();
  }

  public int executeHql(String hql)
  {
    Query q = getCurrentSession().createQuery(hql);
    return q.executeUpdate();
  }

  public int executeHql(String hql, Map<String, Object> params)
  {
    Query q = getCurrentSession().createQuery(hql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.executeUpdate();
  }

  public List<Object[]> findBySql(String sql)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    return q.list();
  }

  public List<Object[]> findBySql(String sql, int page, int rows)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }
 
  public List<Object[]> findBySql(String sql, Map<String, Object> params)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.list();
  }

  public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }

  public int executeSql(String sql)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    return q.executeUpdate();
  }

  public int executeSql(String sql, Map<String, Object> params)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.executeUpdate();
  }

  public BigInteger countBySql(String sql)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    return (BigInteger)q.uniqueResult();
  }

  public BigInteger countBySql(String sql, Map<String, Object> params)
  {
    SQLQuery q = getCurrentSession().createSQLQuery(sql);
    if ((params != null) && (!params.isEmpty())) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return (BigInteger)q.uniqueResult();
  }

  public String getCode(String hql, Map<String, Object> params)
  {
    Query q = getCurrentSession().createQuery(hql);
    return (String)q.uniqueResult();
  }

@Override
public List findBySqlString(String paramString) {
	 SQLQuery q = getCurrentSession().createSQLQuery(paramString);
	 return q.list();
}
}