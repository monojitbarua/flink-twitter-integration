package com.flink.twitter.core;

import com.flink.twitter.util.*;

//flink api
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;

//json parser api
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

//flink-twitter api
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class FlinkTwitterIntegration implements FlinkTwitterIntegrationUtils {

    public static void main(String[] args) throws Exception {

        //setting  up flink stream env
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ParameterTool params = ParameterTool.fromArgs(args);
        String resultOutputPath = params.get("output");

        DataStream<String> twitterData = env.addSource(new TwitterSource(FlinkTwitterIntegrationUtils.getTwitterCredentialProperties()));

        //DataStream<JsonNode> parsedData = twitterData.map(new TweeterEventParser());
        //map using lambda exp to convert string tweets into json
        DataStream<JsonNode> parsedData = twitterData.map(value -> {
            ObjectMapper jsonParser = new ObjectMapper();
            JsonNode node = jsonParser.readValue(value, JsonNode.class);
            return node;
        });

        //getting only english language tweets
        DataStream<JsonNode> englishTweets = parsedData.filter(new FilterByLanguage(ENGLISH));

        //getting desired words tweets
        DataStream<JsonNode> desiredWordsTweets = englishTweets.filter(new FilterByKeywords(KEYWORDS));

        //group by device name of tweets
        DataStream<Tuple2<String, JsonNode>> tweetsBySource = desiredWordsTweets.map(new MapTwitterDevice());

        //hourly tweets count by device
        tweetsBySource.map(new ExtractHourOfDay())
                .keyBy(0,1)         // groupBy source and hour
                .sum(2)         // sum for each device
                .writeAsText(resultOutputPath);

        env.execute("Twitter Integration");
    }
}
