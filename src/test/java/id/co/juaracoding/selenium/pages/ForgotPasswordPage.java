package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage {
    private WebDriver driver;

    @FindBy(id = "email") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputEmail;

    @FindBy(id = "btnReset") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement btnReset;

    @FindBy(id = "successMessage") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement successMessage;

    @FindBy(id = "errorMessage") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement errorMessage;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
