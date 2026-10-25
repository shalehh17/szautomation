Feature: Login Web

  Scenario: Login berhasil dengan kredensial valid
    Given user berada di halaman login web
    When user login dengan username dan password yang valid
    Then user berhasil diarahkan ke halaman dashboard

  Scenario: Login gagal dengan password salah
    Given user berada di halaman login web
    When user login dengan password yang salah
    Then muncul pesan kesalahan login pada web
