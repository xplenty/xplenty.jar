package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class UserRouter extends ScalatraServlet {

  put("/user") {
    s"""{"id":666,"name":"Vasya Pupkin","email":"test@xplenty.com","location":"Colorado","confirmed":true,"url":"https://api.xplenty.com/user","gravatar_email":"test@gravatar.com","avatar_url":"https://secure.gravatar.com/avatar/8318e89006033f0f8eec181f1fcec54e276.png","time_zone":"UTC","confirmed_at":"2016-01-17T19:41:12Z","notifications_count":7,"unread_notifications_count":3,"notification_settings":{"email":true,"web":false},"receive_newsletter":true,"created_at":"2016-01-17T19:41:12Z","updated_at":"2016-01-17T19:41:12Z","api_key":null,"last_login":"2016-01-17T19:41:12Z"}"""
  }

  get("/user") {
    val json = request.body
    val apiKey = if (json.contains("\"current_password\":\"supersecretpassword\"")) "\"yepitsapikey\"" else "null"
    s"""{"id":666,"name":"Vasya Pupkin","email":"test@xplenty.com","location":"Colorado","confirmed":true,"url":"https://api.xplenty.com/user","gravatar_email":"test@gravatar.com","avatar_url":"https://secure.gravatar.com/avatar/8318e89006033f0f8eec181f1fcec54e276.png","time_zone":"UTC","confirmed_at":"2016-01-17T19:41:12Z","notifications_count":7,"unread_notifications_count":3,"notification_settings":{"email":true,"web":false},"receive_newsletter":true,"created_at":"2016-01-17T19:41:12Z","updated_at":"2016-01-17T19:41:12Z","api_key":$apiKey,"last_login":"2016-01-17T19:41:12Z"}"""
  }

  get("/user/notifications") {
    s"""[{"id":777,"title":"Cluster available","message":"Cluster is available","last_read_at":"2016-01-17T19:42:18Z","created_at":"2016-01-17T19:42:18Z","updated_at":"2016-01-17T19:42:18Z"}]"""
  }

  post("/user/notifications/mark") {
    """"""
  }

  post("/user_password") {
    halt(201)
  }
}
