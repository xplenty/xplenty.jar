package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class AccountRouter extends ScalatraServlet {

  post("/accounts") {
    s"""{"id":666,"name":"test","uname":"u_666","region":"gcloud::europe-west","location":"Private Drive","role":"admin","url":"https://localhost/accounts/superunique","account_id":"superunique","billing_email":"xardazz@github.com","gravatar_email":"gravatar@gravatar.com","avatar_url":"https://secure.gravatar.com","created_at":"2016-01-13T20:07:21Z","updated_at":"2016-01-13T20:07:21Z","schedules_count":12345678,"connections_count":123,"owner_id":111,"members_count":12345,"packages_count":123456,"jobs_count":1234,"running_jobs_count":1234567,"hooks_count" : 7,"public_key":"ssh-rsa AAAAAAA....AAAAAA Xplenty/superunique"}"""
  }

  put("/accounts/:entityId") {
    val entityId = params("entityId")
    s"""{"id":666,"name":"test","uname":"u_666","region":"gcloud::europe-west","location":"Private Drive","role":"admin","url":"https://localhost/accounts/$entityId","account_id":"$entityId","billing_email":"xardazz@github.com","gravatar_email":"gravatar@gravatar.com","avatar_url":"https://secure.gravatar.com","created_at":"2016-01-13T20:07:21Z","updated_at":"2016-01-13T20:07:21Z","schedules_count":12345678,"connections_count":123,"owner_id":111,"members_count":12345,"packages_count":123456,"jobs_count":1234,"running_jobs_count":1234567,"hooks_count" : 7,"public_key":"ssh-rsa AAAAAAA....AAAAAA Xplenty/$entityId"}"""
  }

  get("/accounts/:entityId") {
    val entityId = params("entityId")
    s"""{"id":666,"name":"test","uname":"u_666","region":"gcloud::europe-west","location":"Private Drive","role":"admin","url":"https://localhost/accounts/$entityId","account_id":"$entityId","billing_email":"xardazz@github.com","gravatar_email":"gravatar@gravatar.com","avatar_url":"https://secure.gravatar.com","created_at":"2016-01-13T20:07:21Z","updated_at":"2016-01-13T20:07:21Z","schedules_count":12345678,"connections_count":123,"owner_id":111,"members_count":12345,"packages_count":123456,"jobs_count":1234,"running_jobs_count":1234567,"hooks_count" : 7,"public_key":"ssh-rsa AAAAAAA....AAAAAA Xplenty/$entityId"}"""
  }

  get("/accounts") {
    s"""[{"id":666,"name":"test","uname":"u_666","region":"gcloud::europe-west","location":"Private Drive","role":"admin","url":"https://localhost/accounts/superunique","account_id":"superunique","billing_email":"xardazz@github.com","gravatar_email":"gravatar@gravatar.com","avatar_url":"https://secure.gravatar.com","created_at":"2016-01-13T20:07:21Z","updated_at":"2016-01-13T20:07:21Z","schedules_count":12345678,"connections_count":123,"owner_id":111,"members_count":12345,"packages_count":123456,"jobs_count":1234,"running_jobs_count":1234567,"hooks_count" : 7,"public_key":"ssh-rsa AAAAAAA....AAAAAA Xplenty/superunique"}]"""
  }

  delete("/accounts/:entityId") {
    val entityId = params("entityId")
    s"""{"id":666,"name":"test","uname":"u_666","region":"gcloud::europe-west","location":"Private Drive","role":"admin","url":"https://localhost/accounts/$entityId","account_id":"$entityId","billing_email":"xardazz@github.com","gravatar_email":"gravatar@gravatar.com","avatar_url":"https://secure.gravatar.com","created_at":"2016-01-13T20:07:21Z","updated_at":"2016-01-13T20:07:21Z","schedules_count":12345678,"connections_count":123,"owner_id":111,"members_count":12345,"packages_count":123456,"jobs_count":1234,"running_jobs_count":1234567,"hooks_count" : 7,"public_key":"ssh-rsa AAAAAAA....AAAAAA Xplenty/$entityId"}"""
  }

  get("/:accountId/api/regions") {
    s"""[{"name":"AWS - US East (N. Virginia)","group_name":"Amazon Web Services","id":"amazon-web-services::us-east-1"},{"name":"AWS - US West (N. California)","group_name":"Amazon Web Services","id":"amazon-web-services::us-west-1"},{"name":"AWS - US West (Oregon)","group_name":"Amazon Web Services","id":"amazon-web-services::us-west-2"},{"name":"AWS - EU (Ireland)","group_name":"Amazon Web Services","id":"amazon-web-services::eu-west-1"},{"name":"SoftLayer - Dallas 5 (DAL05)","group_name":"SoftLayer CloudLayer","id":"soft-layer::dal05"},{"name":"SoftLayer - Amsterdam 1 (AMS01)","group_name":"SoftLayer CloudLayer","id":"soft-layer::ams01"},{"name":"SoftLayer - Singapore 1 (SNG01)","group_name":"SoftLayer CloudLayer","id":"soft-layer::sng01"},{"name":"AWS - Asia Pacific (Singapore)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-southeast-1"},{"name":"AWS - Asia Pacific (Sydney)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-southeast-2"},{"name":"AWS - Asia Pacific (Tokyo)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-northeast-1"},{"name":"AWS - South America (S\u00e3o Paulo)","group_name":"Amazon Web Services","id":"amazon-web-services::sa-east-1"},{"name":"Google Cloud - East Asia","group_name":"Google Cloud","id":"gcloud::asia-east1"},{"name":"Google Cloud - Western Europe","group_name":"Google Cloud","id":"gcloud::europe-west1"},{"name":"Google Cloud - Central US","group_name":"Google Cloud","id":"gcloud::us-central1"}]"""
  }
}
