package mx.edu.utez.proyectointegrador.modelo.dao;

import javafx.scene.control.Alert;
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
    public List<Encargado> readEncargadosEspecificos(String filtro, String valorBusqueda){
        List<Encargado> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "";
            switch (filtro){
                case "Todos" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS ORDER BY ID_ENCARGADO ASC";
                case "Nombre" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS WHERE NOMBRE_COMPLETO LIKE ? ORDER BY ID_ENCARGADO ASC";
                case "Telefono" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS WHERE TELEFONO LIKE ? ORDER BY ID_ENCARGADO ASC";
                case "Correo" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS WHERE CORREO LIKE ? ORDER BY ID_ENCARGADO ASC";
                case "Hora entrada" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS WHERE TO_CHAR(HORA_ENTRADA, 'HH24:MI') LIKE ? ORDER BY ID_ENCARGADO ASC";
                case "Puesto" -> query = "SELECT ID_ENCARGADO, NOMBRE_COMPLETO, TELEFONO, CORREO, PUESTO, HORA_ENTRADA FROM ENCARGADOS WHERE PUESTO LIKE ? ORDER BY ID_ENCARGADO ASC";
                default -> throw new IllegalArgumentException("Filtro inválido: " + filtro);
            }
            PreparedStatement ps = conn.prepareStatement(query);
            if (!filtro.equals("Todos")) {
                if (filtro.equals("Hora entrada")) {
                    ps.setString(1, valorBusqueda); //ya formateado tipo 'HH24:MI'
                } else {
                    ps.setString(1, "%" + valorBusqueda + "%");
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Encargado e = new Encargado();
                e.setIdEncargado(rs.getInt("ID_ENCARGADO"));
                e.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                e.setTelefono(rs.getString("TELEFONO"));
                e.setCorreo(rs.getString("CORREO"));
                e.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                e.setPuesto(rs.getString("PUESTO"));
                lista.add(e);
            }
            rs.close();
            ps.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error de busqueda");
            error.setHeaderText(null);
            error.setContentText("Error de formato de busqueda");
            error.showAndWait();
        }
        return lista;
    }

}
