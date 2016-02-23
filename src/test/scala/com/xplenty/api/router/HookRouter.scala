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

    s"""{"id":666,"active":true,"type" : "$hookType","settings": $settings,"salt":"000abcdead","events":[ "job", "cluster"]}"""
  }

  put("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":["job", "cluster"]}"""
  }

  delete("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":["job", "cluster"]}"""
  }

  get("/:accountId/api/hooks/:hookId") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":["job", "cluster"]}"""
  }

  get("/:accountId/api/hooks") {
    val accountId = params("accountId")
    s"""[{"id":666,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":["job", "cluster"]}]"""
  }

  get("/:accountId/api/hooks/:hookId/ping") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"id":$hookId,"active":true,"type" : "web","settings":{"url":"http://localhost/test","insecure_ssl":true,"basic_auth":false,"basic_auth_data":"somedata","encrypted_basic_auth_data":"wtjnIcvVp1fLC2fy9rAsSQ==\\n"},"salt":"000abcdead","events":["job", "cluster"]}"""
  }

  put("/:accountId/api/hooks/:hookId/reset_salt") {
    val accountId = params("accountId")
    val hookId = params("hookId")
    s"""{"salt" : "newsalt"}"""
  }

  get("/hook_events") {
    s"""[{"id":"job","group_name":"Job","name":"All Job Notifications"},{"id":"job.submitted","group_name":"Job","name":"Job Submitted"},{"id":"job.started","group_name":"Job","name":"Job Started"},{"id":"job.stopped","group_name":"Job","name":"Job Stopped"},{"id":"job.completed","group_name":"Job","name":"Job Completed"},{"id":"job.failed","group_name":"Job","name":"Job Failed"},{"id":"cluster","group_name":"Cluster","name":"All Cluster Notifications"},{"id":"cluster.requested","group_name":"Cluster","name":"Cluster Requested"},{"id":"cluster.available","group_name":"Cluster","name":"Cluster Available"},{"id":"cluster.terminated","group_name":"Cluster","name":"Cluster Terminated"},{"id":"cluster.idled","group_name":"Cluster","name":"Cluster Idled"},{"id":"cluster.error","group_name":"Cluster","name":"Cluster Error"}]"""
  }

  get("/:accountId/api/hooks/types") {
    """[{"type":"email","name":"Email","description":"Our Email integration enables you to receive real-time email alerts about your account activity.","icon_url":"http://api.xplenty.com/assets/vendor/hooks/emailhook-9231bb4b71377e2722ceb6b581ecfaf4.png","groups":[{"group_type":"email","group_name":"Email"}]},{"type":"hipchat","name":"HipChat","description":"Our HipChat integration enables you to receive real-time updates about your account activity into your HipChat rooms.","icon_url":"http://api.xplenty.com/assets/vendor/hooks/hipchathook-33f9ece2a1e6b908c0dec300fb354a5e.png","groups":[{"group_type":"chat","group_name":"Chat"}]},{"type":"pagerduty","name":"PagerDuty","description":"Our PagerDuty integration enables you to receive real-time alerts about your account activity.","icon_url":"https://assets-staging%d.xplenty.comhttps://assets-staging3.xplenty.com/assets/vendor/hooks/pagerdutyhook-1caec2d08c4889dd8543646030dc8f87.png","groups":[{"group_type":"monitoring","group_name":"Monitoring"}]},{"type":"slack","name":"Slack","description":"Our Slack integration enables you to receive real-time updates about your account activity into your team inbox.","icon_url":"https://assets-staging%d.xplenty.comhttps://assets-staging3.xplenty.com/assets/vendor/hooks/slackhook-f083840da32fc8823c121eb13df461cb.png","groups":[{"group_type":"chat","group_name":"Chat"}]},{"type":"web","name":"Webhook","description":"Webhooks enable you to receive real-time updates about your account activity from Xplenty","icon_url":"https://assets-staging%d.xplenty.comhttps://assets-staging1.xplenty.com/assets/vendor/hooks/webhook-61737582c291878603bacbf9b6c707e0.png","groups":[{"group_type":"web","group_name":"Web"}]}]"""
  }

}
