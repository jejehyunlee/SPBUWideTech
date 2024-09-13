package org.wide;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                View view = new View();
                Service service = new Service(view);

                int M = Integer.parseInt(scanner.nextLine().trim());
                for (int i = 0; i < M; i++) {
                    String[] parts = scanner.nextLine().split("-");
                    service.daftarPembeli(parts[0], parts[1]);
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
                    }
                }

                scanner.close();
    }
}



