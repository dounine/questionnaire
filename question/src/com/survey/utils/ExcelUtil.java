package com.survey.utils;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

	public static void fillExcelData(List<Object[]> objects,HttpServletResponse response,String[] headers,String filename) throws Exception{
		Workbook wb = new HSSFWorkbook();
		int rowIndex=0;
		if(null!=objects&&objects.size()>0){
			Sheet sheet = wb.createSheet("统计结果");
			Row row = sheet.createRow(rowIndex++);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			Cell cell = null;
			for (int i = 0; i < headers.length; i++) {
				(cell=row.createCell(i)).setCellValue(headers[i]);
				cell.setCellStyle(cellStyle);
				
			}
			for (Object[] object : objects) {
				row = sheet.createRow(rowIndex++);
				for (int i = 0; i < object.length; i++) {
					if(object[i].toString().endsWith(".0")){
						(cell=row.createCell(i)).setCellValue(String.valueOf(object[i].toString().substring(0, object[i].toString().lastIndexOf("."))));
						cell.setCellStyle(cellStyle);
					}else{
						(cell=row.createCell(i)).setCellValue(String.valueOf(object[i]));
						cell.setCellStyle(cellStyle);
						System.out.println(String.valueOf(object[i]));
					}
				}
			}
			exportExcel(response,wb,filename);
		}
		return;
	}
	
	private static void exportExcel(HttpServletResponse response,Workbook wb,String fileName) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
}
