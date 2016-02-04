package com.ollynural.ui.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 31/01/2016.
 */
@Controller
@RequestMapping("/summoner")
public class SummonerView {

    @RequestMapping("/{summonerName}")
    private String summonerView() {
        System.out.println("got here summoner");
        return "single-summoner-view";
    }

}
