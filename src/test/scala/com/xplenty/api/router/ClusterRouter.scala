package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author $accountId
 */
class ClusterRouter extends ScalatraServlet {

  post("/:accountId/api/clusters") {
    val accountId = params("accountId")
    s"""{"id":666,"name":"brown-sea-277","description":"sea is brown in this time of the year","status":"terminated","owner_id":352,"plan_id":"pro","nodes":7,"type":"production","created_at":"2016-01-22T13:22:20Z","updated_at":"2016-01-22T14:33:03Z","available_since":"2016-01-22T13:31:07Z","terminated_at":"2016-01-22T14:33:02Z","launched_at":"2016-01-22T13:31:06Z","terminate_on_idle":true,"time_to_idle":3600,"terminated_on_idle":true,"region":"amazon-web-services::eu-west-1","zone":null,"master_instance_type":null,"slave_instance_type":null,"master_spot_price":null,"slave_spot_price":null,"master_spot_percentage":null,"slave_spot_percentage":null,"allow_fallback":true,"stack":"white-everest","idle_since":"2016-01-22T14:33:02Z","running_jobs_count":0,"url":"https://localhost/$accountId/api/clusters/666","html_url":"https://localhost/$accountId/clusters/666","creator":{"type":"User","id":352,"display_name":"Alexey Gromov","html_url":"https://localhost/$accountId/settings/members/352","url":"https://localhost/$accountId/api/members/352"}, "bootstrap_actions" : [{"script_path" : "my/super/script.sh", "args" : ["arg1"]}]}"""
  }

  put("/:accountId/api/clusters/:clusterId") {
    val accountId = params("accountId")
    val clusterId = params("clusterId")
    s"""{"id":$clusterId,"name":"brown-sea-277","description":"sea is brown in this time of the year","status":"terminated","owner_id":352,"plan_id":"pro","nodes":7,"type":"production","created_at":"2016-01-22T13:22:20Z","updated_at":"2016-01-22T14:33:03Z","available_since":"2016-01-22T13:31:07Z","terminated_at":"2016-01-22T14:33:02Z","launched_at":"2016-01-22T13:31:06Z","terminate_on_idle":true,"time_to_idle":3600,"terminated_on_idle":true,"region":"amazon-web-services::eu-west-1","zone":null,"master_instance_type":null,"slave_instance_type":null,"master_spot_price":null,"slave_spot_price":null,"master_spot_percentage":null,"slave_spot_percentage":null,"allow_fallback":true,"stack":"white-everest","idle_since":"2016-01-22T14:33:02Z","running_jobs_count":0,"url":"https://localhost/$accountId/api/clusters/$clusterId","html_url":"https://localhost/$accountId/clusters/$clusterId","creator":{"type":"User","id":352,"display_name":"Alexey Gromov","html_url":"https://localhost/$accountId/settings/members/352","url":"https://localhost/$accountId/api/members/352"}, "bootstrap_actions" : [{"script_path" : "my/super/script.sh", "args" : ["arg1"]}]}"""
  }

  get("/:accountId/api/clusters/:clusterId") {
    val accountId = params("accountId")
    val clusterId = params("clusterId")
    s"""{"id":$clusterId,"name":"brown-sea-277","description":"sea is brown in this time of the year","status":"terminated","owner_id":352,"plan_id":"pro","nodes":7,"type":"production","created_at":"2016-01-22T13:22:20Z","updated_at":"2016-01-22T14:33:03Z","available_since":"2016-01-22T13:31:07Z","terminated_at":"2016-01-22T14:33:02Z","launched_at":"2016-01-22T13:31:06Z","terminate_on_idle":true,"time_to_idle":3600,"terminated_on_idle":true,"region":"amazon-web-services::eu-west-1","zone":null,"master_instance_type":null,"slave_instance_type":null,"master_spot_price":null,"slave_spot_price":null,"master_spot_percentage":null,"slave_spot_percentage":null,"allow_fallback":true,"stack":"white-everest","idle_since":"2016-01-22T14:33:02Z","running_jobs_count":0,"url":"https://localhost/$accountId/api/clusters/$clusterId","html_url":"https://localhost/$accountId/clusters/$clusterId","creator":{"type":"User","id":352,"display_name":"Alexey Gromov","html_url":"https://localhost/$accountId/settings/members/352","url":"https://localhost/$accountId/api/members/352"}, "bootstrap_actions" : [{"script_path" : "my/super/script.sh", "args" : ["arg1"]}]}"""
  }

  delete("/:accountId/api/clusters/:clusterId") {
    val accountId = params("accountId")
    val clusterId = params("clusterId")
    s"""{"id":$clusterId,"name":"brown-sea-277","description":"sea is brown in this time of the year","status":"terminated","owner_id":352,"plan_id":"pro","nodes":7,"type":"production","created_at":"2016-01-22T13:22:20Z","updated_at":"2016-01-22T14:33:03Z","available_since":"2016-01-22T13:31:07Z","terminated_at":"2016-01-22T14:33:02Z","launched_at":"2016-01-22T13:31:06Z","terminate_on_idle":true,"time_to_idle":3600,"terminated_on_idle":true,"region":"amazon-web-services::eu-west-1","zone":null,"master_instance_type":null,"slave_instance_type":null,"master_spot_price":null,"slave_spot_price":null,"master_spot_percentage":null,"slave_spot_percentage":null,"allow_fallback":true,"stack":"white-everest","idle_since":"2016-01-22T14:33:02Z","running_jobs_count":0,"url":"https://localhost/$accountId/api/clusters/$clusterId","html_url":"https://localhost/$accountId/clusters/$clusterId","creator":{"type":"User","id":352,"display_name":"Alexey Gromov","html_url":"https://localhost/$accountId/settings/members/352","url":"https://localhost/$accountId/api/members/352"}, "bootstrap_actions" : [{"script_path" : "my/super/script.sh", "args" : ["arg1"]}]}"""
  }

  get("/:accountId/api/clusters") {
    val accountId = params("accountId")
    s"""[{"id":666,"name":"brown-sea-277","description":"sea is brown in this time of the year","status":"terminated","owner_id":352,"plan_id":"pro","nodes":7,"type":"production","created_at":"2016-01-22T13:22:20Z","updated_at":"2016-01-22T14:33:03Z","available_since":"2016-01-22T13:31:07Z","terminated_at":"2016-01-22T14:33:02Z","launched_at":"2016-01-22T13:31:06Z","terminate_on_idle":true,"time_to_idle":3600,"terminated_on_idle":true,"region":"amazon-web-services::eu-west-1","zone":null,"master_instance_type":null,"slave_instance_type":null,"master_spot_price":null,"slave_spot_price":null,"master_spot_percentage":null,"slave_spot_percentage":null,"allow_fallback":true,"stack":"white-everest","idle_since":"2016-01-22T14:33:02Z","running_jobs_count":0,"url":"https://localhost/$accountId/api/clusters/666","html_url":"https://localhost/$accountId/clusters/666","creator":{"type":"User","id":352,"display_name":"Alexey Gromov","html_url":"https://localhost/$accountId/settings/members/352","url":"https://localhost/$accountId/api/members/352"}, "bootstrap_actions" : [{"script_path" : "my/super/script.sh", "args" : ["arg1"]}]}]"""
  }

  get("/:accountId/api/clusters/:clusterId/instances") {
    val accountId = params("accountId")
    val clusterId = params("clusterId")
    s"""[{"status":"available","master":true,"spot":false,"vpc":false,"zone":"eu-west1","url":"https://localhost/$accountId/api/clusters/$clusterId/instances/i-4d1b39a7","instance_id":"i-4d1b39a7","private_dns":"ip-10-124-29-23.ec2.internal","public_dns":"ec2-55-27-210-201.compute-1.amazonaws.com","instance_type":"sometype"}]"""
  }
}
