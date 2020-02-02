package com.optus.apis.textprocessor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Model class to hold the search result.
 */
public class SearchResult {

	List<Map.Entry<String, Integer>> counts = new ArrayList<Map.Entry<String, Integer>>();

	public SearchResult() {
	}

	public SearchResult(List<Map.Entry<String, Integer>> counts) {
		this.counts = counts;
	}

	public List<Map.Entry<String, Integer>> getCounts() {
		return counts;
	}

	public void setCounts(List<Map.Entry<String, Integer>> counts) {
		this.counts = counts;
	}

}
