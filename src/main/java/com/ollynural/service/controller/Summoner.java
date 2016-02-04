package com.ollynural.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollynural.service.dto.total.SingleSummonerPlayerDTO;
import com.ollynural.service.singleservice.SingleSummonerLogic;
import com.ollynural.service.universityservice.UniversityLogic;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Spring Help
 */
@RestController
@RequestMapping("/summoner")
public class Summoner {

    final static Logger logger = Logger.getLogger(Summoner.class);

    private SingleSummonerLogic singleSummonerLogic = new SingleSummonerLogic();

    @RequestMapping(value = "/name/{summonerName}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public SingleSummonerPlayerDTO returnSingleSummoner(@PathVariable("summonerName") String summonerName) throws Exception {
        logger.info("START PROCESS ---------------------------");
        SingleSummonerPlayerDTO singleSummonerPlayerDTO = new SingleSummonerPlayerDTO();
        try {
            singleSummonerPlayerDTO = singleSummonerLogic.getOrRetrieveBasicAndRankedSummonerInformation(summonerName);
            singleSummonerPlayerDTO.setUsernameExists(true);
        } catch (RuntimeException e) {
            logger.error("Username doesn't exist");
            singleSummonerPlayerDTO.setUsernameExists(false);
        }
        logger.info("END PROCESS -----------------------------");
        return singleSummonerPlayerDTO;
    }

}
