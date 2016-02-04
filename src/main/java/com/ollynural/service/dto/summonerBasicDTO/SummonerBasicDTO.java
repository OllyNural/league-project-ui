package com.ollynural.service.dto.summonerBasicDTO;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO to represent basic summoner call from RIOT's API
 */
public class SummonerBasicDTO {

    @JsonIgnore
    protected Map<String, SingleSummonerBasicDTO> nonMappedAttributes;

    @JsonAnyGetter
    public Map<String, SingleSummonerBasicDTO> getNonMappedAttributes() {
        return nonMappedAttributes;
    }

    @JsonAnySetter
    public void setNonMappedAttributes(String key, SingleSummonerBasicDTO value) {
        if (nonMappedAttributes == null) {
            nonMappedAttributes = new HashMap<String, SingleSummonerBasicDTO>();
        }
        if (key != null) {
            if (value != null) {
                nonMappedAttributes.put(key, value);
            } else {
                nonMappedAttributes.remove(key);
            }
        }
    }

    // This is not being used, going to get rid of it soon

    @JsonIgnore
    private SingleSummonerBasicDTO singleSummonerBasicDTO;

    public SingleSummonerBasicDTO getSingleSummonerBasicDTO() {
        return singleSummonerBasicDTO;
    }

    public void setSingleSummonerBasicDTO(SingleSummonerBasicDTO singleSummonerBasicDTO) {
        this.singleSummonerBasicDTO = singleSummonerBasicDTO;
    }

    // This is for the last updated time in the database

    @JsonIgnore
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date update_time) {
        this.updateTime = update_time;
    }

}
