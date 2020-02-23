package com.flink.twitter.util;

import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public interface FlinkTwitterIntegrationUtils {

    public static final String ENGLISH = "en";
    public static final String UESR = "user";
    public static final String LANG = "lang";
    public static final String TEXT = "text";
    public static final String SOURCE = "source";
    public static final String IPAD = "ipad";
    public static final String IPHONE = "iphone";
    public static final String MAC = "mac";
    public static final String ANDROID = "android";
    public static final String BB = "BlackBerry";
    public static final String OTHER = "Other";
    public static final String WEB = "web";
    public static final String BROWSER = "browser";
    public static final String CREATED_AT = "created_at";
    public static final List<String> KEYWORDS = Arrays.asList("temperature increase","weather change",
            "climate", "global warming","pollution","save earth","INDvNZ","#INDvNZ","#MathematicianSwara","MathematicianSwara","#AmazonWheelOfFortune","AmazonWheelOfFortune");


    //create a developer account in tweeter and create an apps. then get the access token and access key and secret key
    public static Properties getTwitterCredentialProperties(){
        Properties twitterCredentials = new Properties();
        twitterCredentials.setProperty(TwitterSource.CONSUMER_KEY, "<your api key>");
        twitterCredentials.setProperty(TwitterSource.CONSUMER_SECRET, "<your api secret key>");
        twitterCredentials.setProperty(TwitterSource.TOKEN, "<your access token>");
        twitterCredentials.setProperty(TwitterSource.TOKEN_SECRET, "<your access token secret>");
        return twitterCredentials;
    }
}
