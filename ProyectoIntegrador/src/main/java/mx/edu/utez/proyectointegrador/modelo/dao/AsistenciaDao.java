package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDao {
    //Funcion de crear (C) del CRUD
    public boolean createAsistencia(Asistencia as) {
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO LISTA_DE_ASISTENCIA (MATRICULA, FECHA, HORA_ENTRADA, HORA_SALIDA) VALUES (?, ?, ?, ?)";
        try{
            Connection connection = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, as.getMatricula());
            ps.setDate(2, as.getFecha());
            ps.setTimestamp(3, as.getHoraEntrada());
            ps.setTimestamp(4, as.getHoraSalida());
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
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
    public List<Asistencia> readAsistencia() {
        String query = "SELECT * FROM LISTA_DE_ASISTENCIA ORDER BY NUM_REGISTRO ASC";
        List<Asistencia> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Asistencia as = new Asistencia();
                as.setNumRegistro(rs.getInt("NUM_REGISTRO"));
                as.setMatricula(rs.getString("MATRICULA"));
                as.setFecha(rs.getDate("FECHA"));
                as.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                as.setHoraSalida(rs.getTimestamp("HORA_SALIDA"));
                lista.add(as);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args) {
        AsistenciaDao dao = new AsistenciaDao();
        List<Asistencia> datos = dao.readAsistencia();
        //Ciclo Foreach
        for(Asistencia p : datos){
            System.out.println(p.getNumRegistro());
        }
    }

    //Funcion de actualizar (U) del CRUD
    public boolean updateAsistencia(int numRegistro, Asistencia as) {
        //Obtener la conexion
        //Preparar el sql statement
        String query = "UPDATE LISTA_DE_ASISTENCIA SET MATRICULA=?, FECHA=?, HORA_ENTRADA=?, HORA_SALIDA=? WHERE NUM_REGISTRO=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, as.getMatricula());
            ps.setDate(2, as.getFecha());
            ps.setTimestamp(3, as.getHoraEntrada());
            ps.setTimestamp(4, as.getHoraSalida());
            ps.setInt(5, numRegistro); //Este es el WHERE NUM_REGISTRO=?
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                conn.close();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Funcion de eliminar (D) del crud
    public boolean deleteAsistencia(int numRegistro) {
        boolean seBorro = false;
        String query = "DELETE FROM LISTA_DE_ASISTENCIA WHERE NUM_REGISTRO=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, numRegistro);
            if(ps.executeUpdate()>0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return seBorro;
    }

}
