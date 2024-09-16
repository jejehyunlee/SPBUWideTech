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
                int maxKapasitas = 0;
                if (jenisKendaraan.equalsIgnoreCase("mobil")){
                    maxKapasitas = 50;
                } else if (jenisKendaraan.equalsIgnoreCase("motor")) {
                    maxKapasitas = 10;
                } else {
                    System.out.println("kendaraan : " + jenisKendaraan + " tidak di izinkan");
                    System.exit(202);
                }
            pembeli.put(nama + "-" + jenisKendaraan , new Kendaraan(jenisKendaraan, maxKapasitas));

        }

        public boolean isPembeliTerdaftar(String nama, String jenisKendaraan) {
            return pembeli.containsKey(nama + "-" + jenisKendaraan);
        }

        public void prosesIsi(String input) {

            try {

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
            }catch (Exception e){
                System.out.println("Error : " + e.getMessage());
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
            boolean ketemu = false;

            for (Transaksi transaction : transactions) {
                if (transaction.getJenisKendaraan().equalsIgnoreCase(jenisKendaraan)) {
                    listPembeli.add(transaction.getPembeli() + " telah mengisi: " + transaction.getJumlah() + " liter");
                    total += transaction.getJumlah();
                    ketemu = true;
                }

            }

            for (String infoPembeli : listPembeli) {
                System.out.println(infoPembeli);
            }

            if (ketemu){
                System.out.println("\n");
                System.out.println("-----------------------------");
                System.out.println("Total pengisian " + jenisKendaraan + ": " + total + " liter ");
            }else {
                System.out.println("Kendaraan belum terdaftar");
            }

        }


        public void isiUlang(String input) {
            String[] parts = input.split(";");
            String[] vehicleParts = parts[1].split("-");
            String pembeli = vehicleParts[0];
            String jenisKendaraan = vehicleParts[1];

            try {
                String key = pembeli + "-" + jenisKendaraan;
                Kendaraan kendaraan = this.pembeli.get(key);
                if (kendaraan != null) {
                    kendaraan.refill();
                    System.out.println(pembeli + " berhasil melakukan pengisian ulang sebanyak " + kendaraan.getKapasitasSaatIni() + " liter");
                } else {
                    System.out.println("Nama dan kendaraan belum terdaftar");
                }
            }catch (Exception e){
                System.out.println("Error : " + Arrays.toString(e.getStackTrace()));
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

        public void testConsole(){
            for (Transaksi transaksi : transactions)
            {
                view.printToTerminal(transaksi);
            }
        }
}

