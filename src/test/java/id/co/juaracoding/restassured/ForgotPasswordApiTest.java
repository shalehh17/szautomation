package id.co.juaracoding.restassured;

import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class ForgotPasswordApiTest extends BaseRestAssuredTest {

    @Test
    public void forgot_password_with_registered_email_should_succeed() {
        String[] captcha = ambilCaptcha();

        // ⚠️ Enkripsi: TIDAK ADA field yang dienkripsi (⛔ dilarang memanggil RsaHelper di sini, kirim email sebagai plaintext)
        String requestBody = String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.VALID_USERNAME, captcha[1], captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/forgot-password");

        // Skenario Berhasil (🔴 Wajib): Email terdaftar -> status code 200
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    @Test
    public void forgot_password_with_unregistered_email_should_fail() {
        String[] captcha = ambilCaptcha();
        String unregisteredEmail = "tidak-terdaftar-" + System.currentTimeMillis() + "@example.com";

        // Email dikirim sebagai plaintext, tanpa RsaHelper
        String requestBody = String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                unregisteredEmail, captcha[1], captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/forgot-password");

        // Skenario Gagal: Email tidak terdaftar -> assert ke error_code (🔴 BUKAN ke field message)
        response.then()
                .body("error_code", anyOf(equalTo("API-ECMXS40401"), equalTo("AUTH_EMAIL_NOT_FOUND"), notNullValue()));
    }
}
