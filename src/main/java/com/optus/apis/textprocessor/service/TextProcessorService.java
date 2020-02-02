package com.optus.apis.textprocessor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.optus.apis.textprocessor.exceptions.ProcessExecutionException;
import com.optus.apis.textprocessor.model.SearchResult;
import com.optus.apis.textprocessor.model.SearchText;
import com.optus.apis.textprocessor.util.TextProcessorAPIConstants;

@Service
public class TextProcessorService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(TextProcessorService.class);

	@Value("${search.file.name}")
	private String searchFileName;

	public SearchResult searchAndCount(SearchText searchText, String traceId) throws ProcessExecutionException {

		Map<String, Integer> wordCountMap = getWordCountMap(traceId);

		List<Map.Entry<String, Integer>> searchResult = searchText.getSearchText().stream()
				.map(text -> new AbstractMap.SimpleEntry<String, Integer>((String) text,
						wordCountMap.get(((String) text).toLowerCase())))
				.map(t -> Objects.isNull(t.getValue()) ? new AbstractMap.SimpleEntry<String, Integer>(t.getKey(), 0)
						: t)
				.collect(Collectors.toCollection(ArrayList::new));

		return new SearchResult(searchResult);

	}

	private Map<String, Integer> getWordCountMap(String traceId) throws ProcessExecutionException {
		Map<String, Integer> wordCounter = null;
		try {
			Stream<String> lineStreamFromFile = Files.lines(Paths.get(searchFileName));
			List<String> list = lineStreamFromFile.map(w -> w.split("\\s+")).flatMap(Arrays::stream)
					.collect(Collectors.toList());
			wordCounter = list.stream().collect(
					Collectors.toMap(w -> w.toLowerCase().replaceAll("[\\-\\+\\.\\^:,]", ""), w -> 1, Integer::sum));
			lineStreamFromFile.close();

		} catch (IOException e) {
			logger.error("TraceId: " + traceId + ", Problem reading the file to be used for search", e);
			throw new ProcessExecutionException("Problem reading the file to be used for search -> " + e.getMessage());
		}
		return wordCounter;

	}

	public String getHighestCountWordsByLimit(Integer limit, String traceId) throws ProcessExecutionException {
		Map<String, Integer> wordCountMap = getWordCountMap(traceId);
		final StringBuffer sb = new StringBuffer();
		List<Map.Entry<String, Integer>> searchResult = wordCountMap.entrySet().stream()
				.sorted((Map.Entry.<String, Integer>comparingByValue().reversed())).limit(limit)
				.collect(Collectors.toCollection(ArrayList::new));
		for (Map.Entry<String, Integer> entry : searchResult) {
			sb.append(entry.getKey()).append(TextProcessorAPIConstants.CSV_SEPARATOR).append(entry.getValue())
					.append(TextProcessorAPIConstants.NEW_LINE_SEPARATOR);
		}
		return sb.toString();
	}
}
