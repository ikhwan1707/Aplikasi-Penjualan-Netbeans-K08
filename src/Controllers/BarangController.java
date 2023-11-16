/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.db;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author iLumniX
 */
public class BarangController {

    private final Connection cn = db.connect();

//  Sudah Optimal
    public String autoIncrement() {

        String kodeBarang = null;
        String checkKode = null;

        try {
            String query = "SELECT COUNT(KodeBarang) FROM  tblbarang";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "KB";
                        kodeBarang = String.format("%s%03d", kodeDepan, count);
                    }

                    String query2 = "SELECT KodeBarang FROM tblbarang WHERE KodeBarang = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, kodeBarang);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                checkKode = r.getString("KodeBarang");
                            }
                        }
                    }

                    if (kodeBarang.equals(checkKode)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(kodeBarang);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            kodeBarang = String.format("%s%03d", huruf, angka);
                        }
                    }

                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return kodeBarang;

    }

//  Sudah Optimal
    public List<String[]> index() {

        List<String[]> dataBarang = new ArrayList<>();

        try {
            String query = "SELECT tblbarang.*, tbljenis.Jenis AS Jenis FROM tblbarang JOIN tbljenis ON tblbarang.KodeJenis = tbljenis.KodeJenis";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String kodeBarang = rs.getString("KodeBarang");
                        String namaBarang = rs.getString("NamaBarang");
                        String hargaNet = rs.getString("HargaNet");
                        String hargaJual = rs.getString("HargaJual");
                        String stok = rs.getString("Stok");
                        String jenis = rs.getString("Jenis");

                        String[] data = {kodeBarang, namaBarang, jenis, hargaNet, hargaJual, stok};
                        dataBarang.add(data);
                    }

                    System.out.print("\n{\n \tCode\t: 200\n \tMessage\t: Data barang Berhasil terpanggil\n }\n");
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return dataBarang;
    }

//  Sudah Optimal
    public void store(String namaBarang, String kodeJenis, String hargaNet, String hargaJual, String stok) {
        try {
            String query = "INSERT INTO tblbarang VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, autoIncrement());
                ps.setString(2, namaBarang);
                ps.setString(3, kodeJenis);
                ps.setString(4, hargaNet);
                ps.setString(5, hargaJual);
                ps.setString(6, stok);

                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }

//  Sudah Optimal  
    public String[] show(String kodeBarang) {
        String[] dataBarang = new String[7];

        try {
            String query = "SELECT tblbarang.*, tbljenis.Jenis FROM tblbarang "
                    +"JOIN tbljenis ON tbljenis.KodeJenis = tblbarang.KodeJenis " 
                    +"WHERE tblbarang.KodeBarang = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, kodeBarang);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dataBarang[0] = rs.getString("KodeBarang");
                        dataBarang[1] = rs.getString("NamaBarang");
                        dataBarang[2] = rs.getString("KodeJenis");
                        dataBarang[3] = rs.getString("Jenis");
                        dataBarang[4] = rs.getString("HargaNet");
                        dataBarang[5] = rs.getString("HargaJual");
                        dataBarang[6] = rs.getString("Stok");
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return dataBarang;
    }

//  Sudah Optimal
    public void update(String kodeBarang, String namaBarang, String kodeJenis, String hargaNet, String hargaJual, String stok) {
        try {
            String query = "UPDATE tblbarang SET NamaBarang = ?, KodeJenis = ?, HargaNet = ?, HargaJual = ?, Stok = ? WHERE KodeBarang = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, namaBarang);
                ps.setString(2, kodeJenis);
                ps.setString(3, hargaNet);
                ps.setString(4, hargaJual);
                ps.setString(5, stok);
                ps.setString(6, kodeBarang);

                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }

//  Sudah Optimal
    public void updateStok(String kodeBarang, String Stok) {
        String stok = null;
        try {
            String queryStok = "SELECT stok FROM tblbarang WHERE KodeBarang = ?";
            try (PreparedStatement ps = cn.prepareStatement(queryStok)) {
                ps.setString(1, kodeBarang);
                try (ResultSet r = ps.executeQuery()) {
                    if (r.next()) {
                        stok = r.getString("stok");
                    }
                    r.close();
                }
                ps.close();
            }
            int jumlahStok = Integer.parseInt(stok) + Integer.parseInt(Stok);
            String query = "UPDATE tblbarang SET Stok = ? WHERE KodeBarang = ?";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                p.setString(1, Integer.toString(jumlahStok));
                p.setString(2, kodeBarang);
                p.executeUpdate();
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

//  Sudah Optimal
    public void delete(String kodeJenis) {
        try {
            String query = "DELETE FROM tblbarang where KodeBarang = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, kodeJenis);

                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }
}
