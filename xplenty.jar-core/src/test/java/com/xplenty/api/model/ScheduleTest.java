package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 21:18
 */
public class ScheduleTest extends TestCase {
    //TODO implement schedule add, schedule get info, schedule remove, schedule update, clone schedule


    @Test
    public void testBuilder() {
        // TODO implement actual test after implementing schedule creation
        Schedule sched = new Schedule();
        assertNotNull(sched);
    }

    public static Schedule createMockSchedule(Date now) {
        final Schedule sched = new Schedule();

        sched.createdAt = now;
        sched.updatedAt = now;
        sched.startAt = now;
        sched.nextRunAt = now;
        sched.lastRunAt = now;
        sched.intervalAmount = 10L;
        sched.intervalUnit = Xplenty.ScheduleIntervalUnit.days;
        sched.id = 666L;
        sched.name = "testSchedule";
        sched.description = "some neat description";
        sched.ownerId = 1L;
        sched.status = Xplenty.ScheduleStatus.enabled;
        sched.lastRunStatus = "Successfully stored zillion records";
        sched.executionCount = 1L;
        sched.url = String.format("https://api.xplenty.com/testacc/api/%s", Xplenty.Resource.Schedule.format(Long.toString(sched.id)));
        final ScheduleTask task = new ScheduleTask();
        task.nodes = 3;
        task.terminateOnIdle = true;
        task.timeToIdle = 1800;
        final ArrayList<ScheduleTaskPackage> packages = new ArrayList<ScheduleTaskPackage>();
        final ScheduleTaskPackage taskPackage = new ScheduleTaskPackage();
        taskPackage.packageId = 1L;
        final Map<String, String> vars = new HashMap<String, String>();
        vars.put("var1", "varvalue");
        taskPackage.variables = vars;
        packages.add(taskPackage);
        task.packages = packages;
        sched.task = task;

        return sched;
    }
}
