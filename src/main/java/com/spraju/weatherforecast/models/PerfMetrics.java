package com.spraju.weatherforecast.models;

public class PerfMetrics {
    private String actionType;
    private Boolean isSuccessFull;
    private Long processingTime;

    private Exception e;

    public PerfMetrics(String actionType, Boolean isSuccessFull, Long processingTime) {
        this.actionType = actionType;
        this.isSuccessFull = isSuccessFull;
        this.processingTime = processingTime;
    }

    public PerfMetrics(String actionType, Boolean isSuccessFull, Long processingTime, Exception e) {
        this.actionType = actionType;
        this.isSuccessFull = isSuccessFull;
        this.processingTime = processingTime;
        this.e = e;
    }

    public String getSuccessPerfMetricsLog(){
         return "PerfMetrics{" +
                "actionType='" + actionType + '\'' +
                ", isSuccessFull=" + isSuccessFull +
                ", processingTime=" + processingTime +
                '}';
    }

    public String getFailurePerfMetricsLog(){
        return "PerfMetrics{" +
                "actionType='" + actionType + '\'' +
                ", isSuccessFull=" + isSuccessFull +
                ", processingTime=" + processingTime +
                "exception= "+ e.getStackTrace().toString()+
                '}';
    }




}
