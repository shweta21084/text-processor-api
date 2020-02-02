package com.optus.apis.textprocessor.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Model class to hold the search text.
 */
public class SearchText {
	
	List<Object> searchText = new ArrayList<Object>();
	
	public SearchText() {
	}
	
	public SearchText(List<Object> searchText) {
		this.searchText = searchText;
	}

	public List<Object> getSearchText() {
		return searchText;
	}

	public void setCounts(List<Object> searchText) {
		this.searchText = searchText;
	}
}
