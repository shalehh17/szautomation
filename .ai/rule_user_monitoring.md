# Aturan & Panduan Pengujian Fitur User Monitoring HADIR

Dokumen ini adalah aturan resmi pengujian fitur manajemen User Monitoring (*Monitoring Karyawan*) pada portal HADIR (`https://magang.dikahadir.com/management/user-monitoring`) yang diselaraskan dengan arsitektur **SQA Hybrid Automation Framework (Java 21, Cucumber BDD, TestNG, Selenium WebDriver, dan REST Assured)**.

---

## 1. Konteks Halaman & Komponen Antarmuka (UI)

Halaman **User Monitoring** (`/management/user-monitoring`) adalah modul pengelolaan akun karyawan yang berada di bawah pantauan atau monitoring operasional HADIR. Halaman ini digunakan oleh administrator untuk mendaftarkan akun pemantauan baru, menyaring dan memfilter user berdasarkan nama atau unit kerja, mengedit data user (nama, email, password, departemen/unit), serta menghapus penugasan monitoring karyawan.

### Komponen Utama pada Antarmuka:

1. **Header & Judul Halaman:**
   - **Judul Halaman:** `User Monitoring` (`#page-title` / `.page-title`).
   - **Breadcrumbs Navigasi:** `Management / User Monitoring` (`#breadcrumb-home`, `#breadcrumb-current`).

2. **Bagian Form Pencarian & Toolbar Aksi (Filter Card):**
   - **Form Pencarian:** `#form-search` (`form.form-search`).
   - **Input Search:** `#search` (`input[type="search"]`, class `.input-search.form-control`, placeholder `"cari berdasarkan nama"`).
   - **Tombol Filter Unit:** `#btn-filter` (Tombol secondary dengan ikon funnel/filter untuk membuka dialog modal filter unit).
   - **Tombol Reset:** `#btn-reset` (Tombol warning text `Reset` untuk mengosongkan search & filter lalu merefresh tabel).
   - **Tombol Search:** `#btn-search` (Tombol primary submit bertuliskan `Search`).
   - **Tombol Tambahkan:** `#btn-tambah` (Tombol primary dengan ikon plus `Tambahkan` untuk membuka dialog modal penambahan user monitoring).

3. **Tabel Data User Monitoring (`#user-monitoring-table`):**
   - **Header Kolom (6 Kolom Utama):**
     1. `FOTO` (`#th-photo`) - Kolom avatar/gambar profil karyawan berukuran 42x42px atau inisial nama.
     2. `NAMA` (`#th-fullname`) - Kolom nama lengkap karyawan (`user.fullname`).
     3. `EMAIL` (`#th-email`) - Kolom alamat email user (`user.email`).
     4. `UNIT` (`#th-unit`) - Kolom divisi/departemen kerja (`user.unit` / `job_departement.name`).
     5. `TANGGAL DIBUAT` (`#th-created-at`) - Kolom tanggal pembuatan (`created_at`, format `DD MMM YYYY` dan `HH:mm:ss`).
     6. `AKSI` (`#th-action`) - Kolom tombol aksi manipulasi data baris.
   - **Baris Data Tabel:** Diberi identifier unik `#user-row-{id}`.
   - **Sel Kolom:**
     - `.cell-photo` (Avatar inisial atau foto profil)
     - `.cell-fullname` (Nama tebal)
     - `.cell-email` (Email abu-abu)
     - `.cell-unit` (Badge unit warna biru muda `.badge-unit`)
     - `.cell-created-at` (Tanggal dan jam dibuat)
   - **Tombol Aksi per Baris:**
     - **Tombol Edit (`#btn-edit-{id}` / `.action-btn.edit.btn-edit-user`):** Ikon pensil untuk membuka modal dialog *Edit User Monitoring*.
     - **Tombol Hapus (`#btn-delete-{id}` / `.action-btn.delete.btn-delete-user`):** Ikon tempat sampah untuk membuka modal dialog *Hapus User Monitoring*.
     - **3-Dots Action Menu (`.btn-action-menu` / `#card-actions-menu`):** Menu pop-up vertikal opsi baris.
   - **Pesan Tabel Kosong:** `#empty-table-message` (Teks tebal `Tidak Ada Data` dengan colspan 6).

4. **Modal Dialog Filter User Monitoring (`#modal-filter-user`):**
   - **Judul:** `Filter User Monitoring` (`#alert-dialog-title-filter`).
   - **Form:** `#form-filter-user`.
   - **Dropdown Unit:** `#job_departement_id` (`name="job_departement_id"`, pilihan: Semua Unit, Technology, Human Resources, Finance & Accounting, Marketing, Operations, Unit Kosong).
   - **Tombol Batal:** `#btn-batal-filter` (`Batal`, tombol secondary).
   - **Tombol Terapkan:** `#btn-terapkan-filter` (`Terapkan`, tombol primary).
   - **Tombol Close (X):** `#modal-filter-user .action-btn`.

5. **Modal Dialog Tambah User Monitoring (`#modal-tambah-user`):**
   - **Judul:** `Add User Monitoring` (`#alert-dialog-title-add`).
   - **Form:** `#form-tambah-user`.
   - **Field Nama User:** `#add-fullname` (`name="fullname"`, tipe `text`, required, placeholder `"Masukkan Nama User"`).
   - **Field Email:** `#add-email` (`name="email"`, tipe `email`, required, placeholder `"Masukkan Email"`).
   - **Field Password User:** `#add-password` (`name="password"`, tipe `password`, required, placeholder `"Masukkan Password User"`).
   - **Field Unit:** `#add-unit` (`name="job_departement_id"`, dropdown select unit, required).
   - **Tombol Batal:** `#btn-batal-tambah` (`Batal`, tombol secondary).
   - **Tombol Tambah:** `#btn-simpan-tambah` (`Tambah`, tombol primary).
   - **Tombol Close (X):** `#modal-tambah-user .action-btn`.

6. **Modal Dialog Edit User Monitoring (`#modal-edit-user`):**
   - **Judul:** `Edit User Monitoring` (`#alert-dialog-title-edit`).
   - **Form:** `#form-edit-user`.
   - **Hidden Input ID:** `#edit-id` (`name="id"`).
   - **Field Nama User:** `#edit-fullname` (`name="fullname"`, required).
   - **Field Email:** `#edit-email` (`name="email"`, required).
   - **Field Password User:** `#edit-password` (`name="password"`, opsional, placeholder `"Kosongkan jika tidak diubah"`).
   - **Field Unit:** `#edit-unit` (`name="job_departement_id"`, required).
   - **Tombol Batal:** `#btn-batal-edit` (`Batal`, tombol secondary).
   - **Tombol Simpan:** `#btn-simpan-edit` (`Simpan`, tombol primary).
   - **Tombol Close (X):** `#modal-edit-user .action-btn`.

7. **Modal Dialog Konfirmasi Hapus Data (`#modal-delete-user`):**
   - **Judul:** `Hapus User Monitoring` (`#alert-dialog-title-delete`).
   - **Form:** `#form-delete-user`.
   - **Hidden Input ID:** `#delete-id`.
   - **Teks Konfirmasi:** `#delete-confirm-text` (`Apakah anda yakin ingin menghapus user monitoring ini?`).
   - **Tombol Batal:** `#btn-batal-delete` (`Tidak`, tombol secondary).
   - **Tombol Konfirmasi Hapus:** `#btn-confirm-delete` (`Hapus`, tombol danger).
   - **Tombol Close (X):** `#modal-delete-user .action-btn`.

8. **Bar Paginasi & Pengaturan Tampilan:**
   - **Dropdown Rows per Page:** `#rows-per-page` (pilihan: `5`, `10`, `25`).
   - **Label Informasi Rentang Baris:** `#displayed-rows` (Format `X-Y of Z`, misal `1-10 of 12` atau `0-0 of 0`).
   - **Tombol Navigasi Halaman:**
     - First Page: `#btn-first-page` (`«`)
     - Previous Page: `#btn-prev-page` (`‹`)
     - Next Page: `#btn-next-page` (`›`)
     - Last Page: `#btn-last-page` (`»`)
   - **Checkbox Dense Padding:** `#checkbox-dense` (Toggle class `.dense` pada elemen tabel).

9. **Notifikasi Toast / Snackbar (`#alert-snackbar`):**
   - **Kontainer:** `#alert-snackbar.snackbar` (durasi auto-close 15 detik / 15000ms).
   - **Teks Pesan:** `#alert-message`:
     - Tambah Sukses: `"Berhasil menambahkan user monitoring"`
     - Edit Sukses: `"Berhasil ubah user monitoring"`
     - Hapus Sukses: `"Berhasil menghapus user monitoring"`
     - Filter Sukses: `"Berhasil Filter User Monitoring"`
     - Validasi Form Wajib: `"Harap lengkapi semua data wajib!"` / `"Nama, Email, dan Unit wajib diisi!"`
   - **Tombol Close Snackbar:** `.snackbar-close` (Menutup snackbar seketika).
   - **Global Keyboard Listener:** Menekan tombol `Escape` menutup seluruh modal dialog yang sedang terbuka.

---

## 2. Informasi Proyek & Kontrak API (Fakta Terverifikasi)

- **URL Aplikasi UI Live:** `https://magang.dikahadir.com/management/user-monitoring`
- **URL Server Uji Lokal (Offline Mirror):** `http://localhost:8085/management/user-monitoring`
- **Autentikasi & Otorisasi:**
  - Halaman memerlukan sesi login manajemen / administrator yang sah.
  - Sesi yang tidak valid diarahkan (*HTTP 307 Redirect*) ke `/authentication/login`.
- **Endpoint API Terkait (Backend Hadir Magang):**
  1. **Fetch Data User Monitoring:**
     - `GET /api/v2/users?page={page}&limit={limit}&fullname[$like]=%{query}%&job_departement_id={unit_id}`
     - Header: `Authorization: Bearer <token>`
     - Response: JSON memuat `{ data: [...], total: <number> }`
  2. **Tambah User Monitoring:**
     - `POST /api/v2/users`
     - Header: `Content-Type: application/json`, `Authorization: Bearer <token>`
     - Payload JSON:
       ```json
       {
         "fullname": "Ahmad Fauzi",
         "email": "ahmad.fauzi@hadir.com",
         "password": "Password123!",
         "job_departement_id": "1"
       }
       ```
     - Response: `201 Created`
     - Pesan Notifikasi UI: `"Berhasil menambahkan user monitoring"`
  3. **Edit / Update User Monitoring:**
     - `PATCH /api/v2/users/{id}`
     - Header: `Content-Type: application/json`, `Authorization: Bearer <token>`
     - Payload JSON:
       ```json
       {
         "fullname": "Ahmad Fauzi M.T.",
         "email": "ahmad.fauzi@hadir.com",
         "job_departement_id": "1"
       }
       ```
     - Response: `200 OK`
     - Pesan Notifikasi UI: `"Berhasil ubah user monitoring"`
  4. **Hapus User Monitoring:**
     - `DELETE /api/v2/users/{id}`
     - Header: `Authorization: Bearer <token>`
     - Response: `200 OK`
     - Pesan Notifikasi UI: `"Berhasil menghapus user monitoring"`
  5. **Endpoint Pendukung Master Data:**
     - `GET /api/filter-user/departements` (Menyediakan master daftar unit/departemen)

---

## 3. Format Tanggal & Waktu Terstandar
- **Database / API:** Format ISO-8601 string (`YYYY-MM-DD HH:mm:ss`, e.g. `2026-10-15 09:30:00`).
- **Antarmuka UI:** Ditampilkan 2 baris terpisah pada kolom `TANGGAL DIBUAT`:
  - Baris atas (tebal): Format tanggal `DD MMM YYYY` (e.g. `2026-10-15`).
  - Baris bawah (abu-abu): Format waktu `HH:mm:ss` (e.g. `09:30:00`).

---

## 4. Standar Arsitektur Otomasi Framework

1. **Page Object Model (POM):**
   - Dikelola di `src/main/java/com/company/sqa/pages/UserMonitoringPage.java` mewarisi `BasePage.java`.
   - Menggunakan explicit wait dinamis dan non-blocking wait untuk pengecekan elemen tertutup.
2. **Step Definitions & Feature Files:**
   - Step UI di `src/test/java/com/company/sqa/ui/steps/UserMonitoringSteps.java`.
   - Step API di `src/test/java/com/company/sqa/api/steps/UserMonitoringApiSteps.java`.
   - Fitur Gherkin UI di `src/test/resources/features/ui/user_monitoring/`.
   - Fitur Gherkin API di `src/test/resources/features/api/user_monitoring/`.
3. **Pemberian Tag Cucumber Terstandar:**
   - Tag Modul Utama: `@ui`, `@user-monitoring`.
   - Tag Fitur Modular: `@ui-elements`, `@search`, `@add`, `@edit`, `@delete`, `@table-pagination`, `@ux`, `@security-boundary`, `@api-user-monitoring`.
