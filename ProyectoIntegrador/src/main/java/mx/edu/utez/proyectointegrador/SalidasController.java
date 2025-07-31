package mx.edu.utez.proyectointegrador;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.modelo.Falta;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;
import mx.edu.utez.proyectointegrador.modelo.dao.FaltaDao;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalidasController {
    @FXML
    private TextField matricula;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Button registrar;
    @FXML
    private ProgressIndicator spinner;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void recuperarContra(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecuperarContra.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Recuperar contraseña");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); //Espera a que se cierre la ventana
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void registrarSalida(ActionEvent event) {
        String matriculaIngresada = matricula.getText();
        String contraseniaIngresada = contrasena.getText();

        Task<Void> tarea = new Task<>() {
            @Override
            protected Void call() {
                AlumnoDao alumnoDao = new AlumnoDao();
                AsistenciaDao asistenciaDao = new AsistenciaDao();
                FaltaDao faltaDao = new FaltaDao();
                Alumno alumno = alumnoDao.obtenerAlumnoPorCredenciales(matriculaIngresada, contraseniaIngresada);
                if (alumno == null) {
                    Platform.runLater(() -> mostrarAlerta("Error", "Credenciales incorrectas"));
                    return null;
                }
                LocalDate hoy = LocalDate.now();
                Asistencia asistencia = asistenciaDao.buscarAsistenciaPorMatriculaYFecha(matriculaIngresada, Date.valueOf(hoy));
                if (asistencia == null) {
                    Platform.runLater(() -> mostrarAlerta("Error", "No se encontró un registro de entrada para hoy"));
                    return null;
                }

                if (asistencia.getHoraSalida() != null) {
                    Platform.runLater(() -> mostrarAlerta("Error", "Ya registraste tu hora de salida hoy"));
                    return null;
                }
                Timestamp horaSalidaActual = Timestamp.valueOf(LocalDateTime.now());
                Timestamp horaSalidaProgramada = alumno.getHoraSalida();
                if (horaSalidaActual.before(horaSalidaProgramada)) {
                    asistenciaDao.eliminarAsistenciaPorId(asistencia.getNumRegistro());
                    Falta falta = new Falta();
                    falta.setMatricula(matriculaIngresada);
                    falta.setFechaFalta(Date.valueOf(hoy));
                    falta.setJustificada("No");
                    faltaDao.createFalta(falta);
                    Platform.runLater(() -> mostrarAlerta("Advertencia", "Saliste antes de tiempo. Se registró falta"));
                } else {
                    asistencia.setHoraSalida(horaSalidaActual);
                    boolean actualizado = asistenciaDao.actualizarHoraSalida(asistencia);
                    if (actualizado) {
                        Platform.runLater(() -> mostrarAlerta("Éxito", "Hora de salida registrada correctamente"));
                    } else {
                        Platform.runLater(() -> mostrarAlerta("Error", "No se pudo actualizar la hora de salida"));
                    }
                }
                return null;
            }
        };
        new Thread(tarea).start();
    }

}
