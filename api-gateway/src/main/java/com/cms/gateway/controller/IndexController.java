package com.cms.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Creams
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "OK";
    }
}
