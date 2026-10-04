package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LupaPasswordTest extends BaseSeleniumTest {

    @Test
    public void forgot_password_with_registered_email_should_succeed() {
        driver.get(TestConfig.BASE_URL + "/forgot-password");
        
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        
        // Contoh Penggunaan (Silakan lengkapi aksi di ForgotPasswordPage.java):
        // forgotPasswordPage.inputEmail(TestConfig.VALID_USERNAME);
        // forgotPasswordPage.clickReset();
        
        // String successMsg = forgotPasswordPage.getSuccessMessage();
        // Assert.assertTrue(successMsg.contains("berhasil") || successMsg.contains("success"), 
        //         "Pesan notifikasi sukses reset password harus muncul untuk email yang terdaftar");
        
        // Assertion sementara sebagai placeholder
        Assert.assertTrue(true, "Pesan instruksi reset password harus berhasil ditampilkan");
    }

    @Test
    public void forgot_password_with_unregistered_email_should_fail() {
        driver.get(TestConfig.BASE_URL + "/forgot-password");
        
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        
        // Contoh Penggunaan (Silakan lengkapi aksi di ForgotPasswordPage.java):
        // forgotPasswordPage.inputEmail("email.tidak.dikenal@example.com");
        // forgotPasswordPage.clickReset();
        
        // String errorMsg = forgotPasswordPage.getErrorMessage();
        // Assert.assertTrue(errorMsg.contains("tidak terdaftar") || errorMsg.contains("not found"), 
        //         "Sistem harus menampilkan pesan error jika email pemulihan tidak terdaftar di database");
        
        // Assertion sementara sebagai placeholder
        Assert.assertTrue(true, "Pesan peringatan untuk email tidak terdaftar harus ditampilkan");
    }
}
