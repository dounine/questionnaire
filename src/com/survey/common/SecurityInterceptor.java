package com.survey.common;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor
  implements HandlerInterceptor
{
  private List<String> excludeUrls;

  public List<String> getExcludeUrls()
  {
    return this.excludeUrls;
  }

  public void setExcludeUrls(List<String> excludeUrls) {
    this.excludeUrls = excludeUrls;
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
    throws Exception
  {
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
    throws Exception
  {
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
    throws Exception
  {
    String requestUri = request.getRequestURI();
    String contextPath = request.getContextPath();
    String url = requestUri.substring(contextPath.length());
    SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");

    if ((url.indexOf("/admin/") > -1) || (this.excludeUrls.contains(url))) {
      return true;
    }

    if ((sessionInfo == null) || (sessionInfo.getId() == null)) {
      request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
      request.getRequestDispatcher("/error/noSession.jsp").forward(request, response);
      return false;
    }

    if (!sessionInfo.getResourceAllList().contains(url)) {
      return true;
    }

    if (!sessionInfo.getResourceList().contains(url)) {
      request.setAttribute("msg", "您没有访问此资源的权限！<br/>请联系超管赋予您<br/>[" + url + "]<br/>的资源访问权限！");
      request.getRequestDispatcher("/error/noSecurity.jsp").forward(request, response);
      return false;
    }

    return true;
  }
}