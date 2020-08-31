package api;

import helpers.HTTPMessageSender;

import helpers.TestEnvironment;
import io.restassured.response.Response;


public class API {
    private final String username ;
    private final String pass;
    private String token;
    private final HTTPMessageSender sender;
    private final String key;

    public API(String apiKey) {
        sender = new HTTPMessageSender(TestEnvironment.BASIC_URI);
        this.username= System.getenv("username");
        this.pass=System.getenv("password");

        this.key = apiKey;
    }

    //authentication
    public Response createGuestSession() {
        return sender.getRequest("/authentication/guest_session/new?api_key=" + key);

    }

    public String getRequestToken() {

        token = sender.getRequest("/authentication/token/new?api_key=" + key).then().extract().path("request_token");

        return token;
    }

    public Response createSessionWithLogin() {

        token = sender.getRequest("/authentication/token/new?api_key=" + key).then().extract().path("request_token");

        String json = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + pass + "\",\n" +
                "  \"request_token\": \"" + token + "\"\n" +
                "}";
        //System.out.println(json);
        return sender.postRequest("/authentication/token/validate_with_login?api_key=" + key, json);



    }


    public Response createSession() {


        createSessionWithLogin();
        String json = "{\n" +
                "  \"request_token\": \"" + token + "\"\n" +
                "}";


        return sender.postRequest("/authentication/session/new?api_key=" + key, json);


    }

    public Response deleteSession() {
        String id = createSession().then().extract().path("session_id");
        String json = "{\n" +
                "  \"session_id\": \"" + id + "\"\n" +
                "}";
        return sender.deleteRequest("/authentication/session?api_key=" + key, json);
    }

    //lists
    public Response createList(String name, String descrition, String language) {


        String json = "{\n" +
                "  \"name\": \"" + name + ".\",\n" +
                "  \"description\": \"" + descrition + "\",\n" +
                "  \"language\": \"" + language + "\"\n" +
                "}";

        System.out.println(json);
        String id = createSession().then().extract().path("session_id");

        return sender.postRequest("/list?api_key=" + key + "&session_id=" + id, json);



    }

    public Response getDetails(String listNumer) {
        return sender.getRequest("/list/" + listNumer + "?api_key=" + key);

    }

    public Response checkItemStatus(String listNumer, String movieId) {
        return sender.getRequest("/list/" + listNumer + "/item_status?movie_id=" + movieId + "&api_key=" + key);

    }

    public Response addMovie(String listNumer) {
        String json = "{\n" +
                "  \"media_id\": 18\n" +
                "}";
        String session_id = createSession().then().extract().path("session_id");
        return sender.postRequest("/list/" + listNumer + "/add_item?api_key=" + key + "&session_id=" + session_id, json);

    }

    public Response removeMovie(String listNumer) {

        String json = "{\n" +
                "  \"media_id\": 18\n" +
                "}";
        String session_id = createSession().then().extract().path("session_id");
        return sender.postRequest("/list/" + listNumer + "/remove_item?api_key=" + key + "&session_id=" + session_id, json);


    }

    public Response clearList(String listNumer) {
        String id = createSession().then().extract().path("session_id");

        String confirm = "true";
        return  sender.postRequest("/list/" + listNumer + "/clear?session_id=" + id + "&confirm=" + confirm + "&api_key=" + key, "");

    }

    public Response deleteList(String listNumer) {
        String id = createSession().then().extract().path("session_id");

        return sender.deleteRequest("/list/" + listNumer + "?session_id=" + id + "&api_key=" + key, "");

    }

    //rating
    public Response rateMovie(String movieID, String rate) {

        String json = "{\n" +
                "  \"value\": " + rate + "\n" +
                "}";
        String id = createSession().then().extract().path("session_id");

        return sender.postRequest("/movie/" + movieID + "/rating?session_id=" + id + "&api_key=" + key, json);

    }

    public Response rateTvShow(String showID, String rate) {

        String json = "{\n" +
                "  \"value\": " + rate + "\n" +
                "}";
        String id = createSession().then().extract().path("session_id");

        return sender.postRequest("/tv/" + showID + "/rating?session_id=" + id + "&api_key=" + key, json);

    }

    public Response rateEpisode(String showID, String season, String episodeNumber, String rate) {

        String json = "{\n" +
                "  \"value\": " + rate + "\n" +
                "}";
        String id = createSession().then().extract().path("session_id");

        return sender.postRequest("/tv/" + showID + "/season/" + season + "/episode/" + episodeNumber + "/rating?session_id=" + id + "&api_key=" + key, json);

    }

    public Response getFavoritesMovies() {
        String id = createSession().then().extract().path("session_id");

        return sender.getRequest("/account/account_id/favorite/movies?session_id=" + id + "&api_key=" + key);
    }
}
