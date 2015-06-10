package com.survey.controller.basic;

import com.alibaba.fastjson.JSON;
import com.survey.base.BaseController;
import com.survey.common.Grid;
import com.survey.common.Json;
import com.survey.common.PageFilter;
import com.survey.model.basic.Raw;
import com.survey.service.basic.RawService;
import com.survey.service.sys.DictionaryServiceI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/raw"})
public class RawController extends BaseController
{

  @Autowired
  private RawService rawService;

  @Autowired
  private DictionaryServiceI dictionaryService;

  @RequestMapping({"/manager"})
  public String manager(HttpServletRequest request)
  {
    request.setAttribute("rawtypeJson", JSON.toJSONString(this.dictionaryService.combox("rawtype")));
    return "/basic/raw";
  }
  @RequestMapping({"/dataGrid"})
  @ResponseBody
  public Grid dataGrid(Raw raw, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.rawService.dataGrid(raw, ph));
    grid.setTotal(this.rawService.count(raw, ph));
    return grid;
  }

  @RequestMapping({"/addPage"})
  public String addPage(HttpServletRequest request) {
    request.getSession().setAttribute("rawCode", this.rawService.getMaxCode());
    return "/basic/rawAdd";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Raw raw, HttpServletRequest request) {
    Json j = new Json();
    try {
      this.rawService.add(raw, request);
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
      this.rawService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Raw get(Long id) {
    return this.rawService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Raw r = this.rawService.get(id);
    request.setAttribute("raw", r);
    return "/basic/rawEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Raw raw, HttpServletRequest request) {
    Json j = new Json();
    try {
      this.rawService.edit(raw, request);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}