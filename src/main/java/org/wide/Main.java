package org.wide;

import org.wide.entity.Kendaraan;
import org.wide.entity.Transaksi;

import java.util.*;

public class Main {

    static Map<String, Kendaraan> pembeliTerdaftar = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static View view = new View();
    static Service service = new Service(view);


    public static void main(String[] args) {


                System.out.print("Register Jumlah Pembeli : ");
                int M = Integer.parseInt(scanner.nextLine().trim());
                for (int i = 0; i < M; i++) {
                    boolean inputValid = false;
                    while (!inputValid){
                        try {
                            String[] parts = scanner.nextLine().split("-");
                            String nama = parts[0].trim();
                            String jenisKendaraan = parts[1].trim();

                            if(service.isPembeliTerdaftar(nama, jenisKendaraan)){
                                System.out.println("Nama : " + nama + " | Kendaraan : "+ jenisKendaraan + " Sudah Terdaftar");
                            } else {
                                service.daftarPembeli(nama, jenisKendaraan);
                                inputValid = true;
                                System.out.println("success registered");
                            }
                        } catch (Exception e) {
                            inputValid = false;
                            System.out.println("Error : " + e.getMessage());
                            System.out.println("input tidak sesuai (ex = nama-jenis)");
                        }
                    }
                }

                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.startsWith("isi;")) {
                        service.prosesIsi(input);
                    } else if (input.startsWith("data pembeli")) {
                        service.cetakDataPembeli();
                    } else if (input.startsWith("data transaksi")) {
                        service.cetakDataTransaksi();
                    } else if (input.startsWith("total;")) {
                        service.processTotal(input);
                    } else if (input.startsWith("refill;")) {
                        service.isiUlang(input);
                    } else if (input.startsWith("cek;")) {
                        service.cekDataPembeli(input);
                    }else if (input.startsWith("close")) {
                        System.exit(200);
                    }else if (input.startsWith("test")) {
                        service.testConsole();
                    }
                    else {
                        System.out.println("Kesalahan input");
                    }
                }

                scanner.close();
    }




}



