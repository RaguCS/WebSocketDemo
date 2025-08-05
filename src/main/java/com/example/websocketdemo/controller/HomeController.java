package com.example.websocketdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    // Import statement for RequestMethod
    // This import allows us to specify the HTTP method for the request mapping
//    import org.springframework.web.bind.annotation.RequestMethod;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        System.out.println("hitting home page");
        return "chat";
    }
}
