package com.example.app.simple_calls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.service.LogService;


@Controller
public class SimpleCallsController {

    @Autowired
    LogService logService;

    @GetMapping(path = "/")
    public String getSpa() {
        return "forward:vue/index.html";
    }
    
    @RequestMapping("/index")
    public String Index() {
        return "index.html";
    }

    @RequestMapping("/simple_calls")
    public String Simple_Calls() {

        return "simple_calls.html";
    }

}
