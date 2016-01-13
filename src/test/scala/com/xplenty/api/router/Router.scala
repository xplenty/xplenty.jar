package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Created with IntelliJ IDEA.
 * User: alexanderdomeshek
 * Date: 5/16/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
class Router extends ScalatraServlet{

    get("/:accountID/api/clusters/:cluster_id/watchers"){
       """[{"id":1,"display_name":"Xplenty"}]"""
    }

    post("/:accountID/api/clusters/:cluster_id/watchers"){
        """{"created_at":"2013-04-09T11:19:20Z","cluster_url":"https://api.xplenty.com/xplenation/api/clusters/370"}"""
    }

    delete("/:accountID/api/clusters/:cluster_id/watchers"){
        response.setStatus(204)
    }

    get("/:accountID/api/jobs/:cluster_id/watchers"){
        """[{"id":1,"display_name":"Xplenty - Job watcher"}]"""
    }

    post("/:accountID/api/jobs/:cluster_id/watchers"){
        """{"created_at":"2013-04-09T11:19:20Z","job_url":"https://api.xplenty.com/xplenation/api/jobs/370"}"""
    }

    delete("/:accountID/api/jobs/:cluster_id/watchers"){
        response.setStatus(204)
    }
}
