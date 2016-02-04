package com.ollynural.service.universityservice;

import com.ollynural.persistence.accessdatabase.DatabaseAccessor;
import com.ollynural.persistence.sendtodatabase.DatabaseSender;
import com.ollynural.service.converter.validation.ValidationOfUsername;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import com.ollynural.service.dto.total.UniversitySummonerDTO;
import com.ollynural.service.httpclient.RiotURLGetter;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by Admin on 02/02/2016.
 */
public class UniversityLogic {

    private final DatabaseAccessor databaseAccessor = new DatabaseAccessor();
    private final DatabaseSender databaseSender = new DatabaseSender();

    final static Logger logger = Logger.getLogger(UniversityLogic.class);
    private RiotURLGetter riotURLGetter = new RiotURLGetter();

    public void addUniversityRankingWithSummonerNameAndUniversityCode(String summonerName, String universityCode) throws SQLException {
        logger.info(String.format("Adding university rankings for [%s] with university code of [%s]", summonerName, universityCode));
        String newSummonerName = ValidationOfUsername.validateUsername(summonerName);
        SummonerBasicDTO summonerBasicDTO;
        try {
            summonerBasicDTO = riotURLGetter.getSummonerBasicInfoByUsername(newSummonerName);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        logger.info("SummonerBasicDTO is: " + summonerBasicDTO.getNonMappedAttributes());

        // Assign summonerId to the ID returned
        Long basicId = summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId();
        logger.info("basicId: " + basicId);
        // Get the one from the database
        SummonerBasicDTO summonerBasicDTOOld = databaseAccessor.returnSummonerBasicDTOFromDatabaseUsingId(basicId);

        // Checking if the summonerName from the call is different to the one in the database
        // This is a problem with the unique key constraint as when you change username, the ID remains the same
        if (summonerBasicDTOOld.getNonMappedAttributes() != null) {
            Object myKey = summonerBasicDTOOld.getNonMappedAttributes().keySet().toArray()[0];
            Long oldSummonerName = summonerBasicDTOOld.getNonMappedAttributes().get(myKey).getId();
            if (!summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId().equals(summonerBasicDTOOld.getNonMappedAttributes().get(myKey).getId()) && summonerBasicDTOOld.getNonMappedAttributes().get(myKey).getId() != null) {
                logger.info("Names were not the same from database and Riot call");
                // This means the username has changed since we last stored it in the database
                // We need to delete data from the basic table with the ID and then re-insert it with the new data
                databaseSender.deleteSummonerBasicInfoForGivenId(basicId);
                databaseSender.insertSummonerBasicInfo(summonerBasicDTO, newSummonerName);
            }
        } else {
            databaseSender.insertSummonerBasicInfo(summonerBasicDTO, newSummonerName);
        }

        // Insert ranked information
        SummonerRankedInfoDTO summonerRankedInfoDTO = riotURLGetter.getRankedInfoByID(basicId);
        databaseSender.insertRankedInfo(summonerRankedInfoDTO);

        // Delete any occurrence of the summoner name in the database
        databaseSender.deleteUniversityInfoForGivenName(newSummonerName);
        databaseSender.addSummonerNameAndUniversityCode(basicId, summonerName, universityCode);

    }

    public UniversitySummonerDTO getUniversityRankingsByUniversityCode(String universityCode) throws SQLException {
        logger.info(String.format("Getting university rankings by code [%s]", universityCode));
        UniversitySummonerDTO universitySummonerDTO = databaseAccessor.getAllUniversityRankingsForGivenCode(universityCode);

        // Checks that the ranked information returns is existent, and if so whether it is in date
        for (int i = 0; i < universitySummonerDTO.getSummonerUniversityDTOs().size(); i++) {
            Long rankedSummonerId = universitySummonerDTO.getSummonerUniversityDTOs().get(i).getSummonerRankedInfoDTO().getID();
            Long summonerId = universitySummonerDTO.getSummonerUniversityDTOs().get(i).getID();
            if (rankedSummonerId == null) {
                logger.info("Getting ranked information from RIOT");
                logger.info(riotURLGetter.getRankedInfoByID(summonerId));
                universitySummonerDTO.getSummonerUniversityDTOs().get(i).setSummonerRankedInfoDTO(riotURLGetter.getRankedInfoByID(summonerId));
            } else {
                logger.info(String.format("Ranked information for [%s] existed but might be an old cache, UPDATING", summonerId));
                // Here you will check the cache
            }
            logger.info(universitySummonerDTO);
        }
        return universitySummonerDTO;
    }
}
