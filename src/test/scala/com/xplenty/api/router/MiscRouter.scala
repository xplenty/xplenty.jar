package com.xplenty.api.router

import org.scalatra.ScalatraServlet

/**
 * Mock server returning fixed values
 * @author xardazz
 */
class MiscRouter extends ScalatraServlet {

  get("/product_updates") {
    """[{"id":9,"title":"na na ","created_at":"2014-03-27T09:40:28Z","body":"momo","body_html":"<p>momo</p>","body_text":"momo","likes_count":3,"liked":false},{"id":8,"title":"na nah nahma nahman","created_at":"2014-03-27T09:38:20Z","body":"na nah nahma nahman","body_html":"<p>na nah nahma nahman</p>\n","body_text":"na nah nahma nahman\n","likes_count":0,"liked":false},{"id":7,"title":"new new new","created_at":"2014-03-27T09:37:12Z","body":"announcement","body_html":"<p>announcement</p>\n","body_text":"announcement\n","likes_count":2,"liked":true}]"""
  }

  post("/product_updates/:productUpdateId/like") {
    val productUpdateId = params("productUpdateId")
    s"""{"id":$productUpdateId,"title":"na na ","created_at":"2014-03-27T09:40:28Z","body":"momo","body_html":"<p>momo</p>","body_text":"momo","likes_count":3,"liked":false}"""
  }

  get("/regions") {
     """[{"name":"AWS - US East (N. Virginia)","group_name":"Amazon Web Services","id":"amazon-web-services::us-east-1"},{"name":"AWS - US West (N. California)","group_name":"Amazon Web Services","id":"amazon-web-services::us-west-1"},{"name":"AWS - US West (Oregon)","group_name":"Amazon Web Services","id":"amazon-web-services::us-west-2"},{"name":"AWS - EU (Ireland)","group_name":"Amazon Web Services","id":"amazon-web-services::eu-west-1"},{"name":"SoftLayer - Dallas 5 (DAL05)","group_name":"SoftLayer CloudLayer","id":"soft-layer::dal05"},{"name":"SoftLayer - Amsterdam 1 (AMS01)","group_name":"SoftLayer CloudLayer","id":"soft-layer::ams01"},{"name":"SoftLayer - Singapore 1 (SNG01)","group_name":"SoftLayer CloudLayer","id":"soft-layer::sng01"},{"name":"AWS - Asia Pacific (Singapore)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-southeast-1"},{"name":"AWS - Asia Pacific (Sydney)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-southeast-2"},{"name":"AWS - Asia Pacific (Tokyo)","group_name":"Amazon Web Services","id":"amazon-web-services::ap-northeast-1"},{"name":"AWS - South America (S\u00e3o Paulo)","group_name":"Amazon Web Services","id":"amazon-web-services::sa-east-1"},{"name":"Google Cloud - East Asia","group_name":"Google Cloud","id":"gcloud::asia-east1"},{"name":"Google Cloud - Western Europe","group_name":"Google Cloud","id":"gcloud::europe-west1"},{"name":"Google Cloud - Central US","group_name":"Google Cloud","id":"gcloud::us-central1"}]"""
  }

  get("/:accountId/api/stacks") {
    """[{"name":"Mint Everest","id":"mint-everest"},{"name":"White Everest","id":"white-everest"},{"name":"Lime Everest","id":"lime-everest"},{"name":"Blue Everest","id":"blue-everest"},{"name":"White Logan","id":"white-logan"},{"name":"Blue Logan","id":"blue-logan"}]"""
  }

  get("/variables") {
    """{"_MAX_COMBINED_SPLIT_SIZE":67108864,"_BYTES_PER_REDUCER":209715200,"_LINE_RECORD_READER_MAX_LENGTH":1048576,"_DEFAULT_TIMEZONE":"'+00:00'","_DEFAULT_PARALLELISM":0,"_SHUFFLE_INPUT_BUFFER_PERCENT":0.7,"_COPY_PARALLELISM":"(int)$_CLUSTER_NODES_COUNT * 3","_COPY_TARGET_PARTITIONS":"100","_COPY_TARGET_SIZE":"64","_PARQUET_COMPRESSION":"'UNCOMPRESSED'","_PARQUET_PAGE_SIZE":"1 * 1024 * 1024","_PARQUET_BLOCK_SIZE":"128 * 1024 * 1024","_GA_API_REQUEST_READ_TIMEOUT":"30 * 1000","_GA_API_REQUEST_MAX_RESULTS":1000,"_GA_API_MAX_INPUT_SPLITS":10,"_CACHED_BAG_MEMORY_PERCENT":0.2,"_MAP_MAX_ATTEMPTS":4,"_REDUCER_MAX_ATTEMPTS":4,"_MAP_MAX_FAILURES_PERCENT":0,"_REDUCER_MAX_FAILURES_PERCENT":0,"_MAP_TASK_TIMEOUT":"600 * 1000"}"""
  }

  get("/timezones") {
     """[{"name":"(GMT+03:00) St. Petersburg","id":"St. Petersburg"},{"name":"(GMT+03:00) Volgograd","id":"Volgograd"},{"name":"(GMT+03:30) Tehran","id":"Tehran"}]"""
  }

}
