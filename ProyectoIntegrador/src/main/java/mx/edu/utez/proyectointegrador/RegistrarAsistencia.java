package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Date;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RegistrarAsistencia {
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField horaEntrada;
    @FXML
    private TextField horaSalida;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void registrarAsistencia(ActionEvent event) {
        if (matricula.getText().isEmpty() ||
                fecha.getValue() == null) {
            mostrarAlerta("Error!", "Matricula y fecha son obligatirios");
            return;
        }
        try{
            //Validar formato de hora
            LocalDate fechaSeleccionada = fecha.getValue(); // Fecha del DatePicker
            LocalTime horaE = LocalTime.parse(horaEntrada.getText());
            LocalTime horaS = LocalTime.parse(horaSalida.getText());
            Timestamp horaEA = Timestamp.valueOf(fechaSeleccionada.atTime(horaE));
            Timestamp horaSA = Timestamp.valueOf(fechaSeleccionada.atTime(horaS));
            Asistencia nuevo = new Asistencia(
                    matricula.getText(),
                    Date.valueOf(fechaSeleccionada),
                    horaEA,
                    horaSA
            );
            AsistenciaDao dao = new AsistenciaDao();
            if (dao.createAsistencia(nuevo)) {
                matricula.clear();
                fecha.setValue(null);
                horaEntrada.clear();
                horaSalida.clear();
                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Éxito");
                exito.setHeaderText(null);
                exito.setContentText("Asistencia registrada correctamente");
                exito.showAndWait();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error!", "Error al registrar asistencia");
            }
        }catch(DateTimeParseException e){
            mostrarAlerta("Formato incorrecto", "Asegúrate de ingresar la hora en formato HH:mm:ss");
        }catch(Exception e){
            mostrarAlerta("Error!", "Ocurrio un error inesperado");
            e.printStackTrace();
        }
    }

}

