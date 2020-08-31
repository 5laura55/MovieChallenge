package tests;

import api.API;
import entities.Results;
import helpers.TestEnvironment;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class RatingMediaTest {
    private static API api;

    @BeforeClass
    public static void before() {
        api = new API(TestEnvironment.API_KEY);

    }

    @Test()
    public void testRateMovie() {
        Response rsp = api.rateMovie("577922", "4.5");
        Assert.assertEquals(201, rsp.getStatusCode());

    }

    @Test()
    public void testRateTvShow() {

        Response rsp = api.rateTvShow("63174", "3.5");
        Assert.assertEquals(201, rsp.getStatusCode());

    }

    @Test()
    public void testRateTvEpisode() {

        Response rsp = api.rateEpisode("63174", "2", "2", "4.5");
        Assert.assertEquals(201, rsp.getStatusCode());

    }

    //extra feature
    @Test()
    public void testFavoriteMoviesToObjects() {
        Response rsp = api.getFavoritesMovies();
        Results result = rsp.as(Results.class);
        Assert.assertEquals("Joker",result.getResults().get(0).getTitle());
    }


}
