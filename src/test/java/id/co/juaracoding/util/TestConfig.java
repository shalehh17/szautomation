package id.co.juaracoding.util;

public class TestConfig {
    // Sesuaikan port URL dengan yang berjalan di Simple Apps Anda (lihat VERIFY-aplikasi.docx)
    public static final String BASE_URL = "http://localhost:8080"; 
    
    // Kredensial akun seed (lihat README §5 Simple Apps)
    public static final String VALID_USERNAME = "admin1";
    public static final String VALID_PASSWORD = "Admin1@123";

    // Tambahan untuk RestAssured:
    public static final String BASE_API_URL = BASE_URL; // Gunakan host & port backend Simple Apps
    public static final String X_API_KEY = "d461265dd7323fef9755bb3257275d67"; // Kunci lisensi dari README Simple Apps §4
}
