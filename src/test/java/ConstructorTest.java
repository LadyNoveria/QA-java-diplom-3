import driver.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pajeObjects.HomePageStellarBurgers;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {
    private final String URI = "https://stellarburgers.nomoreparties.site/";
    private WebDriver driver;
    private HomePageStellarBurgers homePageStellarBurgers;

    @Before
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        homePageStellarBurgers = new HomePageStellarBurgers(driver);
    }

    @Test
    @DisplayName("Успешный переход в раздел \"Булки\"")
    public void successfulTransitionToSectionBuns() {
        driver.get(URI);
        homePageStellarBurgers.waitForLoadPageTitleTitle();
        homePageStellarBurgers.clickSaucesButton();
        homePageStellarBurgers.clickBunsButton();
        assertTrue(homePageStellarBurgers.isBuns());
    }

    @Test
    @DisplayName("Успешный переход в раздел \"Соусы\"")
    public void successfulTransitionToSectionSauces() {
        driver.get(URI);
        homePageStellarBurgers.waitForLoadPageTitleTitle();
        homePageStellarBurgers.clickSaucesButton();
        assertTrue(homePageStellarBurgers.isSauces());
    }

    @Test
    @DisplayName("Успешный переход в раздел \"Начинки\"")
    public void successfulTransitionToToppingsSection() {
        driver.get(URI);
        homePageStellarBurgers.waitForLoadPageTitleTitle();
        homePageStellarBurgers.clickToppingsButton();
        assertTrue(homePageStellarBurgers.isToppings());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
