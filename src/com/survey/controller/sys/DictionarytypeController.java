package com.survey.controller.sys;

import com.survey.base.BaseController;
import com.survey.common.Json;
import com.survey.common.Tree;
import com.survey.model.sys.Dictionarytype;
import com.survey.service.sys.DictionarytypeServiceI;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/dictionarytype"})
public class DictionarytypeController extends BaseController
{

  @Autowired
  private DictionarytypeServiceI dictionarytypeService;

  @RequestMapping({"/tree"})
  @ResponseBody
  public List<Tree> tree(HttpSession session)
  {
    return this.dictionarytypeService.tree();
  }
  @RequestMapping({"/add"})
  @ResponseBody
  public Json add(Dictionarytype dictionarytype) {
    Json j = new Json();
    try {
      this.dictionarytypeService.add(dictionarytype);
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
    this.dictionarytypeService.delete(id);
    j.setMsg("删除成功！");
    j.setSuccess(true);
    return j;
  }
  @RequestMapping({"/get"})
  @ResponseBody
  public Dictionarytype get(Long id) {
    return this.dictionarytypeService.get(id);
  }
  @RequestMapping({"/edit"})
  @ResponseBody
  public Json edit(Dictionarytype dictionarytype) {
    Json j = new Json();
    try {
      this.dictionarytypeService.edit(dictionarytype);
      j.setSuccess(true);
      j.setMsg("编辑成功！");
    } catch (Exception e) {
      j.setMsg(e.getMessage());
    }
    return j;
  }
}