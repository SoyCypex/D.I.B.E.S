package mx.edu.utez.proyectointegrador.modelo.dao;

import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {
    //Funcion de crear alumno (C) del CRUD
    public boolean createAlumno(Alumno a){
        //Obtener la conexion
        //Preparar el sql statement
        String query = "INSERT INTO ALUMNOS (MATRICULA, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, CARRERA, CUATRIMESTRE_ACTUAL, CONTRASENIA, TELEFONO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection connection = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection .prepareStatement(query);
            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellidoPaterno());
            ps.setString(4, a.getApellidoMaterno());
            ps.setString(5, a.getCarrera());
            ps.setString(6, a.getCuatrimestreActual());
            ps.setString(7, a.getContrasenia());
            ps.setString(8, a.getTelefono());
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
        String query = "SELECT  * FROM ALUMNOS ORDER BY NOMBRE ASC";
        List<Alumno> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Alumno a = new Alumno();
                a.setMatricula(rs.getString("MATRICULA"));
                a.setNombre(rs.getString("NOMBRE"));
                a.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                a.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                a.setCarrera(rs.getString("CARRERA"));
                a.setCuatrimestreActual(rs.getString("CUATRIMESTRE_ACTUAL"));
                a.setContrasenia(rs.getString("CONTRASENIA"));
                a.setTelefono(rs.getString("TELEFONO"));
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
        String query = "UPDATE ALUMNOS SET NOMBRE=?, APELLIDO_PATERNO=?, APELLIDO_MATERNO=?, CARRERA=?, CUATRIMESTRE_ACTUAL=?, CONTASENIA=?, TELEFONO=? WHERE MATRICULA=? ";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellidoPaterno());
            ps.setString(3, a.getApellidoMaterno());
            ps.setString(4, a.getCarrera());
            ps.setString(5, a.getCuatrimestreActual());
            ps.setString(6, a.getContrasenia());
            ps.setString(7, a.getTelefono());
            ps.setString(8, matricula); //Este es el WHERE MATRICULA=?
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
            Connection conn = OracleDatabaseConnectionManager.getConnection();
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
}
