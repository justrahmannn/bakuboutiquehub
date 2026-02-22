package com.atl.bakuboutiquehub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> index() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "BakuBoutiqueHub Backend is running!");
        response.put("version", "0.0.1-SNAPSHOT");
        return response;
    }
}
