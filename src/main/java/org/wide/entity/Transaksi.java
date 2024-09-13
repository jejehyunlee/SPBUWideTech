package org.wide.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaksi {

    private String pembeli;
    private String jenisKendaraan;
    private int jumlah;
    private LocalDateTime timestamp;

    public Transaksi(String pembeli, String jenisKendaraan, int jumlah) {
        this.pembeli = pembeli;
        this.jenisKendaraan = jenisKendaraan;
        this.jumlah = jumlah;
        this.timestamp = LocalDateTime.now(); // current time
    }

    public Transaksi() {

    }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
