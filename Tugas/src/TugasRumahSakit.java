import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Date;

public class TugasRumahSakit {
    static int nomor = 1;

    public static void main(String[] args) {
        Queue<String> antrianRS = new ArrayDeque<>();
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            tampilkanMenu();

            int pilihan;
            while (true) {
                System.out.print("Pilih Menu Tujuan (1-7): ");
                String inputStr = input.nextLine();
                try {
                    pilihan = Integer.parseInt(inputStr);
                    if (pilihan >= 1 && pilihan <= 7) {
                        break;
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan masukkan nomor menu (1-7).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Harap masukkan nomor menu (1-7).");
                }
            }

            switch (pilihan) {
                case 1:
                    // Tambah Pasien
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
                    // Menampilkan Daftar Antrian
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
                    // Memanggil Pasien
                    System.out.println("\n======= Memanggil Pasien =======");
                    if (!antrianRS.isEmpty()) {
                        System.out.println("Pasien " + antrianRS.poll() + " Silahkan Menuju Ruang Periksa");
                        nomor++;
                    } else {
                        System.out.println("Antrian kosong.");
                    }
                    System.out.println("=============================\n");
                    break;

                case 4:
                    // Cari Nomor Antrian
                    System.out.print("Masukkan nama pasien untuk mencari nomor antrian: ");
                    String cariNama = input.nextLine();
                    int nomorAntrian = -1;
                    int currentNomor = nomor;
                    for (String p : antrianRS) {
                        if (p.equalsIgnoreCase(cariNama)) {
                            nomorAntrian = currentNomor;
                            break;
                        }
                        currentNomor++;
                    }
                    if (nomorAntrian != -1) {
                        System.out.println("Pasien \"" + cariNama + "\" berada di nomor antrian: " + nomorAntrian);
                    } else {
                        System.out.println("Pasien \"" + cariNama + "\" tidak ditemukan dalam antrian.");
                    }
                    break;

                case 5:
                    // Unduh Antrian
                    System.out.print("Masukkan nama file untuk menyimpan antrian: ");
                    String namaFile = input.nextLine();

                    System.out.print("Direktori penyimpanan file: ");
                    String dir = input.nextLine();

                    String defaultDir = System.getProperty("user.dir");
                    File daftarAntrian;
                    if (dir.isEmpty()) {
                        daftarAntrian = new File(defaultDir, namaFile);
                    } else {
                        daftarAntrian = new File(dir, namaFile);
                    }

                    try {
                        daftarAntrian.createNewFile();
                        FileOutputStream fileOutput = new FileOutputStream(daftarAntrian);
                        int no = nomor;
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy HH:mm");
                        Date now = new Date();
                        for (String p : antrianRS) {
                            String line = no++ + ") " + p + " [" + sdf.format(now) + "]\n";
                            fileOutput.write(line.getBytes());
                        }
                        
                        fileOutput.close();
                        System.out.println("Antrian berhasil diunduh ke dalam file " + daftarAntrian.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
                    }
                    break;

                    case 6:
                    // Read data from file
                    System.out.print("Masukkan nama file untuk membaca antrian: ");
                    String bacaFile = input.nextLine();
                    
                    try { 
                        File antrian = new File(bacaFile);
                        FileReader reader = new FileReader(antrian);
                        BufferedReader buff = new BufferedReader(reader);
                        String baris = buff.readLine();
                        while (baris != null) {
                            System.out.println(baris); // Menampilkan setiap baris dari file
                            baris = buff.readLine();
                        }
                        buff.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File tidak ditemukan: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
                    }
                    break;                

                case 7:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan layanan di Rumah Sakit kami.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }

        input.close(); // Menutup scanner di luar loop
    }
    static void tampilkanMenu() {
        System.out.println("==========================");
            System.out.println("Sistem Antrian Rumah Sakit");
            System.out.println("==========================");
            System.out.println("1. Tambah Antrian Pasien");
            System.out.println("2. Lihat Antrian Pasien");
            System.out.println("3. Layani Pasien");
            System.out.println("4. Cari Nomor Antrian Pasien");
            System.out.println("5. Unduh Data Antrian");
            System.out.println("6. Baca Data dari File");
            System.out.println("7. Keluar");
            System.out.println("==========================");
    }
}

