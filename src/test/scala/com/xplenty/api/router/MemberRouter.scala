package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class MemberRouter extends ScalatraServlet {

    post("/:accountId/api/members") {
      val accId = params("accountId")
      s"""{"id":387,"name":"member1","email":"test@xplenty.com","gravatar_email":"test@xplenty.com","created_at":"2016-01-13T16:44:20Z","updated_at":"2016-01-13T16:44:20Z","confirmed_at":null,"location":null,"avatar_url":"https://secure.gravatar.com/","role":"admin","owner":false,"url":"https://localhost/$accId/api/members/387","html_url":"https://localhost/$accId/settings/members/387","confirmed":false}"""
    }

  put("/:accountId/api/members/:memberId") {
    val accId = params("accountId")
    val memberId = params("memberId")
    s"""{"id":$memberId,"name":"member1","email":"test@xplenty.com","gravatar_email":"test@xplenty.com","created_at":"2016-01-13T16:44:20Z","updated_at":"2016-01-13T16:44:20Z","confirmed_at":"2016-01-13T16:44:20Z","location":"Moscow","avatar_url":"https://secure.gravatar.com/","role":"member","owner":false,"url":"https://localhost/$accId/api/members/$memberId","html_url":"https://localhost/$accId/settings/members/$memberId","confirmed":true}"""
  }

  get("/:accountId/api/members/:memberId") {
    val accId = params("accountId")
    val memberId = params("memberId")
    s"""{"id":$memberId,"name":"member1","email":"test@xplenty.com","gravatar_email":"test@xplenty.com","created_at":"2016-01-13T16:44:20Z","updated_at":"2016-01-13T16:44:20Z","confirmed_at":"2016-01-13T16:44:20Z","location":"Moscow","avatar_url":"https://secure.gravatar.com/","role":"admin","owner":false,"url":"https://localhost/$accId/api/members/$memberId","html_url":"https://localhost/$accId/settings/members/$memberId","confirmed":true}"""
  }

  get("/:accountId/api/members") {
    val accId = params("accountId")
    val memberId = 666
    s"""[{"id":$memberId,"name":"member1","email":"test@xplenty.com","gravatar_email":"test@xplenty.com","created_at":"2016-01-13T16:44:20Z","updated_at":"2016-01-13T16:44:20Z","confirmed_at":"2016-01-13T16:44:20Z","location":"Moscow","avatar_url":"https://secure.gravatar.com/","role":"admin","owner":false,"url":"https://localhost/$accId/api/members/$memberId","html_url":"https://localhost/$accId/settings/members/$memberId","confirmed":true}]"""
  }

    delete("/:accountId/api/members/:memberId") {
      val accId = params("accountId")
      val memberId = params("memberId")
      s"""{"id":$memberId,"name":"member1","email":"test@xplenty.com","gravatar_email":"test@xplenty.com","created_at":"2016-01-13T16:44:20Z","updated_at":"2016-01-13T16:44:20Z","confirmed_at":"2016-01-13T16:44:20Z","location":"Moscow","avatar_url":"https://secure.gravatar.com/","role":"admin","owner":false,"url":"https://localhost/$accId/api/members/$memberId","html_url":"https://localhost/$accId/settings/members/$memberId","confirmed":true}"""
    }
}
