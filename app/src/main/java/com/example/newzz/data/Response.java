package com.example.newzz.data;

import java.util.List;

public class Response{
	private int totalResults;
	private List<ArticlesItem> articles;
	private String status;

	public int getTotalResults(){
		return totalResults;
	}

	public List<ArticlesItem> getArticles(){
		return articles;
	}

	public String getStatus(){
		return status;
	}
}