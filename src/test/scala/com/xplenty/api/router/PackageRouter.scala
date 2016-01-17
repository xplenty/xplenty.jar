package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class PackageRouter extends ScalatraServlet {

  post("/:accountId/api/packages") {
    val accountId = params("accountId")
    s"""{"id":666,"name":"TestPack","description":"TestPack Description","variables":{"var_1":"val1","var_2":"supercomplex"},"url":"https://localhost/$accountId/api/package/666","status":"active","owner_id":111,"created_at":"2016-01-14T20:25:12Z","updated_at":"2016-01-14T20:25:12Z","html_url":"https://localhost/$accountId/package/666","flow_type":"workflow"}"""
  }

  put("/:accountId/api/packages/:entityId") {
    val accountId = params("accountId")
    val entityId = params("entityId")
    s"""{"id":$entityId,"name":"TestPack","description":"TestPack Description","variables":{"var_1":"val1","var_2":"supercomplex"},"url":"https://localhost/$accountId/api/package/$entityId","status":"active","owner_id":111,"created_at":"2016-01-14T20:25:12Z","updated_at":"2016-01-14T20:25:12Z","html_url":"https://localhost/$accountId/package/$entityId","flow_type":"workflow"}"""
  }

  get("/:accountId/api/packages/:entityId") {
    val accountId = params("accountId")
    val entityId = params("entityId")
    s"""{"id":$entityId,"name":"TestPack","description":"TestPack Description","variables":{"var_1":"val1","var_2":"supercomplex"},"url":"https://localhost/$accountId/api/package/$entityId","status":"active","owner_id":111,"created_at":"2016-01-14T20:25:12Z","updated_at":"2016-01-14T20:25:12Z","html_url":"https://localhost/$accountId/package/$entityId","flow_type":"workflow"}"""
  }

  get("/:accountId/api/packages") {
    val accountId = params("accountId")
    s"""[{"id":666,"name":"TestPack","description":"TestPack Description","variables":{"var_1":"val1","var_2":"supercomplex"},"url":"https://localhost/$accountId/api/package/666","status":"active","owner_id":111,"created_at":"2016-01-14T20:25:12Z","updated_at":"2016-01-14T20:25:12Z","html_url":"https://localhost/$accountId/package/666","flow_type":"workflow"}]"""
  }

  delete("/:accountId/api/packages/:entityId") {
    val accountId = params("accountId")
    val entityId = params("entityId")
    s"""{"id":$entityId,"name":"TestPack","description":"TestPack Description","variables":{"var_1":"val1","var_2":"supercomplex"},"url":"https://localhost/$accountId/api/package/$entityId","status":"active","owner_id":111,"created_at":"2016-01-14T20:25:12Z","updated_at":"2016-01-14T20:25:12Z","html_url":"https://localhost/$accountId/package/$entityId","flow_type":"workflow"}"""
  }

  get("/:accountId/api/packages/templates") {
    val accountId = params("accountId")
    s"""[{"id":333,"name":"test template","description":"really good template","position":1,"author":{"id":333,"name":"best template author","avatar_url":"https://localhost/$accountId/api/user/333"}}]"""
  }

  post("/:accountId/api/packages/:packageId/validations") {
    val accountId = params("accountId")
    val packageId = params("packageId")
    s"""{"id":666,"status":"failed","runtime":1234,"errors":[{"message":"couldn't obtain value for var_1","component_id":"12"}],"url":"https://localhost/$accountId/api/packages/$packageId/validations/666","status_message":"Something bad happened","package_id":$packageId,"owner_id":222,"account_id":111,"created_at":"2016-01-14T20:34:27Z","updated_at":"2016-01-14T20:34:27Z"}"""
  }

  get("/:accountId/api/packages/:packageId/validations") {
    val accountId = params("accountId")
    val packageId = params("packageId")
    s"""[{"id":666,"status":"failed","runtime":1234,"errors":[{"message":"couldn't obtain value for var_1","component_id":"12"}],"url":"https://localhost/$accountId/api/packages/$packageId/validations/666","status_message":"Something bad happened","package_id":$packageId,"owner_id":222,"account_id":111,"created_at":"2016-01-14T20:34:27Z","updated_at":"2016-01-14T20:34:27Z"}]"""
  }

  get("/:accountId/api/packages/:packageId/validations/:entityId") {
    val accountId = params("accountId")
    val packageId = params("packageId")
    val entityId = params("entityId")
    s"""{"id":$entityId,"status":"failed","runtime":1234,"errors":[{"message":"couldn't obtain value for var_1","component_id":"12"}],"url":"https://localhost/$accountId/api/packages/$packageId/validations/$entityId","status_message":"Something bad happened","package_id":$packageId,"owner_id":222,"account_id":111,"created_at":"2016-01-14T20:34:27Z","updated_at":"2016-01-14T20:34:27Z"}"""
  }

}
