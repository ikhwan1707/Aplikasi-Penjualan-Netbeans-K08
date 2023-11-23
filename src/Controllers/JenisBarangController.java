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
public class JenisBarangController {

    private final Connection cn = db.connect();

//  Sudah Optimal
    public String autoIncrement() {
        String kodeJenis = null;
        String checkKode = null;
        
        try {
            String query = "SELECT COUNT(KodeJenis) FROM tbljenis";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "KJ";
                        kodeJenis = String.format("%s%03d", kodeDepan, count);
                    }

                    String query2 = "SELECT KodeJenis FROM tbljenis WHERE KodeJenis = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, kodeJenis);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                checkKode = r.getString("KodeJenis");
                            }
                        }
                    }

                    if (kodeJenis.equals(checkKode)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(kodeJenis);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            kodeJenis = String.format("%s%03d", huruf, angka);
                        }
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return kodeJenis;
    }

//  Sudah Optimal
    public List<String[]> index() {
        List<String[]> dataJenisBarang = new ArrayList<>();

        try {
            String query = "SELECT * FROM tbljenis";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String[] data = {rs.getString("KodeJenis"), rs.getString("Jenis")};
                        dataJenisBarang.add(data);
                    }

                    System.out.print("\n{\n \tCode\t: 200\n \tMessage\t: Data jenis Berhasil terpanggil\n }\n");
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
        return dataJenisBarang;
    }

//  Sudah Optimal
    public void store(String jenis) {
        try {
            String query = "INSERT INTO tbljenis (KodeJenis, Jenis) VALUES (?, ?)";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, autoIncrement());
                ps.setString(2, jenis);
                
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }

//  Sudah Optimal
    public String[] show(String kodeJenis) {
        String[] dataJenis = new String[2];

        try {
            String query = "SELECT * FROM tbljenis WHERE KodeJenis = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, kodeJenis);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dataJenis[0] = rs.getString("KodeJenis");
                        dataJenis[1] = rs.getString("Jenis");
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return dataJenis;
    }

//  Sudah Optimal
    public void update(String kodeJenis, String jenis) {
        try {
            String query = "UPDATE tbljenis SET Jenis = ? WHERE KodeJenis = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, jenis);
                ps.setString(2, kodeJenis);

                ps.executeUpdate();
                System.out.print("\n{\n \tCode\t: 200\n \tMessage\t: Jenis barang berhasil di update\n }\n");
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }

//  Sudah Optimal
    public void delete(String kodeJenis) {
        try {
            String query = "DELETE FROM tbljenis WHERE KodeJenis = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, kodeJenis);

                ps.executeUpdate();
                System.out.print("\n{\n \tCode\t: 200\n \tMessage\t: Data jenis barang berhasil terhapus\n }\n");
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: JenisBarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }
    }
}
