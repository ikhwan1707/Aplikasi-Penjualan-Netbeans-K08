/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author iLumniX
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.util.Date;
import java.text.SimpleDateFormat;

import Connection.db;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PenjualanController {

    private final Connection cn = db.connect();

    public String tanggal() {
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalSekarang = new Date();
        String tanggal = formatTanggal.format(tanggalSekarang);
        return tanggal;
    }

    public String autoIncrement() {

        String noFaktur = null;
        String check = null;

        try {
            String query = "SELECT COUNT(NoFaktur) FROM  tblpenjualan";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "NF";
                        noFaktur = String.format("%s%03d", kodeDepan, count);
                    }

                    String query2 = "SELECT NoFaktur FROM tblpenjualan WHERE NoFaktur = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, noFaktur);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                check = r.getString("NoFaktur");
                            }
                        }
                    }

                    if (noFaktur.equals(check)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(noFaktur);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            noFaktur = String.format("%s%03d", huruf, angka);
                        }
                    }

                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return noFaktur;
    }

    public List<String[]> mencariNoFaktur(String noFaktur) {
        List<String[]> dataPenjualan = new ArrayList<>();
        try {
            String query = "SELECT a.NoFaktur, a.TglPenjualan, a.IDPetugas, b.NamaPetugas "
                    + "FROM tblpenjualan AS a "
                    + "JOIN tblpetugas AS b ON b.IDPetugas = a.IDPetugas "
                    + "WHERE a.NoFaktur = ?";

            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, noFaktur);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String NoFaktur = rs.getString("NoFaktur");
                        String TglPenjualan = rs.getString("TglPenjualan");
                        String IdPetugas = rs.getString("IDPetugas");
                        String NamaPetugas = rs.getString("NamaPetugas");
                        String[] data = {NoFaktur, TglPenjualan, IdPetugas, NamaPetugas};
                        dataPenjualan.add(data);
                    }
                    rs.close();
                }
                ps.close();
            }

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return dataPenjualan;
    }

    public List<String[]> index() {
        List<String[]> dataPenjualan = new ArrayList<>();

        String queryPenjualan = "SELECT a.*, b.NamaPetugas FROM tblpenjualan AS a "
                + "JOIN tblpetugas AS b ON a.IDPetugas = b.IDPetugas";
        String queryDetail = "SELECT a.*, b.NamaBarang FROM tbldetailpenjualan AS a "
                + "JOIN tblbarang AS b ON b.KodeBarang = a.KodeBarang "
                + "WHERE a.NoFaktur = ? ";

        try {
            try (PreparedStatement p = cn.prepareStatement(queryPenjualan)) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        String noFaktur = r.getString("NoFaktur");
                        try (PreparedStatement ps = cn.prepareStatement(queryDetail)) {
                        ps.setString(1, noFaktur);
                            try (ResultSet rs = ps.executeQuery()) {

                                List<String> detail = new ArrayList<>();
                                StringBuilder namaBarang = new StringBuilder();
                                while (rs.next()) {
                                    String nb = rs.getString("NamaBarang");
                                    detail.add(nb);
                                }

                                for (String items : detail) {
                                    namaBarang.append(items).append(", ");
                                }
                                
                                if (namaBarang.length() > 0) {
                                    namaBarang.setLength(namaBarang.length() - 2);
                                }

                                String tanggal = r.getString("TglPenjualan");
                                String namaPetugas = r.getString("NamaPetugas");
                                String bayar = r.getString("Bayar");
                                String sisa = r.getString("Sisa");
                                String total = r.getString("Total");
                                String[] data = {noFaktur, namaBarang.toString(), tanggal, namaPetugas, bayar, sisa, total};
                                
                                dataPenjualan.add(data);
                                
                                rs.close();
                            }
                            ps.close();
                        }
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        return dataPenjualan;
    }

    public void store(List<String[]> data, String noFaktur, String tglPenjualan, String idPetugas, String bayar, String sisa, String total) {
        try {
            String query = "INSERT INTO tblpenjualan VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                p.setString(1, noFaktur);
                p.setString(2, tglPenjualan);
                p.setString(3, idPetugas);
                p.setString(4, bayar);
                p.setString(5, sisa);
                p.setString(6, total);
                p.executeUpdate();
                p.close();
            }

            String checkStok = "SELECT Stok FROM tblbarang WHERE KodeBarang = ?";
            String queryStok = "UPDATE tblbarang SET STOK = ? WHERE KodeBarang = ?";
            String queryHistory = "INSERT INTO tbldetailpenjualan VALUES (?, ?, ?, ?)";
            for (String[] v : data) {
                try (PreparedStatement ps = cn.prepareStatement(queryHistory)) {
                    ps.setString(1, noFaktur);
                    ps.setString(2, v[0]);
                    ps.setString(3, v[2]);
                    ps.setString(4, v[3]);
                    ps.executeUpdate();
                    ps.close();
                }
                
                try (PreparedStatement psc = cn.prepareStatement(checkStok)) {
                    psc.setString(1, v[0]);
                    try (ResultSet rsc = psc.executeQuery()) {
                        if (rsc.next()) {
                            int updateStok = Integer.parseInt(rsc.getString("Stok")) - Integer.parseInt(v[2]);
                            try (PreparedStatement pss = cn.prepareStatement(queryStok)) {
                                pss.setString(1, Integer.toString(updateStok));
                                pss.setString(2, v[0]);
                                
                                pss.executeUpdate();
                                pss.close();
                            }
                        }
                        rsc.close();
                    }
                    psc.close();
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    public void delete(String noFaktur) {
        try {
            String query = "DELETE FROM tblpenjualan WHERE NoFaktur = ?";
            String query2 = "DELETE FROM tbldetailpenjualan WHERE NoFaktur = ?";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                p.setString(1, noFaktur);
                p.executeUpdate();
                p.close();
            }

            try (PreparedStatement ps = cn.prepareStatement(query2)) {
                ps.setString(1, noFaktur);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public List<String[]> show(String noFaktur) {
        List<String[]> dataPenjualan = new ArrayList<>();

        String queryPenjualan = "SELECT a.*, b.NamaPetugas FROM tblpenjualan AS a "
                + "JOIN tblpetugas AS b ON a.IDPetugas = b.IDPetugas WHERE a.NoFaktur = ?";
        String queryDetail = "SELECT a.*, b.NamaBarang FROM tbldetailpenjualan AS a "
                + "JOIN tblbarang AS b ON b.KodeBarang = a.KodeBarang "
                + "WHERE a.NoFaktur = ? ";

        try {
            try (PreparedStatement p = cn.prepareStatement(queryPenjualan)) {
                p.setString(1, noFaktur);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        try (PreparedStatement ps = cn.prepareStatement(queryDetail)) {
                        ps.setString(1, noFaktur);
                            try (ResultSet rs = ps.executeQuery()) {

                                List<String> detail = new ArrayList<>();
                                StringBuilder namaBarang = new StringBuilder();
                                while (rs.next()) {
                                    String nb = rs.getString("NamaBarang");
                                    detail.add(nb);
                                }

                                for (String items : detail) {
                                    namaBarang.append(items).append(", ");
                                }
                                
                                if (namaBarang.length() > 0) {
                                    namaBarang.setLength(namaBarang.length() - 2);
                                }

                                
                                String tanggal = r.getString("TglPenjualan");
                                String namaPetugas = r.getString("NamaPetugas");
                                String bayar = r.getString("Bayar");
                                String sisa = r.getString("Sisa");
                                String total = r.getString("Total");
                                String[] data = {noFaktur, namaBarang.toString(), tanggal, namaPetugas, bayar, sisa, total};
                                
                                dataPenjualan.add(data);
                                
                                rs.close();
                            }
                            ps.close();
                        }
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        return dataPenjualan;
    }
}
