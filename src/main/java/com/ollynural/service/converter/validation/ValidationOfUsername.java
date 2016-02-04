package com.ollynural.service.converter.validation;

import org.apache.log4j.Logger;

/**
 * Checks the username is valid to use
 */
public class ValidationOfUsername {

    final static Logger logger = Logger.getLogger(ValidationOfUsername.class);

    public static String validateUsername(String username) {
        String newUsername = "";
        try {
            newUsername = username.toLowerCase();
            newUsername = newUsername.trim();
            newUsername = newUsername.replace(" ", "");
            logger.info("Summoner name has been formatted to: " + newUsername);
        } catch (Exception e) {
            logger.info("Summoner name was already in the correct format: " + newUsername);
        }
        return newUsername;
    }
}
