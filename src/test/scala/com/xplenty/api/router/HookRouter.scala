package com.xplenty.api.router

import com.fasterxml.jackson.databind.{ObjectMapper, JsonNode}
import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class HookRouter extends ScalatraServlet {

  post("/:accountId/api/hooks") {
    val accountId = params("accountId")
    val json = request.body
    val jsonNode: JsonNode = new ObjectMapper().readTree(json)
    val hookType = jsonNode.get("type").asText()
    val settings = hookType match {
        case "email"  => """{"emails":"a@a.com,b@b.com"}"""
        case "hipchat"  => """{"room":"xplenty","auth_token":"aaaa"}"""
        case "pagerduty"  => """{"pd_account":"xplenty1","service_name":"xplenty","service_key":"aaaaaa"}"""
        case "slack"  => """{"team":"xplenty","channel":"xplentych","url":"http://localhost", "username" : "xardazz"}"""
        case "web" => """{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"}"""
    }

    s"""{"id":666,"active":true,"type" : "$hookType","settings": $settings,"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}"""
  }

  put("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}"""
  }

  delete("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}"""
  }

  get("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}"""
  }

  get("/:accountId/api/hooks") {
    val accountId = params("accountId")
    s"""[{"id":666,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}]"""
  }

  get("/:accountId/api/hooks/:hookId/ping") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":[{"id" : 333, "name" : "job", "last_response" : {"code": "200", "body" : "nice event"}, "last_trigger_status" : "success", "last_trigger_time" : "2016-01-18T11:19:20Z"}]}"""
  }

  put("/:accountId/api/hooks/:hookId/reset_salt") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"salt" : "newsalt"}"""
  }

  get("/hook_events") {
    s"""[{"id":"job","group_name":"Job","name":"All Job Notifications"},{"id":"job.submitted","group_name":"Job","name":"Job Submitted"},{"id":"job.started","group_name":"Job","name":"Job Started"},{"id":"job.stopped","group_name":"Job","name":"Job Stopped"},{"id":"job.completed","group_name":"Job","name":"Job Completed"},{"id":"job.failed","group_name":"Job","name":"Job Failed"},{"id":"cluster","group_name":"Cluster","name":"All Cluster Notifications"},{"id":"cluster.requested","group_name":"Cluster","name":"Cluster Requested"},{"id":"cluster.available","group_name":"Cluster","name":"Cluster Available"},{"id":"cluster.terminated","group_name":"Cluster","name":"Cluster Terminated"},{"id":"cluster.idled","group_name":"Cluster","name":"Cluster Idled"},{"id":"cluster.error","group_name":"Cluster","name":"Cluster Error"}]"""
  }

}
