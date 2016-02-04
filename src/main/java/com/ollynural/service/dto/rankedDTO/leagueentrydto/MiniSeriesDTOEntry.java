package com.ollynural.service.dto.rankedDTO.leagueentrydto;

/**
 * Created by Admin on 15/11/2015.
 */
public class MiniSeriesDTOEntry {

    private int losses;
    private String progress;
    private int target;
    private int wins;

    @Override
    public String toString() {
        return "MiniSeriesDTOEntry{" +
                "losses=" + losses +
                ", progress='" + progress + '\'' +
                ", target=" + target +
                ", wins=" + wins +
                '}';
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

}
