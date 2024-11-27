package com.uaspbol.buddypet.tests;

import com.uaspbol.buddypet.utils.Database;

public class TestDatabase {
    public static void main(String[] args) {
        Database db = new Database();

        System.out.println("Memulai koneksi..");

        db.getConnection();
        db.close();
    }
}
