package com.yarzar.booking_system.core_module.jobs;

import com.yarzar.booking_system.core_module.entity.Classes;
import com.yarzar.booking_system.core_module.repository.ClassesRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClassFinishScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(ClassFinishScheduler.class);

    private final Scheduler scheduler;

    private final ClassesRepository classesRepository;

    public ClassFinishScheduler(Scheduler scheduler, ClassesRepository classesRepository) {
        this.scheduler = scheduler;
        this.classesRepository = classesRepository;
    }

    /**
     * this method is used to add a ClassFinishJob to the schedule when classes is created or updated.
     * Adds a ClassFinishJob to the schedule for the given class.
     * @param classes
     */
    public void addToSchedule(Classes classes) {
        LOG.info("Adding ClassFinishJob to schedule for classId: {}", classes.getId());
        try {
            scheduleClassFinishJob(classes);
        } catch (Exception e) {
            LOG.error("Failed to add ClassFinishJob to schedule for classId: {}. Error: {}", classes.getId(), e.getMessage());
        }
    }

    public void makeScheduleForTodayClassesJob() {
        LOG.info("Scheduling ClassFinishJob for classes ending today.");
        try {
            Date now = new Date();
            Date endOfDay = new Date(now.getTime() + 24 * 60 * 60 * 1000 - 1);

            classesRepository.findByEndAtBetween(now.toInstant(), endOfDay.toInstant())
                    .forEach(this::scheduleClassFinishJob);

        } catch (Exception e) {
            LOG.error("Failed to schedule ClassFinishJob for today. Error: {}", e.getMessage());
        }
    }

    public void scheduleClassFinishJob(Classes classes) {
        LOG.info("Scheduling ClassFinishJob for classId: {}", classes.getId());
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("classId", classes.getId().toString());

            JobDetail job = JobBuilder.newJob(ClassFinishJob.class)
                    .withIdentity("class-finish-job-" + classes.getId())
                    .usingJobData(dataMap)
                    .storeDurably()
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("class-finish-trigger-" + classes.getId())
                    .startAt(Date.from(classes.getEndAt()))
                    .build();

            if (scheduler.checkExists(job.getKey())) {
                LOG.info("ClassFinishJob already exists for classId: {}. Updating trigger.", classes.getId());
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            } else {
                LOG.info("Scheduling new ClassFinishJob for classId: {}", classes.getId());
                scheduler.scheduleJob(job, trigger);
            }
        } catch (Exception e) {
            LOG.error("Failed to schedule ClassFinishJob for classId: {}. Error: {}", classes.getId(), e.getMessage());
        }
    }
}
