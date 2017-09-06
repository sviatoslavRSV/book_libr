package com.example.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private Logger logger = Logger.getLogger(IndexController.class);

    @GetMapping(value = "/admin")
    public String listBooksA() {
        logger.debug("show admin index page");
        return "admin/index";
    }
    @GetMapping(value = "/user")
    public String listBooksU() {
        logger.debug("show admin index page");
        return "user/index";
    }


}
