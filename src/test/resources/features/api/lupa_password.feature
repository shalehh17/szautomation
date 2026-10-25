Feature: Lupa Password API

  Scenario: Reset password API berhasil dengan email terdaftar
    Given payload lupa password dengan email terdaftar tanpa enkripsi RSA
    When request POST lupa password dikirim ke endpoint forgot-password
    Then response status code adalah 200

  Scenario: Reset password API gagal dengan email tidak terdaftar
    Given payload lupa password dengan email tidak terdaftar
    When request POST lupa password dikirim ke endpoint forgot-password
    Then response mengembalikan error_code email tidak ditemukan
