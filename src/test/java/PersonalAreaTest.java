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

public class PersonalAreaTest {

    private WebDriver driver;
    private UserClient userClient;
    private UserRequest userRequest;
    private UserResponse userCreationResponse;
    private AuthorizationPage authorizationPage;
    private HomePageStellarBurgers homePageStellarBurgers;
    private ProfilePage profilePage;
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
        homePageStellarBurgers = new HomePageStellarBurgers(driver);
        profilePage = new ProfilePage(driver);
        headers = new Headers(driver);
    }

    @Test
    @DisplayName("Успешный переход в профиль пользователя")
    public void successfulGoToProfileTest() {
        driver.get(BASE_URI);
        headers.clickAccountLoginButton();
        authorizationPage.fillingInFields(userRequest);
        headers.clickAccountLoginButton();
        assertTrue(profilePage.isProfileButton());
        assertTrue(profilePage.isOrderHistoryButton());
        assertTrue(profilePage.isInfoText());
    }

    @Test
    @DisplayName("Успешный переход на главную страницу сайта при нажатии на \"Конструктор\" из личного кабинета")
    public void successfulGoToConstructorTest() {
        driver.get(BASE_URI);
        headers.clickAccountLoginButton();
        authorizationPage.fillingInFields(userRequest);
        headers.clickAccountLoginButton();
        headers.clickConstructorButton();
        assertTrue(homePageStellarBurgers.isPageTitle());
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Успешный переход на главную страницу сайта при нажатии на лого из личного кабинета")
    public void successfulGoToHomePageUsingLogoTest() {
        driver.get(BASE_URI);
        headers.clickAccountLoginButton();
        authorizationPage.fillingInFields(userRequest);
        headers.clickAccountLoginButton();
        headers.clickLogo();
        assertTrue(homePageStellarBurgers.isPageTitle());
        assertTrue(homePageStellarBurgers.isCrateOrderButton());
    }

    @Test
    @DisplayName("Успешный выход пользователя из личного кабинета")
    public void successfulLogoutTest() {
        driver.get(BASE_URI);
        headers.clickAccountLoginButton();
        authorizationPage.fillingInFields(userRequest);
        headers.clickAccountLoginButton();
        profilePage.waitForLoadInfoText();
        profilePage.clickLogoutButton();
        authorizationPage.waitForLoadPageTitle();
        assertTrue(authorizationPage.isPageTitle());
    }

    @After
    public void tearDown() {
        userClient.userDelete(userRequest, UserCreds.getCredsFrom(userCreationResponse));
        driver.quit();
    }
}
