package com.survey.controller.sys;

import com.survey.base.BaseController;
import com.survey.common.Json;
import com.survey.common.SessionInfo;
import com.survey.common.Tree;
import com.survey.model.sys.Resource;
import com.survey.service.sys.ResourceServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/resource"})
public class ResourceController extends BaseController
{

  @Autowired
  private ResourceServiceI resourceService;

  @RequestMapping({"/manager"})
  public String manager()
  {
    return "/admin/resource";
  }
  @RequestMapping({"/tree"})
  @ResponseBody
  public List<Tree> tree(HttpSession session) {
    SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
    return this.resourceService.tree(sessionInfo);
  }
  @RequestMapping({"/allTree"})
  @ResponseBody
  public List<Tree> allTree(boolean flag) {
    return this.resourceService.allTree(flag);
  }
  @RequestMapping({"/treeGrid"})
  @ResponseBody
  public List<Resource> treeGrid() {
    return this.resourceService.treeGrid();
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Resource get(Long id) {
    return this.resourceService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Resource r = this.resourceService.get(id);
    request.setAttribute("resource", r);
    return "/admin/resourceEdit";
  }

  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Resource resource) throws InterruptedException {
    Json j = new Json();
    try {
      this.resourceService.edit(resource);
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
      this.resourceService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  @RequestMapping({"/addPage"})
  public String addPage() {
    return "/admin/resourceAdd";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Resource resource) {
    Json j = new Json();
    try {
      this.resourceService.add(resource);
      j.setSuccess(true);
      j.setMsg("添加成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}