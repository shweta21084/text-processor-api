package com.optus.apis.textprocessor.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optus.apis.textprocessor.model.SearchResult;

@RestController
@RequestMapping(path = "/counter-api")
public class TextProcessorController {
	
	@GetMapping(path = "/search/{searchtext}")
	public SearchResult searchAndCountWords(@PathVariable String searchtext) {
		SimpleEntry<String, Integer> wc1 = new SimpleEntry<String, Integer>(searchtext, 30);
		SimpleEntry<String, Integer> wc2 = new SimpleEntry<String, Integer>("Nikhil", 30);
		ArrayList<Object> counts = new ArrayList<Object>();
		counts.add(wc1);
		counts.add(wc2);
		return new SearchResult(counts);
	}
	

}
