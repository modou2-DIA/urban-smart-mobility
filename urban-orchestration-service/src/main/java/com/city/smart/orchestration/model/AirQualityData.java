package com.city.smart.orchestration.model;



import lombok.AllArgsConstructor;


import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
public class AirQualityData {
    
    private Integer aqiIndex;
    private String status;
    private String advice; // Conseil basé sur l'état de l'air

    public AirQualityData(int aqiIndex, String status, String advice) {
        this.aqiIndex = aqiIndex; // Autoboxing
        this.status = status;
        this.advice = advice;
    }

    
    

    /**
     * @return Integer return the aqiIndex
     */
    public Integer getAqiIndex() {
        return aqiIndex;
    }

    /**
     * @param aqiIndex the aqiIndex to set
     */
    public void setAqiIndex(Integer aqiIndex) {
        this.aqiIndex = aqiIndex;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}