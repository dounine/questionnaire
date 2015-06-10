package com.survey.controller.sys;

import com.survey.base.BaseController;
import com.survey.common.GlobalConstant;
import com.survey.common.Grid;
import com.survey.common.Json;
import com.survey.common.PageFilter;
import com.survey.model.sys.Dictionary;
import com.survey.service.sys.DictionaryServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/dictionary"})
public class DictionaryController extends BaseController
{

  @Autowired
  private DictionaryServiceI dictionaryService;

  @RequestMapping({"/manager"})
  public String manager()
  {
    return "/admin/dictionary";
  }
  @RequestMapping({"/combox"})
  @ResponseBody
  public List<Dictionary> combox(String code) {
    return this.dictionaryService.combox(code);
  }
  
  @RequestMapping({"/dataGrid"})
  @ResponseBody
  public Grid dataGrid(Dictionary dictionary, PageFilter ph) {
    Grid grid = new Grid();
    grid.setRows(this.dictionaryService.dataGrid(dictionary, ph));
    grid.setTotal(this.dictionaryService.count(dictionary, ph));
    return grid;
  }

  @RequestMapping({"/addPage"})
  public String addPage(HttpServletRequest request) {
    request.setAttribute("stateList", GlobalConstant.statelist);
    return "/admin/dictionaryAdd";
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Dictionary dictionary) {
    Json j = new Json();
    Dictionary dic = this.dictionaryService.checkUnique(dictionary);
    if (dic == null)
      try {
        this.dictionaryService.add(dictionary);
        j.setSuccess(true);
        j.setMsg("添加成功！");
      } catch (Exception e) {
        j.setMsg(e.getMessage());
      }
    else {
      j.setMsg("编码不能重复");
    }
    return j;
  }
  @RequestMapping({"/delete"})
  @ResponseBody
  public Json delete(Long id) {
    Json j = new Json();
    try {
      this.dictionaryService.delete(id);
      j.setMsg("删除成功！");
      j.setSuccess(true);
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Dictionary get(Long id) {
    return this.dictionaryService.get(id);
  }

  @RequestMapping({"/editPage"})
  public String editPage(HttpServletRequest request, Long id) {
    Dictionary dic = this.dictionaryService.get(id);
    request.setAttribute("dictionary", dic);
    request.setAttribute("stateList", GlobalConstant.statelist);
    return "/admin/dictionaryEdit";
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Dictionary dictionary) {
    Json j = new Json();
    try {
      this.dictionaryService.edit(dictionary);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}