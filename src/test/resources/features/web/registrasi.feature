Feature: Registrasi Web

  Scenario: Registrasi berhasil dengan data lengkap dan valid
    Given user berada di halaman registrasi web
    When user mengisi formulir registrasi dengan data valid
    Then akun baru berhasil terdaftar di web

  Scenario: Registrasi gagal dengan nomor identitas tidak sesuai ketentuan
    Given user berada di halaman registrasi web
    When user mengisi nomor identitas bukan 16 digit
    Then muncul pesan kesalahan validasi registrasi
