import clients.UserClient;
import driver.WebDriverCreator;
import entities.user.GeneralResponse;
import entities.user.UserCreds;
import entities.user.UserRequest;
import entities.user.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pajeObjects.AuthorizationPage;
import pajeObjects.HomePageStellarBurgers;
import pajeObjects.RegistrationPage;

import static org.junit.Assert.*;

public class RegisterTest {
    private final String URI = "https://stellarburgers.nomoreparties.site/";
    private WebDriver driver;
    private UserClient userClient;
    private UserRequest userRequest;
    private UserResponse userCreationResponse;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private HomePageStellarBurgers homePageStellarBurgers;

    @Before
    public void setUp() {
        userRequest = UserRequest.userGenerator();
        userClient = new UserClient();
        driver = WebDriverCreator.createWebDriver();
        authorizationPage = new AuthorizationPage(driver);
        registrationPage = new RegistrationPage(driver);
        homePageStellarBurgers = new HomePageStellarBurgers(driver);
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void successfulRegisterTest() {
        driver.get(URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.clickRegisterButton();
        registrationPage.fillingInFields(userRequest);
        Response response = userClient.userLogin(userRequest);
        userCreationResponse = response.body().as(UserResponse.class);
        response.then().assertThat().statusCode(200);
        assertTrue(authorizationPage.isPageTitle());
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя с паролем меньше 6 символов")
    public void errorRegisterTest() {
        driver.get(URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.clickRegisterButton();
        userRequest.setPassword("12345");
        registrationPage.fillingInFields(userRequest);
        Response response = userClient.userLogin(userRequest);
        GeneralResponse generalResponse = response.body().as(GeneralResponse.class);
        response.then().assertThat().statusCode(401);
        assertTrue(registrationPage.isError());
        assertFalse(generalResponse.isSuccess());
        assertEquals(registrationPage.getTestError(), "Некорректный пароль");
    }

    @After
    public void tearDown() {
        if (userCreationResponse != null) {
            userClient.userDelete(userRequest, UserCreds.getCredsFrom(userCreationResponse));
        }
        driver.quit();
    }
}
