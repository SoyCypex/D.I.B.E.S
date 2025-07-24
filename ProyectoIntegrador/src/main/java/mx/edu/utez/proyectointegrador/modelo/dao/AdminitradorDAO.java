package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminitradorDAO {
    public boolean validarUsuario(String usuario, String contrasenia) {
        String query = "SELECT * FROM ADMINISTRADOR WHERE USUARIO=? AND CONTRASENIA=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
