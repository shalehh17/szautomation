package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginApiTest extends BaseRestAssuredTest {

    @Test
    public void login_with_valid_credentials_should_succeed() {
        String[] captcha = ambilCaptcha();

        // 🔴 Enkripsi: HANYA field password yang dienkripsi dengan RsaHelper.encrypt(...)
        String encryptedPassword = RsaHelper.encrypt(TestConfig.VALID_PASSWORD);

        // Field username, captcha_answer, dan captcha_hash tetap teks biasa
        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.VALID_USERNAME, encryptedPassword, captcha[1], captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/login");

        // Skenario Berhasil (🔴 Wajib): Response 200 dan body mengembalikan token JWT
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.token", notNullValue());
    }

    @Test
    public void login_with_invalid_password_should_fail() {
        String[] captcha = ambilCaptcha();

        // 🔴 Enkripsi password salah dengan RsaHelper.encrypt(...)
        String encryptedWrongPassword = RsaHelper.encrypt("PasswordSalah@123");

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.VALID_USERNAME, encryptedWrongPassword, captcha[1], captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/login");

        // Skenario Gagal: Password salah -> assert ke error_code (🔴 BUKAN ke field message)
        response.then()
                .statusCode(401)
                .body("status", equalTo("ERROR"))
                .body("error_code", anyOf(equalTo("API-ECMXS40107"), equalTo("AUTH_INVALID_CREDENTIALS"), notNullValue()));
    }
}
