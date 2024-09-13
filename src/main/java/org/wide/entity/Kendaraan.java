package org.wide.entity;

public class Kendaraan {

    private String type;
    private int maxKapasitas;
    private int kapasitasSaatIni;

    public Kendaraan(String type, int maxKapasitas) {
        this.type = type;
        this.maxKapasitas = maxKapasitas;
        this.kapasitasSaatIni = maxKapasitas; // Inisialisasi dengan kapasitas penuh
    }

    public boolean canRefill(int jumlah) {
        return kapasitasSaatIni >= jumlah;
    }

    public void isiPertamax(int jumlah) {
        kapasitasSaatIni -= jumlah;
    }

    public void refill() {
        this.kapasitasSaatIni = Math.min(this.maxKapasitas, this.kapasitasSaatIni + 10); // refill max 10 liter
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxKapasitas() {
        return maxKapasitas;
    }

    public void setMaxKapasitas(int maxKapasitas) {
        this.maxKapasitas = maxKapasitas;
    }

    public int getKapasitasSaatIni() {
        return kapasitasSaatIni;
    }

    public void setKapasitasSaatIni(int kapasitasSaatIni) {
        this.kapasitasSaatIni = kapasitasSaatIni;
    }
}
