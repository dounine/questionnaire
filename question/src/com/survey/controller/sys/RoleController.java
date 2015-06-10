package com.survey.controller.sys;

import com.survey.base.BaseController;
import com.survey.common.Grid;
import com.survey.common.Json;
import com.survey.common.PageFilter;
import com.survey.common.Tree;
import com.survey.model.sys.Role;
import com.survey.service.sys.RoleServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/role"})
public class RoleController extends BaseController
{

  @Autowired
  private RoleServiceI roleService;

  @RequestMapping({"/manager"})
  public String manager()
  {
    return "/admin/role";
  }
  @RequestMapping({"/dataGrid"})
  @ResponseBody
  public Grid dataGrid(Role role, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.roleService.dataGrid(role, ph));
    grid.setTotal(this.roleService.count(role, ph));
    return grid;
  }
  @RequestMapping({"/tree"})
  @ResponseBody
  public List<Tree> tree() {
    return this.roleService.tree();
  }

  @RequestMapping({"/addPage"})
  public String addPage() {
    return "/admin/roleAdd";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Role role) {
    Json j = new Json();
    try {
      this.roleService.add(role);
      j.setSuccess(true);
      j.setMsg("添加成功！");
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
      this.roleService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Role get(Long id) {
    return this.roleService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Role r = this.roleService.get(id);
    request.setAttribute("role", r);
    return "/admin/roleEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Role role) {
    Json j = new Json();
    try {
      this.roleService.edit(role);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }

  @RequestMapping({"/grantPage"})
  public String grantPage(HttpServletRequest request, Long id) {
    Role r = this.roleService.get(id);
    request.setAttribute("role", r);
    return "/admin/roleGrant";
  }
  @RequestMapping({"/grant"})
  @ResponseBody
  public Json grant(Role role) {
    Json j = new Json();
    try {
      this.roleService.grant(role);
      j.setMsg("授权成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}