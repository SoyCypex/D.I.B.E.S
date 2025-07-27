package mx.edu.utez.proyectointegrador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Retardo;
import mx.edu.utez.proyectointegrador.modelo.dao.RetardoDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;

public class ListaRetardosController implements Initializable{
    @FXML
    private TableView<Retardo> tablaRetardo;
    @FXML
    private TableColumn<Retardo, Integer> tablaNum;
    @FXML
    private TableColumn<Retardo, String> tablaMatricula;
    @FXML
    private TableColumn<Retardo, Date> tablaFecha;
    @FXML
    private TableColumn<Retardo, Timestamp> tablaHora;
    @FXML
    private  TableColumn<Retardo, Timestamp> tablaTiempo;
    @FXML
    private TableColumn<Retardo, String> tablaJustificado;
    @FXML
    private Button eliminar;
    @FXML
    private Button menu;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        RetardoDao dao = new RetardoDao();
        List<Retardo> datos = dao.readRetardo();
        Tooltip tooltip = new Tooltip("Doble clic para editar • Backspace o 'Eliminar' para borrar");
        Tooltip.install(tablaRetardo, tooltip);
        ObservableList<Retardo> datosObservables = FXCollections.observableArrayList(datos);
        tablaNum.setCellValueFactory(new PropertyValueFactory<>("numRetardo"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRetardo"));
        tablaHora.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tablaHora.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toLocalDateTime().toLocalTime().format(formatter));
                }
            }
        });

        tablaTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoRetardo"));
        tablaTiempo.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toLocalDateTime().toLocalTime().format(formatter));
                }
            }
        });
        tablaJustificado.setCellValueFactory(new PropertyValueFactory<>("justificado"));
        //Pintar los datos
        tablaRetardo.setItems(datosObservables);
        //Ponerle un evento a la tabla
        tablaRetardo.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !tablaRetardo.getSelectionModel().isEmpty()) {
                Retardo seleccionado = tablaRetardo.getSelectionModel().getSelectedItem();
                abrirVentanaEdicion(seleccionado);
            }else if(event.getClickCount() == 1 && !tablaRetardo.getSelectionModel().isEmpty()) {
                eliminar.setDisable(false);
            }
        });
        tablaRetardo.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Retardo seleccionado =  tablaRetardo.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteRetardo(seleccionado.getNumRetardo());
                        tablaRetardo.getItems().remove(seleccionado);
                        tablaRetardo.refresh();
                    }
                }
            }
        });
    }

    public void abrirVentanaEdicion(Retardo retardo){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarRetardo.fxml"));
            Parent root = loader.load();
            //Pasar Retardo
            EditarRetardos controller = loader.getController();
            controller.setRetardo(retardo);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar retardo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tablaRetardo.refresh();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas seguro que quieres eliminar este registro?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void eliminarRetardo(){
        if(confirmDelete()){
            RetardoDao dao = new RetardoDao();
            Retardo seleccionado = tablaRetardo.getSelectionModel().getSelectedItem();
            tablaRetardo.getItems().remove(seleccionado);
            tablaRetardo.refresh();
            dao.deleteRetardo(seleccionado.getNumRetardo());
        }
    }

    @FXML
    void registrarRetardo(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarRetardo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar retardo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //Cuando se cierre la ventana actualiza la tabla
            RetardoDao dao = new RetardoDao();
            List<Retardo> datos = dao.readRetardo();
            tablaRetardo.setItems(FXCollections.observableArrayList(datos));
            tablaRetardo.refresh();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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
