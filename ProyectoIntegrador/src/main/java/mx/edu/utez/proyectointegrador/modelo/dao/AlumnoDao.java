package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Alumno;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager.getConnection;

public class AlumnoDao {
    //Funcion de crear alumno (C) del CRUD
    public boolean createAlumno(Alumno a){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO ALUMNOS (MATRICULA, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, CARRERA, CUATRIMESTRE_ACTUAL, CONTRASENIA, TELEFONO, HORA_ENTRADA, HORA_SALIDA, FECHA_FINALIZACION, ID_ENCARGADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection .prepareStatement(query);
            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellidoPaterno());
            ps.setString(4, a.getApellidoMaterno());
            ps.setString(5, a.getCarrera());
            ps.setString(6, a.getCuatrimestreActual());
            ps.setString(7, a.getContrasenia());
            ps.setString(8, a.getTelefono());
            ps.setTimestamp(9, a.getHoraEntrada());
            ps.setTimestamp(10, a.getHoraSalida());
            ps.setDate(11, a.getFechaFinalizacion());
            ps.setInt(12, a.getIdEncargado());
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
    public List<Alumno> readAlumnos(){
        String query = "SELECT MATRICULA, NOMBRE, CARRERA, CUATRIMESTRE_ACTUAL, HORA_ENTRADA, HORA_SALIDA, FECHA_FINALIZACION, ID_ENCARGADO FROM ALUMNOS ORDER BY MATRICULA ASC";
        List<Alumno> lista = new ArrayList<>();
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Alumno a = new Alumno();
                a.setMatricula(rs.getString("MATRICULA"));
                a.setNombre(rs.getString("NOMBRE"));
                a.setCarrera(rs.getString("CARRERA"));
                a.setCuatrimestreActual(rs.getString("CUATRIMESTRE_ACTUAL"));
                a.setHoraEntrada(rs.getTimestamp("HORA_ENTRADA"));
                a.setHoraSalida(rs.getTimestamp("HORA_SALIDA"));
                a.setFechaFinalizacion(rs.getDate("FECHA_FINALIZACION"));
                a.setIdEncargado(rs.getInt("ID_ENCARGADO"));
                lista.add(a);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args){
        AlumnoDao dao = new AlumnoDao();
        List<Alumno> lista = dao.readAlumnos();
        //Ciclo Foreach
        for(Alumno a : lista){
            System.out.println(a.getMatricula());
        }
    }

    //Funcion de actualizar (U) del CRUD
    public boolean updateAlumno(String matricula, Alumno a){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "UPDATE ALUMNOS SET NOMBRE=?, APELLIDO_PATERNO=?, APELLIDO_MATERNO=?, CARRERA=?, CUATRIMESTRE_ACTUAL=?, CONTASENIA=?, TELEFONO=?, HORA_ENTRADA=?, HORA_SALIDA=?, FECHA_FINALIZACION=?, ID_ENCARGADO=? WHERE MATRICULA=? ";
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellidoPaterno());
            ps.setString(3, a.getApellidoMaterno());
            ps.setString(4, a.getCarrera());
            ps.setString(5, a.getCuatrimestreActual());
            ps.setString(6, a.getContrasenia());
            ps.setString(7, a.getTelefono());
            ps.setTimestamp(8, a.getHoraEntrada());
            ps.setTimestamp(9, a.getHoraSalida());
            ps.setDate(10, a.getFechaFinalizacion());
            ps.setInt(11, a.getIdEncargado());
            ps.setString(12, matricula); //Este es el WHERE MATRICULA=?
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
    public boolean deleteAlumno(String matricula){
        String query = "DELETE FROM ALUMNOS WHERE MATRICULA=?";
        boolean seBorro = false;
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, matricula);
            if(ps.executeUpdate()>0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return seBorro;
    }
    //Encontrar por matricula para modificar
    public Alumno findByMatricula(String matricula){
        Alumno alumno = null;
        String query = "SELECT * FROM ALUMNOS WHERE MATRICULA=?";
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, matricula);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                alumno = new Alumno(
                  rs.getString("MATRICULA"),
                  rs.getString("NOMBRE"),
                  rs.getString("APELLIDO_PATERNO"),
                  rs.getString("APELLIDO_MATERNO"),
                  rs.getString("CARRERA"),
                  rs.getString("CUATRIMESTRE_ACTUAL"),
                  rs.getString("CONTRASENIA"),
                  rs.getString("TELEFONO"),
                  rs.getTimestamp("HORA_ENTRADA"),
                  rs.getTimestamp("HORA_SALIDA"),
                  rs.getDate("FECHA_FINALIZACION"),
                  rs.getInt("ID_ENCARGADO")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return alumno;
    }
    //Validar credenciales
    public boolean validarCredenciales(String matricula, String contrasenia){
        String query = "SELECT COUNT(*) FROM ALUMNOS WHERE MATRICULA=? AND CONTRASENA=?";
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, matricula);
            ps.setString(2, contrasenia);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //Buscar alumno
    public Alumno getAlumnoByMatricula(String matricula){
        String query = "SELECT * FROM ALUMNOS WHERE MATRICULA=?";
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, matricula);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String nombre = rs.getString("NOMBRE");
                    String apellidoPaterno = rs.getString("APELLIDO_PATERNO");
                    String apellidoMaterno = rs.getString("APELLIDO_MATERNO");
                    String carrera = rs.getString("CARRERA");
                    String cuatrimestreActual = rs.getString("CUATRIMESTRE_ACTUAL");
                    String contrasenia = rs.getString("CONTRASENA");
                    String telefono = rs.getString("TELEFONO");
                    Timestamp horaEntradaAsignada = rs.getTimestamp("HORA_ENTRADA");
                    Timestamp horaSalidaAsignada = rs.getTimestamp("HORA_SALIDA");
                    Date fechaFinalizacion = rs.getDate("FECHA_FINALIZACION");
                    int idEncargado = rs.getInt("ID_ENCARGADO");

                    return new Alumno(matricula, nombre, apellidoPaterno, apellidoMaterno, carrera, cuatrimestreActual, contrasenia, telefono, horaEntradaAsignada, horaSalidaAsignada, fechaFinalizacion, idEncargado);

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
