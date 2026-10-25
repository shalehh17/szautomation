Feature: Registrasi API

  Scenario: Registrasi API berhasil dengan data valid
    Given payload registrasi lengkap dengan 6 field terenkripsi RSA
    When request POST registrasi dikirim ke endpoint register
    Then response status code adalah 200 atau mengembalikan kode sukses

  Scenario: Registrasi API gagal dengan nomor identitas tidak 16 digit
    Given payload registrasi dengan nomor KTP tidak 16 digit
    When request POST registrasi dikirim ke endpoint register
    Then response mengembalikan error_code validasi identitas
