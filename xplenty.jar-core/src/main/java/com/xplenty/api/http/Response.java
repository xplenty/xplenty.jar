package com.xplenty.api.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.XplentyObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 28.12.15
 * Time: 19:13
 */
public abstract class Response {
    protected static final String INVALID_TYPE_INFORMATION = "Invalid type information!";
    protected final Http.ResponseStatus status;
    protected final String content;
    protected final Map<String, String> headers;

    protected Response(String content, int status, Map<String, String> headers) {
        this.content = content;
        this.status = Http.ResponseStatus.fromCode(status);
        this.headers = headers;
    }


    public static Response forContentType(Http.MediaType type, String content, int status, Map<String, String> headers) {
        switch (type) {
            case JSON:
                return new JsonResponse(content, status, headers);
            default:
                throw new UnsupportedOperationException(String.format("This media type [%s] not supported!", type));
        }
    }

    public abstract <T> T getContent(TypeReference<T> typeReference) throws XplentyAPIException;

    public abstract <T> T getContent(Class<T> typeReference) throws XplentyAPIException;

    public boolean isValid() {
        return content != null && content.length() > 0 && status.getCode() < 400 && status.getCode() > 100;
    }

    @SuppressWarnings("unchecked")
    protected <T> void checkTypedInfo(TypeReference<T> typeReference) {
        if (typeReference.getType() instanceof Class) {
            if (XplentyObject.class.isAssignableFrom((Class) typeReference.getType())) {
                return;
            } else {
                throw new XplentyAPIException(INVALID_TYPE_INFORMATION);
            }
        }
        final ParameterizedType type = (ParameterizedType) typeReference.getType();
        final Class rawType = (Class) type.getRawType();
        final Type[] actualTypeArguments = type.getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            throw new XplentyAPIException(INVALID_TYPE_INFORMATION);
        }
        Class cls;
        if (List.class.isAssignableFrom(rawType)) {
            cls = (Class) actualTypeArguments[0];
            if (XplentyObject.class.isAssignableFrom(cls)) {
                return;
            }
        } else if (Map.class.isAssignableFrom(rawType)) {
            cls = (Class) actualTypeArguments[1];
            if (XplentyObject.class.isAssignableFrom(cls)) {
                return;
            }
        }
        throw new XplentyAPIException(INVALID_TYPE_INFORMATION);
    }

    public Http.ResponseStatus getStatus() {
        return status;
    }

    public String getRawContent() {
        return content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
