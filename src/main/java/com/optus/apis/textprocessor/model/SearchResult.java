package com.optus.apis.textprocessor.model;

import java.util.ArrayList;

/*
 * Model class to hold the search result.
 */
public class SearchResult {

	ArrayList<Object> counts = new ArrayList<Object>();

	public SearchResult() {
	}

	public SearchResult(ArrayList<Object> counts) {
		this.counts = counts;
	}

	public ArrayList<Object> getCounts() {
		return counts;
	}

	public void setCounts(ArrayList<Object> counts) {
		this.counts = counts;
	}

}
