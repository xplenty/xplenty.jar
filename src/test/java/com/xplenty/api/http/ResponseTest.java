package com.xplenty.api.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.exceptions.AuthFailedException;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.PublicKey;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 6:54
 */
public class ResponseTest extends TestCase {
    public void testValidate() {
        Response resp = new Response("abc", 403, new HashMap<String, String>()) {
            @Override
            public <T> T getContent(TypeReference<T> typeReference) throws XplentyAPIException {
                return null;
            }

            @Override
            public <T> T getContent(Class<T> typeReference) throws XplentyAPIException {
                return null;
            }
        };

        try {
            resp.validate("");
        } catch (Exception ex) {
            assertEquals(RequestFailedException.class, ex.getClass());
            RequestFailedException rfex = (RequestFailedException) ex;
            assertEquals(Http.ResponseStatus.HTTP_403.getCode(), rfex.getStatus());
            assertEquals(Http.ResponseStatus.HTTP_403.getDescription(), rfex.getStatusDescription());
        }

        resp = new Response("abc", 401, new HashMap<String, String>()) {
            @Override
            public <T> T getContent(TypeReference<T> typeReference) throws XplentyAPIException {
                return null;
            }

            @Override
            public <T> T getContent(Class<T> typeReference) throws XplentyAPIException {
                return null;
            }
        };

        try {
            resp.validate("");
        } catch (Exception ex) {
            assertEquals(AuthFailedException.class, ex.getClass());
            AuthFailedException rfex = (AuthFailedException) ex;
            assertEquals(Http.ResponseStatus.HTTP_401.getCode(), rfex.getStatus());
            assertEquals(Http.ResponseStatus.HTTP_401.getDescription(), rfex.getStatusDescription());
        }

        resp = new Response("abc", 200, new HashMap<String, String>()) {
            @Override
            public <T> T getContent(TypeReference<T> typeReference) throws XplentyAPIException {
                return null;
            }

            @Override
            public <T> T getContent(Class<T> typeReference) throws XplentyAPIException {
                return null;
            }
        };

        resp.validate("");
        assertTrue(resp.isValid());
    }

   public void testForContentType() {
       Response resp = Response.forContentType(Http.MediaType.JSON, "dd", 200, new HashMap<String, String>());
       assertNotNull(resp);
       assertEquals(JsonResponse.class, resp.getClass());
       assertTrue(resp.isValid());
       assertEquals("dd", resp.getRawContent());
       assertNotNull(resp.getHeaders());
       assertEquals(Http.ResponseStatus.HTTP_200, resp.getStatus());

       resp = Response.forContentType(Http.MediaType.JSON, "dd", 404, new HashMap<String, String>());
       assertFalse(resp.isValid());

   }

   public void testCheckTypedInfo() {
       Response resp = Response.forContentType(Http.MediaType.JSON, "dd", 200, new HashMap<String, String>());
       try {
           resp.checkTypedInfo(new TypeReference<List<Map<String, String>>>() {});
       } catch (Exception ex) {
           assertEquals(XplentyAPIException.class, ex.getClass());
           assertEquals(Response.INVALID_TYPE_INFORMATION, ex.getMessage());
       }

       try {
           resp.checkTypedInfo(new TypeReference<Map<Long, Long>>() {});
       } catch (Exception ex) {
           assertEquals(XplentyAPIException.class, ex.getClass());
           assertEquals(Response.INVALID_TYPE_INFORMATION, ex.getMessage());
       }

       try {
           resp.checkTypedInfo(new TypeReference<Object>() {});
       } catch (Exception ex) {
           assertEquals(XplentyAPIException.class, ex.getClass());
           assertEquals(Response.INVALID_TYPE_INFORMATION, ex.getMessage());
       }

       resp.checkTypedInfo(new TypeReference<List<Cluster>>() {});
       resp.checkTypedInfo(new TypeReference<Map<Cluster, String>>() {});
       resp.checkTypedInfo(new TypeReference<Map<String, Cluster>>() {});

   }

   public void testJsonResponse() {
       JsonResponse resp = new JsonResponse("{\"a\" : 9, \"b\": \"omg\"}", 200, new HashMap<String, String>());
       assertEquals("{\"a\" : 9, \"b\": \"omg\"}", resp.getRawContent());
       assertNotNull(resp.getHeaders());
       assertEquals(Http.ResponseStatus.HTTP_200, resp.getStatus());
       assertTrue(resp.isValid());
       resp.validate("test");
       Map<String, String> map = resp.getContent(new TypeReference<Map<String, String>>() {});
       assertNotNull(map);
       assertEquals("9", map.get("a"));
       assertEquals("omg", map.get("b"));
       try {
           resp.getContent(new TypeReference<Map<String, Cluster>>() {});
       } catch (Exception ex) {
           // json doesn't contain cluster object
           assertEquals(IllegalArgumentException.class, ex.getClass());
       }

       try {
           resp.getContent(new TypeReference<Map<String, Long>>() {});
       } catch (Exception ex) {
           assertEquals(XplentyAPIException.class, ex.getClass());
       }


       resp = new JsonResponse("{\"id\":33,\"comment\":\"xardazz@github.com\",\"name\":\"Test\",\"fingerprint\":\"ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff\",\"created_at\":\"2016-01-06T20:05:21Z\",\"updated_at\":\"2016-01-06T20:05:21Z\",\"url\":\"https://localhost/user/keys/33\"}", 200, new HashMap<String, String>());
       assertNotNull(resp.getHeaders());
       assertEquals(Http.ResponseStatus.HTTP_200, resp.getStatus());
       assertTrue(resp.isValid());
       resp.validate("test");
       PublicKey pk = resp.getContent(PublicKey.class);
       assertNotNull(pk);
       assertEquals(33, pk.getId().longValue());

   }
}
