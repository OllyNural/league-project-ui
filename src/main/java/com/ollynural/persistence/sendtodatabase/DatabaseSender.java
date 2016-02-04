package com.ollynural.persistence.sendtodatabase;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.rankedDTO.leagueentrydto.SummonerRankedInfoDTOEntry;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Admin on 14/11/2015.
 * Class to send requests to the Database to insert/update data
 */
public class DatabaseSender {

    //Properties here until i get properties file working properly
    private String TABLE_BASIC_INFO = "league_database_schema.basic_summoner_info";
    private String TABLE_RANKED_INFO = "league_database_schema.ranked_info";
    private String TABLE_UNIVERISTY_INFO = "league_database_schema.university_info";
    private String DATABASE_SCHEMA = "jdbc:mysql://localhost:3306/league_database_schema";
    private String USERNAME = "admin";
    private String PASSWORD = "admin_pass";

    private Properties prop = new Properties();
    private InputStream input = null;
    private ObjectMapper mapper = new ObjectMapper();

    final static Logger logger = Logger.getLogger(DatabaseSender.class);

    private void loadProperties() {
//        String filename = "config.properties";
//        input = getClass().getClassLoader().getResourceAsStream(filename);
//        if (input == null) {
//            System.out.println("Sorry, unable to find " + filename);
//        }
//        try {
//            prop.load(input);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL Driver?!?");
            e.printStackTrace();
        }
    }

    public void insertSummonerBasicInfo(SummonerBasicDTO summonerBasicDTO, String newSummonerName) throws SQLException {
        logger.info("Inserting summoner basic information");
        PreparedStatement stmt = null;
        try {
            loadProperties();
            stmt = null;
            String query = "INSERT INTO " + TABLE_BASIC_INFO + " (summonerID, summoner_name, summoner_icon, revision_date, summoner_level)" +
                    "VALUES ( ? , ? , ? , ? , ? );";
            try {
                Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
                stmt = conn.prepareStatement(query);

                logger.info("Id or name is: " + summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId());

                // Set the variables from the DTO into the query
                stmt.setLong(1, summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getId());
                stmt.setString(2, summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getName());
                stmt.setLong(3, summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getProfileIconId());
                stmt.setLong(4, summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getRevisionDate());
                stmt.setInt(5, summonerBasicDTO.getNonMappedAttributes().get(newSummonerName).getSummonerLevel());

                stmt.executeUpdate();

                System.out.println("Full Summoner Information in basic_summoner_info table was stored");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Database error with connection: " + e);
            e.printStackTrace();
        }

    }

    public void insertRankedInfo(SummonerRankedInfoDTO summonerRankedInfoDTO) throws SQLException {
        logger.info("Inserting ranked information");

        // Gets the ID from the RankedDTO to put into database
        Map.Entry<String, List<SummonerRankedInfoDTOEntry>> entry = summonerRankedInfoDTO.getIntegerSummonerRankedInfoDTOEntryMap().entrySet().iterator().next();
        Integer summonerId = Integer.parseInt(entry.getKey());

        Map<String, List<SummonerRankedInfoDTOEntry>> summonerRankedInfoDTOEntry = summonerRankedInfoDTO.getIntegerSummonerRankedInfoDTOEntryMap();

        loadProperties();

        // Prepares the SQL Statement
        PreparedStatement stmt = null;
        String query = "INSERT INTO " + TABLE_RANKED_INFO + " (summonerID, ranked_information_for_summoner)" +
                "VALUES ( ? , ? );";

        try {
            Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, summonerId);
            logger.info(summonerRankedInfoDTOEntry);
            String summonerRankedInfoString = mapper.writeValueAsString(summonerRankedInfoDTOEntry);
            logger.info(summonerRankedInfoString);
            stmt.setString(2, summonerRankedInfoString);

            stmt.executeUpdate();

            logger.info("Summoner ID in Ranked Information table was stored!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteSummonerBasicInfoForGivenId(Long basicId) {
        logger.info("Deleting summonerBasicInfo from database using: " + basicId);
        PreparedStatement stmt = null;
        try {
            loadProperties();

            stmt = null;
            String query = "DELETE FROM " + TABLE_BASIC_INFO + " WHERE summonerID = ?";
            try {
                Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
                stmt = conn.prepareStatement(query);

                stmt.setLong(1, basicId);

                stmt.executeUpdate();
                logger.info("Basic Info was deleted for: " + basicId);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Database error with connection: " + e);
            e.printStackTrace();
        }
    }
    public void addSummonerNameAndUniversityCode(Long basicId, String summonerName, String universityCode) throws SQLException {
        logger.info("Adding summoner name: [" + summonerName + "/" + basicId + "] and university: [" + universityCode + "]");
        loadProperties();
        PreparedStatement stmt;
        String query = "INSERT INTO " + TABLE_UNIVERISTY_INFO + " VALUES ( ? , ? , ? )";
        Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
        stmt = conn.prepareStatement(query);

        stmt.setLong(1, basicId);
        stmt.setString(2, universityCode);
        stmt.setString(3, summonerName);
        stmt.executeUpdate();
        logger.info("Summoner name and university were added to the database");

        stmt.close();
    }

    public void deleteUniversityInfoForGivenName(String summonerName) throws SQLException {
        logger.info("Deleting university by summoner name: [" + summonerName + "]");
        loadProperties();
        PreparedStatement stmt;
        String query = "DELETE FROM " + TABLE_UNIVERISTY_INFO + "  WHERE summoner_name = ?";
        Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
        stmt = conn.prepareStatement(query);

        stmt.setString(1, summonerName);
        stmt.executeUpdate();
        logger.info("Summoner name and info were deleted from the university ranking table");

        stmt.close();
    }

    public void updateSummonerNameBasicTable(Long basicId, Object riotSummonerName) throws SQLException {
        logger.info(String.format("Updating BasicDTO name to [%s] for basicSummonerDTO with ID of [%s]", riotSummonerName, basicId));
        loadProperties();
        PreparedStatement stmt;
        String query = "UPDATE " + TABLE_BASIC_INFO + " SET summoner_name = ? WHERE summonerID = ?";
        Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
        stmt = conn.prepareStatement(query);

        stmt.setObject(1, riotSummonerName);
        stmt.setLong(2, basicId);
        stmt.executeUpdate();

        stmt.close();
    }

    public void updateSummonerNameUniversityTable(Long basicId, Object riotSummonerName) throws SQLException {
        logger.info(String.format("Updating UniversityDTO name to [%s] for basicSummonerDTO with ID of [%s]", riotSummonerName, basicId));
        loadProperties();
        PreparedStatement stmt;
        String query = "UPDATE " + TABLE_UNIVERISTY_INFO + " SET summoner_name = ? WHERE summonerID = ?";
        Connection conn = DriverManager.getConnection(DATABASE_SCHEMA, USERNAME, PASSWORD);
        stmt = conn.prepareStatement(query);

        stmt.setObject(1, riotSummonerName);
        stmt.setLong(2, basicId);
        stmt.executeUpdate();

        stmt.close();
    }
}

