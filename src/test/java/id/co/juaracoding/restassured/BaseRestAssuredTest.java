package id.co.juaracoding.restassured;

import id.co.juaracoding.util.TestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseRestAssuredTest {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setupRestAssured() {
        RestAssured.baseURI = TestConfig.BASE_API_URL;
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("X-API-KEY", TestConfig.X_API_KEY)
                .build();
    }

    protected RequestSpecification specDasar() {
        return RestAssured.given(requestSpec);
    }

    protected String[] ambilCaptcha() {
        try {
            Response response = specDasar().get("/api/v1/captcha");
            String captchaHash = response.jsonPath().getString("data.captcha_hash");
            String captchaValue = response.jsonPath().getString("data.captcha_value");
            return new String[]{
                captchaHash != null ? captchaHash : "mock_captcha_hash",
                captchaValue != null ? captchaValue : "1234"
            };
        } catch (Exception e) {
            return new String[]{"mock_captcha_hash", "1234"};
        }
    }
}
