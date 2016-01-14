package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.*;
import com.xplenty.api.model.Package;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ITPackageTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/package";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long packageId = 666L;
    private Long packageTemplateId = 333L;
    private Long packageValidationId = 666L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

   public void testCreatePackage() throws Exception {
       Map<String, String> packVars = new HashMap<>();
       packVars.put("var_1", "val1");
       packVars.put("var_2", "super$$$\"complex'val\n\t");
       Package c = new Package().fromTemplate(3L).withDataFlow("someflow").withDescription("test description").
               withFlowType(Xplenty.PackageFlowType.workflow).withName("Test Pack").withSourcePackageId(111L).
               withVariables(packVars);
       c = api.createPackage(c);
       checkPackage(c);
   }

    public void testUpdatePackage() throws Exception {
        Map<String, String> packVars = new HashMap<>();
        packVars.put("var_1", "val1");
        packVars.put("var_2", "super$$$\"complex'val\n\t");
        Package c = new Package().withDataFlow("someflow").withDescription("test description").
                withFlowType(Xplenty.PackageFlowType.workflow).withName("Test Pack").withId(packageId).
                withVariables(packVars);
        c = api.updatePackage(c);
        checkPackage(c);
    }

    public void testDeletePackage() throws Exception {
        Package c = api.deletePackage(packageId);
        checkPackage(c);
    }

    public void testPackageInfo() throws Exception {
        Package c = api.getPackageInfo(packageId);
        checkPackage(c);
    }

    public void testListPackages() throws Exception {
        List<Package> list = api.listPackages();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Package c = list.get(0);
        checkPackage(c);
    }

    public void testListPackageTemplates() throws Exception {
        List<PackageTemplate> list = api.listPackageTemplates();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        PackageTemplate c = list.get(0);
        checkPackageTemplate(c);
    }

    public void testListPackageValidations() throws Exception {
        List<PackageValidation> list = api.listPackageValidations(packageId);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        PackageValidation c = list.get(0);
        checkPackageValidation(c);
    }

    public void testPackageValidationInfo() throws Exception {
        PackageValidation c =  api.getPackageValidationInfo(packageId, packageValidationId);
        checkPackageValidation(c);
    }

    public void testRunPackageValidation() throws Exception {
        PackageValidation c =  api.runPackageValidation(packageId);
        checkPackageValidation(c);
    }


    private void checkPackage(Package c) throws ParseException {
        assertNotNull(c);
        assertEquals(packageId, c.getId());

        assertEquals("TestPack", c.getName());
        assertEquals("TestPack Description", c.getDescription());
        assertEquals(Xplenty.PackageFlowType.workflow, c.getFlowType());
        assertEquals(String.format("https://localhost/%s/api/package/%s", accountID, packageId), c.getUrl());
        assertEquals(String.format("https://localhost/%s/package/%s", accountID, packageId), c.getHtmlUrl());
        assertEquals(Xplenty.PackageStatus.active, c.getStatus());
        assertEquals(111, c.getOwnerId().longValue());
        assertEquals(dFormat.parse("2016-01-14T20:25:12Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-14T20:25:12Z"), c.getUpdatedAt());

        Map<String, String> packVars = c.getVariables();
        assertNotNull(packVars);
        assertEquals("val1", packVars.get("var_1"));
        assertEquals("supercomplex", packVars.get("var_2"));

    }

    private void checkPackageValidation(PackageValidation c) throws ParseException {
        assertNotNull(c);
        assertEquals(packageValidationId, c.getId());

        assertEquals("Something bad happened", c.getStatusMessage());
        assertEquals(String.format("https://localhost/%s/api/packages/%s/validations/%s", accountID, packageId, packageValidationId), c.getUrl());
        assertEquals(Xplenty.PackageValidationStatus.failed, c.getStatus());
        assertEquals(222, c.getOwnerId().longValue());
        assertEquals(111, c.getAccountId().longValue());
        assertEquals(packageId, c.getPackageId());
        assertEquals(1234, c.getRuntime().longValue());
        assertEquals(dFormat.parse("2016-01-14T20:34:27Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-14T20:34:27Z"), c.getUpdatedAt());

        List<PackageValidationError> errors  = c.getErrors();
        assertNotNull(errors);
        assertEquals("12", errors.get(0).getComponentId());
        assertEquals("couldn't obtain value for var_1", errors.get(0).getMessage());
    }

    private void checkPackageTemplate(PackageTemplate c) throws ParseException {
        assertNotNull(c);
        assertEquals(packageTemplateId, c.getId());
        assertEquals("test template", c.getName());
        assertEquals("really good template", c.getDescription());
        assertEquals(1, c.getPosition().longValue());

        PackageTemplateAuthor pta = c.getAuthor();
        assertNotNull(pta);
        assertEquals(333, pta.getId().longValue());
        assertEquals("best template author", pta.getName());
        assertEquals(String.format("https://localhost/%s/api/user/333", accountID), pta.getAvatarUrl());
    }
}
