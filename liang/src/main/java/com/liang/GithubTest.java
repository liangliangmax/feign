package com.liang;

import example.github.GitHubExample;
import feign.Feign;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.ribbon.RibbonClient;
import feign.slf4j.Slf4jLogger;
import sun.net.www.http.HttpClient;

import java.util.List;

public class GithubTest {

    interface GitHub {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("POST /repos/{owner}/{repo}/issues")
        void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);

    }

    public static class Contributor {
        String login;
        int contributions;
    }

    public static class Issue {
        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }

    public static void main(String... args) {
        GitHub github = Feign.builder()
                .encoder(new GsonEncoder())
                //.encoder(new JacksonEncoder())
                .decoder(new GsonDecoder())
                //.decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .client(new OkHttpClient())
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
