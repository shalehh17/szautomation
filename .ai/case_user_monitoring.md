# Test Case Pengujian Fitur User Monitoring - HADIR

**Tujuan:** Menjadi katalog test case manual dan dasar otomasi komprehensif untuk modul **User Monitoring** pada portal HADIR (`https://magang.dikahadir.com/management/user-monitoring`) menggunakan Selenium WebDriver dan REST Assured.

---

## 1. Konteks Halaman dan Matriks Data Uji

Halaman **User Monitoring** digunakan oleh tim manajemen/administrator untuk memantau data akun karyawan, mencari akun berdasarkan nama/email, memfilter berdasarkan unit kerja, menambah user monitoring baru, memperbarui akun, dan menghapus akun dari daftar pemantauan.

### Matriks Data Uji (Test Data Matrix):

| Kategori Data | Contoh Nilai Data | Kegunaan |
|:---|:---|:---|
| **Nama User Valid** | `Ahmad Fauzi`, `Bella Saphira`, `Cahyo Nugroho` | Pencarian nama tepat & penambahan akun valid |
| **Substring / Parsial** | `Ahmad`, `Bella`, `Fauzi`, `Nugroho` | Uji pencarian nama parsial & case-insensitive |
| **Email Valid** | `user.baru@hadir.com`, `monitor.test@corp.id` | Uji validasi input email pada tambah & edit |
| **Email Format Rusak** | `email-tanpa-domain`, `user@.com`, `@domain.com` | Validasi penolakan format email tidak standar |
| **Password Valid** | `Password123!`, `Rahasia@2026` | Uji input password pada modal tambah |
| **Unit Kerja Terdaftar** | `Technology`, `Human Resources`, `Finance & Accounting`, `Marketing`, `Operations` | Pilihan dropdown unit kerja |
| **Pencarian Fiktif** | `UserFiktif999XYZ`, `NonExistentName` | Uji pesan tabel kosong (`Tidak Ada Data`) |
| **Injeksi XSS** | `<script>alert('xss')</script>`, `<img src=x onerror=alert(1)>` | Uji keamanan sanitasi input pencarian dan form |
| **Injeksi SQL** | `' OR '1'='1`, `'; DROP TABLE users; --` | Uji ketahanan terhadap injeksi database |
| **String Batas (Boundary)** | Karakter 255+ / 300 karakter acak | Uji stabilitas buffer input nama |
| **Karakter Unicode/Emoji** | `👤⚡🔥#*&`, `Üser Möñïtorïng` | Uji ketahanan encoding karakter |

---

## 2. Katalog Kasus Uji Lengkap (Test Cases)

### Kategori 1: UI Elements & Layout Smoke (@ui-elements)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `UI-001` | Membuka halaman User Monitoring | Buka URL -> Judul `User Monitoring` tampil utuh | P1 |
| `UI-002` | Verifikasi breadcrumbs navigasi | Periksa breadcrumbs -> Menampilkan `Management / User Monitoring` | P3 |
| `UI-003` | Verifikasi form pencarian nama | Periksa field `#search` -> Placeholder `cari berdasarkan nama` tampil | P1 |
| `UI-004` | Verifikasi tombol Filter Unit | Periksa tombol `#btn-filter` -> Tampil dengan ikon filter | P2 |
| `UI-005` | Verifikasi tombol Reset | Periksa tombol `#btn-reset` -> Tampil berdampingan dengan tombol search | P2 |
| `UI-006` | Verifikasi tombol Search | Periksa tombol `#btn-search` -> Tampil dengan label `Search` | P1 |
| `UI-007` | Verifikasi tombol Tambahkan | Periksa tombol `#btn-tambah` -> Tampil dengan label `Tambahkan` | P1 |
| `UI-008` | Verifikasi header kolom tabel (6 kolom) | Periksa header tabel -> Tampil FOTO, NAMA, EMAIL, UNIT, TANGGAL DIBUAT, AKSI | P1 |
| `UI-009` | Verifikasi baris data tabel | Periksa tbody -> Data karyawan memuat avatar, nama tebal, email, badge unit, dan tanggal | P1 |
| `UI-010` | Verifikasi tombol aksi Edit per baris | Periksa baris data -> Tombol pensil Edit (`title="Edit"`) tampil dan aktif | P1 |
| `UI-011` | Verifikasi tombol aksi Delete per baris | Periksa baris data -> Tombol tempat sampah Delete (`title="Delete"`) tampil dan aktif | P1 |
| `UI-012` | Verifikasi kontrol paginasi baris | Periksa bagian bawah tabel -> Dropdown rows per page dan info displayed rows tampil | P2 |
| `UI-013` | Verifikasi tombol navigasi halaman | Periksa bar paginasi -> Tombol First, Prev, Next, Last tampil utuh | P2 |
| `UI-014` | Verifikasi checkbox Dense padding | Periksa bar paginasi -> Checkbox `Dense padding` tampil | P3 |
| `UI-015` | Verifikasi kontainer toast snackbar | Periksa DOM -> Kontainer `#alert-snackbar` siap menerima event notifikasi | P2 |

---

### Kategori 2: Pencarian / Search (@search)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `SRC-001` | Cari user dengan nama lengkap tepat | Masukkan `"Ahmad Fauzi"` -> Tabel hanya menampilkan baris Ahmad Fauzi | P1 |
| `SRC-002` | Cari user dengan potongan nama awal (substring) | Masukkan `"Ahmad"` -> Seluruh karyawan bernama depan Ahmad ditampilkan | P1 |
| `SRC-003` | Cari user dengan huruf kecil semua (lowercase) | Masukkan `"bella saphira"` -> Sistem mencocokkan secara case-insensitive | P2 |
| `SRC-004` | Cari user dengan huruf kapital semua (uppercase) | Masukkan `"CAHYO"` -> Sistem mencocokkan nama Cahyo Nugroho | P2 |
| `SRC-005` | Cari user berdasarkan alamat email | Masukkan `"dewi.anggraini@hadir.com"` -> Baris karyawan Dewi Anggraini tampil | P1 |
| `SRC-006` | Cari user dengan nama tidak terdaftar | Masukkan `"NamaFiktif999XYZ"` -> Tabel menampilkan pesan `Tidak Ada Data` | P1 |
| `SRC-007` | Tekan tombol Enter pada field search | Ketik `"Eko"` lalu tekan Enter -> Pencarian dieksekusi tanpa klik tombol Cari | P2 |
| `SRC-008` | Cari dengan spasi di awal dan akhir (trailing spaces) | Masukkan `"   Farhan   "` -> Sistem melakukan auto-trim dan menampilkan Farhan | P2 |
| `SRC-009` | Reset hasil pencarian via tombol Reset | Setelah cari -> Klik `Reset` -> Field kosong dan seluruh data kembali tampil | P1 |
| `SRC-010` | Pencarian dengan kata kunci 1 karakter | Masukkan `"a"` -> Seluruh nama yang memuat huruf "a" ditampilkan | P3 |
| `SRC-011` | Perbarui info displayed rows setelah pencarian | Cari nama -> Label `displayed-rows` menampilkan rentang baris hasil pencarian | P2 |
| `SRC-012` | Tombol navigasi disabled pada 1 halaman hasil cari | Cari nama 1 baris -> Tombol Next, Prev, First, Last otomatis disabled | P3 |
| `SRC-013` | Buka modal filter unit | Klik tombol `#btn-filter` -> Modal dialog Filter User Monitoring terbuka | P2 |
| `SRC-014` | Filter unit Technology | Pilih unit `Technology` -> Klik `Terapkan` -> Tabel hanya menampilkan unit Technology | P1 |
| `SRC-015` | Filter unit tanpa data | Pilih `Unit Kosong` -> Klik `Terapkan` -> Tabel menampilkan `Tidak Ada Data` | P2 |

---

### Kategori 3: Tambah Data User Monitoring (@add)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `ADD-001` | Membuka modal dialog Tambah User | Klik `Tambahkan` -> Modal dialog `Add User Monitoring` ditampilkan | P1 |
| `ADD-002` | Verifikasi field wajib pada form tambah | Periksa form -> Field Nama User, Email, Password, Unit bertanda wajib/required | P2 |
| `ADD-003` | Tambah user monitoring dengan seluruh data valid | Isi Nama, Email, Password, Unit -> Klik `Tambah` -> Toast sukses tampil | P1 |
| `ADD-004` | Verifikasi user baru masuk ke daftar tabel | Setelah tambah sukses -> Baris user baru muncul di urutan pertama tabel | P1 |
| `ADD-005` | Kosongkan field Nama User saat tambah | Isi Email, Password, Unit, kosongkan Nama -> Klik `Tambah` -> Form cegah submit | P1 |
| `ADD-006` | Kosongkan field Email saat tambah | Isi Nama, Password, Unit, kosongkan Email -> Klik `Tambah` -> Form cegah submit | P1 |
| `ADD-007` | Kosongkan field Password saat tambah | Isi Nama, Email, Unit, kosongkan Password -> Klik `Tambah` -> Form cegah submit | P1 |
| `ADD-008` | Tidak memilih unit kerja saat tambah | Isi Nama, Email, Password, biarkan Unit kosong -> Klik `Tambah` -> Form cegah submit | P1 |
| `ADD-009` | Tambah user dengan format email salah | Masukkan email `"bukan-email-valid"` -> Form memvalidasi tipe email HTML5 | P2 |
| `ADD-010` | Batal tambah user via tombol Batal | Buka modal tambah -> Isi data -> Klik `Batal` -> Modal tertutup tanpa simpan data | P2 |
| `ADD-011` | Tutup modal tambah via tombol close (X) | Buka modal tambah -> Klik tombol X -> Modal tertutup seketika | P3 |
| `ADD-012` | Tutup modal tambah via tombol Escape | Buka modal tambah -> Tekan tombol keyboard `Escape` -> Modal tertutup | P3 |
| `ADD-013` | Pembaruan total count paginasi setelah tambah | Tambah 1 user -> Label paginasi bertambah 1 total baris | P2 |
| `ADD-014` | Tambah user pada unit Human Resources | Pilih unit `Human Resources` -> User tersimpan dengan badge HR | P2 |
| `ADD-015` | Tambah user pada unit Operations | Pilih unit `Operations` -> User tersimpan dengan badge Operations | P2 |

---

### Kategori 4: Edit Data User Monitoring (@edit)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `EDT-001` | Membuka modal dialog Edit User | Klik tombol Edit baris ke-1 -> Modal dialog `Edit User Monitoring` ditampilkan | P1 |
| `EDT-002` | Verifikasi pre-filled data Nama User | Buka modal edit -> Field Nama User otomatis terisi sesuai baris data | P1 |
| `EDT-003` | Verifikasi pre-filled data Email User | Buka modal edit -> Field Email otomatis terisi sesuai baris data | P1 |
| `EDT-004` | Verifikasi pre-filled data Unit User | Buka modal edit -> Dropdown Unit otomatis memilih unit karyawan tersebut | P1 |
| `EDT-005` | Ubah Nama User menjadi nama baru valid | Ubah nama menjadi `"Ahmad Fauzi Perkasa"` -> Klik `Simpan` -> Nama baris terupdate | P1 |
| `EDT-006` | Ubah Unit kerja karyawan menjadi Marketing | Ganti unit ke `Marketing` -> Klik `Simpan` -> Badge unit baris ke-1 terupdate Marketing | P1 |
| `EDT-007` | Ubah Unit kerja karyawan menjadi Technology | Ganti unit ke `Technology` -> Klik `Simpan` -> Badge unit terupdate Technology | P1 |
| `EDT-008` | Simpan tanpa mengubah data apapun | Buka modal edit -> Langsung klik `Simpan` -> Toast sukses tampil dan modal tertutup | P2 |
| `EDT-009` | Kosongkan field Nama User saat edit | Hapus nama hingga kosong -> Klik `Simpan` -> Form mencegah penyimpanan | P1 |
| `EDT-010` | Kosongkan field Email saat edit | Hapus email hingga kosong -> Klik `Simpan` -> Form mencegah penyimpanan | P1 |
| `EDT-011` | Batal edit via tombol Batal | Ubah nama -> Klik tombol `Batal` -> Modal tertutup dan data tabel tidak berubah | P2 |
| `EDT-012` | Tutup modal edit via tombol close (X) | Buka modal edit -> Klik tombol close X -> Modal tertutup tanpa simpan | P3 |
| `EDT-013` | Tutup modal edit via tombol Escape | Buka modal edit -> Tekan `Escape` -> Modal tertutup tanpa simpan | P3 |
| `EDT-014` | Verifikasi teks pesan toast sukses edit | Simpan edit -> Toast notifikasi memuat `"Berhasil ubah user monitoring"` | P2 |
| `EDT-015` | Edit user setelah pencarian aktif | Cari user -> Klik Edit pada hasil cari -> Ubah unit -> Data hasil cari terupdate | P2 |

---

### Kategori 5: Hapus Data User Monitoring (@delete)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `DEL-001` | Membuka modal konfirmasi Hapus User | Klik tombol Delete pada baris ke-1 -> Modal `Hapus User Monitoring` tampil | P1 |
| `DEL-002` | Verifikasi teks konfirmasi hapus | Periksa teks modal -> Memuat `Apakah anda yakin ingin menghapus user monitoring ini?` | P2 |
| `DEL-003` | Batalkan penghapusan via tombol Tidak | Buka modal hapus -> Klik `Tidak` -> Modal tertutup dan data tetap ada di tabel | P1 |
| `DEL-004` | Tutup modal hapus via tombol close (X) | Buka modal hapus -> Klik tombol X -> Modal tertutup tanpa menghapus data | P3 |
| `DEL-005` | Tutup modal hapus via tombol Escape | Buka modal hapus -> Tekan tombol `Escape` -> Modal tertutup tanpa hapus | P3 |
| `DEL-006` | Konfirmasi hapus user via tombol Hapus | Buka modal hapus -> Klik `Hapus` -> Toast `"Berhasil menghapus user monitoring"` tampil | P1 |
| `DEL-007` | Verifikasi baris benar-benar hilang dari tabel | Catat nama baris 1 -> Hapus -> User tersebut tidak lagi terlihat di tabel | P1 |
| `DEL-008` | Pembaruan total count paginasi setelah hapus | Hapus 1 baris user -> Total data pada label paginasi berkurang 1 | P2 |
| `DEL-009` | Hapus seluruh baris data hingga tabel kosong | Hapus seluruh data -> Tabel menampilkan pesan `Tidak Ada Data` | P2 |
| `DEL-010` | Hapus baris data pada halaman kedua tabel | Pindah ke halaman 2 -> Hapus baris ke-1 -> Baris terhapus dan tabel merefresh | P2 |
| `DEL-011` | Verifikasi teks pesan toast sukses hapus | Hapus user -> Toast memuat teks `"Berhasil menghapus user monitoring"` | P2 |
| `DEL-012` | Hapus user saat hasil filter unit aktif | Filter unit `Technology` -> Hapus salah satu user -> Konteks filter tetap aktif | P2 |
| `DEL-013` | Penutupan toast notifikasi hapus secara manual | Munculkan toast hapus -> Klik tombol silang (`&times;`) -> Toast tertutup seketika | P3 |
| `DEL-014` | Pencegahan double-click tombol Hapus | Klik tombol `Hapus` -> Tombol terproteksi dan request ganda dicegah | P3 |
| `DEL-015` | Pembatalan hapus berulang kali | Buka modal hapus lalu batalkan beberapa kali -> Seluruh data tetap utuh tanpa error | P3 |

---

### Kategori 6: Paginasi Tabel (@table-pagination)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `PAG-001` | Verifikasi opsi baris per halaman | Buka dropdown `#rows-per-page` -> Tersedia pilihan 5, 10, dan 25 | P2 |
| `PAG-002` | Ubah limit baris per halaman ke 5 | Pilih limit `5` -> Tabel hanya menampilkan maksimal 5 baris data | P2 |
| `PAG-003` | Ubah limit baris per halaman ke 25 | Pilih limit `25` -> Seluruh data (12 baris) tampil dalam 1 halaman | P2 |
| `PAG-004` | Navigasi ke halaman selanjutnya (Next Page) | Klik tombol Next (`›`) -> Tabel menampilkan baris halaman kedua | P1 |
| `PAG-005` | Navigasi ke halaman sebelumnya (Previous Page) | Berada di halaman 2 -> Klik Prev (`‹`) -> Tabel kembali ke halaman pertama | P1 |
| `PAG-006` | Navigasi langsung ke halaman terakhir (Last Page) | Klik tombol Last (`»`) -> Tabel langsung berpindah ke halaman akhir | P2 |
| `PAG-007` | Navigasi langsung ke halaman pertama (First Page) | Berada di halaman akhir -> Klik First (`«`) -> Tabel langsung kembali ke halaman 1 | P2 |
| `PAG-008` | Tombol Prev & First disabled di halaman pertama | Buka halaman awal -> Tombol Prev dan First berstatus disabled | P2 |
| `PAG-009` | Tombol Next & Last disabled di halaman terakhir | Pindah ke halaman akhir -> Tombol Next dan Last berstatus disabled | P2 |
| `PAG-010` | Akurasi label rentang baris di halaman 1 | Periksa label -> Menampilkan `1-10 of 12` pada limit default 10 | P2 |
| `PAG-011` | Akurasi label rentang baris di halaman 2 | Klik Next -> Label menampilkan rentang lanjutan `11-12 of 12` | P2 |
| `PAG-012` | Toggle checkbox Dense padding aktif | Centang checkbox `Dense padding` -> Jarak padding baris tabel mengecil rapat | P3 |
| `PAG-013` | Toggle checkbox Dense padding non-aktif | Hapus centang `Dense padding` -> Jarak padding baris kembali normal | P3 |
| `PAG-014` | Reset paginasi saat mengubah rows per page | Berada di halaman kedua -> Ubah limit ke 25 -> Paginasi otomatis reset ke halaman 1 | P2 |
| `PAG-015` | Konsistensi data saat navigasi bolak-balik | Navigasi bolak-balik halaman 1 dan 2 -> Data konsisten tanpa duplikasi | P3 |

---

### Kategori 7: Aksesibilitas Keyboard & UX (@ux)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `UX-001` | Navigasi fokus keyboard via tombol Tab | Tekan Tab berurutan -> Fokus berpindah dari input search, filter, reset, cari, hingga tombol tabel | P2 |
| `UX-002` | Submit form pencarian via tombol Enter | Ketik nama pada search input lalu tekan Enter -> Pencarian dieksekusi | P2 |
| `UX-003` | Tutup modal filter via tombol Escape | Buka modal filter -> Tekan tombol keyboard `Escape` -> Modal tertutup | P2 |
| `UX-004` | Tutup modal tambah via tombol Escape | Buka modal tambah -> Tekan tombol keyboard `Escape` -> Modal tertutup | P2 |
| `UX-005` | Tutup modal edit via tombol Escape | Buka modal edit -> Tekan tombol keyboard `Escape` -> Modal tertutup | P2 |
| `UX-006` | Tutup modal hapus via tombol Escape | Buka modal hapus -> Tekan tombol keyboard `Escape` -> Modal tertutup | P2 |
| `UX-007` | Responsivitas tampilan pada resolusi tablet (1024x768) | Atur viewport browser 1024x768 -> Toolbar dan tabel tersusun rapi tanpa overlap | P3 |
| `UX-008` | Responsivitas tampilan pada zoom 200% | Zoom halaman 200% -> Teks dan tombol aksi tetap terbaca dan dapat diklik | P3 |
| `UX-009` | Indikator hover pada tombol aksi edit dan hapus | Arahkan kursor ke tombol aksi -> Indikator visual hover terlihat jelas | P3 |
| `UX-010` | Tooltip title pada tombol aksi | Periksa tombol aksi -> Tombol Edit memiliki title "Edit", tombol Delete memiliki title "Delete" | P3 |

---

### Kategori 8: Keamanan & Boundary Testing (@security-boundary)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `SEC-001` | Sanitasi script XSS pada input pencarian | Masukkan payload `<script>alert('xss')</script>` -> Sistem merender sebagai teks aman | P1 |
| `SEC-002` | Ketahanan terhadap injeksi SQL pada pencarian | Masukkan payload `' OR '1'='1` -> Sistem memperlakukan input sebagai teks pencarian biasa | P1 |
| `SEC-003` | Ketahanan karakter tanda kutip tunggal/ganda | Masukkan karakter `'"` pada search -> Halaman tidak crash atau melempar unhandled exception | P1 |
| `SEC-004` | Sanitasi payload XSS pada Nama User saat tambah | Masukkan nama `<b onmouseover=alert(1)>XSS</b>` -> Input tersanitasi aman di antarmuka | P1 |
| `SEC-005` | Ketahanan injeksi SQL pada Email saat tambah | Masukkan email `admin'--@hadir.com` -> Sistem menolak atau memproses secara aman | P1 |
| `SEC-006` | Input nama dengan string panjang (300+ karakter) | Masukkan nama sepanjang 300 karakter -> Sistem memotong/memproses tanpa buffer overflow | P2 |
| `SEC-007` | Pencarian dengan karakter emoji dan simbol unik | Masukkan simbol `👤⚡🔥#*&` pada pencarian -> Sistem merespons normal tanpa error encoding | P3 |
| `SEC-008` | Manipulasi ID user non-eksisten pada operasi edit | Coba update user dengan ID `99999` yang tidak terdaftar -> Sistem menampilkan error aman | P1 |
| `SEC-009` | Manipulasi ID user dengan format acak tidak valid | Coba kirim operasi update dengan format ID rusak -> Request ditolak dengan validasi aman | P2 |
| `SEC-010` | Manipulasi ID user non-eksisten pada operasi delete | Coba kirim delete user dengan ID non-eksisten -> Sistem menolak operasi secara aman | P1 |
| `SEC-011` | Akses langsung URL tanpa sesi login aktif | Akses `/management/user-monitoring` tanpa login -> Pengguna dialihkan ke `/authentication/login` | P1 |
| `SEC-012` | Akses API dengan token otentikasi kedaluwarsa | Request endpoint dikirim dengan token expired -> Response API mengembalikan status `401` | P1 |

---

### Kategori 9: Backend REST API Endpoints (@api-user-monitoring)
| ID | Skenario Pengujian | Langkah & Ekspektasi | Prioritas |
|:---|:---|:---|:---:|
| `API-001` | Fetch daftar user monitoring dengan token valid | `GET /api/v2/users` -> Status 200 OK, response berupa array JSON data user | P1 |
| `API-002` | Fetch user monitoring tanpa header Authorization | `GET /api/v2/users` tanpa token -> Status 401 Unauthorized | P1 |
| `API-003` | Fetch user monitoring dengan query paginasi | `GET /api/v2/users?page=1&limit=5` -> Status 200 OK, data berjumlah maksimal 5 | P2 |
| `API-004` | Fetch user monitoring dengan filter query nama | `GET /api/v2/users?fullname[$like]=%Ahmad%` -> Status 200 OK, data terfilter nama Ahmad | P1 |
| `API-005` | Fetch user monitoring dengan filter query departemen | `GET /api/v2/users?job_departement_id=1` -> Status 200 OK, data terfilter unit terkait | P2 |
| `API-006` | Tambah user monitoring dengan payload lengkap valid | `POST /api/v2/users` payload valid -> Status 201 Created / 200 OK, id user terbentuk | P1 |
| `API-007` | Tambah user monitoring tanpa field wajib fullname | `POST /api/v2/users` tanpa fullname -> Status 400 Bad Request | P1 |
| `API-008` | Tambah user monitoring tanpa field wajib email | `POST /api/v2/users` tanpa email -> Status 400 Bad Request | P1 |
| `API-009` | Tambah user monitoring tanpa field wajib password | `POST /api/v2/users` tanpa password -> Status 400 Bad Request | P1 |
| `API-010` | Tambah user monitoring dengan format email tidak valid | `POST /api/v2/users` email format salah -> Status 400 / 422 Unprocessable Entity | P2 |
| `API-011` | Update nama dan departemen user monitoring | `PATCH /api/v2/users/{id}` payload valid -> Status 200 OK, data terupdate | P1 |
| `API-012` | Update user monitoring dengan ID non-eksisten | `PATCH /api/v2/users/99999` -> Status 404 Not Found | P2 |
| `API-013` | Delete user monitoring terdaftar | `DELETE /api/v2/users/{id}` -> Status 200 OK, data terhapus | P1 |
| `API-014` | Delete user monitoring dengan ID non-eksisten | `DELETE /api/v2/users/99999` -> Status 404 Not Found | P2 |
| `API-015` | Fetch master opsi departemen untuk filter | `GET /api/filter-user/departements` -> Status 200 OK, daftar opsi departemen tersedia | P2 |
