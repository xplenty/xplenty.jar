package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class ConnectionRouter extends ScalatraServlet {

  get("/:accountId/api/connections") {
    """[{"id":"33","name":"Salesforce Connection","created_at":"2015-11-08T11:47:16Z","updated_at":"2016-01-15T08:15:50Z","type":"salesforce"},{"id":"388","name":"MySCP","created_at":"2016-01-08T17:46:37Z","updated_at":"2016-02-09T17:11:02Z","type":"sftp"}]"""
  }

  get("/:accountId/api/connections/:type/:connectionId") {
    val xtype = params("type")
    val connectionId = params("connectionId")
    s"""{"id":"$connectionId","name":"Salesforce Connection","created_at":"2015-11-08T11:47:16Z","updated_at":"2016-01-15T08:15:50Z","type":"$xtype"}"""
  }

  get("/:accountId/api/connections/types") {
    """[{"type":"adwords","name":"Google AdWords","description":"Google (PPC) Pay-Per-Click online advertising service","icon_url":"https://xplenty.com/assets/vendor/google-words-66f0580dec1f5c82432c95adf2bdf20d.png","groups":[{"group_type":"services","group_name":"Services"}]}]"""
  }

  delete("/:accountId/api/connections/:type/:connectionId") {
    val xtype = params("type")
    val connectionId = params("connectionId")
    s"""{"id":"$connectionId","name":"Salesforce Connection","created_at":"2015-11-08T11:47:16Z","updated_at":"2016-01-15T08:15:50Z","type":"$xtype"}"""
  }

}
