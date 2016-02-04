package com.ollynural.persistence.accessdatabase;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollynural.service.converter.JsonToDTO;
import com.ollynural.service.dto.SummonerUniversityDTO;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.summonerBasicDTO.SingleSummonerBasicDTO;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import com.ollynural.service.dto.total.UniversitySummonerDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Admin on 14/11/2015.
 * Class to send requests to the Database
 */
public class DatabaseAccessor {

    //Properties here until i get properties file working properly
    private String TABLE_BASIC_INFO = "league_database_schema.basic_summoner_info";
    private String TABLE_RANKED_INFO = "league_database_schema.ranked_info";
    private String TABLE_UNIVERSITY_INFO = "league_database_schema.university_info";
    private String DATABASE_SCHEMA = "jdbc:mysql://localhost:3306/league_database_schema";
    private String USERNAME = "admin";
    private String PASSWORD = "admin_pass";

    private JsonToDTO jsonToDTO = new JsonToDTO();

    final static Logger logger = Logger.getLogger(DatabaseAccessor.class);

    private Properties prop = new Properties();

    private void loadProperties() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL Driver?!?");
            e.printStackTrace();
        }
    }

    public SummonerBasicDTO returnSummonerDTOFromDatabaseUsingName(String newUsername) {

        SummonerBasicDTO summonerBasicDTO = new SummonerBasicDTO();
        SingleSummonerBasicDTO singleSummonerBasicDTO = new SingleSummonerBasicDTO();

        // Database setup
        logger.info("Returning Summoner DTO from database using Summoner Name");
        PreparedStatement stmt = null;
        try {
            loadProperties();

            String query = "SELECT * FROM " + TABLE_BASIC_INFO + " WHERE summoner_name = ? ";

            Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);

            stmt = conn.prepareStatement(query);
            stmt.setString(1, newUsername);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //Set all the values into a DTO
                summonerBasicDTO = new SummonerBasicDTO();

                singleSummonerBasicDTO.setId(rs.getInt("summonerID"));
                singleSummonerBasicDTO.setName(rs.getString("summoner_name"));
                singleSummonerBasicDTO.setProfileIconId(rs.getInt("summoner_icon"));
                singleSummonerBasicDTO.setRevisionDate(rs.getLong("revision_date"));
                singleSummonerBasicDTO.setSummonerLevel(rs.getInt("summoner_level"));
                summonerBasicDTO.setNonMappedAttributes(newUsername, singleSummonerBasicDTO);

            }
        } catch (Exception e) {
            System.out.println("Database error with connection: " + e);
            e.printStackTrace();
        }
        //Close stmt and connection
        try{
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Basic DTO that was in database: " + summonerBasicDTO.getNonMappedAttributes());
        return summonerBasicDTO;
    }

    public SummonerBasicDTO returnSummonerBasicDTOFromDatabaseUsingId(Long basicId) {

        SummonerBasicDTO summonerBasicDTO = new SummonerBasicDTO();
        SingleSummonerBasicDTO singleSummonerBasicDTO = new SingleSummonerBasicDTO();

        // Database setup
        logger.info("Returning Summoner DTO from database using Summoner ID");
        PreparedStatement stmt = null;
        try {
            loadProperties();
            String query = "SELECT * FROM " + TABLE_BASIC_INFO + " WHERE summonerID = ? ";

            Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);

            stmt = conn.prepareStatement(query);
            stmt.setLong(1, basicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //Set all the values into a DTO
                summonerBasicDTO = new SummonerBasicDTO();

                singleSummonerBasicDTO.setId(rs.getInt("summonerID"));
                singleSummonerBasicDTO.setName(rs.getString("summoner_name"));
                singleSummonerBasicDTO.setProfileIconId(rs.getInt("summoner_icon"));
                singleSummonerBasicDTO.setRevisionDate(rs.getLong("revision_date"));
                singleSummonerBasicDTO.setSummonerLevel(rs.getInt("summoner_level"));
                summonerBasicDTO.setNonMappedAttributes(rs.getString("summoner_name"), singleSummonerBasicDTO);

            }
        } catch (Exception e) {
            System.out.println("Database error with connection: " + e);
            e.printStackTrace();
        }
        //Close stmt and connection
        try{
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Basic DTO that was in database: " + summonerBasicDTO.getNonMappedAttributes());
        return summonerBasicDTO;
    }

    public SummonerRankedInfoDTO getSummonerRankedInfoForGivenId(Long summonerID) {
        // Database Setup
        logger.info(String.format("Checking Summoner Ranking Exists for given Summoner ID: [%s]", summonerID));
        SummonerRankedInfoDTO summonerRankedInfoDTO = new SummonerRankedInfoDTO();
        try {
            loadProperties();
            PreparedStatement stmt;
            String query = "SELECT * FROM " + TABLE_RANKED_INFO + " WHERE summonerID = ? ";

            Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);

            stmt = conn.prepareStatement(query);
            stmt.setLong(1, summonerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                summonerRankedInfoDTO.setUpdateTime(rs.getDate("update_time"));
                summonerRankedInfoDTO = JsonToDTO.convertRankedInformationStringToJson(rs.getString("ranked_information_for_summoner"));
                summonerRankedInfoDTO.setID(rs.getLong("summonerID"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return summonerRankedInfoDTO;
    }

    public SummonerUniversityDTO returnUniversityDTOFromID(Long summonerID){
        // Database Setup
        logger.info("Returning University DTO from given Summoner ID");
        SummonerUniversityDTO summonerUniversityDTO = new SummonerUniversityDTO();
        try {
            loadProperties();
            PreparedStatement stmt;
            String query = "SELECT * FROM " + TABLE_UNIVERSITY_INFO + " WHERE summonerID = ? ";

            Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);

            stmt = conn.prepareStatement(query);
            stmt.setLong(1, summonerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                summonerUniversityDTO.setID(rs.getLong("summonerID"));
                summonerUniversityDTO.setUniversityName(rs.getString("summoner_name"));
                summonerUniversityDTO.setUniversityName(rs.getString("university_code"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        if(summonerUniversityDTO.getID() == null) {
            summonerUniversityDTO = new SummonerUniversityDTO();
        }
        return summonerUniversityDTO;
    }

    public UniversitySummonerDTO getAllUniversityRankingsForGivenCode(String universityCode) throws SQLException {
        int count = 0;
        loadProperties();
        logger.info(String.format("Returning University DTO from given university name: [%s]", universityCode));
        String query = "SELECT university_info.summonerID, university_info.university_code, university_info.summoner_name, ranked_info.ranked_information_for_summoner FROM " +
                TABLE_UNIVERSITY_INFO +
                " INNER JOIN ranked_info ON university_info.summonerID=ranked_info.summonerID" +
                " WHERE university_info.university_code= ?";
        Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, universityCode);
        ResultSet rs = stmt.executeQuery();
        ArrayList<SummonerUniversityDTO> summonerUniversityDTOArray = new ArrayList<SummonerUniversityDTO>();
        UniversitySummonerDTO universitySummonerDTO = new UniversitySummonerDTO();
        while (rs.next()) {
            // Get DTO from database
            SummonerUniversityDTO summonerUniversityDTO = new SummonerUniversityDTO();
            summonerUniversityDTO.setUniversityName(rs.getString("university_code"));
            summonerUniversityDTO.setID(rs.getLong("summonerId"));
            summonerUniversityDTO.setSummonerName(rs.getString("summoner_name"));
            summonerUniversityDTO.setSummonerRankedInfoDTO(JsonToDTO.convertRankedInformationStringToJson(rs.getString("ranked_information_for_summoner")));

            //Add all to array
            summonerUniversityDTOArray.add(summonerUniversityDTO);
            count++;
        }
        logger.info(String.format("[%s] summoners were returned from the database matching university code [%s]", count, universityCode));
        universitySummonerDTO.setSingleSummonerPlayerDTOs(summonerUniversityDTOArray);
        logger.info(String.format("UniversityArrayDTO is [%s]", universitySummonerDTO));
        return universitySummonerDTO;
    }
}
