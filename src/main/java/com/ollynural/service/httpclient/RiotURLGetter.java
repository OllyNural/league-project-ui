package com.ollynural.service.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollynural.service.converter.JsonToDTO;
import com.ollynural.service.converter.validation.ValidationOfUsername;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 02/02/2016.
 */
public class RiotURLGetter {

    ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(RiotURLGetter.class);
    private String APIKey = "896349ec-4ce2-49ed-be1a-b480bf0c7d49";

    public SummonerBasicDTO getSummonerBasicInfoByUsername(String newUsername) {

        logger.info("Getting summoner basic information by Username from RIOT's API, for: " + newUsername);

        JSONObject json = null;

        try {
            URL url = new URL("https://euw.api.pvp.net/api/lol/euw/v1.4/summoner/by-name/" + newUsername + "?api_key=" + APIKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            logger.info(url);

            if (conn.getResponseCode() != 200) {
                logger.error("FAIL on get basic summoner DTO - " + conn.getResponseCode());
                throw new RuntimeException("HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            json = new JSONObject(sb.toString());
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString = json != null ? json.toString() : null;
        logger.info("JSON returned: " + jsonString);
        SummonerBasicDTO summonerBasicDTO = new SummonerBasicDTO();
        try {
            summonerBasicDTO = mapper.readValue(jsonString, SummonerBasicDTO.class);
            // Formats the name
            Object myKey = summonerBasicDTO.getNonMappedAttributes().keySet().toArray()[0];
            String formattedName = ValidationOfUsername.validateUsername(summonerBasicDTO.getNonMappedAttributes().get(myKey).getName());
            summonerBasicDTO.getNonMappedAttributes().get(myKey).setName(formattedName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return summonerBasicDTO;
    }

    public SummonerRankedInfoDTO getRankedInfoByID(long summonerId) {

        logger.info("Getting summoner ranked information by Username from RIOT's API, for: " + summonerId);

        JSONObject json = null;

        try {
            URL url = new URL("https://euw.api.pvp.net/api/lol/euw/v2.5/league/by-summoner/" + summonerId + "/entry?api_key=" + APIKey);
            logger.info("Getting from: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != 200) {
                return new SummonerRankedInfoDTO();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));

            String output;
            StringBuilder sb = new StringBuilder();
            System.out.print("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            json = new JSONObject(sb.toString());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json == null) {
            throw new RuntimeException("Json is null");
        }

        String jsonString = json.toString();
        logger.info("Total json: " + jsonString);
        return JsonToDTO.convertRankedInformationStringToJson(jsonString);

    }
}
