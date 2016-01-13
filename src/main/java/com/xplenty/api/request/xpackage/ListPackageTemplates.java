package com.xplenty.api.request.xpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PackageTemplate;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * List package templates that are available for the authenticated user.
 * You can use template to create new package with predefined settings.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:58
 */
public class ListPackageTemplates extends AbstractListRequest<List<PackageTemplate>> {

    public ListPackageTemplates() {
        super(null, false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.PackageTemplates.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PackageTemplates.name;
    }

    @Override
    public List<PackageTemplate> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<PackageTemplate>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
