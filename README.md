# Tugas-10
Kalkulator-Android-SharedPreferences

# Aplikasi Kalkulator Android - SharedPreferences

## Deskripsi
Aplikasi kalkulator Android berbasis Java yang menerapkan SharedPreferences untuk menyimpan pengaturan tampilan pengguna.

## Identitas
- Nama: Abdillah
- NPM: 2459201097
- Kelas: 2B
- Mata Kuliah: Pemrograman Mobile

## Teknologi
- Android Studio
- Java
- XML
- SharedPreferences

## Fitur Aplikasi
1. Operasi perhitungan matematika.
2. Dark Mode.
3. Light Mode.
4. Penyimpanan pengaturan tema menggunakan SharedPreferences.
5. Riwayat perhitungan menggunakan Array.

## Implementasi SharedPreferences
SharedPreferences digunakan untuk menyimpan pengaturan tema menggunakan tipe data Boolean.

- `true` = Dark Mode
- `false` = Light Mode

Pengaturan tema tetap tersimpan meskipun aplikasi ditutup dan dibuka kembali.

## Struktur Pengelolaan
- `MainActivity.java`: Mengatur aktivitas dan fungsi aplikasi kalkulator.
- `PrefsManager.java`: Mengelola penyimpanan dan pembacaan pengaturan tema.
- `activity_main.xml`: Mengatur tampilan aplikasi.

## Cara Menjalankan
1. Clone atau download repository ini.
2. Buka proyek menggunakan Android Studio.
3. Tunggu proses Gradle Sync selesai.
4. Jalankan aplikasi menggunakan emulator atau perangkat Android.

## Tujuan
Mempelajari dan menerapkan SharedPreferences sebagai media penyimpanan data sederhana pada aplikasi Android.
