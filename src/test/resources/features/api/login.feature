Feature: Login API

  Scenario: Login API berhasil dengan kredensial valid
    Given payload login valid dengan password terenkripsi RSA
    When request POST login dikirim ke endpoint login
    Then response status code adalah 200 dan mengembalikan token JWT

  Scenario: Login API gagal dengan password salah
    Given payload login dengan password salah terenkripsi RSA
    When request POST login dikirim ke endpoint login
    Then response mengembalikan error_code kredensial tidak valid
