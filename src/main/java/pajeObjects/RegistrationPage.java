package pajeObjects;

import entities.user.UserRequest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final By pageTitle = By.xpath(".//h2[text()='Регистрация']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By nameField = By.xpath(".//label[text()='Имя']/parent::div/input");
    private final By emailField = By.xpath(".//label[text()='Email']/parent::div/input");
    private final By passwordField = By.name("Пароль");
    private final By errorIncorrectPassword = By.xpath(".//p[@class='input__error text_type_main-default']");
    private final By signInLink = By.className("Auth_link__1fOlj");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожтдание отображения заголовка страницы")
    public void waitForLoadPageTitle() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver ->
                driver.findElement(pageTitle).isDisplayed());
    }

    @Step("Заполнение полей формы регистрации и нажатие на кнопку Зарегистрироваться")
    public void fillingInFields(UserRequest userRequest) {
        waitForLoadPageTitle();
        fillingInNameField(userRequest.getName());
        fillingInEmailField(userRequest.getEmail());
        fillingInPasswordField(userRequest.getPassword());
        clickRegisterButton();
    }

    @Step("Проверка отображения сообщения об ошибке")
    public boolean isError() {
        return driver.findElement(errorIncorrectPassword).isDisplayed();
    }

    @Step("Возвращение текста ошибки")
    public String getTestError() {
        return driver.findElement(errorIncorrectPassword).getText();
    }

    @Step("Нажать ссылку Войти")
    public void clickSignInLink() {
        waitForLoadPageTitle();
        driver.findElement(signInLink).click();
    }

    @Step("Заполнение поля Имя")
    private void fillingInNameField(String name) {
        driver.findElement(nameField).click();
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Заполнение поля Email")
    private void fillingInEmailField(String email) {
        driver.findElement(emailField).click();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнение поля Пароль")
    private void fillingInPasswordField(String password) {
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    private void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }
}
