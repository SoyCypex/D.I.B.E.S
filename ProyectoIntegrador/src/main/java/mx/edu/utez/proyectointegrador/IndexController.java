package mx.edu.utez.proyectointegrador;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IndexController {
    @FXML
    private Text horas;
    @FXML
    private Text fechas;
    @FXML
    private Button abrirBecarios;
    @FXML
    private Button abrirRetardos;
    @FXML
    private Button abrirFaltas;
    @FXML
    private Button abrirAsistencias;
    @FXML
    private Button abrirEncargados;
    @FXML
    private Button salir;

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
    //Abrir becarios
    @FXML
    void abrirBecarios(ActionEvent event){
        try{
            //Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaBecarios.fxml"));
            Parent root = (Parent) loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Becarios");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Abrir retardos
    @FXML
    void abrirRetardos(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaRetardos.fxml"));
            Parent root = (Parent) loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Retardos");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Abrir faltas
    @FXML
    void abrirFaltas(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaFaltas.fxml"));
            Parent root = (Parent) loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Retardos");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Abrir asistencias
    @FXML
    void abrirAsistencias(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Asistencia.fxml"));
            Parent root = (Parent) loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Retardos");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Abrir encargados
    @FXML
    void abrirEncargados(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Encargados.fxml"));
            Parent root = (Parent) loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Retardos");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Cerrar sesion
    @FXML
    void salir(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginAdmin.fxml"));
            Parent root = loader.load();
            //Crear una nueva ventana
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Admin");
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
