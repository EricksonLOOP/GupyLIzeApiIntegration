package com.tom.gupy.Controllers.Requests;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ContratoController {
    @GetMapping("/")
    public String HelloWorld(){
        return "Hello World";
    }
}
