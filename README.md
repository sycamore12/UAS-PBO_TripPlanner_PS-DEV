TripPlanner

Deskripsi Singkat
  TripPlanner adalah aplikasi desktop berbasis JavaFX yang dirancang untuk membantu pengguna menyusun rencana perjalanan (itinerary) secara efisien. Dengan antarmuka yang interaktif, pengguna dapat menentukan destinasi, memilih tanggal, serta menambahkan berbagai aktivitas harian seperti kunjungan tempat wisata dan kuliner. Aplikasi ini menyimpan semua data secara permanen menggunakan database PostgreSQL.

  Proyek ini dibuat untuk menunjukkan implementasi konsep Pemrograman Berbasis Objek (PBO), termasuk Enkapsulasi, Pewarisan (Inheritance), dan Polimorfisme dalam aplikasi dunia nyata.

Fitur Utama
  Input Data Perjalanan: Pengguna dapat dengan mudah memasukkan destinasi, tanggal mulai, dan tanggal akhir perjalanan.

  Manajemen Aktivitas Harian: Menambahkan aktivitas untuk setiap hari dalam perjalanan, dengan dua kategori utama:

  Aktivitas Wisata: Lengkap dengan detail lokasi dan harga tiket.

  Aktivitas Kuliner: Lengkap dengan detail nama restoran dan jenis masakan.

  Tampilan Itinerary Terstruktur: Rencana perjalanan ditampilkan secara rapi per hari, lengkap dengan waktu dan deskripsi setiap aktivitas.

  Penyimpanan Data Permanen: Semua data perjalanan dan aktivitas disimpan ke database PostgreSQL, sehingga rencana dapat diakses kembali kapan saja.

Cara Menjalankan Aplikasi
  Berikut adalah langkah-langkah untuk menginstal, mengonfigurasi, dan menjalankan proyek ini di lingkungan lokal Anda.

  1. Dependencies
      Pastikan perangkat lunak berikut sudah terinstal di komputer Anda:

      JDK: Versi 17 atau yang lebih baru.

      Apache Maven: Untuk manajemen dependensi dan build proyek.

      PostgreSQL: Server database untuk menyimpan data.

      IDE Java: Seperti IntelliJ IDEA atau Eclipse.

  2. Langkah-langkah Instalasi
        a. Clone Repositori
        Buka terminal atau command prompt, lalu clone repositori ini:

        git clone [URL_REPOSITORI_ANDA]
        cd TripPlanner

        b. Konfigurasi Database
        Buka aplikasi manajemen database Anda (misalnya pgAdmin).

        Buat database baru dengan nama trip_planner_db.

Jalankan skrip SQL yang ada di file database_schema.sql (atau salin dari bawah) untuk membuat semua tabel yang diperlukan.

CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE day_plans (
    id SERIAL PRIMARY KEY,
    trip_id INTEGER NOT NULL REFERENCES trips(id) ON DELETE CASCADE,
    plan_date DATE NOT NULL
);

CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    day_plan_id INTEGER NOT NULL REFERENCES day_plans(id) ON DELETE CASCADE,
    start_time TIME NOT NULL,
    description TEXT NOT NULL,
    activity_type VARCHAR(50) NOT NULL,
    location VARCHAR(255),
    ticket_price NUMERIC(10, 2),
    restaurant_name VARCHAR(255),
    cuisine_type VARCHAR(100)
);

        c. Update Password Database

        Buka proyek di IntelliJ IDEA atau IDE Anda.

        Navigasi ke file src/main/java/com/ps_dev/tripplanner/db/DatabaseConnector.java.

        Ubah nilai variabel DB_PASSWORD dengan password PostgreSQL Anda.

        // Ganti "your_password" dengan password Anda
        private static final String DB_PASSWORD = "your_password"; 

  3. Menjalankan Aplikasi
    Anda bisa menjalankan aplikasi langsung dari IDE Anda dengan membuka file Main.java dan menjalankannya, atau menggunakan Maven melalui terminal:

    # Perintah ini akan mengompilasi dan menjalankan aplikasi
    mvn clean javafx:run

Aplikasi TripPlanner sekarang seharusnya sudah berjalan di layar Anda.

Link Youtube: https://youtu.be/z5v3xMqa10Y?si=FHClqXl93ecrvppD
