/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author iLumniX
 */
public class db {

    public static Connection connect() {
        Connection mysqlConnection = null;

        try {
            String url = "jdbc:mysql://localhost:3306/db_penjualan_barang_pas_xiib";
            String username = "root";
            String password = "";

            mysqlConnection = DriverManager.getConnection(url, username, password);
            System.out.print("\n{\n\tcode\t: 200\n\tSuccess\t: Database berhasil tersambung dengan project.\n}\n");
        } catch (SQLException e) {
            System.err.print("\n{\n\tcode\t: 500\n\tError\t: Database tidak berhasil tersambung dengan project\n\tMessage\t: " + e.getMessage() + "\n}\n");
        }
        return mysqlConnection;
    }

    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.print("\n{\n\tcode\t: 200\n\tSuccess\t: Connection berhasil tertutup.\n}\n");
            } catch (SQLException e) {
                System.err.print("\n{\n\tcode\t: 500\n\tError\t: Connection tidak berhasil di hapus\n\tMessage\t: " + e.getMessage() + "\n");
            }

        }
    }

    public static void main(String[] args) {
        Connection mysqlConnection = connect();
        disconnect(mysqlConnection);
    }
}
