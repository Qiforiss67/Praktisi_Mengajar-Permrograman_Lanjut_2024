import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class TugasExceptionHandlingRumahSakit {

    public static void main(String[] args) {
        // Queue for patient
        Queue<String> antrianRS = new ArrayDeque<>();

        // Scanner for user input
        Scanner input = new Scanner(System.in);
        boolean running = true;
        int nomor = 1;

        while (running) {
            tampilkanMenu();

            int pilihan;
            while (true) {
                System.out.print("Pilih Menu Tujuan (1-4): ");
                String inputStr = input.nextLine();
                try {
                    pilihan = Integer.parseInt(inputStr);
                    if (pilihan >= 1 && pilihan <= 4) {
                        break;
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan masukkan nomor menu (1-4).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Harap masukkan nomor menu (1-4).");
                }
            }

            switch (pilihan) {
                case 1:
                    // Add a patient to the queue
                    try {
                        System.out.print("Masukkan nama pasien: ");
                        String pasien = input.nextLine();
                        if (!pasien.matches("[a-zA-Z\\s]+")) {
                            throw new Exception("Nama pasien hanya boleh mengandung huruf dan spasi.");
                        } else {
                            System.out.println("Pasien \"" + pasien + "\" berhasil ditambahkan.");
                            antrianRS.offer(pasien);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    // Display patient queue
                    System.out.println("\n======= Daftar Antrian Pasien =======");
                    if (!antrianRS.isEmpty()) {
                        int no = nomor;
                        for (String nama : antrianRS) {
                            System.out.println(no++ + ". " + nama);
                        }
                    } else {
                        System.out.println("Antrian kosong.");
                    }
                    System.out.println("=====================================\n");
                    break;

                case 3:
                    // Serve a patient from the queue
                    System.out.println("\n======= Pasien Keluar=======");
                    if (!antrianRS.isEmpty()) {
                        System.out.println("Pasien Selanjutnya: " + antrianRS.poll() + " Silahkan Menuju Ruang Periksa");
                        nomor++;
                    } else {
                        System.out.println("Antrian kosong.");
                    }
                    System.out.println("=============================\n");
                    break;

                case 4:
                    // Exit the program
                    running = false;
                    System.out.println("Terima kasih telah menggunakan layanan di Rumah Sakit kami.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }

        input.close(); // Closing the scanner outside the loop
    }

    static void tampilkanMenu() {
        System.out.println("==========================");
        System.out.println("Sistem Antrian Rumah Sakit");
        System.out.println("==========================");
        System.out.println("1. Tambah Antrian Pasien");
        System.out.println("2. Lihat Antrian Pasien");
        System.out.println("3. Layani Pasien");
        System.out.println("4. Keluar");
        System.out.println("==========================");
    }
}
