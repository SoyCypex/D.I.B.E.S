package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;
import java.sql.Date;

import java.time.LocalDate;
import java.time.LocalTime;

public class CredencialesController {
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Button registrar;

    private String tipoRegistro;
    private AsistenciaDao asistenciaDao;
    private AlumnoDao alumnoDao;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void initialize(){
        asistenciaDao = new AsistenciaDao();
        alumnoDao = new AlumnoDao();
    }

    public void setTipoRegistro(String tipoRegistro){
        this.tipoRegistro = tipoRegistro;
    }

    @FXML
    void registrar(ActionEvent event){
        String matricula =  usuario.getText();
        String contrasenia =  contrasena.getText();

        if(matricula.isEmpty() || contrasenia.isEmpty()){
            mostrarAlerta("Error!!", "Por favor ingrese credenciales");
            return;
        }

        try{
            if(alumnoDao.validarCredenciales(matricula, contrasenia)) {
                Alumno alumno = alumnoDao.getAlumnoByMatricula(matricula);
                if (alumno == null) {
                    mostrarAlerta("Error!!", "Alumno no encontrado");
                    return;
                }
                //Obtener fecha y hora actuales del sistema
                LocalDate fecha = LocalDate.now();
                LocalTime hora = LocalTime.now();
                //Convertir la hora asignada del alumno (Timestamp) a LocalTime para las comparaciones
                LocalTime horaEntradaAsignadaAlumno = null;
                if (alumno.getHoraEntrada() != null) {
                    horaEntradaAsignadaAlumno = alumno.getHoraEntrada().toLocalDateTime().toLocalTime();
                }
                LocalTime horaSalidaAsignadaAlumno = null;
                if (alumno.getHoraSalida() != null) {
                    horaSalidaAsignadaAlumno = alumno.getHoraSalida().toLocalDateTime().toLocalTime();
                }

                if ("entrada".equals(tipoRegistro)) {
                    //registrarEntrada(matricula, fecha, hora, horaEntradaAsignadaAlumno, alumno);
                } else if ("salida".equals(tipoRegistro)) {
                    //registrarSalida(matricula, fecha, hora, horaSalidaAsignadaAlumno, alumno);
                }
            }else{
                mostrarAlerta("Error", "Usuario o contrasenia incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error en la BD", "Ocurrio un error " + e.getMessage());
        }
    }


}
