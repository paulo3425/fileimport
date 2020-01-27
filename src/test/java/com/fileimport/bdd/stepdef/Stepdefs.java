package com.fileimport.bdd.stepdef;

import com.fileimport.dto.AvgProductDto;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Stepdefs {

    private Response response;
    private String path;


    @When(value = "^I POST to \"([^\"]*)\"$", timeout = 9999)
    public void iPOSTTo(String endPoint) throws Throwable {
        this.response = given()
                .contentType(ContentType.JSON)
                .post(this.path + endPoint)
                .then()
                .extract()
                .response();

    }

    @Then("^Status code should be \"([^\"]*)\"$")
    public void statusCodeShouldBe(int status) throws Throwable {
        this.response.then().statusCode(status);
    }


    @Given("^Set Api$")
    public void setApi() {
        this.path = "http://localhost:8080";
    }

    @When("^I GET to \"([^\"]*)\"$")
    public void iGETTo(String uri) throws Throwable {
        this.response = given()
                .contentType(ContentType.JSON)
                .get(this.path + uri)
                .then()
                .extract()
                .response();
    }

    @And("^Sum of list should be \"([^\"]*)\"$")
    public void sumOfListShouldBe(BigDecimal value) throws Throwable {
        AvgProductDto[] products = response.as(AvgProductDto[].class);

        BigDecimal sum = Arrays.stream(products).map(AvgProductDto::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumWithScale = sum.setScale(2, RoundingMode.HALF_DOWN);
        Assert.assertEquals(sumWithScale, value);
    }
}
