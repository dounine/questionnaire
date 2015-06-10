package com.survey.model.paper;

import java.io.Serializable;

public class QuestionType implements Serializable{

	public Integer id;
	private String text;
	
	public QuestionType(){
		
	}
	
	public QuestionType(Integer id,String text){
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
