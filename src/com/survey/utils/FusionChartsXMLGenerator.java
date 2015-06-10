package com.survey.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FusionChartsXMLGenerator {
	public static final int BOOLEAN_TRUE = 0;  
    public static final int BOOLEAN_FALSE = 1;  
    private static Logger LOGGER = LoggerFactory.getLogger(FusionChartsXMLGenerator.class);   
      
    private static FusionChartsXMLGenerator singleton = new FusionChartsXMLGenerator();  
      
    public static FusionChartsXMLGenerator getInstance() {  
        return singleton;  
    }  
      
    private FusionChartsXMLGenerator() {}  
      
    private String[] colors = {"AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E",  
            "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE"};  
      
    /** 
     * data形式为:  
     * "" category1,category2,category3...-------->这是第一行  
     * type1 value1,value2,value3,...  
     * type2 value1,value2,value3,... 
     * ...... 
     */  
    public String getMultiDSXML(List<List<String>> data,   
            String caption, String subCaption,   
            String xAxisName, String yAxisName,   
            int showNames,int showValues,  
            int decimalPrecision, int rotateNames) {  
        double max = -Double.MAX_VALUE, min = Double.MAX_VALUE;  
        for (int i = 1; i < data.size(); i++) {  
            List row = (List)data.get(i);  
            for (int j = 1; j < row.size(); j++) {  
                String val = (String)row.get(j);  
                if (val != null && val.length() > 0) {  
                    double v = Double.parseDouble(val);  
                    if (v > max) {  
                        max = v;  
                    }  
                    if (v < min) {  
                        min = v;  
                    }  
                }  
            }  
        }  
          
        if (max == -Double.MAX_VALUE) {  
            max = 0;  
        }  
          
        if (min == Double.MAX_VALUE) {  
            min = 0;  
        }  
          
        if (min == max && min == 0) {  
            min = 0;  
            max = 100;  
        }  
          
          
        max = Math.abs(max / 10) + max;  
        min = min - Math.abs(min / 10);  
          
        int valCnt = ((List)data.get(0)).size() - 1;  
        if (valCnt > 30) {  
            showNames = 0;  
            showValues = 0;  
        }  
          
        StringBuffer strXml = new StringBuffer();  
        strXml.append("<graph baseFont='SunSim' baseFontSize='12' caption='" +   
                caption +"' subcaption='" + subCaption + "' " +  
                "yAxisMinValue='" + min + "' yAxisMaxValue='" + max + "' " +  
                "xAxisName='" + xAxisName + "' yAxisName='" + yAxisName + "' hovercapbg='FFECAA' " +  
                "hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='" + decimalPrecision + "' " +  
                "showValues='" + showValues + "' numdivlines='10' numVdivlines='0' " +  
                "showNames='" + showNames + "' rotateNames='" + rotateNames + "' " +  
                "rotateYAxisName='0' showAlternateHGridColor='1'>");  
      
        strXml.append("<categories>");  
        List headerRow = (List)data.get(0);  
        for (int i = 1; i < headerRow.size(); i++) {  
            strXml.append("<category name='" + headerRow.get(i) + "'/>");  
        }  
        strXml.append("</categories>");  
          
        for (int i = 1; i < data.size(); i++) {  
            List row = (List)data.get(i);  
            String name = (String)row.get(0);  
            String color = colors[(i - 1) % 12];  
            strXml.append("<dataset seriesName='" + name +"' " +  
                    "color='" + color + "' anchorBorderColor='" + color + "' " +  
                    "anchorBgColor='" + color + "'>");  
            for (int j = 1; j < row.size(); j++) {  
                strXml.append("<set value='" + (String)row.get(j) +"'/>");  
            }  
            strXml.append("</dataset>");  
        }  
          
        strXml.append("</graph>");  
        String str =  strXml.toString();  
        LOGGER.info("=============/n" + str + "/n==============/n");  
        return str;  
    }  
      
    /** 
     *  数据格式为: 
     *  name value  
     *  name value 
     *  ...... 
     */  
    public String getSingleDSXML(List<List<String>> data,   
            String caption, String subCaption,   
            String xAxisName, String yAxisName,   
            int showNames,int showValues,  
            int decimalPrecision, int rotateNames) {  
          
        double max = -Double.MAX_VALUE, min = Double.MAX_VALUE;  
        for (int i = 0; i < data.size(); i++) {  
            List<String> row = data.get(i);  
            double value = Double.parseDouble(row.get(1));  
            if (value > max) {  
                max = value;  
            }  
            if (value < min) {  
                min = value;  
            }  
        }  
          
        if (max == -Double.MAX_VALUE) {  
            max = 0;  
        }  
          
        if (min == Double.MAX_VALUE) {  
            min = 0;  
        }  
          
        if (min == max && min == 0) {  
            min = 0;  
            max = 100;  
        }  
          
          
        max = Math.abs(max / 10) + max;  
        min = min - Math.abs(min / 10);  
          
        int valCnt = data.size() - 1;  
        if (valCnt > 30) {  
            showNames = 0;  
            showValues = 0;  
        }  
          
        StringBuffer strXml = new StringBuffer();  
        strXml.append("<graph baseFont='SunSim' baseFontSize='12' caption='" +   
                caption +"' subcaption='" + subCaption + "' " +  
                "yAxisMinValue='" + min + "' yAxisMaxValue='" + max + "' " +  
                "xAxisName='" + xAxisName + "' yAxisName='" + yAxisName + "' hovercapbg='FFECAA' " +  
                "hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='" + decimalPrecision + "' " +  
                "showValues='" + showValues + "' numdivlines='10' numVdivlines='0' " +  
                "showNames='" + showNames + "' rotateNames='" + rotateNames + "' " +  
                "rotateYAxisName='0' showAlternateHGridColor='1'>");  
        for (int i = 0; i < data.size(); i++) {  
            List<String> row = data.get(i);  
            String label = row.get(0);  
            String value = row.get(1);  
            String color = colors[i % 12];  
            strXml.append("<set name='" + label + "' value='" + value + "' color='" + color + "'/>");  
              
        }  
        strXml.append("</graph>");  
        String str =  strXml.toString();  
        LOGGER.info("=============/n" + str + "/n==============/n");  
        return str;  
    }
}
