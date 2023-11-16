/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Connection.db;

/**
 *
 * @author iLumniX
 */
public class countController {

    private final Connection cn = db.connect();

    public String petugas() {
        String count = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM tblpetugas";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        count = r.getString("count");
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return count;
    }
    public String distributor() {
        String count = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM tbldistributor";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        count = r.getString("count");
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return count;
    }
    public String barang() {
        String count = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM tblbarang";
            try (PreparedStatement p = cn.prepareStatement(query)) {
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        count = r.getString("count");
                    }
                    r.close();
                }
                p.close();
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return count;
    }
}
