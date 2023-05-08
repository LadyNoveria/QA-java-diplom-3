package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private final WebDriver driver;
    private final By signInLink = By.xpath(".//a[@href='/login']");
    private final By pageTitle = By.xpath(".//h2[text()='Восстановление пароля']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка отображения заголовка страницы")
    public void waitForLoadPageTitle() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver ->
                driver.findElement(pageTitle).isDisplayed());
    }

    @Step("Нажать на ссылку Войти")
    public void clickSignInLink() {
        waitForLoadPageTitle();
        driver.findElement(signInLink).click();
    }
}
