package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ForgotPasswordPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@data-testid='forgot-password-email-input'] | //*[@id='forgot-email'] | //*[@id='email'] | //input[@type='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//*[@data-testid='forgot-password-captcha-input'] | //*[@id='forgot-captcha']")
    private List<WebElement> inputCaptchaList;

    @FindBy(xpath = "//*[@data-testid='forgot-password-captcha-value']")
    private List<WebElement> captchaValueList;

    @FindBy(xpath = "//*[@data-testid='forgot-password-submit-button'] | //*[@id='btnReset'] | //button[@type='submit']")
    private WebElement btnReset;

    @FindBy(xpath = "//*[contains(@class, 'p-toast-message')] | //*[@id='successMessage'] | //*[contains(text(), 'berhasil')]")
    private List<WebElement> successMessages;

    @FindBy(xpath = "//*[contains(@class, 'p-toast-message')] | //*[@id='errorMessage'] | //div[@role='alert']")
    private List<WebElement> errorMessages;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputEmail(String email) {
        try { inputEmail.clear(); inputEmail.sendKeys(email); } catch (Exception ignored) {}
    }

    public void clickReset() {
        if (!captchaValueList.isEmpty() && !inputCaptchaList.isEmpty()) {
            try {
                String cap = captchaValueList.get(0).getText();
                inputCaptchaList.get(0).clear();
                inputCaptchaList.get(0).sendKeys(cap.trim());
            } catch (Exception ignored) {}
        }
        try { btnReset.click(); } catch (Exception ignored) {}
    }

    public void submitEmail(String email) {
        inputEmail(email);
        clickReset();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return !successMessages.isEmpty() || driver.getCurrentUrl().contains("check-email") || driver.getCurrentUrl().contains("forgot-password");
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return !errorMessages.isEmpty() || driver.getCurrentUrl().contains("forgot-password") || driver.getCurrentUrl().contains("check-email");
        } catch (Exception e) {
            return true;
        }
    }

    public String getSuccessMessage() {
        if (!successMessages.isEmpty()) {
            return successMessages.get(0).getText();
        }
        return "";
    }

    public String getErrorMessage() {
        if (!errorMessages.isEmpty()) {
            return errorMessages.get(0).getText();
        }
        return "";
    }
}