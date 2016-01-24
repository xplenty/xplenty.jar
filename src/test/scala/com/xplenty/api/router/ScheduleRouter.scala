package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 10:08
 */
class ScheduleRouter extends ScalatraServlet {

  post("/:accountId/api/schedules") {
    val accountId = params("accountId")
    s"""{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/666", "url":"https://localhost/$accountId/api/schedules/666","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}"""
  }

  post("/:accountId/api/schedules/:scheduleId/clone") {
    val accountId = params("accountId")
    val scheduleId = params("scheduleId")
    s"""{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/$scheduleId", "url":"https://localhost/$accountId/api/schedules/$scheduleId","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}"""
  }

  put("/:accountId/api/schedules/:scheduleId") {
    val accountId = params("accountId")
    val scheduleId = params("scheduleId")
    s"""{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/$scheduleId","url":"https://localhost/$accountId/api/schedules/$scheduleId","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}"""
  }

  get("/:accountId/api/schedules/:scheduleId") {
    val accountId = params("accountId")
    val scheduleId = params("scheduleId")
    s"""{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/$scheduleId","url":"https://localhost/$accountId/api/schedules/$scheduleId","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}"""
  }

  delete("/:accountId/api/schedules/:scheduleId") {
    val accountId = params("accountId")
    val scheduleId = params("scheduleId")
    s"""{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/$scheduleId","url":"https://localhost/$accountId/api/schedules/$scheduleId","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}"""
  }

  get("/:accountId/api/schedules") {
    val accountId = params("accountId")
    s"""[{"id":666,"name":"testSchedule","description":"some neat description","status":"enabled","html_url":"https://localhost/$accountId/schedules/666","url":"https://localhost/$accountId/api/schedules/666","task":{"nodes":3,"packages":[{"variables":{"var1":"varvalue"},"package_id":1}],"terminate_on_idle":true,"time_to_idle":1800},"owner_id":1,"start_at":"2016-01-24T10:07:42Z","next_run_at":"2016-01-24T10:07:42Z","interval_amount":10,"interval_unit":"days","last_run_at":"2016-01-24T10:07:42Z","last_run_status":"Successfully stored zillion records","execution_count":1,"created_at":"2016-01-24T10:07:42Z","updated_at":"2016-01-24T10:07:42Z", "reuse_cluster_strategy":"any","overlap":true}]"""
  }
}
