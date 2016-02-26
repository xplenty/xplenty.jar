package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class JobRouter extends ScalatraServlet {

  get("/:accountId/api/jobs") {
    val accountId = params("accountId")
    s"""[{"id":666,"status":"failed","variables":{"a" : 89},"owner_id":11,"progress":1.0,"outputs_count":0,"started_at":"2016-02-26T14:22:59Z","completed_at":"2016-02-26T14:24:35Z","failed_at":"2016-02-26T14:24:35Z","created_at":"2016-02-26T14:22:53Z","updated_at":"2016-02-26T14:24:35Z","cluster_id":777,"package_id":888,"errors":"Package failed to execute.","url":"https://xplenty.com/$accountId/api/jobs/666","html_url":"https://xplenty.com/$accountId/jobs/666","creator":{"type":"User","id":1,"display_name":"Alexey","html_url":"https://xplenty.com/$accountId/settings/members/1","url":"https://xplenty.com/$accountId/api/members/1"},"log_url":"https://xplenty.com/$accountId/api/jobs/666/log","outputs":[],"runtime_in_seconds":96}]"""
  }

  post("/:accountId/api/jobs") {
    val accountId = params("accountId")
    s"""{"id":666,"status":"failed","variables":{"a" : 89},"owner_id":11,"progress":1.0,"outputs_count":0,"started_at":"2016-02-26T14:22:59Z","completed_at":"2016-02-26T14:24:35Z","failed_at":"2016-02-26T14:24:35Z","created_at":"2016-02-26T14:22:53Z","updated_at":"2016-02-26T14:24:35Z","cluster_id":777,"package_id":888,"errors":"Package failed to execute.","url":"https://xplenty.com/$accountId/api/jobs/666","html_url":"https://xplenty.com/$accountId/jobs/666","creator":{"type":"User","id":1,"display_name":"Alexey","html_url":"https://xplenty.com/$accountId/settings/members/1","url":"https://xplenty.com/$accountId/api/members/1"},"log_url":"https://xplenty.com/$accountId/api/jobs/666/log","outputs":[],"runtime_in_seconds":96}"""
  }

  get("/:accountId/api/jobs/:jobId") {
    val accountId = params("accountId")
    val jobId = params("jobId")
    s"""{"id":$jobId,"status":"failed","variables":{"a" : 89},"owner_id":11,"progress":1.0,"outputs_count":0,"started_at":"2016-02-26T14:22:59Z","completed_at":"2016-02-26T14:24:35Z","failed_at":"2016-02-26T14:24:35Z","created_at":"2016-02-26T14:22:53Z","updated_at":"2016-02-26T14:24:35Z","cluster_id":777,"package_id":888,"errors":"Package failed to execute.","url":"https://xplenty.com/$accountId/api/jobs/$jobId","html_url":"https://xplenty.com/$accountId/jobs/$jobId","creator":{"type":"User","id":1,"display_name":"Alexey","html_url":"https://xplenty.com/$accountId/settings/members/1","url":"https://xplenty.com/$accountId/api/members/1"},"log_url":"https://xplenty.com/$accountId/api/jobs/$jobId/log","outputs":[],"runtime_in_seconds":96}"""
  }

  delete("/:accountId/api/jobs/:jobId") {
    val accountId = params("accountId")
    val jobId = params("jobId")
    s"""{"id":$jobId,"status":"failed","variables":{"a" : 89},"owner_id":11,"progress":1.0,"outputs_count":0,"started_at":"2016-02-26T14:22:59Z","completed_at":"2016-02-26T14:24:35Z","failed_at":"2016-02-26T14:24:35Z","created_at":"2016-02-26T14:22:53Z","updated_at":"2016-02-26T14:24:35Z","cluster_id":777,"package_id":888,"errors":"Package failed to execute.","url":"https://xplenty.com/$accountId/api/jobs/$jobId","html_url":"https://xplenty.com/$accountId/jobs/$jobId","creator":{"type":"User","id":1,"display_name":"Alexey","html_url":"https://xplenty.com/$accountId/settings/members/1","url":"https://xplenty.com/$accountId/api/members/1"},"log_url":"https://xplenty.com/$accountId/api/jobs/$jobId/log","outputs":[],"runtime_in_seconds":96}"""
  }

  get("/:accountId/api/jobs/:jobId/log") {
    val accountId = params("accountId")
    val jobId = params("jobId")
    s"""{"body":"======== launcher  launcher  stdout.summary ========","url":"https://xplenty.com/$accountId/api/jobs/$jobId/log"}"""
  }

  get("/:accountId/api/jobs/:jobId/variables") {
    """{"a" : "test", "b" : 42}"""
  }

}
