package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Date;

import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Retardo;
import mx.edu.utez.proyectointegrador.modelo.dao.RetardoDao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RegistrarRetardos{
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField horaIngreso;
    @FXML
    private TextField tiempoRetardo;
    @FXML
    private TextField justificado;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void registrarRetardo(ActionEvent event) {
        if (matricula.getText().isEmpty() ||
                fecha.getValue() == null) {
            mostrarAlerta("Error!", "Matricula y fecha son obligatirios");
            return;
        }
        try{
            //Validar formato de hora
            LocalDate fechaSeleccionada = fecha.getValue(); // Fecha del DatePicker
            LocalTime horaE = LocalTime.parse(horaIngreso.getText());
            LocalTime tiempoR = LocalTime.parse(tiempoRetardo.getText());
            Timestamp horaEN = Timestamp.valueOf(fechaSeleccionada.atTime(horaE));
            Timestamp tiempoRE = Timestamp.valueOf(fechaSeleccionada.atTime(tiempoR));
            Retardo nuevo = new Retardo(
                matricula.getText(),
                Date.valueOf(fechaSeleccionada),
                horaEN,
                tiempoRE,
                justificado.getText()
            );
            RetardoDao dao = new RetardoDao();
            if(dao.createRetardo(nuevo)){
                matricula.clear();
                fecha.setValue(null);
                horaIngreso.clear();
                tiempoRetardo.clear();
                justificado.clear();
                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Éxito");
                exito.setHeaderText(null);
                exito.setContentText("Retardo registrado correctamente");
                exito.showAndWait();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }else{
                mostrarAlerta("Error!", "Error al registrar retardo");
            }
        }catch(DateTimeParseException e){
            mostrarAlerta("Formato incorrecto", "Asegúrate de ingresar la hora en formato HH:mm:ss");
        }catch(Exception e){
            mostrarAlerta("Error!", "Ocurrio un error inesperado");
            e.printStackTrace();
        }
    }
}
