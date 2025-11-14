package com.sena.proyecto.dao;

import com.sena.proyecto.model.Usuario;
import com.sena.proyecto.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    Connection con;

    public UsuarioDAO() {
        con = Conexion.getConnection();
    }

    public boolean insertarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios(nombre, apellido, email, password, rol, estado) VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRol());
            ps.setBoolean(6, u.isEstado());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertando: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getBoolean("estado")
                );

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error listando: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, email=?, password=?, rol=?, estado=? WHERE id=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRol());
            ps.setBoolean(6, u.isEstado());
            ps.setInt(7, u.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error actualizando: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error eliminando: " + e.getMessage());
            return false;
        }
    }
}
