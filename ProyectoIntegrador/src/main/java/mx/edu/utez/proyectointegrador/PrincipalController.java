package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;

public class PrincipalController {
    @FXML
    private Label horas;
    @FXML
    private Label fechas;

    private AnimationTimer timer;

    public void initialize() {
        relojPantalla();
    }

    public void relojPantalla() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalTime tiempoActual = LocalTime.now();
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                horas.setText(tiempoActual.format(formatoHora));
                fechas.setText(fechaActual.format(formatoFecha));
            }
        };
        timer.start();
    }
    public void detenerReloj() {
        if (timer != null) {
            timer.stop();
        }
    }
    //Ir al inicio de sesion de admin
    @FXML
    void irAdmin(ActionEvent event){
        try{
            //Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginAdmin.fxml"));
            Parent root = loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Login administrador");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Abrir superposicion
    //Entrada
    //Salida

}