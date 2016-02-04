package com.ollynural.service.dto.summonerBasicDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 20/12/2015.
 */
public class SingleSummonerBasicDTO {
    @JsonProperty("id")
    private long Id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profileIconId")
    private int profileIconId;
    @JsonProperty("revisionDate")
    private Long revisionDate;
    @JsonProperty("summonerLevel")
    private int summonerLevel;
//    @JsonProperty("update_time")
//    private Time cacheTime;
//
//    public Time getTime(){
//        return cacheTime;
//    }

    public void setId(long ID) {
        this.Id = ID;
    }

    public Long getId() {
        return Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getProfileIconId() {return profileIconId;}

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getRevisionDate() {return revisionDate;}

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getSummonerLevel() {return summonerLevel;}

    public String toString(){
        return "id: " + Id + ", name: " + name + ", profileIconId: " + profileIconId
                + ", revisionDate: " + revisionDate + ", summonerLevel: " + summonerLevel;
    }

}
