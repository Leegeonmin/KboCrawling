package com.amazontest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerConfig {

    @Scheduled(cron = "0/5 * * * * ?")
    public void changeReservationStatus(){

        System.out.println("test scheduler and ec2");
    }
}
