/**
 * 
 */
package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.JobStatus;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

/**
 * @author Yuriy Kovalek
 *
 */
public class JobTest extends TestCase {
	@Test
	public void testBuilder() {
		Job j = new Job().onCluster(1)
							.withPackage(8)
							.withId(7);
		
		assertNotNull(j);
		assertEquals(new Long(1), j.getClusterId());
		assertEquals(new Long(7), j.getId());
		assertEquals(new Long(8), j.getPackageId());
		assertNull(j.getVariables());
	}
	
	public static Job createMockJob(Date now) {
		Job j = new Job();
		j.clusterId = (long)1;
		j.id = (long)7;
		j.packageId = (long)8;
		j.variables = new HashMap<String, String>();
		j.createdAt = now;
		j.startedAt = now;
		j.updatedAt = now;
		j.completedAt = now;
		j.failedAt = now;
		j.ownerId = (long)2;
		j.errors = "";
		j.outputsCount = 3;
		j.outputs = Arrays.asList(createMockJobOutput(now));
		j.runtimeInSeconds = (long)333;
		j.progress = 50.0;
		j.status = JobStatus.running;
		j.url = "https://www.xplenty.com/api/" + Xplenty.Resource.Job.format(Long.toString(j.id));
		
		return j;
	}
	
	public static JobOutput createMockJobOutput(Date now) {
		JobOutput jo = new JobOutput();
		jo.id = 1L;
		jo.name = "mock job output";
		jo.recordsCount = 2L;
		jo.createdAt = now;
		jo.updatedAt = now;
        JobOutput.Component co = new JobOutput.Component();
        co.name = "dest1";
        co.type = "cloud_storage";
        List<String> fld = new ArrayList<>();
        fld.add("ddd");
        fld.add("bbb");
        co.fields = fld;
		jo.component = co;
		jo.previewUrl = "https://xplenty.com/api/jobs/7/outputs/777/preview";
        jo.previewType = "json";
		
		return jo;
	}

    public static JobLog createMockJobLog() {
        JobLog jl = new JobLog();
        jl.log = "Invalid output path: couldn't fetch";
        jl.url = "https://testapi.xplenty.com/api/jobs/666/log";
        return jl;
    }

    public static Map<String, String> createMockJobExecutionVariables() {
        Map<String, String> vars = new HashMap<>();
        vars.put("_ACCOUNT_ID", "666");
        vars.put("_ACCOUNT_ID2", "777");
        return vars;
    }

    public static JobOutputPreview createMockJobOutputPreview() {
        JobOutputPreview jop = new JobOutputPreview();
        jop.preview = "out1\tout2\tout3";
        jop.url = "https://testapi.xplenty.com/api/jobs/1/outputs/1/preview";
        return jop;
    }
}
