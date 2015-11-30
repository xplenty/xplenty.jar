/**
 * 
 */
package com.xplenty.api.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.JobStatus;

import junit.framework.TestCase;

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
		jo.componentName = "mock component name";
		jo.previewUrl = "http://example.com/mockuoutput.txt";
		
		return jo;
	}
}
