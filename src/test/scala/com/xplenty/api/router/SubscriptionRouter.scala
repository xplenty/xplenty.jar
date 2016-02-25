package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class SubscriptionRouter extends ScalatraServlet {

  get("/:accountId/api/plans") {
    """[{"id":"expert--7","name":"Expert","description":"Expert","position":7,"price_cents":199900,"price_currency":"USD","price_unit":"month","cluster_node_hours_included":0,"cluster_node_hours_limit":-1,"cluster_node_price_cents":71,"cluster_node_price_currency":"USD","cluster_node_price_unit":"hour","cluster_nodes_limit":32,"cluster_size_limit":0,"clusters_limit":4096,"sandbox_clusters_limit":1,"sandbox_node_hours_included":0,"sandbox_node_hours_limit":-1,"members_limit":4096,"created_at":"2016-02-26T04:51:47Z","updated_at":"2016-02-26T04:51:47Z"}]"""
  }

  get("/:accountId/api/payment_method") {
    """{"url":"https://testapi.xplenty.com/api/payment_method","card_last_4":9876,"card_number":"xxxx-xxxx-xxxx-9876","expiration_date":"06/66","card_type":"MasterCard"}"""
  }

  get("/:accountId/api/subscription") {
     """{"trial_period_days":14,"plan_id":"shai-shy","trial_start":"2015-11-05T15:12:00Z","trial_end":"2015-11-19T15:12:00Z","trialling":false,"url":"https://xplenty.com/xardazz/api/subscription"}"""
  }

  put("/:accountId/api/payment_method") {
    """{"url":"https://testapi.xplenty.com/api/payment_method","card_last_4":9876,"card_number":"xxxx-xxxx-xxxx-9876","expiration_date":"06/66","card_type":"MasterCard"}"""
  }
}
