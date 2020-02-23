package com.flink.twitter.util;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class ExtractHourOfDay implements MapFunction<Tuple2<String, JsonNode>, Tuple3<String, String, Integer>>, FlinkTwitterIntegrationUtils {

    public Tuple3<String, String, Integer> map(Tuple2<String, JsonNode> value) {
        JsonNode node = value.f1;
        String timestamp = node.get(CREATED_AT).asText();
        String hour = timestamp.split(" ")[3].split(":")[0] + "th hour";
        return new Tuple3<String, String, Integer>(value.f0, hour, 1);
    }
}
