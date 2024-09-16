package org.wide;

import org.wide.entity.Kendaraan;
import org.wide.entity.Transaksi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class View {

        public void printDataBeli(String nama, Kendaraan kendaraan) {
            System.out.println(nama + " jenis " + kendaraan.getType()  + " sisa maksimal pengisian: " + kendaraan.getKapasitasSaatIni() + " liter");
        }

        public void printTransaction(Transaksi transaction) {

            // Mengambil waktu sekarang
            LocalDateTime now = transaction.getTimestamp();

            // Membuat formatter dengan pola yang diinginkan
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", new Locale("id", "ID"));

            // Memformat tanggal dan waktu
            String formattedDate = now.format(formatter);

            System.out.println(transaction.getPembeli()
                    + " | mengisi pertamax | jenis "
                    + transaction.getJenisKendaraan() + " | sebanyak "
                    + transaction.getJumlah() + " liter | " + "Time : "
                    + formattedDate);
        }


        public void printToTerminal(Transaksi transaksi) {

            System.out.println(transaksi.toString());

        }
}
