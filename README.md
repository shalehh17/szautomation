# 🚀 SZ Automation Framework

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium_WebDriver-43B02A?style=for-the-badge&logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![REST Assured](https://img.shields.io/badge/REST_Assured-2E8555?style=for-the-badge&logo=fastapi&logoColor=white)](https://rest-assured.io/)
[![Cucumber BDD](https://img.shields.io/badge/Cucumber_BDD-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io/)
[![TestNG](https://img.shields.io/badge/TestNG-FF6C37?style=for-the-badge&logo=testng&logoColor=white)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

An enterprise-grade **End-to-End Test Automation Framework** built with Java to deliver comprehensive test coverage for both **Web User Interface (Web UI)** and **RESTful API Services**. Developed as part of the **SQA-RPA With Artificial Intelligence** bootcamp (Batch 30), this repository houses automated test suites designed for the *Simple Apps* platform.

---

## 📑 Daftar Isi (Table of Contents)
- [Arsitektur & Modul Pengujian](#-arsitektur--modul-pengujian)
  - [1. TestNG & Core Utilities](#1-testng--core-utilities)
  - [2. Web UI Automation (Selenium WebDriver)](#2-web-ui-automation-selenium-webdriver)
  - [3. RESTful API Automation (REST Assured)](#3-restful-api-automation-rest-assured)
  - [4. Behavior-Driven Development (Cucumber BDD)](#4-behavior-driven-development-cucumber-bdd)
- [Struktur Direktori (Project Structure)](#-struktur-direktori-project-structure)
- [Teknologi & Dependensi](#-teknologi--dependensi)
- [Prasyarat Sistem (Prerequisites)](#-prasyarat-sistem-prerequisites)
- [Konfigurasi Proyek (Configuration)](#-konfigurasi-proyek-configuration)
- [Cara Menjalankan Pengujian (Test Execution)](#-cara-menjalankan-pengujian-test-execution)
  - [Menjalankan Seluruh Suite](#menjalankan-seluruh-suite)
  - [Menjalankan Per Modul Spesifik](#menjalankan-per-modul-spesifik)
- [Laporan Pengujian (Test Reporting)](#-laporan-pengujian-test-reporting)
- [Profil Pembuat (Author)](#-profil-pembuat-author)

---

## 🎯 Arsitektur & Modul Pengujian

Repository ini mengintegrasikan 4 pilar utama pengujian otomasi:

### 1. TestNG & Core Utilities
* Pengujian level unit & logika algoritma menggunakan framework **TestNG**.
* Validasi fungsi utilitas teks (palindrom, perhitungan vokal, penanganan string kosong/null, dan *edge cases*).
* Manajemen *test runner* melalui file konfigurasi XML (`testng.xml`).

### 2. Web UI Automation (Selenium WebDriver)
* Dibangun menggunakan pola desain **Page Object Model (POM)** dan Selenium **PageFactory** untuk memisahkan abstraksi elemen antarmuka (locators) dari alur logika tes (*clean code & high maintainability*).
* Didukung oleh **Selenium Manager** (Selenium 4.21+) yang otomatis mengunduh dan menyinkronkan driver browser tanpa setup manual.
* **Cakupan Skenario:**
  * **Autentikasi & Login:** Validasi login berhasil (*positive flow* mengarah ke `/dashboard`), serta penolakan login dengan kredensial salah (*negative flow*).
  * **Registrasi Akun:** Form pendaftaran pengguna baru dengan validasi format KTP/NPWP 16-digit.
  * **Lupa Password:** Permintaan tautan reset password dengan validasi status akun.

### 3. RESTful API Automation (REST Assured)
* Pengujian otomatisasi layanan backend RESTful microservices.
* **Fitur Utama:**
  * Validasi HTTP Response Status Code (`200 OK`, `400 Bad Request`, `401 Unauthorized`, `404 Not Found`).
  * Asersi payload respon JSON dan verifikasi integritas data.
  * **Keamanan API:** Otentikasi lisensi header (`X-API-KEY`) dan manajemen JWT Token.
  * **Enkripsi RSA:** Menggunakan helper utilitas `RsaHelper` untuk mengenkripsi payload data sensitif sebelum dikirim ke endpoint aplikasi.

### 4. Behavior-Driven Development (Cucumber BDD)
* Menjembatani skenario bisnis ke dalam langkah-langkah pengujian teknis yang mudah dibaca menggunakan sintaks **Gherkin** (`Given - When - Then`).
* Memiliki dual-runner mandiri:
  * `CucumberWebRunner`: Menjalankan seluruh skenario UI Gherkin.
  * `CucumberApiRunner`: Menjalankan seluruh skenario API Gherkin.
* Menyediakan skenario positif dan negatif lengkap di folder `src/test/resources/features/`.

---

## 📂 Struktur Direktori (Project Structure)

```text
szautomation/
├── pom.xml                                      # Konfigurasi Maven dependencies & build plugins
├── testng.xml                                   # TestNG suite runner XML
├── README.md                                    # Dokumentasi proyek
├── .gitignore                                   # File eksklusi Git
│
├── src/main/java/id/co/juaracoding/
│   └── util/
│       └── TextUtil.java                        # Kelas logika utilitas pemrosesan teks
│
└── src/test/
    ├── java/id/co/juaracoding/
    │   ├── cucumber/                            # Modul BDD Cucumber
    │   │   ├── api/
    │   │   │   ├── ApiSteps.java                # Step definitions untuk pengujian REST API
    │   │   │   └── CucumberApiRunner.java       # TestNG Runner untuk Cucumber API
    │   │   └── web/
    │   │       ├── WebSteps.java                # Step definitions untuk pengujian Web UI
    │   │       └── CucumberWebRunner.java       # TestNG Runner untuk Cucumber Web
    │   │
    │   ├── restassured/                         # Modul Pengujian REST API
    │   │   ├── BaseRestAssuredTest.java         # Konfigurasi dasar RestAssured (Base URI & Headers)
    │   │   ├── LoginApiTest.java                # Test suite API login & token retrieval
    │   │   ├── RegisterApiTest.java             # Test suite API registrasi pengguna
    │   │   ├── ForgotPasswordApiTest.java       # Test suite API forgot password
    │   │   └── util/
    │   │       └── RsaHelper.java               # Helper kriptografi enkripsi payload RSA
    │   │
    │   ├── selenium/                            # Modul Pengujian Web UI (Selenium WebDriver)
    │   │   ├── BaseSeleniumTest.java            # Setup WebDriver (@BeforeMethod) & Teardown (@AfterMethod)
    │   │   ├── LoginTest.java                   # Test suite skenario Login
    │   │   ├── RegistrasiTest.java              # Test suite skenario Registrasi
    │   │   ├── LupaPasswordTest.java            # Test suite skenario Reset Password
    │   │   └── pages/                           # Page Object Model (POM) Classes
    │   │       ├── LoginPage.java
    │   │       ├── RegisterPage.java
    │   │       └── ForgotPasswordPage.java
    │   │
    │   ├── testng/                              # Modul Pengujian TestNG Dasar
    │   │   └── TextUtilTest.java                # Pengujian unit logic & edge cases
    │   │
    │   └── util/
    │       └── TestConfig.java                  # Konfigurasi terpusat (URL, User Seed, API Keys)
    │
    └── resources/
        └── features/                            # Gherkin Feature Files (BDD)
            ├── api/
            │   ├── login.feature
            │   ├── registrasi.feature
            │   └── lupa_password.feature
            └── web/
                ├── login.feature
                ├── registrasi.feature
                └── lupa_password.feature
```

---

## 🛠 Teknologi & Dependensi

| Kategori | Teknologi | Versi | Keterangan |
| :--- | :--- | :--- | :--- |
| **Bahasa Pemrograman** | Java | 11 / 21 | Bahasa utama framework otomasi |
| **Build & Dependency Tool** | Apache Maven | 3.8+ | Manajemen dependensi dan siklus build |
| **Unit Testing Framework** | TestNG | 7.8.0 | Test runner, assertions, dan test lifecycle |
| **UI Automation** | Selenium Java | 4.21.0 | Automasi browser berbasis Chrome WebDriver |
| **API Automation** | REST Assured | 5.4.0 | Library pengujian HTTP request & JSON response |
| **BDD Framework** | Cucumber Java & TestNG | 7.18.0 | Behavioral testing dengan sintaks Gherkin |
| **JSON Parser** | Jackson Databind | 2.17.2 | Serialisasi dan deserialisasi payload JSON |

---

## ⚙️ Prasyarat Sistem (Prerequisites)

Sebelum menjalankan pengujian, pastikan perangkat Anda telah terpasang:
1. **Java Development Kit (JDK 11 atau 21)**
   ```bash
   java -version
   ```
2. **Apache Maven**
   ```bash
   mvn -version
   ```
3. **Google Chrome Browser** (terbaru).
4. **Target Aplikasi (Simple Apps)**:
   Aplikasi target `simple-apps-backend-0.0.1-SNAPSHOT.jar` harus berjalan secara lokal:
   ```bash
   java -jar simple-apps-backend-0.0.1-SNAPSHOT.jar
   # Akses Web UI: http://localhost:8080
   ```

---

## 🔧 Konfigurasi Proyek (Configuration)

Konfigurasi aplikasi pengujian dikelola secara terpusat pada file:
👉 `src/test/java/id/co/juaracoding/util/TestConfig.java`

```java
public class TestConfig {
    public static final String BASE_URL = "http://localhost:8080"; 
    public static final String BASE_API_URL = BASE_URL;
    
    // Kredensial akun uji bawaan (Seed User)
    public static final String VALID_USERNAME = "admin1";
    public static final String VALID_PASSWORD = "Admin1@123";

    // Lisensi API Key untuk otentikasi REST API
    public static final String X_API_KEY = "d461265dd7323fef9755bb3257275d67";
}
```

---

## 🧪 Cara Menjalankan Pengujian (Test Execution)

### Menjalankan Seluruh Suite
Untuk mengompilasi dan mengeksekusi suite pengujian default melalui Maven:
```bash
mvn clean test
```

### Menjalankan Per Modul Spesifik

#### 1. Menjalankan Pengujian TestNG (Unit / Logic):
```bash
mvn test -Dtest=TextUtilTest
```

#### 2. Menjalankan Pengujian Web UI (Selenium):
```bash
# Menjalankan seluruh test class UI
mvn test -Dtest=LoginTest,RegistrasiTest,LupaPasswordTest

# Menjalankan spesifik test Login
mvn test -Dtest=LoginTest
```

#### 3. Menjalankan Pengujian REST API (REST Assured):
```bash
# Menjalankan seluruh API test
mvn test -Dtest=LoginApiTest,RegisterApiTest,ForgotPasswordApiTest

# Menjalankan spesifik API Login
mvn test -Dtest=LoginApiTest
```

#### 4. Menjalankan Pengujian BDD (Cucumber):
```bash
# Menjalankan Cucumber Web BDD Runner
mvn test -Dtest=CucumberWebRunner

# Menjalankan Cucumber API BDD Runner
mvn test -Dtest=CucumberApiRunner
```

---

## 📊 Laporan Pengujian (Test Reporting)

Setelah pengujian selesai dijalankan, laporan eksekusi otomatis digenerate pada direktori `target/`:

* **TestNG HTML Report:**  
  `target/surefire-reports/emailable-report.html` & `target/surefire-reports/index.html`
* **Cucumber BDD Web Report:**  
  `target/cucumber-reports/web/cucumber.html`
* **Cucumber BDD API Report:**  
  `target/cucumber-reports/api/cucumber.html`

---

## 👤 Profil Pembuat (Author)

* **Nama:** Shalehuddin Zaki
* **Program:** Bootcamp SQA-RPA With Artificial Intelligence
* **Batch:** 30
* **Mitra Pelatihan:** JuaraCoding
* **GitHub Repository:** [https://github.com/shalehh17/szautomation](https://github.com/shalehh17/szautomation)
