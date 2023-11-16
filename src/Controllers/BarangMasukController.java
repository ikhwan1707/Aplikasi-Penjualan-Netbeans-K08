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
import java.util.Date;

import java.text.SimpleDateFormat;
import java.lang.StringBuilder;

import Connection.db;
import Controllers.BarangController;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarangMasukController {

//  Sudah Optimal
    private final Connection cn = db.connect();

//  Sudah Optimal
    public String tanggal() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalSekarang = new Date();
        String tanggalMasuk = dateFormat.format(tanggalSekarang);
        return tanggalMasuk;
    }

//  Sudah Optimal
    public String autoIncrement() {

        String noNota = null;
        String check = null;

        try {
            String query = "SELECT COUNT(NoNota) FROM  tblbrgmasuk";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "NN";
                        noNota = String.format("%s%03d", kodeDepan, count);
                    }

                    String query2 = "SELECT NoNota FROM tblbrgmasuk WHERE NoNota = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, noNota);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                check = r.getString("NoNota");
                            }
                        }
                    }

                    if (noNota.equals(check)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(noNota);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            noNota = String.format("%s%03d", huruf, angka);
                        }
                    }

                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("\n{\n\tCode\t: 500\n \tMessage\t: BarangController tidak terhubung dengan database\n \tMessage\t: " + e.getMessage() + " \n}\n");
        }

        return noNota;
    }

//  Sudah Optimal
    public Boolean mencariNoNota(String NoNota) {
        Boolean check = false;
        String data = null;
        try {
            String query = "SELECT * FROM tblbrgmasuk WHERE NoNota = ?";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                p.setString(1, NoNota);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        data = r.getString("NoNota");
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        if (data != null) {
            check = true;
        }

        return check;
    }

    public List<String[]> index() {
        List<String[]> dataBrgMasuk = new ArrayList<>();

        try {
            String query = "SELECT a.*, b.NamaPetugas, c.NamaDistributor FROM tblbrgmasuk AS a "
                    + "JOIN tblpetugas AS b ON b.IDPetugas = a.IDPetugas "
                    + "JOIN tbldistributor AS c ON c.IDDistributor = a.IDDistributor";

            String queryHistory = "SELECT b.NamaBarang FROM tbldetailbrgmasuk AS a "
                    + "JOIN tblbarang AS b ON b.KodeBarang = a.KodeBarang "
                    + "WHERE a.NoNota = ?";

            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String noNota = rs.getString("NoNota");
                        try (PreparedStatement psb = cn.prepareStatement(queryHistory)) {
                            psb.setString(1, noNota);
                            try (ResultSet rsb = psb.executeQuery()) {
                                List<String> detail = new ArrayList<>();

                                while (rsb.next()) {
                                    String namaBarang = rsb.getString("NamaBarang");
                                    detail.add(namaBarang);
                                }

                                StringBuilder sb = new StringBuilder();

                                // Mengubah array menjadi String gabungan
                                for (String dataNamaBarang : detail) {
                                    sb.append(dataNamaBarang).append(", ");
                                }

                                // Menghapus ekstra
                                if (sb.length() > 0) {
                                    sb.setLength(sb.length() - 2);
                                }

                                String namaBarang = sb.toString();
                                String tglMasuk = rs.getString("TglMasuk");
                                String namaDistributor = rs.getString("NamaDistributor");
                                String namaPetugas = rs.getString("NamaPetugas");
                                String Total = rs.getString("Total");

                                String[] data = {noNota, namaBarang, tglMasuk, namaDistributor, namaPetugas, Total};
                                dataBrgMasuk.add(data);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return dataBrgMasuk;
    }

    public void store(List<String[]> data, String NoNota, String idPetugas, String idDistributor, String Total) {
        try {
            String queryBrgMasuk = "INSERT INTO tblbrgmasuk(NoNota, TglMasuk, IDPetugas, IDDistributor, Total) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps = cn.prepareStatement(queryBrgMasuk)) {
                ps.setString(1, NoNota);
                ps.setString(2, tanggal());
                ps.setString(3, idPetugas);
                ps.setString(4, idDistributor);
                ps.setString(5, Total);
                ps.executeUpdate();
                ps.close();
            }

            String checkStok = "SELECT Stok FROM tblbarang WHERE KodeBarang = ?";
            String queryStok = "UPDATE tblbarang SET Stok = ? WHERE KodeBarang = ?";
            String queryHistory = "INSERT INTO tbldetailbrgmasuk(NoNota, KodeBarang, Jumlah, Subtotal) "
                    + "VALUES (?, ?, ?, ?)";
            for (String[] v : data) {
                try (PreparedStatement p = cn.prepareStatement(queryHistory)) {
                    p.setString(1, NoNota);
                    p.setString(2, v[0]);
                    p.setString(3, v[2]);
                    p.setString(4, v[3]);

                    p.executeUpdate();
                    p.close();
                }

                try (PreparedStatement psc = cn.prepareStatement(checkStok)) {
                    psc.setString(1, v[0]);
                    try (ResultSet rsc = psc.executeQuery()) {
                        if (rsc.next()) {
                            try (PreparedStatement pst = cn.prepareStatement(queryStok)) {
                                int stok = Integer.parseInt(rsc.getString("Stok")) - Integer.parseInt(v[2]);
                                pst.setString(1, Integer.toString(stok));
                                pst.setString(2, v[0]);

                                pst.executeUpdate();
                                pst.close();
                            }
                            rsc.close();
                        }
                    }
                    psc.close();
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    public void delete(String noNota) {
        try {
            String query = "DELETE FROM tblbrgmasuk WHERE NoNota = ?";
            String query2 = "DELETE FROM tbldetailbrgmasuk WHERE NoNota = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, noNota);
                ps.executeUpdate();
                ps.close();
            }

            try (PreparedStatement ps2 = cn.prepareStatement(query2)) {
                ps2.setString(1, noNota);
                ps2.executeUpdate();
                ps2.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    public String[] show(String NoNota) {

        String[] dataBrgMasuk = new String[6];

        try {
            String query = "SELECT a.*, b.NamaPetugas, c.NamaDistributor FROM tblbrgmasuk AS a "
                    + "JOIN tblpetugas AS b ON b.IDPetugas = a.IDPetugas "
                    + "JOIN tbldistributor AS c ON c.IDDistributor = a.IDDistributor "
                    + "WHERE a.NoNota = ?";

            String queryHistory = "SELECT a.KodeBarang FROM tbldetailbrgmasuk AS a "
                    + "JOIN tblbarang AS b ON b.KodeBarang = a.KodeBarang "
                    + "WHERE a.NoNota = ?";

            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, NoNota);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String noNota = rs.getString("NoNota");
                        try (PreparedStatement psb = cn.prepareStatement(queryHistory)) {
                            psb.setString(1, noNota);
                            try (ResultSet rsb = psb.executeQuery()) {
                                List<String> detail = new ArrayList<>();

                                while (rsb.next()) {
                                    String kodeBarang = rsb.getString("KodeBarang");
                                    detail.add(kodeBarang);
                                }

                                StringBuilder sb = new StringBuilder();

                                // Mengubah array menjadi String gabungan
                                for (String kodeBarang : detail) {
                                    sb.append(kodeBarang).append(", ");
                                }

                                // Menghapus ekstra
                                if (sb.length() > 0) {
                                    sb.setLength(sb.length() - 2);
                                }

                                String kodeBarang = sb.toString();
                                String tglMasuk = rs.getString("TglMasuk");
                                String namaDistributor = rs.getString("NamaDistributor");
                                String namaPetugas = rs.getString("NamaPetugas");
                                String Total = rs.getString("Total");

                                dataBrgMasuk[0] = noNota;
                                dataBrgMasuk[1] = kodeBarang;
                                dataBrgMasuk[2] = tglMasuk;
                                dataBrgMasuk[3] = namaDistributor;
                                dataBrgMasuk[4] = namaPetugas;
                                dataBrgMasuk[5] = Total;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return dataBrgMasuk;
    }

    public List<String[]> showDetail(String dataKodeBarang) {
        List<String[]> detail = new ArrayList<>();
        String query = "SELECT a.*, b.* FROM tbldetailbrgmasuk AS A JOIN tblbarang AS b ON a.KodeBarang = b.KodeBarang WHERE a.KodeBarang = ?";
        try {
            String[] dataKode = dataKodeBarang.split(",");
            for (String items : dataKode) {
                try (PreparedStatement p = cn.prepareStatement(query)) {
                    p.setString(1, items);
                    try (ResultSet r = p.executeQuery()) {
                        while (r.next()) {
                            String[] data = {r.getString("KodeBarang"), r.getString("NamaBarang"), r.getString("Jumlah"), r.getString("Subtotal")};
                            detail.add(data);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return detail;
    }
}
