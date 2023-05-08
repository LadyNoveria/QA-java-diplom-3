import clients.UserClient;
import driver.WebDriverCreator;
import entities.user.UserCreds;
import entities.user.UserRequest;
import entities.user.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.*;

import static org.junit.Assert.assertTrue;
import static service.Constants.BASE_URI;

public class AccountLoginTest {
    private WebDriver driver;
    private UserClient userClient;
    private UserRequest userRequest;
    private UserResponse userCreationResponse;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private HomePageStellarBurgers homePageStellarBurgers;
    private PasswordRecoveryPage passwordRecoveryPage;
    private Headers headers;

    @Before
    public void setUp() {
        userRequest = UserRequest.userGenerator();
        userClient = new UserClient();
        userCreationResponse = userClient.userCreate(userRequest)
                .body()
                .as(UserResponse.class);
        driver = WebDriverCreator.createWebDriver();
        authorizationPage = new AuthorizationPage(driver);
        registrationPage = new RegistrationPage(driver);
        homePageStellarBurgers = new HomePageStellarBurgers(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        headers = new Headers(driver);
    }

    @Test
    @DisplayName("Успешный логин пользователя через кнопку \"Войти в аккаунт\" на главной странице")
    public void successfulLoginUsingSignInButtonTest() {
        driver.get(BASE_URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.fillingInFields(userRequest);
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Успешный логин пользователя через ссылку \"Личный кабинет\"")
    public void successfulLoginUsingAccountLoginTest() {
        driver.get(BASE_URI);
        headers.clickAccountLoginButton();
        authorizationPage.fillingInFields(userRequest);
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Успешный логин пользователя через форму регистрации")
    public void successfulLoginUsingSignInLinkToRegistrationPageTest() {
        driver.get(BASE_URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.clickRegisterButton();
        registrationPage.clickSignInLink();
        authorizationPage.fillingInFields(userRequest);
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Успешный логин пользователя через страницу восстановленя пароля")
    public void successfulLoginUsingPasswordRecoveryPageTest() {
        driver.get(BASE_URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.clickRestorePasswordLink();
        passwordRecoveryPage.clickSignInLink();
        authorizationPage.fillingInFields(userRequest);
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Неверные email или пароль для входа")
    public void errorLoginTest() {
        driver.get(BASE_URI);
        homePageStellarBurgers.clickSignInButton();
        authorizationPage.fillingInFields(UserRequest.userGenerator());
        assertTrue(authorizationPage.isPageTitle());
    }

    @After
    public void tearDown() {
        userClient.userDelete(userRequest, UserCreds.getCredsFrom(userCreationResponse));
        driver.quit();
    }
}
