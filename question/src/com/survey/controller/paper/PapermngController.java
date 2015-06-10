package com.survey.controller.paper;

import com.survey.base.BaseController;
import com.survey.base.ServiceException;
import com.survey.common.Json;
import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.model.paper.Papermng;
import com.survey.service.paper.PapermngServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/papermng"})
public class PapermngController extends BaseController
{

  @Autowired
  private PapermngServiceI papermngService;

  @RequestMapping({"/manager"})
  public String manager()
  {
    return "/paper/papermng";
  }
  @RequestMapping({"/treeGrid"})
  @ResponseBody
  public List<Papermng> treeGrid() {
    return this.papermngService.treeGrid();
  }
  @RequestMapping({"/tree"})
  @ResponseBody
  public List<Tree> tree(HttpSession session) {
    return this.papermngService.tree();
  }
  @RequestMapping({"/treeByUser"})
  @ResponseBody
  public List<Tree> treeByUser(HttpSession session) {
    SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
    return this.papermngService.treeByUser(sessionInfo);
  }

  @RequestMapping({"/addPage"})
  public String addPage()
  {
    return "/paper/papermngAdd";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Papermng papermng) {
    Json j = new Json();
    try {
      this.papermngService.add(papermng);
      j.setSuccess(true);
      j.setMsg("添加成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Papermng get(Long id) {
    return this.papermngService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Papermng o = this.papermngService.get(id);
    request.setAttribute("papermng", o);
    return "/paper/papermngEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Papermng org) throws InterruptedException {
    Json j = new Json();
    try {
      this.papermngService.edit(org);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/delete"})
  @ResponseBody
  public Json delete(Long id) {
    Json j = new Json();
    try {
      this.papermngService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (ServiceException e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  @RequestMapping({"/grantPage"})
  public String grantPage(HttpServletRequest request, Long id) {
    Papermng r = this.papermngService.get(id);
    request.setAttribute("papermng", r);
    return "/paper/papermngGrant";
  }
  @RequestMapping({"/grant"})
  @ResponseBody
  public Json grant(Papermng papermng) {
    Json j = new Json();
    try {
      this.papermngService.grant(papermng);
      j.setMsg("授权成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}