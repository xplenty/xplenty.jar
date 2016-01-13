package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

/**
 * Author: Xardas
 * Date: 11.01.16
 * Time: 16:38
 */
public class PackageTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Package pack = createMockPackage(now);
        assertNotNull(pack);
        assertEquals(now.getTime(), pack.getCreatedAt().getTime());

        pack = createMockPackageForCreation();
        assertNotNull(pack);
        assertEquals("name", pack.getName());
        assertEquals("somestr", pack.dataFlowJson);
        assertEquals("testdesc", pack.getDescription());
        assertEquals(666, pack.getId().longValue());
        assertEquals(777, pack.packageTemplateId.longValue());
        assertEquals(222, pack.sourcePackageId.longValue());
        assertEquals(Xplenty.PackageFlowType.dataflow, pack.getFlowType());
        assertNotNull(pack.getVariables());
        assertEquals("super$$$\"complex'val\n\t", pack.getVariables().get("var_2"));

        PackageValidation pv = createMockPackageValidation(now);
        assertNotNull(pv);
        assertEquals(now.getTime(), pv.getCreatedAt().getTime());

        PackageTemplate pta = createMockPackageTemplate();
        assertNotNull(pta);
        assertEquals(666, pta.getId().longValue());
    }

    public static Package createMockPackageForCreation() {
        Map<String, String> packVars = new HashMap<>();
        packVars.put("var_1", "val1");
        packVars.put("var_2", "super$$$\"complex'val\n\t");
        Package pack = new Package().fromTemplate(777L).withDataFlow("somestr").withDescription("testdesc").
                withName("name").withFlowType(Xplenty.PackageFlowType.dataflow).withSourcePackageId(222L).
                withId(666L).withVariables(packVars);
        return pack;
    }


    public static Package createMockPackage(Date now) {
        Package pack = new Package();
        pack.id = 666L;
        pack.name = "TestPack";
        pack.description = "TestPack Description";
        pack.flowType = Xplenty.PackageFlowType.workflow;
        pack.ownerId = 111L;
        pack.url = "https://testapi.xplenty.com/api/package/666";
        pack.htmlUrl = "https://testapi.xplenty.com/package/666";
        pack.status = Xplenty.PackageStatus.active;
        Map<String, String> packVars = new HashMap<>();
        packVars.put("var_1", "val1");
        packVars.put("var_2", "super$$$\"complex'val\n\t");
        pack.variables = packVars;
        pack.createdAt = now;
        pack.updatedAt = now;

        return pack;
    }

    public static PackageValidation createMockPackageValidation(Date now) {
        PackageValidation pv = new PackageValidation();
        pv.id = 666L;
        pv.accountId = 111L;
        pv.ownerId = 222L;
        pv.packageId = 777L;
        pv.status = Xplenty.PackageValidationStatus.failed;
        pv.statusMessage = "Something bad happened";
        pv.url = "https://testapi.xplenty.com/api/packages/777/validations/666";
        pv.runtime = 1234L;
        pv.updatedAt = now;
        pv.createdAt = now;
        List<PackageValidationError> errors = new ArrayList<>();
        PackageValidationError err = new PackageValidationError();
        err.componentId = "12";
        err.message = "couldn't obtain value for var_1";
        errors.add(err);
        pv.errors = errors;
        return pv;
    }

    public static PackageTemplate createMockPackageTemplate() {
        PackageTemplate pt = new PackageTemplate();
        pt.id = 666L;
        pt.name = "test template";
        pt.description = "really good template";
        pt.position = 1;
        PackageTemplateAuthor pta = new PackageTemplateAuthor();
        pta.id = 333L;
        pta.name = "best template author";
        pta.avatarUrl = "https://testapi.xplenty.com/api/user/333";
        pt.author = pta;
        return pt;
    }
}
