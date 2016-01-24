package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.JsonMapperFactory;
import junit.framework.TestCase;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 21:18
 */
public class ScheduleTest extends TestCase {


    @Test
    public void testBuilder() throws Exception {
        Date now = new Date();
        Schedule sched = createMockSchedule(now);
        assertNotNull(sched);

        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        sched = new Schedule();
        sched.withId(189L).withName("1111SUPER SCHEDULE LAUNCH IT NOW MEGA TEST").withDescription("1111LAUNCH YOUR SCHEDULE NO SMS NO PAYMENT");
        sched.withIntervalAmount(100L).withIntervalUnit(Xplenty.ScheduleIntervalUnit.hours).withStartAt(now);
        sched.withStatus(Xplenty.ScheduleStatus.disabled);
        ScheduleTask task = new ScheduleTask().withNodes(10).withTerminateOnIdle(true).withTimeToIdle(1000);
        List<ScheduleTaskPackage> packages = new ArrayList<>();
        ScheduleTaskPackage pack = new ScheduleTaskPackage().withPackageId(4991L);
        Map<String, String> vars = new HashMap<>();
        vars.put("testvar", "testval");
        pack.withVariables(vars);
        packages.add(pack);
        task.withPackages(packages);
        sched.withTask(task);
        assertEquals(String.format("{\"id\":189,\"name\":\"1111SUPER SCHEDULE LAUNCH IT NOW MEGA TEST\",\"description\":\"1111LAUNCH YOUR SCHEDULE NO SMS NO PAYMENT\",\"status\":\"disabled\",\"task\":{\"nodes\":10,\"packages\":{\"0\":{\"variables\":{\"testvar\":\"testval\"},\"package_id\":4991}},\"terminate_on_idle\":true,\"time_to_idle\":1000},\"start_at\":\"%s\",\"interval_amount\":100,\"interval_unit\":\"hours\"}", dFormat.format(now)), JsonMapperFactory.getInstance().writeValueAsString(sched));

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
        sched.overlap = true;
        sched.reuseClusterStrategy = Xplenty.ReuseClusterStrategy.self;

        return sched;
    }
}
