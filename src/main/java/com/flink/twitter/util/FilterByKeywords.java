package com.flink.twitter.util;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class FilterByKeywords implements FilterFunction<JsonNode>, FlinkTwitterIntegrationUtils {

    private final List<String> filterKeyWords;

    public FilterByKeywords(List<String> filterKeyWords) {
        this.filterKeyWords = filterKeyWords;
    }

    public boolean filter(JsonNode node) {
        if (!node.has(TEXT)){
            return false;
        }
        String text = node.get(TEXT).asText().toLowerCase();

        return filterKeyWords.parallelStream().anyMatch(text::contains);
    }
}
