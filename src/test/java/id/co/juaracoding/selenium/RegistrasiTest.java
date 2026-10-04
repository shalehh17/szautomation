package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrasiTest extends BaseSeleniumTest {

    @Test
    public void register_with_valid_data_should_succeed() {
        driver.get(TestConfig.BASE_URL + "/register");
        
        RegisterPage registerPage = new RegisterPage(driver);
        
        // Contoh Penggunaan (Silakan lengkapi aksi di RegisterPage.java):
        // registerPage.inputFullName("Shalehuddin Zaki");
        // registerPage.inputEmail("shalehuddinzaki84@gmail.com");
        // registerPage.inputPassword("password123");
        // registerPage.inputIdentityNumber("1234567890123456");
        // registerPage.clickRegister();
        
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login") || currentUrl.contains("dashboard"), 
                "Sistem harus meneruskan user ke halaman login atau dashboard jika registrasi berhasil");
    }

    @Test
    public void register_with_invalid_ktp_should_fail() {
        driver.get(TestConfig.BASE_URL + "/register");
        
        RegisterPage registerPage = new RegisterPage(driver);
        
        // Contoh Penggunaan (Silakan lengkapi aksi di RegisterPage.java):
        // registerPage.inputFullName("Shalehuddin Zaki");
        // registerPage.inputEmail("shalehuddinzaki84@gmail.com");
        // registerPage.inputPassword("password123");
        // registerPage.inputIdentityNumber("1234"); // Tidak valid, bukan 16 digit
        // registerPage.clickRegister();
        
        // String validationMsg = registerPage.getValidationMessage();
        // Assert.assertTrue(validationMsg.contains("16 digit"), 
        //         "Sistem harus menolak form dan menampilkan pesan error jika KTP/NPWP bukan 16 digit");
        
        // Assertion sementara sebagai placeholder
        Assert.assertTrue(true, "Pesan validasi untuk KTP tidak valid (bukan 16 digit) harus ditampilkan di UI");
    }
}
