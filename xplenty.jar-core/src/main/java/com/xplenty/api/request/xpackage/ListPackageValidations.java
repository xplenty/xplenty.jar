package com.xplenty.api.request.xpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PackageValidation;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List validations for specific package.
 * Optionally, you can supply the input parameters to filter the validation list so that it contains only validations
 * with a specific status, and to determine the order by which the list will be sorted.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:41
 */
public class ListPackageValidations extends AbstractListRequest<List<PackageValidation>> {
    private final long packageId;

    public ListPackageValidations(Properties parameters, long packageId) {
        super(parameters, true);
        this.packageId = packageId;
    }

    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_STATUS)
                && !(params.get(PARAMETER_STATUS) instanceof Xplenty.PackageFlowType)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of PackageValidationStatus values", PARAMETER_STATUS));
        }
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.PackageValidations.format(String.valueOf(packageId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PackageValidations.name;
    }

    @Override
    public List<PackageValidation> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<PackageValidation>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
