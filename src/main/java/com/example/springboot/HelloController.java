package com.example.springboot;

import com.google.template.soy.jbcsrc.api.SoySauce;
import com.google.template.soy.jbcsrc.api.SoySauceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private static final String TEMPLATE_NAME = "com.jivesoftware.soy.hello";

    @GetMapping("/")
    @ResponseBody
    public String index() {
        SoySauce soySauce = new SoySauceBuilder().build();
        return soySauce.renderTemplate(TEMPLATE_NAME).renderText()
                .get();
    }
}
