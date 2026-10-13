package com.example.notesAPI.APITest;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    //test naming convention "method_scenario_expected"

    private static Header authHeader;

    @BeforeAll
    static void setup() {
        //specify base uri
        baseURI = "http://localhost:8080/user";

    }

    /// //////////////////////
    /// CREATE USER TESTS ///
    /// //////////////////////

    @Test
    //TODO: figure out a way of dynamically incrementing order numbers based on test position, look into MethodOrderer
    @Order(1)
    void createUser_UserDoesntExists_UserIsCreated() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"SampleUser2",
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warning", equalTo(null))
                .body("data", equalTo(null))
                .statusCode(200);
    }

    @Test
    @Order(2)
    void createUser_UserDoesExists_ConflictResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"SampleUser2",
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/createUser"))
                .body("status", equalTo(409))
                .body("title", equalTo("Conflict"))
                .statusCode(409);//conflict
    }

    @Test
    @Order(3)
    void createUser_EmptyReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("{}")
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    @Test
    @Order(4)
    void createUser_MissingUsernameInReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    @Test
    @Order(5)
    void createUser_MissingEmailInReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"SampleUser2",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    @Test
    @Order(6)
    void createUser_MissingPasswordInReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"SampleUser2"
                            "email":"sampleemail2@gmail.com"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    @Test
    @Order(7)
    void createUser_UsernameTooLong_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"useruseruseruseruseruseru(51 chars including this!)",
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    @Test
    @Order(8)
    void createUser_EmailTooLong_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"SampleUser2",
                            "email":"sampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.com
                            sampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.comsampleemail@gmai
                            (this is 255 chars including this)",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("/user/createUser"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);//bad request
    }

    /// /////////////////////
    /// USER LOGIN TESTS ///
    /// /////////////////////

    @Test
    @Order(9)
    void login_ExistingUser_JWTReturned() {
        String authToken = given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/login")
                .then() //what needs to be asserted/validated/tested for correctness
                .assertThat()
                .statusCode(200) //ok
                .body(not(emptyOrNullString())).extract().body().asString();

        authHeader = new Header("Authorization", "Bearer " + authToken);
    }

    @Test
    @Order(10)
    void login_NonExistingUser_NotFoundResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail3@gmail.com",
                            "userPassword":"qwertyisfun3"
                        }""")
                .post("/login")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/login"))
                .body("status", equalTo(404))
                .body("title", equalTo("Not Found"))
                .statusCode(404);//not found
    }

    @Test
    @Order(11)
    void login_EmptyReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("{}")
                .post("/login")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/login"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(12)
    void login_MissingEmailInReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/login")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/login"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(13)
    void login_MissingPasswordInReqBody_BadReqResponse() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail2@gmail.com",
                        }
                        """)
                .post("/login")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("/user/login"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    /// ///////////////////
    /// GET USER TESTS ///
    /// ///////////////////
    //only jwt token in header is needed for retrieval for this section of tests

    @Test
    @Order(14)
    void getUser_ExistingUser_SuccessResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .get("/getUser")
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warning", emptyOrNullString())
                .body("data.username", equalTo("SampleUser2"))
                .body("data.email", equalTo("sampleemail2@gmail.com"))
                .body("data.password", equalTo(null))
                .statusCode(200);
        ;
    }


    /// ///////////////////////////
    /// UPDATE USER INFO TESTS ///
    /// ///////////////////////////

    ///----------------------
    /// UPDATE EMAIL TESTS
    ///----------------------

    @Test
    @Order(15)
    //updates jwt token
    void updateEmail_EmailIsntAssociatedWithAnotherUser_SuccessResponse() {

        Response response =
                given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                            "newEmail":"sampleemailtest@gmail.com"
                        }
                        """)
                .patch("/updateEmail")
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warning", equalTo(null))
                .body("data", not(emptyOrNullString()))
                        .extract().response();


        String authToken = response.jsonPath().getString("data");

        authHeader = new Header("Authorization", "Bearer "+authToken);
    }

    @Test
    @Order(16)
    //for this test ensure a user with sampleemail@gmail.com exists
    void updateEmail_EmailIsAssociatedWithAnotherUser_ConflictResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                            "newEmail":"sampleemail@gmail.com"
                        }
                        """)
                .patch("/updateEmail")
                .then()
                .assertThat()
                .statusCode(409)
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updateEmail"))
                .body("status", equalTo(409))
                .body("title", equalTo("Conflict"));

        }

    @Test
    @Order(17)
    void updateEmail_EmptyReqBody_BadResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                        
                        }
                        """)
                .patch("/updateEmail")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updateEmail"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(18)
    void updateEmail_BlankNewEmailField_BadResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                            "newEmail:"
                        }
                        """)
                .patch("/updateEmail")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("/user/updateEmail"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }


    ///------------------------
    /// UPDATE USERNAME TESTS
    ///------------------------

    @Test
    @Order(19)
    void updateUsername_NewUsernameProvided_SuccessResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                        "newData": "newUsername1234"
                        }
                        """)
                .patch("/updateUsername")
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warnings", equalTo(null))
                .body("data", equalTo(null))
                .statusCode(200);

    }

    @Test
    @Order(20)
    void updateUsername_EmptyReqBody_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("{}")
                .patch("/updateUsername")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updateUsername"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(21)
    void updateUsername_BlankNewDataField_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                        "newData": ""
                        }
                        """)
                .patch("/updateUsername")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updateUsername"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }


    ///--------------------
    /// UPDATE PASSWORD TESTS
    ///--------------------
    @Test
    @Order(22)
    void updatePassword_NewPasswordProvided_SuccessResponse() {

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                        "newData": "NewPasswordForUser1234"
                        }
                        """)
                .patch("/updatePassword")
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warnings", equalTo(null))
                .body("data", equalTo(null))
                .statusCode(200);

    }

    @Test
    @Order(23)
    void updatePassword_EmptyReqBody_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("{}")
                .patch("/updatePassword")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updatePassword"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(24)
    void updatePassword_BlankNewDataField_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                        "newData": ""
                        }
                        """)
                .patch("/updatePassword")
                .then()
                .assertThat()
                .body("detail", not(emptyOrNullString()))
                .body("instance", equalTo("uri=/user/updatePassword"))
                .body("status", equalTo(400))
                .body("title", equalTo("Bad Request"))
                .statusCode(400);
    }

    @Test
    @Order(25)
    void deleteUser_ExistingUser_SuccessResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .delete("/deleteUser")
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("message", not(emptyOrNullString()))
                .body("warnings", emptyOrNullString())
                .body("data", equalTo(null))
                .statusCode(200);
    }

    @Test
    @Order(26)
    void deleteUser_NonExistingUser_UnauthorizedResponse(){
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .delete("/deleteUser")
                .then()
                .assertThat()
                .statusCode(401);//unauthorized
    }

    @Test
    @Order(27)
    void getUser_NonExistingUser_UnauthorizedResponse() {
        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .get("/getUser")
                .then()
                .assertThat()
                .statusCode(401); //unauthorized
    }
}