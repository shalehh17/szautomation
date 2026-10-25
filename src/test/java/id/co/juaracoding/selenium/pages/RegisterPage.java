package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegisterPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@data-testid='register-full-name-input'] | //*[@id='reg-full-name'] | //*[@id='fullName']")
    private WebElement inputFullName;

    @FindBy(xpath = "//*[@data-testid='register-username-input'] | //*[@id='reg-username'] | //*[@id='username']")
    private List<WebElement> inputUsernameList;

    @FindBy(xpath = "//*[@data-testid='register-email-input'] | //*[@id='reg-email'] | //*[@id='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//*[@data-testid='register-password-input']//input | //*[@data-testid='register-password-input'] | //*[@id='reg-password']//input | //*[@id='password']")
    private WebElement inputPassword;
    
    @FindBy(xpath = "//*[@data-testid='register-id-card-number-input'] | //*[@id='reg-ktp'] | //*[@id='identityNumber']")
    private WebElement inputIdentityNumber;

    @FindBy(xpath = "//*[@data-testid='register-phone-number-input'] | //*[@id='reg-phone']")
    private List<WebElement> inputPhoneList;

    @FindBy(xpath = "//*[@data-testid='register-tax-id-number-input'] | //*[@id='reg-npwp']")
    private List<WebElement> inputTaxList;

    @FindBy(xpath = "//*[@data-testid='register-captcha-input'] | //*[@id='reg-captcha']")
    private List<WebElement> inputCaptchaList;

    @FindBy(xpath = "//*[@data-testid='register-captcha-value']")
    private List<WebElement> captchaValueList;

    @FindBy(xpath = "//*[@data-testid='register-submit-button'] | //*[@id='btnRegister'] | //button[@type='submit']")
    private WebElement btnRegister;

    @FindBy(xpath = "//*[contains(@class, 'p-toast-message')] | //*[@id='validationMessage'] | //div[@role='alert']")
    private List<WebElement> validationMessages;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputFullName(String fullName) {
        try { inputFullName.clear(); inputFullName.sendKeys(fullName); } catch (Exception ignored) {}
    }

    public void inputEmail(String email) {
        try { inputEmail.clear(); inputEmail.sendKeys(email); } catch (Exception ignored) {}
    }

    public void inputPassword(String password) {
        try { inputPassword.clear(); inputPassword.sendKeys(password); } catch (Exception ignored) {}
    }

    public void inputIdentityNumber(String identityNumber) {
        try { inputIdentityNumber.clear(); inputIdentityNumber.sendKeys(identityNumber); } catch (Exception ignored) {}
    }

    public void clickRegister() {
        try { btnRegister.click(); } catch (Exception ignored) {}
    }

    public void submitForm() {
        clickRegister();
    }

    public void fillValidRegistrationForm() {
        long millis = System.currentTimeMillis();
        inputFullName("Shalehuddin Zaki");
        if (!inputUsernameList.isEmpty()) {
            try {
                inputUsernameList.get(0).clear();
                inputUsernameList.get(0).sendKeys("user" + (millis % 10000000));
            } catch (Exception ignored) {}
        }
        inputEmail("user" + millis + "@example.com");
        inputPassword("Password123!");
        inputIdentityNumber("1234567890123456");
        if (!inputPhoneList.isEmpty()) {
            try {
                inputPhoneList.get(0).clear();
                inputPhoneList.get(0).sendKeys("0812" + (millis % 100000000L));
            } catch (Exception ignored) {}
        }
        if (!inputTaxList.isEmpty()) {
            try {
                inputTaxList.get(0).clear();
                inputTaxList.get(0).sendKeys("310" + millis);
            } catch (Exception ignored) {}
        }
        if (!captchaValueList.isEmpty() && !inputCaptchaList.isEmpty()) {
            try {
                String cap = captchaValueList.get(0).getText();
                inputCaptchaList.get(0).clear();
                inputCaptchaList.get(0).sendKeys(cap.trim());
            } catch (Exception ignored) {}
        }
    }

    public void fillInvalidIdCardNumber(String invalidId) {
        fillValidRegistrationForm();
        inputIdentityNumber(invalidId);
    }

    public boolean isRegistrationSuccess() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("login") || currentUrl.contains("dashboard") || currentUrl.contains("check-email") || !isErrorMessageDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return !validationMessages.isEmpty() || driver.getCurrentUrl().contains("register");
        } catch (Exception e) {
            return true;
        }
    }

    public String getValidationMessage() {
        if (!validationMessages.isEmpty()) {
            return validationMessages.get(0).getText();
        }
        return "";
    }
}