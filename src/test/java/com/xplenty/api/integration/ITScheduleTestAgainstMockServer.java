package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.model.ScheduleTask;
import com.xplenty.api.model.ScheduleTaskPackage;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ITScheduleTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/schedule";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 666L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testCreateSchedule() throws Exception {
        Schedule c = new Schedule();
        c.withName("1111SUPER SCHEDULE LAUNCH IT NOW MEGA TEST").withDescription("1111LAUNCH YOUR SCHEDULE NO SMS NO PAYMENT");
        c.withIntervalAmount(100L).withIntervalUnit(Xplenty.ScheduleIntervalUnit.hours).withStartAt(new Date());
        c.withStatus(Xplenty.ScheduleStatus.disabled);
        ScheduleTask task = new ScheduleTask().withNodes(10).withTerminateOnIdle(true).withTimeToIdle(1000);
        List<ScheduleTaskPackage> packages = new ArrayList<>();
        ScheduleTaskPackage pack = new ScheduleTaskPackage().withPackageId(4991L);
        Map<String, String> vars = new HashMap<>();
        vars.put("testvar", "testval");
        pack.withVariables(vars);
        packages.add(pack);
        task.withPackages(packages);
        c.withTask(task);
        c = api.createSchedule(c);
        checkEntity(c);
    }

    public void testUpdateSchedule() throws Exception {
        Schedule c = new Schedule();
        c.withId(entityId).withName("1111SUPER SCHEDULE LAUNCH IT NOW MEGA TEST").withDescription("1111LAUNCH YOUR SCHEDULE NO SMS NO PAYMENT");
        c.withIntervalAmount(100L).withIntervalUnit(Xplenty.ScheduleIntervalUnit.hours).withStartAt(new Date());
        c.withStatus(Xplenty.ScheduleStatus.disabled);
        ScheduleTask task = new ScheduleTask().withNodes(10).withTerminateOnIdle(true).withTimeToIdle(1000);
        List<ScheduleTaskPackage> packages = new ArrayList<>();
        ScheduleTaskPackage pack = new ScheduleTaskPackage().withPackageId(4991L);
        Map<String, String> vars = new HashMap<>();
        vars.put("testvar", "testval");
        pack.withVariables(vars);
        packages.add(pack);
        task.withPackages(packages);
        c.withTask(task);
        c = api.updateSchedule(c);
        checkEntity(c);
    }

    public void testGetScheduleInfo() throws Exception {
        Schedule c = api.getScheduleInfo(entityId);
        checkEntity(c);
    }

    public void testDeleteSchedule() throws Exception {
        Schedule c = api.deleteSchedule(entityId);
        checkEntity(c);
    }

    public void testCloneSchedule() throws Exception {
        Schedule c = api.cloneSchedule(entityId);
        checkEntity(c);
    }

    public void testListSchedules() throws Exception {
        List<Schedule> list = api.listSchedules();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        Schedule c = list.get(0);
        checkEntity(c);
    }

    private void checkEntity(Schedule c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals("testSchedule", c.getName());
        assertEquals("some neat description", c.getDescription());
        assertEquals("Successfully stored zillion records", c.getLastRunStatus());
        assertEquals(Xplenty.ScheduleStatus.enabled, c.getStatus());
        assertEquals(String.format("https://localhost/%s/api/schedules/%s", accountID, entityId), c.getUrl());
        assertEquals(1, c.getExecutionCount().longValue());
        assertEquals(10, c.getIntervalAmount().longValue());
        assertEquals(1, c.getOwnerId().longValue());
        assertEquals(Xplenty.ScheduleIntervalUnit.days, c.getIntervalUnit());
        assertEquals(dFormat.parse("2016-01-24T10:07:42Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-24T10:07:42Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-24T10:07:42Z"), c.getLastRunAt());
        assertEquals(dFormat.parse("2016-01-24T10:07:42Z"), c.getNextRunAt());
        assertEquals(dFormat.parse("2016-01-24T10:07:42Z"), c.getStartAt());
        assertTrue(c.getOverlap());
        assertEquals(Xplenty.ReuseClusterStrategy.any, c.getReuseClusterStrategy());

        ScheduleTask task = c.getTask();
        assertNotNull(task);
        assertTrue(task.getTerminateOnIdle());
        assertEquals(3, task.getNodes().intValue());
        assertEquals(1800, task.getTimeToIdle().intValue());
        List<ScheduleTaskPackage> list = task.getPackages();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        ScheduleTaskPackage xpackage = list.get(0);
        assertEquals(1, xpackage.getPackageId().intValue());
        assertNotNull(xpackage.getVariables());
        assertEquals("varvalue", xpackage.getVariables().get("var1"));
    }
}
