package com.yarzar.booking_system.core_module.config;

import com.yarzar.booking_system.core_module.jobs.ClassFinishScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class SimpleScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SimpleScheduler.class.getName());

    private final ClassFinishScheduler classFinishScheduler;

    public SimpleScheduler(ClassFinishScheduler classFinishScheduler) {
        this.classFinishScheduler = classFinishScheduler;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void scheduleClassesUpdate() {
        logger.info("Running scheduleClassesUpdate");
        try {
            classFinishScheduler.makeScheduleForTodayClassesJob();
        } catch (Exception e) {
            logger.error("Error in scheduleClassesUpdate: ", e);
        } finally {
            logger.info("scheduleClassesUpdate finished");
        }
    }
}
