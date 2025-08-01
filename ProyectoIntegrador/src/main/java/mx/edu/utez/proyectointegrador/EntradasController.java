package mx.edu.utez.proyectointegrador;

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
import mx.edu.utez.proyectointegrador.modelo.Retardo;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;
import mx.edu.utez.proyectointegrador.modelo.dao.FaltaDao;
import mx.edu.utez.proyectointegrador.modelo.dao.RetardoDao;

import javafx.concurrent.Task;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class EntradasController {
    @FXML
    private TextField matricula;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Button registrar;
    @FXML
    private ProgressIndicator spinner;

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
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
    void registrarEntrada(ActionEvent event) {
        String matriculaTxt = matricula.getText().trim();
        String contraseniaTxt = contrasena.getText().trim();
        matricula.setDisable(true);
        contrasena.setDisable(true);
        spinner.setVisible(true);
        registrar.setDisable(true);
        Task<String> tareaRegistro = new Task<>() {
            @Override
            protected String call() {
                AlumnoDao alumnoDao = new AlumnoDao();
                AsistenciaDao asistenciaDao = new AsistenciaDao();
                RetardoDao retardoDao = new RetardoDao();
                FaltaDao faltaDao = new FaltaDao();
                //Obtener alumno y validar credenciales
                Alumno alumno = alumnoDao.obtenerAlumnoPorCredenciales(matriculaTxt, contraseniaTxt);
                if (alumno == null) {
                    return "Credenciales incorrectas.";
                }
                String matricula = alumno.getMatricula();
                LocalDate hoy = LocalDate.now();
                //Verificar si ya registró entrada o tiene falta
                if (asistenciaDao.yaExisteAsistenciaHoy(matricula, hoy)) {
                    return "Ya registraste tu entrada hoy.";
                }
                if (faltaDao.existeFaltaHoy(matricula, hoy)) {
                    return "No puedes registrar entrada. Ya tienes una falta registrada hoy.";
                }
                //Obtener hora esperada y hora actual
                Timestamp horaEsperadaTS = alumno.getHoraEntrada();
                LocalDateTime horaEsperada = horaEsperadaTS.toLocalDateTime();
                LocalDateTime horaActual = LocalDateTime.now();
                long minutosDiferencia = Duration.between(horaEsperada, horaActual).toMinutes();
                Timestamp timestampActual = Timestamp.valueOf(horaActual);
                //CASO 1: Asistencia (temprano, puntual o ≤15 minutos tarde)
                if (minutosDiferencia <= 15 && minutosDiferencia >= -60) {
                    Asistencia asistencia = new Asistencia(matricula, Date.valueOf(hoy), timestampActual, null);
                    boolean ok = asistenciaDao.createAsistencia(asistencia);
                    return ok ? "Entrada registrada como asistencia." : "Error al registrar asistencia.";
                }
                //CASO 2: Retardo (entre 16 y 20 minutos)
                else if (minutosDiferencia > 15 && minutosDiferencia <= 20) {
                    Retardo retardo = new Retardo(
                            matricula,
                            Date.valueOf(hoy),
                            timestampActual,
                            timestampActual,
                            "No"
                    );
                    boolean r1 = retardoDao.createRetardo(retardo);
                    Asistencia asistencia = new Asistencia(matricula, Date.valueOf(hoy), timestampActual, null);
                    boolean r2 = asistenciaDao.createAsistencia(asistencia);
                    return (r1 && r2) ? "Entrada registrada con retardo." : "Error al registrar retardo.";
                }
                //CASO 3: Falta (más de 20 minutos tarde)
                else {
                    Falta falta = new Falta(matricula, Date.valueOf(hoy), "No");
                    boolean ok = faltaDao.createFalta(falta);
                    return ok ? "Llegaste demasiado tarde. Se registró una falta." : "Error al registrar falta.";
                }
            }
        };
        //Manejo del resultado
        tareaRegistro.setOnSucceeded(e -> {
            spinner.setVisible(false);
            matricula.setDisable(false);
            contrasena.setDisable(false);
            registrar.setDisable(false);
            String resultado = tareaRegistro.getValue();
            mostrarAlerta("Resultado", resultado, Alert.AlertType.INFORMATION);
            if (resultado.startsWith("Entrada registrada")) {
                cerrarVentana(); //solo si fue exitosa
            }
        });
        tareaRegistro.setOnFailed(e -> {
            spinner.setVisible(false);
            matricula.setDisable(false);
            contrasena.setDisable(false);
            registrar.setDisable(false);
            mostrarAlerta("Error", "Ocurrió un error inesperado. Revisa tu conexión.", Alert.AlertType.ERROR);
        });
        new Thread(tareaRegistro).start();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) matricula.getScene().getWindow();
        stage.close();
    }

}
