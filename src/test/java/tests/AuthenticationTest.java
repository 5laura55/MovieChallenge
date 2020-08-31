package tests;


import api.API;

import helpers.TestEnvironment;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class AuthenticationTest {


    private static API api;

    @BeforeClass
    public static void before() {
        api = new API(TestEnvironment.API_KEY);

    }

    @Test
    public void testCreateGuestSession() {
        Response rsp = api.createGuestSession();
        Assert.assertEquals(200, rsp.getStatusCode());
    }

    @Test
    public void testGetRequestToken() {
        String rsp = api.getRequestToken();
        Assert.assertNotNull(rsp);

    }

    @Test()
    public void testCreateSessionWithLogin() {

        Response rsp = api.createSessionWithLogin();
        Assert.assertEquals(200, rsp.getStatusCode());
    }

    @Test()
    public void testCreateSession() {

        Response rsp = api.createSession();
        Assert.assertEquals(200, rsp.getStatusCode());
    }

    @Test()
    public void testDeleteSession() {

        Response rsp = api.deleteSession();
        Assert.assertEquals(200, rsp.getStatusCode());
    }

}
