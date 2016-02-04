package com.ollynural.service.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.rankedDTO.leagueentrydto.SummonerRankedInfoDTOEntry;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts json from GET request to the DTO assosiated with that object
 */
public class JsonToDTO {

    final static Logger logger = Logger.getLogger(JsonToDTO.class);

    private SummonerBasicDTO summonerBasicDTO = new SummonerBasicDTO();

    public SummonerBasicDTO convertBasicInformationStringToJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            summonerBasicDTO = mapper.readValue(json, SummonerBasicDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return summonerBasicDTO;
    }

    public static SummonerRankedInfoDTO convertRankedInformationStringToJson(String json){
        SummonerRankedInfoDTO summonerRankedInfoDTO = new SummonerRankedInfoDTO();
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory factory = mapper.getTypeFactory();
        // type of key of response map
        JavaType stringType = factory.constructType(String.class);
        // type of value of response map
        JavaType listOfDtosType = factory.constructCollectionType(ArrayList.class, SummonerRankedInfoDTOEntry.class);
        // create type of map
        JavaType responseType = factory.constructMapType(HashMap.class, stringType, listOfDtosType);
        try {
            assert json != null;
            Map<String, List<SummonerRankedInfoDTOEntry>> response = new ObjectMapper().readValue(json, responseType);
            summonerRankedInfoDTO.setIntegerSummonerRankedInfoDTOEntryMap(response);
            summonerRankedInfoDTO.setID(Long.parseLong(response.keySet().toArray()[0].toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return summonerRankedInfoDTO;
    }

}
