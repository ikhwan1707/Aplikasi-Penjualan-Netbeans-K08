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

// Connectin Database
import Connection.db;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistributorController {

    private final Connection cn = db.connect();

//  Sudah Optimal
    public String autoIncrement() {
        
        String kodeDistributor = null;
        String checkKode = null;
        
        try {
            String query = "SELECT COUNT(IDDistributor) FROM tbldistributor";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "KD";
                        kodeDistributor = String.format("%s%03d", kodeDepan, count);
                    }
                    
                    String query2 = "SELECT IDDistributor FROM tbldistributor WHERE IDDistributor = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, kodeDistributor);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                checkKode = r.getString("IDDistributor");
                            }
                        }
                    }
                    
                    if (kodeDistributor.equals(checkKode)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(kodeDistributor);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            kodeDistributor = String.format("%s%03d", huruf, angka);
                        }
                    }
                    
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return kodeDistributor;
    }

//  Sudah Optimal
    public List<String[]> index() {
        List<String[]> dataDistributor = new ArrayList<>();
        try {
            String query = "SELECT * FROM tbldistributor";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String IDDistributor = rs.getString("IDDistributor");
                        String NamaDistributor = rs.getString("NamaDistributor");
                        String Alamat = rs.getString("Alamat");
                        String KotaAsal = rs.getString("KotaAsal");
                        String Email = rs.getString("Email");
                        String Telepon = rs.getString("Telpon");
                        String[] data = {IDDistributor, NamaDistributor, Alamat, KotaAsal, Email, Telepon};
                        dataDistributor.add(data);
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return dataDistributor;
    }

//  Sudah Optimal
    public void store(String NamaDistributor, String Alamat, String KotaAsal, String Email, String Telpon) {
        try {
            String query = "INSERT INTO tbldistributor VAlUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, autoIncrement());
                ps.setString(2, NamaDistributor);
                ps.setString(3, Alamat);
                ps.setString(4, KotaAsal);
                ps.setString(5, Email);
                ps.setString(6, Telpon);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }

//  Sudah Optimal
    public String[] show(String IDDistributor) {
        String[] dataDistributor = new String[6];
        try {
            String query = "SELECT * FROM tbldistributor WHERE IDDistributor = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, IDDistributor);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dataDistributor[0] = rs.getString("IDDistributor");
                        dataDistributor[1] = rs.getString("NamaDistributor");
                        dataDistributor[2] = rs.getString("Alamat");
                        dataDistributor[3] = rs.getString("KotaAsal");
                        dataDistributor[4] = rs.getString("Email");
                        dataDistributor[5] = rs.getString("Telpon");
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return dataDistributor;
    }

//  Sudah Optimal
    public void update(String IDDistributor, String NamaDistributor, String Alamat, String KotaAsal, String Email, String Telepon) {
        try {
            String query = "UPDATE tbldistributor SET NamaDistributor = ?, Alamat = ?, KotaAsal = ?, Email = ?, Telpon = ? WHERE IDDistributor = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, NamaDistributor);
                ps.setString(2, Alamat);
                ps.setString(3, KotaAsal);
                ps.setString(4, Email);
                ps.setString(5, Telepon);
                ps.setString(6, IDDistributor);
                
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }

//  Sudah Optimal
    public void delete(String IDDistributor) {
        try {
            String query = "DELETE FROM tbldistributor WHERE IDDistributor = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, IDDistributor);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Distributor\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }
}
