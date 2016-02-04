package com.ollynural.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollynural.service.dto.total.UniversitySummonerDTO;
import com.ollynural.service.universityservice.UniversityLogic;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Spring Help
 */
@RestController
public class University {

    private ObjectMapper mapper = new ObjectMapper();
    final static Logger logger = Logger.getLogger(University.class);

    private UniversityLogic universityLogic = new UniversityLogic();

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public UniversitySummonerDTO returnSingleSummoner(@RequestParam("universitycode") String universityCode) throws Exception {
        logger.info("START PROCESS ---------------------------");
        if (!universityCode.equals("") || universityCode != null) {
            UniversitySummonerDTO universityArrayDTO = universityLogic.getUniversityRankingsByUniversityCode(universityCode);
            String json = mapper.writeValueAsString(universityArrayDTO);
            return universityArrayDTO;
        } else {
            logger.info("No code provided");
            return null;
        }
    }

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public Response addSummonerToUniversity(@RequestParam("summonerName") String summonerName,
                                         @RequestParam("universityCode") String universityCode) throws Exception {
        try {
            logger.info("Adding [" + summonerName + "] to the university database");
            universityLogic.addUniversityRankingWithSummonerNameAndUniversityCode(summonerName, universityCode);
        } catch (RuntimeException e) {
            logger.info(String.format("Summoner name [%s] does not exist in EUW", summonerName));
            return Response.status(500).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }
}
