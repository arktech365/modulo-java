package com.sena.proyecto.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3307/proyecto_usuarios";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a MySQL");
        } catch (Exception e) {
            System.out.println("Error en conexión: " + e.getMessage());
        }

        return con;
    }
}
