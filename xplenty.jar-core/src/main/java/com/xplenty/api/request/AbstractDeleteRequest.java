package com.xplenty.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.util.Http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: Xardas
 * Date: 18.12.15
 * Time: 20:20
 */
public abstract class AbstractDeleteRequest<T> implements Request<T> {
    protected final long entityId;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected AbstractDeleteRequest(Long entityId) {
        this.entityId = entityId;
        final Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new XplentyAPIException("Developer error, no actual type information passed!");
        }
        this.clazz = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public T getResponse(ClientResponse response) {
        String json = response.getEntity(String.class);
        try {
            final T value = new ObjectMapper().readValue(json, this.clazz);
            return value;
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }


    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.DELETE;
    }
}
