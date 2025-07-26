package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Encargado;
import mx.edu.utez.proyectointegrador.modelo.dao.EncargadoDao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RegistrarEncargados{
    @FXML
    private TextField nombre;
    @FXML
    private TextField telefono;
    @FXML
    private TextField correo;
    @FXML
    private TextField puesto;
    @FXML
    private TextField horaEntrada;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void guardar(ActionEvent event){
        if(nombre.getText().isEmpty() ||
            correo.getText().isEmpty()){
            mostrarAlerta("Error!", "El nombre y correo son requeridos");
            return;
        }
        try{
            //Validar formato de hora
            LocalTime.parse(horaEntrada.getText());
            Encargado nuevo = new Encargado(
            nombre.getText(),
            telefono.getText(),
            correo.getText(),
            puesto.getText(),
            Timestamp.valueOf(LocalDate.now().atTime(LocalTime.parse(horaEntrada.getText())))
          );
          EncargadoDao dao = new EncargadoDao();
          if(dao.createEncargado(nuevo)){
              nombre.clear();
              telefono.clear();
              correo.clear();
              puesto.clear();
              horaEntrada.clear();
              Alert exito = new Alert(Alert.AlertType.INFORMATION);
              exito.setTitle("Éxito");
              exito.setHeaderText(null);
              exito.setContentText("Encargado registado correctamente");
              exito.showAndWait();
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.close();
          }else{
              mostrarAlerta("Error!", "Encargado no se pudo registrar");
          }
        }catch(DateTimeParseException e){
            mostrarAlerta("Formato incorrecto", "Asegúrate de ingresar la hora en formato HH:mm:ss");
        }catch(Exception e){
            mostrarAlerta("Error!", "Ocurrió un error inesperado");
            e.printStackTrace();
        }
    }


}
