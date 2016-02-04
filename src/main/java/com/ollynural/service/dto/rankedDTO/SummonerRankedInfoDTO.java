package com.ollynural.service.dto.rankedDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ollynural.service.dto.rankedDTO.leagueentrydto.SummonerRankedInfoDTOEntry;

import java.util.*;

/**
 * DTO to represent Database information for ranked_info table
 */
public class SummonerRankedInfoDTO {

    @Override
    public String toString() {
        return "SummonerRankedInfoDTO{" +
                "integerSummonerRankedInfoDTOEntryMap=" + integerSummonerRankedInfoDTOEntryMap +
                ", summonerID=" + summonerID +
                ", updateTime=" + updateTime +
                '}';
    }

    private Map<String, List<SummonerRankedInfoDTOEntry>> integerSummonerRankedInfoDTOEntryMap;

    @JsonIgnore
    private Long summonerID;

    @JsonIgnore
    private Date updateTime;

    public Map<String, List<SummonerRankedInfoDTOEntry>> getIntegerSummonerRankedInfoDTOEntryMap() {
        return integerSummonerRankedInfoDTOEntryMap;
    }

    public void setIntegerSummonerRankedInfoDTOEntryMap(Map<String, List<SummonerRankedInfoDTOEntry>> integerSummonerRankedInfoDTOEntryMap) {
        this.integerSummonerRankedInfoDTOEntryMap = integerSummonerRankedInfoDTOEntryMap;
    }

    public Long getID() {
        return summonerID;
    }

    public void setID(Long summonerID) {
        this.summonerID = summonerID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date update_time) {
        this.updateTime = update_time;
    }

}
