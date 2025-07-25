package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ListaRetardosController{
    @FXML
    private Button menu;

    @FXML
    void regresarMenu(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminIndex.fxml"));
            Parent root = loader.load();
            //Crear una nueva ventana
            Stage loginStage = new Stage();
            loginStage.setTitle("Admin Index");
            loginStage.setScene(new Scene(root));
            loginStage.show();
            //Cerrar la ventana actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
