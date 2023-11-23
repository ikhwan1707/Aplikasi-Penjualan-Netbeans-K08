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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Connection Database
import Connection.db;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetugasController {

    private final Connection cn = db.connect();

//  Sudah Optimal
    public String autoIncrement() {
        
        String kodePetugas = null;
        String checkKode = null;
        
        try {
            String query = "SELECT COUNT(IDPetugas) FROM tblpetugas";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        int count = rs.getInt(1) + 1;
                        String kodeDepan = "KP";
                        kodePetugas = String.format("%s%03d", kodeDepan, count);
                    }

                    String query2 = "SELECT IDPetugas FROM tblpetugas WHERE IDPetugas = ?";
                    try (PreparedStatement p = cn.prepareStatement(query2)) {
                        p.setString(1, kodePetugas);
                        try (ResultSet r = p.executeQuery()) {
                            if (r.next()) {
                                checkKode = r.getString("IDPetugas");
                            }
                        }
                    }

                    if (kodePetugas.equals(checkKode)) {
                        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)");
                        Matcher dataKode = pattern.matcher(kodePetugas);
                        if (dataKode.matches()) {
                            String huruf = dataKode.group(1);
                            int angka = Integer.parseInt(dataKode.group(2)) + 1;
                            kodePetugas = String.format("%s%03d", huruf, angka);
                        }
                    }
                    
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return kodePetugas;
    }

//  Sudah Optimal
    public List<String[]> index() {
        List<String[]> dataPetugas = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblpetugas";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String IDPetugas = rs.getString("IDPetugas");
                        String NamaPetugas = rs.getString("NamaPetugas");
                        String Alamat = rs.getString("Alamat");
                        String Email = rs.getString("Email");
                        String Telepon = rs.getString("Telpon");
                        String[] data = {IDPetugas, NamaPetugas, Alamat, Email, Telepon};
                        dataPetugas.add(data);
                    }

                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return dataPetugas;
    }

//  Sudah Optimal
    public void store(String NamaPetugas, String Alamat, String Email, String Telepon) {
        try {
            String query = "INSERT INTO tblpetugas VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, autoIncrement());
                ps.setString(2, NamaPetugas);
                ps.setString(3, Alamat);
                ps.setString(4, Email);
                ps.setString(5, Telepon);
                
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }

//  Sudah Optimal
    public String[] show(String IDPetugas) {
        String[] dataPetugas = new String[5];
        try {
            String query = "SELECT * FROM tblpetugas WHERE IDPetugas = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, IDPetugas);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dataPetugas[0] = rs.getString("IDPetugas");
                        dataPetugas[1] = rs.getString("NamaPetugas");
                        dataPetugas[2] = rs.getString("Alamat");
                        dataPetugas[3] = rs.getString("Email");
                        dataPetugas[4] = rs.getString("Telpon");
                    }
                    rs.close();
                }
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return dataPetugas;
    }

//  Sudah Optimal
    public void update(String IDPetugas, String NamaPetugas, String Alamat, String Email, String Telpon) {
        try {
            String query = "UPDATE tblpetugas SET NamaPetugas = ?, Alamat = ?, Email = ?, Telpon = ? WHERE IDPetugas = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, NamaPetugas);
                ps.setString(2, Alamat);
                ps.setString(3, Email);
                ps.setString(4, Telpon);
                ps.setString(5, IDPetugas);
                
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }

//  Sudah Optimal
    public void delete(String IDPetugas) {
        try {
            String query = "DELETE FROM tblpetugas WHERE IDPetugas = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, IDPetugas);
                
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.print("\n{\n \tCode\t: 500\n \tError\t: Tidak dapat tersambung dengan Controller Petugas\n \tMessage\t: " + e.getMessage() + "\n}\n");
        }
    }
}
