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
import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;

public class AsistenciaController implements Initializable{
    @FXML
    private Button menu;
    @FXML
    private TableView<Asistencia> tablaAsistencia;
    @FXML
    private TableColumn<Asistencia,Integer> tablaNum;
    @FXML
    private TableColumn<Asistencia,String> tablaMatricula;
    @FXML
    private TableColumn<Asistencia,Date> tablaFecha;
    @FXML
    private TableColumn<Asistencia, Timestamp> tablaEntrada;
    @FXML
    private TableColumn<Asistencia,Timestamp> tablaSalida;
    @FXML
    private Button eliminarAsistencia;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        AsistenciaDao dao = new AsistenciaDao();
        List<Asistencia> datos = dao.readAsistencia();
        Tooltip tooltip = new Tooltip("Doble clic para editar • Backspace o 'Eliminar' para borrar");
        Tooltip.install(tablaAsistencia, tooltip);
        ObservableList<Asistencia> datosObservables = FXCollections.observableArrayList(datos);

        tablaNum.setCellValueFactory(new PropertyValueFactory<>("numRegistro"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tablaEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tablaSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tablaEntrada.setCellFactory(column -> new TableCell<>() {
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
        tablaSalida.setCellFactory(column -> new TableCell<>() {
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
        //Pintar los datos
        tablaAsistencia.setItems(datosObservables);
        //Ponerle un evento a la tabla
        tablaAsistencia.setOnMouseClicked(( event) -> {
            if(event.getClickCount() == 2 && !tablaAsistencia.getSelectionModel().isEmpty()) {
                Asistencia seleccionado = tablaAsistencia.getSelectionModel().getSelectedItem();
                abrirVentanaEdicion(seleccionado);
            } else if (event.getClickCount() == 1 && !tablaAsistencia.getSelectionModel().isEmpty()) {
                eliminarAsistencia.setDisable(false);
            }
        });
        tablaAsistencia.setOnKeyPressed(( event) -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Asistencia seleccionado = (Asistencia)tablaAsistencia.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteAsistencia(seleccionado.getNumRegistro());
                        tablaAsistencia.getItems().remove(seleccionado);
                        tablaAsistencia.refresh();
                    }
                }
            }
        });
    }

    public void abrirVentanaEdicion(Asistencia asistencia){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarAsistencia.fxml"));
            Parent root = loader.load();
            //Pasar asistencia
            EditarAsistencia controller = loader.getController();
            controller.setAsistencia(asistencia);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Asistencia");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tablaAsistencia.refresh();
        }catch (Exception e){
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

    public void eliminarA(){
        if((confirmDelete())){
            AsistenciaDao dao = new AsistenciaDao();
            Asistencia seleccionado = tablaAsistencia.getSelectionModel().getSelectedItem();
            tablaAsistencia.getItems().remove(seleccionado);
            tablaAsistencia.refresh();
            dao.deleteAsistencia(seleccionado.getNumRegistro());
        }
    }

    @FXML
    void registrarAsistencia(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarAsistencia.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Asistencia");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //Cuando se cierre la ventana actualiza la tabla
            AsistenciaDao dao = new AsistenciaDao();
            List<Asistencia> lista = dao.readAsistencia();
            tablaAsistencia.setItems(FXCollections.observableArrayList(lista));
            tablaAsistencia.refresh();
        }catch (IOException e){
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
