package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaHelper;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RegisterApiTest extends BaseRestAssuredTest {

    private String angkaUnik() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Test
    public void register_with_valid_data_should_succeed() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();

        String username = "user" + unik.substring(unik.length() - 8);
        String emailPlain = "user" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpPlain = "300" + unik; // 16 digit (3 digit awalan + 13 digit millis)
        String npwpPlain = "310" + unik; // 16 digit
        String birthDatePlain = "2000-01-01";

        // 🔴 Enkripsi: Ada 6 field yang wajib dienkripsi: password, email, birth_date, phone_number, id_card_number, tax_id_number
        // Field lain (username, full_name, address, gender, last_education, dll.) tetap teks biasa
        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Password123@A") + "\","
                + "\"full_name\":\"Peserta SQA\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jl. Sudirman No 123\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(ktpPlain) + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"12345\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar()
                .body(body)
                .post("/api/v1/register");

        // Skenario Berhasil (🔴 Wajib): Response sukses dan data terdaftar
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data", notNullValue());
    }

    @Test
    public void register_with_invalid_id_card_number_should_fail() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();

        String username = "userfail" + unik.substring(unik.length() - 8);
        String emailPlain = "userfail" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        // Misal id_card_number bukan 16 digit (15 digit)
        String ktpTidakSah = RsaHelper.encrypt("123456789012345");
        String npwpPlain = "320" + unik;
        String birthDatePlain = "2000-01-01";

        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Password123@A") + "\","
                + "\"full_name\":\"Peserta SQA\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jl. Sudirman No 123\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + ktpTidakSah + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"12345\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar()
                .body(body)
                .post("/api/v1/register");

        // Skenario Gagal: id_card_number bukan 16 digit -> assert ke error_code (🔴 BUKAN ke field message)
        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("error_code", anyOf(equalTo("API-ECM010001"), equalTo("VALIDATION_ERROR"), notNullValue()));
    }
}
