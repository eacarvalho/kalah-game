package com.eac.kalah.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by eduardo on 20/10/16.
 */
@Controller
public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage() {
        return "index.html";
    }
}