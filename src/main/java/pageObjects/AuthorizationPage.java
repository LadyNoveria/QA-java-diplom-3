package pageObjects;

import entities.user.UserRequest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationPage {
    private final WebDriver driver;
    private final By registerButton = By.xpath(".//a[@href='/register']");
    private final By restorePasswordLink = By.xpath(".//a[@href='/forgot-password']");
    private final By pageTitle = By.xpath(".//h2[text()='Вход']");
    private final By emailField = By.name("name");
    private final By passwordField = By.name("Пароль");
    private final By signInButton = By.xpath(".//button[text()='Войти']");

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы")
    public void waitForLoadPageTitle() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver ->
                driver.findElement(pageTitle).isDisplayed());
    }

    @Step("Заполнение полей формы входа в личный кабинет")
    public void fillingInFields(UserRequest userRequest) {
        waitForLoadPageTitle();
        fillingInEmailField(userRequest.getEmail());
        fillingInPasswordField(userRequest.getPassword());
        clickSignInButton();
    }

    @Step("Проверка отображения заголовка страницы")
    public boolean isPageTitle() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    @Step("Нажать на кнопку Регистрация")
    public void clickRegisterButton() {
        waitForLoadPageTitle();
        driver.findElement(registerButton).click();
    }

    @Step("Нажать на кнопку Восстановить пароль")
    public void clickRestorePasswordLink() {
        waitForLoadPageTitle();
        driver.findElement(restorePasswordLink).click();
    }

    @Step("Нажать на кнопку Войти")
    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    @Step("Заполнение поля Email")
    private void fillingInEmailField(String email) {
        driver.findElement(emailField).click();
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнение поля Пароль")
    private void fillingInPasswordField(String password) {
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
}
