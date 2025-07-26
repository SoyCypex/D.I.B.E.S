package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Encargado;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EncargadoDao {
    //Funcion de crear (C) del CRUD
    public boolean createEncargado(Encargado en){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO ENCARGADOS (NOMBRE_COMPLETO, CORREO, PUESTO, HORA_ENTRADA, TELEFONO) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection connection = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, en.getNombreCompleto());
            ps.setString(2, en.getCorreo());
            ps.setString(3, en.getPuesto());
            ps.setTimestamp(4, en.getHoraEntrada());
            ps.setString(5, en.getTelefono());
            int resultado = ps.executeUpdate();
            if(resultado>0){
                connection.close();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Funcion de Lectura (R) del CRUD
    public List<Encargado> readEncargado(){
        String query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS";
        List<Encargado> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Encargado en = new Encargado();
                en.setIdEncargado(rs.getInt("ID_ENCARGADO"));
                en.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                en.setTelefono(rs.getString("TELEFONO"));
                en.setCorreo(rs.getString("CORREO"));
                en.setPuesto(rs.getString("PUESTO"));
                en.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                lista.add(en);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args){
        EncargadoDao dao = new EncargadoDao();
        List<Encargado> lista = dao.readEncargado();
        for(Encargado en : lista){
            System.out.println(en.getIdEncargado());
        }
    }

    //Funcion de actualizar (U) del CRUD
    public boolean updateEncargado(int IdEncargado, Encargado en){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "UPDATE ENCARGADOS SET NOMBRE_COMPLETO=?, CORREO=?, PUESTO=?, HORA_ENTRADA=?, TELEFONO=? WHERE ID_ENCARGADO=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, en.getNombreCompleto());
            ps.setString(2, en.getCorreo());
            ps.setString(3, en.getPuesto());
            ps.setTimestamp(4, en.getHoraEntrada());
            ps.setString(5, en.getTelefono());
            ps.setInt(6, IdEncargado); //Este es el WHERE ID=?
            int resultado = ps.executeUpdate();
            if(resultado>0){
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Funcion de eliminar (D) del CRUD
    public boolean deleteEncargado(int IdEncargado){
        boolean seBorro = false;
        String query = "DELETE FROM ENCARGADOS WHERE ID_ENCARGADO=? ";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, IdEncargado);
            if(ps.executeUpdate()>0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return seBorro;
    }
}
