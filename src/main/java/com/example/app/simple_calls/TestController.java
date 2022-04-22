package com.example.app.simple_calls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.service.LogService;
import com.example.domain.model.LogEntity;


@Controller
public class TestController {

    @Autowired
    LogService logService;
    
    @RequestMapping("/index")
    public String Index() {
        return "index.html";
    }

    @RequestMapping("/calls")
    public String Caller() {

        try{

            List<LogEntity> logList = logService.logList();

        } catch (Exception ex) {

        }

        return "calls.html";
    }    
}
