package com.liang.feign;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

import java.util.List;

public class GithubTest {

    public static void main(String... args) {
        GitHub github = Feign.builder()
                .encoder(new GsonEncoder())
                //.encoder(new JacksonEncoder())
                .decoder(new GsonDecoder())
                //.decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                //.client(new OkHttpClient())
                //.client(new HttpClient())
                //.client(RibbonClient.create())
                .target(GitHub.class, "https://api.github.com");

        // Fetch and print a list of the contributors to this library.
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
