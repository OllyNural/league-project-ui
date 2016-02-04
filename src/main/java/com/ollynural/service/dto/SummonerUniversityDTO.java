package com.ollynural.service.dto;


import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;

/**
 * Created by Admin on 22/11/2015.
 */
public class SummonerUniversityDTO {

    @Override
    public String toString() {
        return "SummonerUniversityDTO{" +
                "summonerID=" + summonerID +
                ", summonerName='" + summonerName + '\'' +
                ", universityName='" + universityName + '\'' +
                ", summonerRankingInfoDTO='" + summonerRankedInfoDTO + '\'' +
                '}';
    }

    private Long summonerID;
    private String summonerName;
    private String universityName;
    private SummonerRankedInfoDTO summonerRankedInfoDTO;

    public SummonerRankedInfoDTO getSummonerRankedInfoDTO() {
        return summonerRankedInfoDTO;
    }

    public void setSummonerRankedInfoDTO(SummonerRankedInfoDTO summonerRankedInfoDTO) {
        this.summonerRankedInfoDTO = summonerRankedInfoDTO;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Long getID() {
        return summonerID;
    }

    public void setID(Long summonerID) {
        this.summonerID = summonerID;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

}
