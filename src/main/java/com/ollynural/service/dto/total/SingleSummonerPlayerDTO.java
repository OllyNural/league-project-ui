package com.ollynural.service.dto.total;


import com.ollynural.service.dto.SummonerUniversityDTO;
import com.ollynural.service.dto.rankedDTO.SummonerRankedInfoDTO;
import com.ollynural.service.dto.summonerBasicDTO.SummonerBasicDTO;

/**
 * Created by Admin on 05/12/2015.
 */
public class SingleSummonerPlayerDTO {

    private SummonerBasicDTO summonerBasicDTO;
    private SummonerRankedInfoDTO summonerRankedInfoDTO;
    private SummonerUniversityDTO summonerUniversityDTO;
    private boolean usernameExists;

    @Override
    public String toString() {
        return "SingleSummonerPlayerDTO{" +
                "summonerBasicDTO=" + summonerBasicDTO +
                ", summonerRankedInfoDTO=" + summonerRankedInfoDTO +
                ", summonerUniversityDTO=" + summonerUniversityDTO +
                ", usernameExists=" + usernameExists +
                '}';
    }

    public boolean isUsernameExists() {
        return usernameExists;
    }

    public void setUsernameExists(boolean usernameExists) {
        this.usernameExists = usernameExists;
    }

    public SummonerBasicDTO getSummonerBasicDTO() {
        return summonerBasicDTO;
    }

    public void setSummonerBasicDTO(SummonerBasicDTO summonerBasicDTO) {
        this.summonerBasicDTO = summonerBasicDTO;
    }

    public SummonerRankedInfoDTO getSummonerRankedInfoDTO() {
        return summonerRankedInfoDTO;
    }

    public void setSummonerRankedInfoDTO(SummonerRankedInfoDTO summonerRankedInfoDTO) {
        this.summonerRankedInfoDTO = summonerRankedInfoDTO;
    }

    public SummonerUniversityDTO getSummonerUniversityDTO() {
        return summonerUniversityDTO;
    }

    public void setSummonerUniversityDTO(SummonerUniversityDTO summonerUniversityDTO) {
        this.summonerUniversityDTO = summonerUniversityDTO;
    }

}
