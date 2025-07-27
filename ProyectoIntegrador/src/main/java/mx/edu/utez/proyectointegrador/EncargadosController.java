package mx.edu.utez.proyectointegrador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import mx.edu.utez.proyectointegrador.modelo.Encargado;
import mx.edu.utez.proyectointegrador.modelo.dao.EncargadoDao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class EncargadosController implements Initializable{
    @FXML
    private Button menu;
    @FXML
    private TableView<Encargado> tablaEncargado;
    @FXML
    private TableColumn<Encargado, Integer> tablaId;
    @FXML
    private TableColumn<Encargado, String> tablaNombre;
    @FXML
    private TableColumn<Encargado, String> tablaTelefono;
    @FXML
    private TableColumn<Encargado, String> tablaCorreo;
    @FXML
    private TableColumn<Encargado, Timestamp> tablaHora;
    @FXML
    private TableColumn<Encargado, String> tablaPuesto;
    @FXML
    private Button eliminar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EncargadoDao dao = new EncargadoDao();
        List<Encargado> datos = dao.readEncargado();
        Tooltip tooltip = new Tooltip("Doble clic para editar • Backspace o 'Eliminar' para borrar");
        Tooltip.install(tablaEncargado, tooltip);
        ObservableList<Encargado> datosObservables = FXCollections.observableArrayList(datos);

        tablaId.setCellValueFactory(new PropertyValueFactory<>("idEncargado"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tablaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tablaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tablaHora.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tablaHora.setCellFactory(column -> new TableCell<Encargado, Timestamp>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

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

        tablaPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        //Pintar los datos
        tablaEncargado.setItems(datosObservables);
        //Ponerle un evento a la tabla
        tablaEncargado.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !tablaEncargado.getSelectionModel().isEmpty()){
                Encargado seleccionado = tablaEncargado.getSelectionModel().getSelectedItem();
                abrirVentanaEdicion(seleccionado);
            } else if(event.getClickCount() == 1 && !tablaEncargado.getSelectionModel().isEmpty()) {
                eliminar.setDisable(false);
            }
        });
        tablaEncargado.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Encargado seleccionado = (Encargado) tablaEncargado.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteEncargado(seleccionado.getIdEncargado());
                        tablaEncargado.getItems().remove(seleccionado);
                        tablaEncargado.refresh();
                    }
                }
            }
        });
    }

    public void abrirVentanaEdicion(Encargado encargado){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormModiEncargados.fxml"));
            Parent root = loader.load();
            //Pasar el encargado
            EditarEncargados controller = loader.getController();
            controller.setEncargado(encargado);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Encargado");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tablaEncargado.refresh();
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

    public void borrarEncargado(){
        if((confirmDelete())){
            EncargadoDao dao = new EncargadoDao();
            Encargado seleccionado = (Encargado) tablaEncargado.getSelectionModel().getSelectedItem();
            tablaEncargado.getItems().remove(seleccionado);
            tablaEncargado.refresh();
            dao.deleteEncargado(seleccionado.getIdEncargado());
        }
    }

    @FXML
    void registrarEncargado(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormRegEncargados.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Encargado");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //Cuando se cierre la ventana actualiza la tabla
            EncargadoDao dao = new EncargadoDao();
            List<Encargado> lista = dao.readEncargado();
            tablaEncargado.setItems(FXCollections.observableArrayList(lista));
            tablaEncargado.refresh();
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
