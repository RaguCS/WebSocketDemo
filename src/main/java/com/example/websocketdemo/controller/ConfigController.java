package com.example.websocketdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigController {

    @Value("${app.websocket.url}")
    private String websocketUrl;

    @GetMapping("/config")
    public Map<String, String> getConfig() {
        return Map.of("websocketUrl", websocketUrl);
    }
}
