package com.ollynural.ui.controller;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 31/01/2016.
 */
@Controller
public class summonerview {

    @RequestMapping("/summoner")
    private String summonerView() {
        return "summoner-view";
    }

}
