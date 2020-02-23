package com.flink.twitter.util;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class FilterByLanguage implements FilterFunction<JsonNode>, FlinkTwitterIntegrationUtils {
    String filteredLanguage;

    public FilterByLanguage(String filteredLanguage) {
        this.filteredLanguage = filteredLanguage;
    }

    public boolean filter(JsonNode node){
        boolean isFilteredLang = false;
        if(node !=null){
            isFilteredLang = node.has(UESR) && node.get(UESR).has(UESR) &&
                    node.get(UESR).get(LANG).asText().equals(filteredLanguage);
        }
        return isFilteredLang;
    }
}
