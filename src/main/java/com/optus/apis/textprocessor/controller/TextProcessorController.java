package com.optus.apis.textprocessor.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optus.apis.textprocessor.exceptions.ProcessExecutionException;
import com.optus.apis.textprocessor.model.SearchResult;
import com.optus.apis.textprocessor.model.SearchText;
import com.optus.apis.textprocessor.service.TextProcessorService;
import com.optus.apis.textprocessor.util.TextProcessorAPIUtils;

@RestController
@RequestMapping(path = "/counter-api")
public class TextProcessorController {

	private TextProcessorService textProcessorService;

	public TextProcessorController(TextProcessorService textProcessorService) {
		this.textProcessorService = textProcessorService;
	}

	@PostMapping(path = "/search", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> searchAndCountWords(@RequestBody SearchText searchText,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {
		traceId = getTraceId(traceId);
		SearchResult searchAndCount;
		try {
			searchAndCount = textProcessorService.searchAndCount(searchText, traceId);
			return new ResponseEntity<>(searchAndCount, HttpStatus.OK);
		} catch (ProcessExecutionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getTraceId(String traceId) {
		if (!TextProcessorAPIUtils.doesStringValueExists(traceId)) {
			traceId = UUID.randomUUID().toString();
		}
		return traceId;
	}

}
