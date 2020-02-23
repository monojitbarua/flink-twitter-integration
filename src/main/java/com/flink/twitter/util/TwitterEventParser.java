package com.flink.twitter.util;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class TwitterEventParser implements MapFunction<String, JsonNode>{

    public JsonNode map(String value) throws Exception{
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode node = jsonParser.readValue(value, JsonNode.class);
        return node;
    }
}
