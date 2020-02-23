package com.flink.twitter.util;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class MapTwitterDevice implements MapFunction<JsonNode, Tuple2<String, JsonNode>>, FlinkTwitterIntegrationUtils {

    public Tuple2<String, JsonNode> map(JsonNode node){
        String device = "";

        if (node.has(SOURCE)){
            String sourceHtml = node.get(SOURCE).asText().toLowerCase();

            if (sourceHtml.contains(IPHONE) || sourceHtml.contains(IPAD))
                device = IPHONE;
            else if (sourceHtml.contains(MAC))
                device = MAC;
            else if (sourceHtml.contains(ANDROID))
                device = ANDROID;
            else if (sourceHtml.contains(BB))
                device = BB;
            else if (sourceHtml.contains(WEB))
                device = BROWSER;
            else
                device = OTHER;
        }
        return new Tuple2<String, JsonNode>(device, node);     // returns  (Android,tweet)
    }
}
