/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaspbol.buddypet.utils;

import java.sql.*;

/**
 *
 * @author LENOVO
 */
public class Database {
    private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "buddy";
    private final String PASSWORD = "buddypet10";

    public Connection conn;

    public void getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            System.out.println("Koneksi berhasil!");
        } catch (Exception e) {
            System.out.println("Tidak dapat terkoneksi.");
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Koneksi tidak dapat diberhentikan.");
            System.out.println(e.getMessage());
        }
    }

}
