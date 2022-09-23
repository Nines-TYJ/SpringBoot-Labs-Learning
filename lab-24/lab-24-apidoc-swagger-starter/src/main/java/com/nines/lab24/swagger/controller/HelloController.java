package com.nines.lab24.swagger.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanyujie
 * @classname HelloController
 * @description TODO
 * @date 2022/8/16 17:28
 * @since 1.0
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
