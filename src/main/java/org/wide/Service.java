package org.wide;

import org.wide.entity.Kendaraan;
import org.wide.entity.Transaksi;

import java.util.*;

public class Service {
        private final Map<String, Kendaraan> pembeli = new HashMap<>();
        private final List<Transaksi> transactions = new ArrayList<>();

        private final View view;

        public Service(View view) {
            this.view = view;
        }

        public void daftarPembeli(String nama, String jenisKendaraan) {
            int maxKapasitas = jenisKendaraan.equalsIgnoreCase("mobil") ? 50 : 10;
            pembeli.put(nama + "-" + jenisKendaraan, new Kendaraan(jenisKendaraan, maxKapasitas));
        }

        public void prosesIsi(String input) {
            String[] parts = input.split(";");
            String[] vehicleParts = parts[1].split("-");
            String namaPembeli = vehicleParts[0];
            String jenisKendaraan = vehicleParts[1];
            int jumlah = Integer.parseInt(parts[2]);

            String key = namaPembeli + "-" + jenisKendaraan;
            Kendaraan kendaraan = pembeli.get(key);
            if (kendaraan != null) {
                if (kendaraan.canRefill(jumlah)) {
                    kendaraan.isiPertamax(jumlah);
                    transactions.add(new Transaksi(namaPembeli, jenisKendaraan, jumlah));
                    System.out.println("Berhasil melakukan pengisian " + jenisKendaraan + "[" + namaPembeli + "]" +" Jumlah :" + jumlah + " Liter");
                } else {
                    System.out.println("Maaf, sisa pertamax tidak mencukupi");
                }
            } else {
                System.out.println("Nama dan kendaraan belum terdaftar");
            }
        }

        public void cetakDataPembeli() {
            List<String> namaPembeli = new ArrayList<>(pembeli.keySet());
            namaPembeli.sort(Comparator.naturalOrder());

            for (String nama : namaPembeli) {
                Kendaraan kendaraan = pembeli.get(nama);
                view.printDataBeli(nama.split("-")[0], kendaraan);
            }
        }

        public void cetakDataTransaksi() {
            if (transactions.isEmpty()) {
                System.out.println("Belum ada transaksi apapun");
            } else {

                for (Transaksi transaksi : transactions) {
                   view.printTransaction(transaksi);
                }
            }
        }

        public void processTotal(String input) {
            String jenisKendaraan = input.split(";")[1];
            int total = 0;
            List<String> listPembeli = new ArrayList<>();

            for (Transaksi transaction : transactions) {
                if (transaction.getJenisKendaraan().equalsIgnoreCase(jenisKendaraan)) {
                    listPembeli.add(transaction.getPembeli() + " telah mengisi: " + transaction.getJumlah() + " liter");
                    total += transaction.getJumlah();
                }
            }

            for (String infoPembeli : listPembeli) {
                System.out.println(infoPembeli);
            }

            System.out.println("\n");
            System.out.println("-----------------------------");
            System.out.println("Total pengisian " + jenisKendaraan + ": " + total + " liter ");

        }


        public void isiUlang(String input) {
            String[] parts = input.split(";");
            String[] vehicleParts = parts[1].split("-");
            String pembeli = vehicleParts[0];
            String jenisKendaraan = vehicleParts[1];

            String key = pembeli + "-" + jenisKendaraan;
            Kendaraan kendaraan = this.pembeli.get(key);
            if (kendaraan != null) {
                kendaraan.refill();
                System.out.println(pembeli + " berhasil melakukan pengisian ulang sebanyak " + kendaraan.getKapasitasSaatIni() + " liter");
            } else {
                System.out.println("Nama dan kendaraan belum terdaftar");
            }
        }

        public void cekDataPembeli(String input) {
            String cekPembeli = input.split(";")[1];
            boolean ketemu = false;

            for (Map.Entry<String, Kendaraan> entry : pembeli.entrySet()) {
                if (entry.getKey().startsWith(cekPembeli)) {
                    Kendaraan kendaraan = entry.getValue();
                    System.out.println(kendaraan.getType() + " : " + kendaraan.getKapasitasSaatIni() + " liter") ;
                    ketemu = true;
                }
            }

            if (!ketemu) {
                System.out.println("Nama tersebut belum terdaftar");
            }
        }
}

