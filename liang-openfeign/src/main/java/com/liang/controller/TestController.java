package com.liang.controller;

import com.liang.client.Contributor;
import com.liang.client.Github;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private Github github;

    @RequestMapping("/test")
    public String test(){

        List<Contributor> list = github.contributors("OpenFeign", "feign");

        for (Contributor contributor : list) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }

        return "aaa";

    }
}
