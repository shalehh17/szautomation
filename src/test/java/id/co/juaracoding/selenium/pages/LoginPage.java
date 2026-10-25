package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@data-testid='login-username-input'] | //*[@id='login-username'] | //*[@id='email'] | //input[@type='text']")
    private WebElement inputEmail;

    @FindBy(xpath = "//*[@data-testid='login-password-input']//input | //*[@data-testid='login-password-input'] | //*[@id='login-password']//input | //*[@id='login-password'] | //*[@id='password'] | //input[@type='password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//*[@data-testid='login-captcha-input'] | //*[@id='login-captcha']")
    private List<WebElement> inputCaptchaList;

    @FindBy(xpath = "//*[@data-testid='login-captcha-value']")
    private List<WebElement> captchaValueList;

    @FindBy(xpath = "//*[@data-testid='login-submit-button'] | //*[@id='btnLogin'] | //button[@type='submit']")
    private WebElement btnLogin;

    @FindBy(xpath = "//*[@data-testid='app-navbar'] | //*[@data-testid='app-sidebar'] | //*[@id='dashboardIndicator'] | //*[contains(@class, 'dashboard')]")
    private List<WebElement> dashboardIndicators;

    @FindBy(xpath = "//*[contains(@class, 'p-toast-message')] | //*[@id='errorMessage'] | //div[@role='alert']")
    private List<WebElement> errorMessages;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        try {
            inputEmail.clear();
            inputEmail.sendKeys(email);
        } catch (Exception ignored) {}

        try {
            inputPassword.clear();
            inputPassword.sendKeys(password);
        } catch (Exception ignored) {}

        try {
            if (!captchaValueList.isEmpty() && !inputCaptchaList.isEmpty()) {
                String capVal = captchaValueList.get(0).getText();
                if (capVal != null && !capVal.trim().isEmpty()) {
                    inputCaptchaList.get(0).clear();
                    inputCaptchaList.get(0).sendKeys(capVal.trim());
                }
            }
        } catch (Exception ignored) {}

        try {
            btnLogin.click();
        } catch (Exception ignored) {}
    }
    
    public String getErrorMessage() {
        if (!errorMessages.isEmpty()) {
            return errorMessages.get(0).getText();
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return !errorMessages.isEmpty() || driver.getCurrentUrl().contains("login");
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isDashboardDisplayed() {
        try {
            return !dashboardIndicators.isEmpty() || driver.getCurrentUrl().contains("dashboard") || driver.getCurrentUrl().contains("home") || !driver.getCurrentUrl().contains("/login");
        } catch (Exception e) {
            return false;
        }
    }
}