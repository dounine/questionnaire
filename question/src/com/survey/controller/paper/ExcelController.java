package com.survey.controller.paper;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.survey.model.paper.Subjectmng;
import com.survey.service.paper.SubjectmngServiceI;
import com.survey.utils.ExcelRead;
import com.survey.utils.ExcelUtil;
import com.survey.utils.ExportExcel;

@SuppressWarnings("all")
@Controller
@RequestMapping("excel")
public class ExcelController {
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelController.class);

	@Autowired
	private SubjectmngServiceI subjectmngService;

	@RequestMapping("/export.html")
	public String exportexcel(HttpServletResponse response,
			Integer parerIdTemp, HttpServletRequest request) {
		List<Object[]> subjectmngs = subjectmngService
				.getSubjectmngs(parerIdTemp);
		logger.info("subjectmngs的长度为：" + subjectmngs.size());
		if(subjectmngs.size()!=0){
		String[] headers = {"试卷名称","问题题目","问题维度","A选项","B选项","C选项","D选项","E选项","F选项","回答选择项",
				"个人描述","姓名","部门","性别","年龄","学历","司龄","职位","职级","答题时间"
			};// 头部信息
		try {
			ExcelUtil.fillExcelData(subjectmngs, response,headers, "统计结果.xls");
		} catch (Exception e) {
			logger.info("导出错误");
		}finally{
		}
		}
		return "paper/subjectAly";
	}

	@RequestMapping("/import1.html")
	public ModelAndView importExcelGet(Integer parerIdTemp) {
		logger.info("进import1了：值为：" + parerIdTemp);
		ModelAndView modelAndView = new ModelAndView(
				"paper/excelFileFromUpload");
		modelAndView.addObject("parerIdTemp", parerIdTemp);
		return modelAndView;
	}

	@RequestMapping(value = "/import.html", method = RequestMethod.POST)
	public String importExcel(@RequestParam("file") CommonsMultipartFile file,
			Integer parerIdTemp, HttpServletRequest request) {
		logger.info("文件开始上传了");
		if (!file.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/");
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			File file2 = new File(path, new Date().getTime() + fileType);
			try {
				file.getFileItem().write(file2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ExcelRead excelRead = new ExcelRead();
			excelRead.importExcel(request.getSession().getServletContext()
					.getRealPath("/upload/" + file2.getName()));
			List<Object> objects = null;
			logger.info("传过来页面的值为：" + parerIdTemp);
			for (int i = 1; i < excelRead.getSumRow(); i++) {
				objects = excelRead.readRow(0, i);
				Subjectmng subjectmng = null;
				subjectmng = new Subjectmng();
				subjectmng.setPaperid(Long.parseLong(parerIdTemp.toString()));
				if(objects.get(0)!=null)subjectmng.setName(objects.get(0).toString());
				if(objects.get(1)!=null)subjectmng.setQuestiontype(objects.get(1).toString());
				if(objects.get(2)!=null)subjectmng.setAnswerA(objects.get(2).toString());
				if(objects.get(3)!=null)subjectmng.setAnswerB(objects.get(3).toString());
				if(objects.get(4)!=null)subjectmng.setAnswerC(objects.get(4).toString());
				if(objects.get(5)!=null)subjectmng.setAnswerD(objects.get(5).toString());
				if(objects.get(6)!=null)subjectmng.setAnswerE(objects.get(6).toString());
				if(objects.get(7)!=null)subjectmng.setAnswerF(objects.get(7).toString());
				if(objects.get(8)!=null)subjectmng.setKind(objects.get(8).toString());
				this.subjectmngService.add(subjectmng);
			}
			file2.delete();
		}
		return "index";
	}

}
