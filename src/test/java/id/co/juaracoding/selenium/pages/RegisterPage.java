package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    private WebDriver driver;

    @FindBy(id = "fullName") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputFullName;

    @FindBy(id = "email") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputEmail;

    @FindBy(id = "password") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement inputPassword;
    
    @FindBy(id = "identityNumber") // NPWP/KTP - Sesuaikan id/xpath dengan aplikasi Anda
    private WebElement inputIdentityNumber;

    @FindBy(id = "btnRegister") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement btnRegister;

    @FindBy(id = "validationMessage") // Sesuaikan id/name/xpath dengan aplikasi Anda
    private WebElement validationMessage;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
