package mx.edu.utez.proyectointegrador.modelo.dao;

import javafx.scene.control.Alert;
import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDate;
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

    public List<Asistencia> readAsistenciasEspecificas(String filtro, String valorBusqueda){
        List<Asistencia> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "";
            switch (filtro){
                case "Todos" -> query = "SELECT * FROM LISTA_DE_ASISTENCIA ORDER BY NUM_REGISTRO ASC";
                case "Matricula" -> query = "SELECT * FROM LISTA_DE_ASISTENCIA WHERE MATRICULA LIKE ? ORDER BY NUM_REGISTRO ASC";
                case "Fecha" -> query = "SELECT * FROM LISTA_DE_ASISTENCIA WHERE TRUNC(FECHA) = TO_DATE(?, 'YYYY-MM-DD') ORDER BY NUM_REGISTRO ASC";
                case "Hora de entrada" -> query = "SELECT * FROM LISTA_DE_ASISTENCIA WHERE TO_CHAR(HORA_ENTRADA, 'HH24:MI') LIKE ? ORDER BY NUM_REGISTRO";
                case "Hora de salida" -> query = "SELECT * FROM LISTA_DE_ASISTENCIA WHERE TO_CHAR(HORA_SALIDA, 'HH24:MI') LIKE ? ORDER BY NUM_REGISTRO ASC";
                default -> throw new IllegalArgumentException("Filtro inválido: " + filtro);
            }
            PreparedStatement ps = conn.prepareStatement(query);
            if (!filtro.equals("Todos")) {
                if (filtro.equals("Fecha")) {
                    ps.setString(1, valorBusqueda); //formato 'YYYY-MM-DD'
                } else {
                    ps.setString(1, "%" + valorBusqueda + "%"); //para LIKE en otros casos
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Asistencia a = new Asistencia();
                a.setMatricula(rs.getString("MATRICULA"));
                a.setFecha(rs.getDate("FECHA"));
                a.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                a.setHoraSalida(rs.getTimestamp("HORA_SALIDA"));
                lista.add(a);
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

    public boolean yaExisteAsistenciaHoy(String matricula, LocalDate fecha) {
        String query = "SELECT COUNT(*) FROM LISTA_DE_ASISTENCIA WHERE MATRICULA = ? AND TRUNC(FECHA) = ?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, matricula);
            stmt.setDate(2, java.sql.Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrarHoraSalida(String matricula, Timestamp horaSalida) {
        String sql = "UPDATE LISTA_DE_ASISTENCIA SET HORA_SALIDA = ? WHERE MATRICULA = ? AND TRUNC(FECHA) = TRUNC(SYSDATE)";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, horaSalida);
            ps.setString(2, matricula);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Asistencia buscarAsistenciaPorMatriculaYFecha(String matricula, Date fecha) {
        String query = "SELECT * FROM LISTA_DE_ASISTENCIA WHERE MATRICULA = ? AND TRUNC(FECHA) = ?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, matricula);
            ps.setDate(2, fecha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Asistencia a = new Asistencia();
                a.setNumRegistro(rs.getInt("NUM_REGISTRO"));
                a.setMatricula(rs.getString("MATRICULA"));
                a.setFecha(rs.getDate("FECHA"));
                a.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                a.setHoraSalida(rs.getTimestamp("HORA_SALIDA"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarHoraSalida(Asistencia asistencia) {
        String query = "UPDATE LISTA_DE_ASISTENCIA SET HORA_SALIDA=? WHERE NUM_REGISTRO=?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, asistencia.getHoraSalida());
            ps.setInt(2, asistencia.getNumRegistro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAsistenciaPorId(int idAsistencia) {
        String query = "DELETE FROM LISTA_DE_ASISTENCIA WHERE NUM_REGISTRO=?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idAsistencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
