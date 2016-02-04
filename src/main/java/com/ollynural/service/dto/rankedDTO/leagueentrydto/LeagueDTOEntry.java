package com.ollynural.service.dto.rankedDTO.leagueentrydto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the LeagueDTOEntry DTO
 */
public class LeagueDTOEntry {

    private String division;

    private boolean isFreshBlood;
    private boolean isHotStreak;
    private boolean isInactive;
    private boolean isVeteran;

    private int leaguePoints;

    private MiniSeriesDTOEntry miniSeries;
    private String playerOrTeamId;
    private String playerOrTeamName;
    private int wins;
    private int losses;

    @Override
    public String toString() {
        return "LeagueDTOEntry{" +
                "division='" + division + '\'' +
                ", isFreshBlood=" + isFreshBlood +
                ", isHotStreak=" + isHotStreak +
                ", isInactive=" + isInactive +
                ", isVeteran=" + isVeteran +
                ", leaguePoints=" + leaguePoints +
                ", losses=" + losses +
                ", miniSeries=" + miniSeries +
                ", playerOrTeamId='" + playerOrTeamId + '\'' +
                ", playerOrTeamName='" + playerOrTeamName + '\'' +
                ", wins=" + wins +
                '}';
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @JsonProperty("isFreshBlood")
    public boolean isFreshBlood() {
        return isFreshBlood;
    }

    public void setIsFreshBlood(boolean isFreshBlood) {
        this.isFreshBlood = isFreshBlood;
    }

    @JsonProperty("isHotStreak")
    public boolean isHotStreak() {
        return isHotStreak;
    }

    public void setIsHotStreak(boolean isHotStreak) {
        this.isHotStreak = isHotStreak;
    }

    @JsonProperty("isInactive")
    public boolean isInactive() {
        return isInactive;
    }

    public void setIsInactive(boolean isInactive) {
        this.isInactive = isInactive;
    }

    @JsonProperty("isVeteran")
    public boolean isVeteran() {
        return isVeteran;
    }

    public void setIsVeteran(boolean isVeteran) {
        this.isVeteran = isVeteran;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public MiniSeriesDTOEntry getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(MiniSeriesDTOEntry miniSeries) {
        this.miniSeries = miniSeries;
    }

    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    public void setPlayerOrTeamId(String playerOrTeamId) {
        this.playerOrTeamId = playerOrTeamId;
    }

    public String getPlayerOrTeamName() {
        return playerOrTeamName;
    }

    public void setPlayerOrTeamName(String playerOrTeamName) {
        this.playerOrTeamName = playerOrTeamName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

}
