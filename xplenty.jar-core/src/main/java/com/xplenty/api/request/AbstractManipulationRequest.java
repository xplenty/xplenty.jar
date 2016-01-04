package com.xplenty.api.request;

import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 18.12.15
 * Time: 18:49
 */
public abstract class AbstractManipulationRequest<T> extends AbstractRequest<T> {
    protected final T entity;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected AbstractManipulationRequest(T entity) {
        this.entity = entity;
        final Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new XplentyAPIException("Developer error, no actual type information passed!");
        }
        this.clazz = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public T getResponse(Response response) {
        try {
            final T value = response.getContent(this.clazz);
            return value;
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }


    @Override
    public Object getBody() {
        final String packKey = getPackKey();
        if (packKey != null) {
            Map<String, Object> packedEntity = new HashMap<String, Object>();
            packedEntity.put(packKey, entity);
            return packedEntity;
        }
        return entity;
    }

    protected abstract String getPackKey();
}
