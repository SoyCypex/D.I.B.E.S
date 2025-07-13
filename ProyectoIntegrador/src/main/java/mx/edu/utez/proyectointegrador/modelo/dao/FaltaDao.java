package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Falta;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaltaDao {
    //Funcion de crear (C) del CRUD
    public boolean createFalta(Falta f) {
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO LISTA_DE_FALTAS (ID_FALTA, MATRICULA, FECHA_DE_FALTA, JUSTIFICADA) VALUES (?, ?, ?, ?)";
        try{
            Connection connection = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, f.getIdFalta());
            ps.setString(2, f.getMatricula());
            ps.setTimestamp(3, f.getFechaFalta());
            ps.setString(4, f.getJustificada());
            int resultado = ps.executeUpdate();
            if(resultado > 0){
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
    public List<Falta> readFalta(){
        String query = "SELECT * FROM LISTA_DE_FALTAS ORDER BY ID_FALTA ASC";
        List<Falta> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Falta f = new Falta();
                f.setIdFalta(rs.getInt("ID_FALTA"));
                f.setMatricula(rs.getString("MATRICULA"));
                f.setFechaFalta(rs.getTimestamp("FECHA_FALTA"));
                f.setJustificada(rs.getString("JUSTIFICADA"));
                lista.add(f);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args){
        FaltaDao dao = new FaltaDao();
        List<Falta> lista = dao.readFalta();
        for(Falta f : lista){
            System.out.println(f.getIdFalta());
            System.out.println(f.getMatricula());
        }
    }

    //Funcion de actualizar (U) del CRUD
    public boolean updateFalta(int idFalta, Falta f) {
        //Obtener la conexion
        //Preparar el sql statement
        String query = "UPDATE LISTA_DE_FALTAS SET MATRICULA=?, FECHA_FALTA=?, JUSTIFICADA=? WHERE ID_FALTA=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, f.getMatricula());
            ps.setTimestamp(2, f.getFechaFalta());
            ps.setString(3, f.getJustificada());
            ps.setInt(4, idFalta); //Este es el WHERE ID_FALTA=?
            int resultado = ps.executeUpdate();
            if(resultado > 0){
                conn.close();
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Funcion de eliminar (D) del crud
    public boolean deleteFalta(int idFalta) {
        boolean seBorro = false;
        String query = "DELETE FROM LISTA_DE_FALTA WHERE ID_FALTA=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idFalta);
            if(ps.executeUpdate()>0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return seBorro;
    }

}
