package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Headers {
    private final WebDriver driver;
    private final By logo = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']/a");
    private final By accountLoginButton = By.xpath(".//a[@href='/account']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");

    public Headers(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать на кнопку Конструктор")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на Логотип")
    public void clickLogo() {
        driver.findElement(logo).click();
    }

    @Step("Нажать на кнопку Личный кабинет")
    public void clickAccountLoginButton() {
        driver.findElement(accountLoginButton).click();
    }
}
