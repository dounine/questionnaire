 package com.survey.base;
 
 import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.krb5.internal.ccache.CCacheInputStream;

import com.survey.model.paper.AlyxmlDto;
import com.survey.utils.FusionChartsXMLGenerator;
 
@SuppressWarnings("all")
 public class Alyxml
 {
   private static Logger logger = LoggerFactory.getLogger(Alyxml.class);
   public String alyToXml(List<Object[]> listPara, int typeAlyId,String resultName)
   {
   	Document document = DocumentHelper.createDocument();
   	Element chart = document.addElement("chart");
   	chart.addAttribute("caption", resultName);//图表名称
   	chart.addAttribute("shownames", "1");
   	chart.addAttribute("imageSave", "1");
   	chart.addAttribute("baseFont","宋体");
   	chart.addAttribute("baseFontSize","14");
   	chart.addAttribute("imageSaveDialogFontColor", "cfbbfc");
   	Element categories = chart.addElement("categories");
   	
   	Element dataset2  = null;
   	Object[] objects = null;
   	HashSet hashSet = new HashSet();
   	String _name = "";
   	
   	
	List arrayList = new ArrayList();
	for(Object[] objects2 : listPara){
		if(!arrayList.contains(objects2[0].toString())){
			arrayList.add(objects2[0].toString());
			categories.addElement("category").addAttribute("label", objects2[0].toString());
			
		}
	}
	
	for(int i =0;i<listPara.size();i++){
		objects = listPara.get(i);
		if(i==0||i%arrayList.size()==0){
			dataset2 = chart.addElement("dataset");
			dataset2.addAttribute("seriesName", objects[1].toString());
			dataset2.addAttribute("showValues", "1");
		}
		dataset2.addElement("set").addAttribute("value", objects[2].toString());
	}
	 
	 StringWriter sw = new StringWriter();
     XMLWriter xw = new XMLWriter(sw);
	 
     try {
         xw.write(document.getRootElement());
         xw.close();
       } catch (IOException e) {
         e.printStackTrace();
       }
     
     return sw.toString();

   }
   
   public String alyToXmlQuestionType(List listPara)
   {
	   return null;
   }
 
 }
                                                                                
                                                                                
                                                                                
                                                                                
                                                                                