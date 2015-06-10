package com.survey;

public class testforid {
private int id;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public static void main(String[] args){
	String ls_temp ="…Ÿ”Î1ƒÍ";
	ls_temp = ls_temp.replaceAll("\\D{1,}",",").replaceAll("^,{1,}","").replaceAll(",${1,}","");
 
	//ls_temp.replaceAll("\\D{1,}",",");
	System.out.println(ls_temp);
}

}
