package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SalidasController {

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
}
