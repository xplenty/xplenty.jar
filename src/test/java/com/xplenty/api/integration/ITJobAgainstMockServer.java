package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Creator;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobLog;
import com.xplenty.api.request.job.ListJobs;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ITJobAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/job";
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

    public void testRunJob() throws Exception {
        Map<String, String> jobVars = new HashMap<>();
        jobVars.put("c", "700");
        jobVars.put("l", "abd");
        Job c = api.runJob(777, 888, jobVars);
        checkEntity(c);

        c = api.runJob(777, 888, jobVars, true);
        checkEntity(c);
    }

    public void testJobInformation() throws Exception {
        Job c = api.jobInformation(entityId);
        checkEntity(c);

        JobLog jobLog = c.getJobLog();
        checkJobLog(jobLog);
    }

    public void testWaitForStatus() throws Exception {
        Job c = api.jobInformation(entityId);
        checkEntity(c);

        c.waitForStatus(Xplenty.JobStatus.failed);

        try {
            c.waitForStatus(1L, Xplenty.JobStatus.running);
        } catch (XplentyAPIException ex) {
           assertEquals("Timeout occurred while waiting for required job status", ex.getMessage());
        }
    }

    public void testStopJob() throws Exception {
        Job c = api.stopJob(entityId);
        checkEntity(c);
    }

    public void testGetJobLog() throws Exception {
        JobLog c = api.getJobLog(entityId);
        checkJobLog(c);
    }

    public void testGetJobExecutionVariables() throws Exception {
        Map<String, String> c = api.getJobExecutionVariables(entityId);
        checkJobVariables(c);
    }

    public void testListJobs() throws Exception {
        List<Job> list = api.listJobs();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        checkEntity(list.get(0));

        list = api.listJobs(5, 90);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        checkEntity(list.get(0));

        Properties props = new Properties();
        props.put(ListJobs.PARAMETER_STATUS, Xplenty.JobStatus.pending);
        props.put(ListJobs.PARAMETER_INCLUDE, Xplenty.ListJobInclude.xpackage_and_cluster);
        list = api.listJobs(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        checkEntity(list.get(0));
    }

    public void testGetJobOutputPreview() throws Exception {
        //JobOutputPreview c = api.previewJobOutput(entityId, 1);
        // TODO check job output
    }

    public void testGetJobOutput() throws Exception {
        // todo
    }


    private void checkEntity(Job c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals(777, c.getClusterId().longValue());
        assertEquals(0, c.getOutputsCount().longValue());
        assertEquals(11, c.getOwnerId().longValue());
        assertEquals(888, c.getPackageId().longValue());
        assertEquals(96, c.getRuntimeInSeconds().longValue());
        assertEquals(1.0, c.getProgress());
        assertEquals("Package failed to execute.", c.getErrors());
        assertEquals(Xplenty.JobStatus.failed, c.getStatus());
        assertEquals(String.format("https://xplenty.com/%s/api/jobs/%s", accountID, entityId), c.getUrl());
        assertEquals(String.format("https://xplenty.com/%s/jobs/%s", accountID, entityId), c.getHtmlUrl());
        assertEquals(String.format("https://xplenty.com/%s/api/jobs/%s/log", accountID, entityId), c.getLogUrl());
        assertNotNull(c.getVariables());
        assertEquals("89", c.getVariables().get("a"));
        assertEquals(dFormat.parse("2016-02-26T14:22:53Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-02-26T14:24:35Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-02-26T14:22:59Z"), c.getStartedAt());
        assertEquals(dFormat.parse("2016-02-26T14:24:35Z"), c.getCompletedAt());
        assertEquals(dFormat.parse("2016-02-26T14:24:35Z"), c.getFailedAt());
        assertNotNull(c.getOutputs());
        assertTrue(c.getOutputs().size() == 0);
        Creator creator = c.getCreator();
        assertNotNull(creator);
        assertEquals(1, creator.getId().longValue());
        assertEquals("Alexey", creator.getDisplayName());
        assertEquals("User", creator.getType());
        assertEquals(String.format("https://xplenty.com/%s/api/members/1", accountID), creator.getUrl());
        assertEquals(String.format("https://xplenty.com/%s/settings/members/1", accountID), creator.getHtmlUrl());
    }

    private void checkJobLog(JobLog c) {
        assertNotNull(c);
        assertEquals("======== launcher  launcher  stdout.summary ========", c.getLog());
        assertEquals(String.format("https://xplenty.com/%s/api/jobs/%s/log", accountID, entityId), c.getUrl());
    }

    private void checkJobVariables(Map<String, String> c) {
        assertNotNull(c);
        assertEquals("test", c.get("a"));
        assertEquals("42", c.get("b"));
    }
}
