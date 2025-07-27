package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Retardo;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetardoDao {
    //Funcion de crear (C) del CRUD
    public boolean createRetardo(Retardo r){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO RETARDOS (MATRICULA, FECHA_RETARDO, HORA_DE_INGRESO, TIEMPO_DE_RETARDO, JUSTIFICADO) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection connection = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, r.getMatricula());
            ps.setDate(2, r.getFechaRetardo());
            ps.setTimestamp(3, r.getHoraEntrada());
            ps.setTimestamp(4, r.getTiempoRetardo());
            ps.setString(5, r.getJustificado());
            int resultado  = ps.executeUpdate();
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
    public List<Retardo> readRetardo(){
        String query = "SELECT * FROM RETARDOS ORDER BY NUM_RETARDO ASC";
        List<Retardo> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Retardo r = new Retardo();
                r.setNumRetardo(rs.getInt("NUM_RETARDO"));
                r.setMatricula(rs.getString("MATRICULA"));
                r.setFechaRetardo(rs.getDate("FECHA_RETARDO"));
                r.setHoraEntrada(rs.getTimestamp("HORA_DE_INGRESO"));
                r.setTiempoRetardo(rs.getTimestamp("TIEMPO_DE_RETARDO"));
                r.setJustificado(rs.getString("JUSTIFICADO"));
                lista.add(r);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args) {
        RetardoDao dao = new RetardoDao();
        List<Retardo> datos = dao.readRetardo();
        //Ciclo Foreach
        for(Retardo p : datos){
            System.out.println(p.getNumRetardo());
        }
    }

    //Funcion de actualizar (U) del CRUD
    public boolean updateRetardo(int idRetardo, Retardo r){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "UPDATE RETARDOS SET MATRICULA=?, FECHA_RETARDO=?, HORA_DE_INGRESO=?, TIEMPO_DE_RETARDO=?, JUSTIFICADO=? WHERE NUM_RETARDO=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, r.getMatricula());
            ps.setDate(2, r.getFechaRetardo());
            ps.setTimestamp(3, r.getHoraEntrada());
            ps.setTimestamp(4, r.getTiempoRetardo());
            ps.setString(5, r.getJustificado());
            ps.setInt(6, idRetardo); //Este es el WHERE NUM_RETARDO=?
            int resultado  = ps.executeUpdate();
            if(resultado>0){
                conn.close();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Funcion de eliminar (D) del CRUD
    public boolean deleteRetardo(int idRetardo){
        boolean seBorro = false;
        String query = "DELETE FROM RETARDOS WHERE NUM_RETARDO=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idRetardo);
            if(ps.executeUpdate()>0){
                return seBorro;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return seBorro;
    }

}
