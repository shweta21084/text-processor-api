package com.optus.apis.textprocessor.model;

import java.util.ArrayList;

/*
 * Model class to hold the search text.
 */
public class SearchText {
	
	ArrayList<Object> searchText = new ArrayList<Object>();
	
	public SearchText() {
	}
	
	public SearchText(ArrayList<Object> searchText) {
		this.searchText = searchText;
	}

	public ArrayList<Object> getSearchText() {
		return searchText;
	}

	public void setCounts(ArrayList<Object> searchText) {
		this.searchText = searchText;
	}
}
