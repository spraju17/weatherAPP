package com.spraju.weatherforecast.handler;

import com.spraju.weatherforecast.models.PerfMetrics;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LogHandler {

    public void logPerfMetrics(PerfMetrics perfMetrics, Logger logger){
        logger.info(perfMetrics.getSuccessPerfMetricsLog());
    }

    public void logExceptionPerfMetrics(PerfMetrics perfMetrics, Logger logger){
        logger.info(perfMetrics.getFailurePerfMetricsLog());
    }

    public void logEvent(String string, Logger logger){
        logger.info(string);
    }

}
