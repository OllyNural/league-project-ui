package com.ollynural.service.dto.rankedDTO.leagueentrydto;

import java.util.Arrays;

/**
 * DTO to represent ranked information call from RIOT's API
 */
public class SummonerRankedInfoDTOEntry {

    @Override
    public String toString() {
        return "SummonerRankedInfoDTOEntry{" +
                "entries=" + Arrays.toString(entries) +
                ", name='" + name + '\'' +
                ", queue='" + queue + '\'' +
                ", tier='" + tier + '\'' +
                '}';
    }

    private LeagueDTOEntry[] entries;
    private String name;
    private String queue;
    private String tier;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public LeagueDTOEntry[] getEntries() {
        return entries;
    }

    public void setEntries(LeagueDTOEntry[] entries) {
        this.entries = entries;
    }


}
