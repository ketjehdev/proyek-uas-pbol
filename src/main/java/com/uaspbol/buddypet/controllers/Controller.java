package com.uaspbol.buddypet.controllers;

import java.sql.*;

import com.uaspbol.buddypet.utils.Database;

public class Controller {
    protected Connection conn;
    protected PreparedStatement preparedStatement;
    protected Statement statement;
    protected ResultSet result;
    protected String query;

    protected Controller() {
        Database db = new Database();
        conn = db.getConnection();
    }
}
