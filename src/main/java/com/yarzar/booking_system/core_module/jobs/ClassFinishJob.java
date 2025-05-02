package com.yarzar.booking_system.core_module.jobs;

import com.yarzar.booking_system.booking_module.service.IBookingService;
import com.yarzar.booking_system.core_module.repository.ClassWaitListRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassFinishJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(ClassFinishJob.class);

    private final IBookingService bookingService;

    public ClassFinishJob(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        UUID classId = UUID.fromString(dataMap.getString("classId"));

        LOG.info("Executing ClassFinishJob for classId: {}", classId);

        this.bookingService.refundWaitList(classId);

        LOG.info("ClassFinishJob executed successfully for classId: {}", classId);
    }
}
