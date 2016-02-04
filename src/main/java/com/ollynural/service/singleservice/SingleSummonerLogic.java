package com.ollynural.service.singleservice;

import com.ollynural.persistence.accessdatabase.DatabaseAccessor;
import com.ollynural.persistence.sendtodatabase.DatabaseSender;
import com.ollynural.service.converter.validation.ValidationOfUsername;
import com.ollynural.service.dto.SummonerUniversityDTO;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import com.ollynural.service.dto.total.SingleSummonerPlayerDTO;
import com.ollynural.service.httpclient.RiotURLGetter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Admin on 02/02/2016.
 */
public class SingleSummonerLogic {

    private final DatabaseAccessor databaseAccessor = new DatabaseAccessor();
    private final DatabaseSender databaseSender = new DatabaseSender();

    final static Logger logger = Logger.getLogger(SingleSummonerLogic.class);
    private RiotURLGetter riotURLGetter = new RiotURLGetter();

    public SingleSummonerPlayerDTO getOrRetrieveBasicAndRankedSummonerInformation(String summonerName) throws IOException, ParseException, SQLException {

        String newSummonerName = ValidationOfUsername.validateUsername(summonerName);

        SingleSummonerPlayerDTO singleSummonerPlayerDTO = new SingleSummonerPlayerDTO();
        SummonerRankedInfoDTO summonerRankedInfoDTO;
        SummonerUniversityDTO summonerUniversityDTO = new SummonerUniversityDTO();
        SummonerBasicDTO summonerBasicDTO;

        Long rankedId;
        Long universityId;
        Long basicId;

        // Send get request to get basic information from the name and assign to main DTO
        // We do this to get the most recent basic info to check for any name changes - Will make more efficient in the future.
        summonerBasicDTO = riotURLGetter.getSummonerBasicInfoByUsername(newSummonerName);

        singleSummonerPlayerDTO.setSummonerBasicDTO(summonerBasicDTO);
        logger.info("SummonerBasicDTO is: " + summonerBasicDTO.getNonMappedAttributes());

        // Assign summonerId to the ID returned
        basicId = summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId();

        logger.info("basicId: " + basicId);

        // Get the one from the database
        SummonerBasicDTO summonerBasicDTOOld = databaseAccessor.returnSummonerBasicDTOFromDatabaseUsingId(basicId);

        // Checking if the summonerName from the call is different to the one in the database
        // This is a problem with the unique key constraint as when you change username, the ID remains the same
        if (summonerBasicDTOOld.getNonMappedAttributes() != null) {
            Object oldSummonerName = summonerBasicDTOOld.getNonMappedAttributes().keySet().toArray()[0];
            Object riotSummonerName = summonerBasicDTO.getNonMappedAttributes().keySet().toArray()[0];

//            if (!summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId().equals(summonerBasicDTOOld.getNonMappedAttributes().get(oldSummonerName).getId())) {
            if (!oldSummonerName.equals(riotSummonerName)) {
                // This means the username has changed since we last stored it in the database
                // We need to delete data from the basic table with the ID and then re-insert it with the new data
                logger.info(String.format("Names were not the same, updating name for ID [%s]", basicId));
                databaseSender.updateSummonerNameBasicTable(basicId, riotSummonerName);
                databaseSender.updateSummonerNameUniversityTable(basicId, riotSummonerName);
            }
        } else {
            databaseSender.insertSummonerBasicInfo(summonerBasicDTO, newSummonerName);
        }
        singleSummonerPlayerDTO.setSummonerBasicDTO(summonerBasicDTO);

        summonerRankedInfoDTO = databaseAccessor.getSummonerRankedInfoForGivenId(basicId);
        rankedId = summonerRankedInfoDTO.getID();
        logger.info("rankedId that existed: " + rankedId);

        if (rankedId != null) {
            // Set the values as the database values, and return what is already in the database
            singleSummonerPlayerDTO.setSummonerRankedInfoDTO(summonerRankedInfoDTO);
            logger.info("IDs already all exist in the database");
        }

        if (rankedId == null || rankedId == 0) {
            // Send get request to get ranked information from the name and assign to main DTO
            summonerRankedInfoDTO = riotURLGetter.getRankedInfoByID(basicId);
            logger.info("SummonerRankedInfoDTO is: " + summonerRankedInfoDTO.getIntegerSummonerRankedInfoDTOEntryMap());
            singleSummonerPlayerDTO.setSummonerRankedInfoDTO(summonerRankedInfoDTO);
            // Enter information into database
            databaseSender.insertRankedInfo(summonerRankedInfoDTO);
        }

        summonerUniversityDTO = databaseAccessor.returnUniversityDTOFromID(basicId);
        universityId = summonerUniversityDTO.getID();

        if (universityId != null) {
            summonerUniversityDTO.setUniversityName(summonerUniversityDTO.getUniversityName());
            logger.info("SummonerUniversityDTO is: " + summonerUniversityDTO.getUniversityName());
        } else {
            logger.info("SummonerUniversityDTO is: " + summonerUniversityDTO);
        }
        singleSummonerPlayerDTO.setSummonerUniversityDTO(summonerUniversityDTO);
        return singleSummonerPlayerDTO;
    }

}
