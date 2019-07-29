package com.xplenty.api.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xplenty.api.exceptions.XplentyAPIException;

import java.io.IOException;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 05.11.15
 * Time: 16:01
 */
public class JsonResponse extends Response {
    private JsonNode jsoncontent; // lazily initialized


    public JsonResponse(String content, int status, Map<String, String> headers) {
        super(content, status, headers);
    }

    private JsonNode getContent() throws XplentyAPIException {
        if (jsoncontent == null && content != null) {
            try {
                this.jsoncontent = getJsonMapper().readTree(content);
            } catch (IOException e) {
                throw new XplentyAPIException("Error parsing JSON tree", e);
            }
        } else if (content == null) {
            this.jsoncontent = null;
        }
        return this.jsoncontent;
    }

    private ObjectMapper getJsonMapper() {
        return  JsonMapperFactory.getInstance();
    }

    @Override
    public <T> T getContent(TypeReference<T> typeReference) throws XplentyAPIException {
        if (!isValid()) {
            throw new XplentyAPIException("Response doesn't contain any valid content!");
        }
        checkTypedInfo(typeReference);
        return getJsonMapper().convertValue(getContent(), typeReference);
    }

    public <T> T getContent(Class<T> typeReference) throws XplentyAPIException {
        if (!isValid()) {
            throw new XplentyAPIException("Response doesn't contain any valid content!");
        }
        return getJsonMapper().convertValue(getContent(), typeReference);
    }


}
