package com.ollynural.service.dto.total;


import com.ollynural.service.dto.SummonerUniversityDTO;

import java.util.ArrayList;

/**
 * Created by Admin on 21/01/2016.
 */
public class UniversitySummonerDTO {

    @Override
    public String toString() {
        return "UniversitySummonerDTO{" +
                "singleSummonerPlayerDTOs=" + summonerUniversityDTO +
                '}';
    }

    private ArrayList<SummonerUniversityDTO> summonerUniversityDTO;

    public ArrayList<SummonerUniversityDTO> getSummonerUniversityDTOs() {
        return summonerUniversityDTO;
    }

    public void setSingleSummonerPlayerDTOs(ArrayList<SummonerUniversityDTO> summonerUniversityDTO) {
        this.summonerUniversityDTO = summonerUniversityDTO;
    }

}
