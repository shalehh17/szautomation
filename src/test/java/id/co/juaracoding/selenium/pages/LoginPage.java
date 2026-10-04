package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "email") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputEmail;

    @FindBy(id = "password") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputPassword;

    @FindBy(id = "btnLogin") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement btnLogin;

    @FindBy(id = "errorMessage") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement errorMessage;

    @FindBy(id = "dashboardIndicator") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement dashboardIndicator;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        btnLogin.click();
    }
    
    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
