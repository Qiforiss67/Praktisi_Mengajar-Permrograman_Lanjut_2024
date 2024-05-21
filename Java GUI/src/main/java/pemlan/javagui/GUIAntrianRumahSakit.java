package pemlan.javagui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

public class GUIAntrianRumahSakit extends JFrame {
    private Queue<String> antrianRS = new ArrayDeque<>();
    private JTextArea daftarAntrianArea;
    private JTextField namaPasienField;
    private JLabel statusLabel;
    private int nomor = 1;

    public GUIAntrianRumahSakit() {
        setTitle("Sistem Antrian Rumah Sakit");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create components
        daftarAntrianArea = new JTextArea(10, 30);
        daftarAntrianArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(daftarAntrianArea);

        namaPasienField = new JTextField(20);
        statusLabel = new JLabel(" ");

        JButton tambahButton = new JButton("Tambah Pasien");
        JButton lihatButton = new JButton("Lihat Antrian");
        JButton panggilButton = new JButton("Panggil Pasien");
        JButton cariButton = new JButton("Cari Nomor Antrian");
        JButton unduhButton = new JButton("Unduh Data Antrian");
        JButton bacaButton = new JButton("Baca Data dari File");
        JButton resetButton = new JButton("Reset Antrian");

        // Create panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nama Pasien:"));
        inputPanel.add(namaPasienField);
        inputPanel.add(tambahButton);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(lihatButton);
        buttonPanel.add(panggilButton);
        buttonPanel.add(cariButton);
        buttonPanel.add(unduhButton);
        buttonPanel.add(bacaButton);
        buttonPanel.add(resetButton);

        // Add components to main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(statusLabel, BorderLayout.PAGE_END);

        add(panel);

        // Action listeners
        tambahButton.addActionListener(e -> tambahPasien());
        lihatButton.addActionListener(e -> lihatAntrian());
        panggilButton.addActionListener(e -> panggilPasien());
        cariButton.addActionListener(e -> cariNomorAntrian());
        unduhButton.addActionListener(e -> unduhAntrian());
        bacaButton.addActionListener(e -> bacaDataDariFile());
        resetButton.addActionListener(e -> resetAntrian());
    }

    private void tambahPasien() {
        String pasien = namaPasienField.getText();
        if (!pasien.matches("[a-zA-Z\\s]+")) {
            statusLabel.setText("Nama pasien hanya boleh mengandung huruf dan spasi.");
        } else {
            antrianRS.offer(pasien);
            statusLabel.setText("Pasien \"" + pasien + "\" berhasil ditambahkan.");
            namaPasienField.setText("");
        }
    }

    private void lihatAntrian() {
        daftarAntrianArea.setText("");
        if (antrianRS.isEmpty()) {
            daftarAntrianArea.append("Antrian kosong.\n");
        } else {
            int no = nomor;
            for (String nama : antrianRS) {
                daftarAntrianArea.append(no++ + ". " + nama + "\n");
            }
        }
    }

    private void panggilPasien() {
        if (antrianRS.isEmpty()) {
            statusLabel.setText("Antrian kosong.");
        } else {
            String pasien = antrianRS.poll();
            statusLabel.setText("Pasien " + pasien + " silahkan menuju ruang periksa.");
            nomor++;
            lihatAntrian();
        }
    }

    private void cariNomorAntrian() {
        String cariNama = namaPasienField.getText();
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
            statusLabel.setText("Pasien \"" + cariNama + "\" berada di nomor antrian: " + nomorAntrian);
        } else {
            statusLabel.setText("Pasien \"" + cariNama + "\" tidak ditemukan dalam antrian.");
        }
    }

    private void unduhAntrian() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileOutputStream fileOutput = new FileOutputStream(file)) {
                int no = nomor;
                SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy HH:mm");
                Date now = new Date();
                for (String p : antrianRS) {
                    String line = no++ + ") " + p + " [" + sdf.format(now) + "]\n";
                    fileOutput.write(line.getBytes());
                }
                statusLabel.setText("Antrian berhasil diunduh ke dalam file " + file.getAbsolutePath());
            } catch (IOException e) {
                statusLabel.setText("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
            }
        }
    }

    private void bacaDataDariFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                daftarAntrianArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    daftarAntrianArea.append(line + "\n");
                }
                statusLabel.setText("Data antrian berhasil dibaca dari file.");
            } catch (IOException e) {
                statusLabel.setText("Terjadi kesalahan saat membaca file: " + e.getMessage());
            }
        }
    }

    private void resetAntrian() {
        antrianRS.clear();
        nomor = 1;
        daftarAntrianArea.setText("");
        statusLabel.setText("Antrian berhasil direset.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIAntrianRumahSakit gui = new GUIAntrianRumahSakit();
            gui.setVisible(true);
        });
    }
}
