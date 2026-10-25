Feature: Lupa Password Web

  Scenario: Permintaan reset password berhasil dengan email terdaftar
    Given user berada di halaman lupa password web
    When user mengirimkan email yang terdaftar
    Then notifikasi proses reset password berhasil ditampilkan

  Scenario: Permintaan reset password gagal dengan email tidak terdaftar
    Given user berada di halaman lupa password web
    When user mengirimkan email yang tidak terdaftar
    Then muncul pesan error bahwa email tidak ditemukan
