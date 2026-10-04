package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseSeleniumTest {

    @Test
    public void login_with_valid_credentials_should_succeed() {
        // Asumsi URL login berada di "/login"
        driver.get(TestConfig.BASE_URL + "/login");
        
        LoginPage loginPage = new LoginPage(driver);
        // Aksi login menggunakan kredensial yang valid dari TestConfig
        loginPage.login(TestConfig.VALID_USERNAME, TestConfig.VALID_PASSWORD);
        
        // Verifikasi berhasil masuk
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"), 
                "User harus diarahkan ke halaman dashboard atau home setelah login sukses");
    }

    @Test
    public void login_with_invalid_password_should_fail() {
        driver.get(TestConfig.BASE_URL + "/login");
        
        LoginPage loginPage = new LoginPage(driver);
        // Aksi login dengan password yang salah
        loginPage.login(TestConfig.VALID_USERNAME, "salahpassword123");
        
        // Verifikasi pesan error
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null, 
                "Pesan error harus muncul dan dapat ditangkap ketika password salah");
    }
}
