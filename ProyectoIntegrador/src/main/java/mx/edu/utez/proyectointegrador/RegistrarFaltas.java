package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Falta;
import mx.edu.utez.proyectointegrador.modelo.dao.FaltaDao;

import java.sql.Date;


public class RegistrarFaltas {
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField justificada;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void registrarFalta(ActionEvent event) {
        if(matricula.getText().isEmpty() || fecha.getValue() == null) {
            mostrarAlerta("Error!", "Matricula y fecha son obligatorios");
            return;
        }
        try{
            Falta nuevo = new Falta(
                    matricula.getText(),
                    Date.valueOf(fecha.getValue()),
                    justificada.getText()
            );
            FaltaDao dao = new FaltaDao();
            if(dao.createFalta(nuevo)){
                matricula.clear();
                fecha.setValue(null);
                justificada.clear();
                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Éxito");
                exito.setHeaderText(null);
                exito.setContentText("Falta registrada correctamente");
                exito.showAndWait();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }else{
                mostrarAlerta("Error!", "Error al registrar falta");
            }
        }catch(Exception e){
            mostrarAlerta("Error!", "Ocurrio un error inesperado");
        }
    }

}
