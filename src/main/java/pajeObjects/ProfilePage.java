package pajeObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final By profileButton = By.xpath(".//a[@href='/account/profile']");
    private final By orderHistoryButton = By.xpath(".//a[@href='/account/order-history']");
    private final By infoText = By.xpath(".//p[text()='В этом разделе вы можете изменить свои персональные данные']");
    private final By logoutButton = By.xpath(".//button[text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки информационного сообщения")
    public void waitForLoadInfoText() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver ->
                driver.findElement(infoText).isDisplayed());
    }

    @Step("Нажать на кнопку Выйти")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    @Step("Проверка отображения ссылки Профиль")
    public boolean isProfileButton() {
        waitForLoadInfoText();
        return driver.findElement(profileButton).isDisplayed();
    }

    @Step("Проверка отображения ссылки История заказов")
    public boolean isOrderHistoryButton() {
        return driver.findElement(orderHistoryButton).isDisplayed();
    }

    @Step("Проверка отображения информационного сообщения")
    public boolean isInfoText() {
        return driver.findElement(infoText).isDisplayed();
    }
}
