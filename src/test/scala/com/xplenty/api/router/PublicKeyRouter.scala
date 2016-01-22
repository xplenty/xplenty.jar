package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class PublicKeyRouter extends ScalatraServlet {

  post("/user/keys") {
    s"""{"id":33,"comment":"xardazz@github.com","name":"Test","fingerprint":"ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff","created_at":"2016-01-06T20:05:21Z","updated_at":"2016-01-06T20:05:21Z","url":"https://localhost/user/keys/33"}"""
  }

  get("/user/keys/:keyId") {
    val keyId = params("keyId")
    s"""{"id":$keyId,"comment":"xardazz@github.com","name":"Test","fingerprint":"ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff","created_at":"2016-01-06T20:05:21Z","updated_at":"2016-01-06T20:05:21Z","url":"https://localhost/user/keys/$keyId"}"""
  }

  get("/user/keys") {
    s"""[{"id":33,"comment":"xardazz@github.com","name":"Test","fingerprint":"ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff","created_at":"2016-01-06T20:05:21Z","updated_at":"2016-01-06T20:05:21Z","url":"https://localhost/user/keys/33"}]"""
  }

  delete("/user/keys/:keyId") {
    val keyId = params("keyId")
    s"""{"id":$keyId,"comment":"xardazz@github.com","name":"Test","fingerprint":"ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff","created_at":"2016-01-06T20:05:21Z","updated_at":"2016-01-06T20:05:21Z","url":"https://localhost/user/keys/$keyId"}"""
  }
}
