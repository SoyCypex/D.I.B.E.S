package mx.edu.utez.proyectointegrador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Falta;
import mx.edu.utez.proyectointegrador.modelo.dao.FaltaDao;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;


public class ListaFaltasController implements Initializable{
    @FXML
    private TextField buscador;
    @FXML
    private Button botonBuscar;
    @FXML
    private ProgressIndicator spinner;
    @FXML
    private ChoiceBox<String> filtro;
    @FXML
    private TableView<Falta> tablaFaltas;
    @FXML
    private TableColumn<Falta,Integer> tablaId;
    @FXML
    private TableColumn<Falta,String> tablaMatricula;
    @FXML
    private TableColumn<Falta, Date> tablaFecha;
    @FXML
    private TableColumn<Falta,String> tablaJustificada;
    @FXML
    private Button menu;
    @FXML
    private Button eliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        filtro.setItems(FXCollections.observableArrayList("Todos", "Matricula", "Fecha", "Justificada"));
        filtro.getSelectionModel().selectFirst(); //Selecciona la primera por defecto
        FaltaDao dao = new FaltaDao();
        List<Falta> datos = dao.readFalta();
        Tooltip tooltip = new Tooltip("Doble clic para editar • Backspace o 'Eliminar' para borrar");
        Tooltip.install(tablaFaltas, tooltip);
        ObservableList<Falta> datoObservables = FXCollections.observableArrayList(datos);

        tablaId.setCellValueFactory(new PropertyValueFactory<>("idFalta"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFalta"));
        tablaJustificada.setCellValueFactory(new PropertyValueFactory<>("justificada"));
        //Pintar los datos
        tablaFaltas.setItems(datoObservables);
        //Ponerle un evento a la tabla
        tablaFaltas.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !tablaFaltas.getSelectionModel().isEmpty()) {
                Falta seleccionado = tablaFaltas.getSelectionModel().getSelectedItem();
                abrirVentanaEdicion(seleccionado);
            }else if(event.getClickCount() == 1 && !tablaFaltas.getSelectionModel().isEmpty()){
                eliminar.setDisable(false);
            }
        });
        tablaFaltas.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Falta seleccionado = (Falta) tablaFaltas.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteFalta(seleccionado.getIdFalta());
                        tablaFaltas.getItems().remove(seleccionado);
                        tablaFaltas.refresh();
                    }
                }
            }
        });
    }

    public void abrirVentanaEdicion(Falta falta){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarFalta.fxml"));
            Parent root = loader.load();
            //Pasar faltas
            EditarFaltas controller = loader.getController();
            controller.setFaltas(falta);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Falta");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tablaFaltas.refresh();
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

    public void eliminarFalta(){
        if(confirmDelete()){
            FaltaDao dao = new FaltaDao();
            Falta seleccionado = tablaFaltas.getSelectionModel().getSelectedItem();
            tablaFaltas.getItems().remove(seleccionado);
            tablaFaltas.refresh();
            dao.deleteFalta(seleccionado.getIdFalta());
        }
    }

    @FXML
    void registrarFalta(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarFalta.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Falta");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //Cuando se cierre la ventana actualiza la tabla
            FaltaDao dao = new FaltaDao();
            List<Falta> datos = dao.readFalta();
            tablaFaltas.setItems(FXCollections.observableArrayList(datos));
            tablaFaltas.refresh();
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

     public void buscar(ActionEvent event) {
        String filtroSeleccionado = filtro.getValue();
        String textoBusqueda = buscador.getText().trim();
        if (filtroSeleccionado == null) return;
        if (filtroSeleccionado.equals("Todos")) {
            textoBusqueda = ""; //no se usará pero igual lo pasamos
        }
        //Aquí hacemos la variable efectivamente final
        final String finalFiltro = filtroSeleccionado;
        final String finalTextoBusqueda = textoBusqueda;
        FaltaDao dao = new FaltaDao();
        spinner.setVisible(true);
        botonBuscar.setDisable(true);
        Task<List<Falta>> tareaBusqueda = new Task<>() {
            @Override
            protected List<Falta> call() throws Exception {
                return dao.readFaltasEspecificas(finalFiltro, finalTextoBusqueda);
            }
        };
        tareaBusqueda.setOnFailed(e -> {
            spinner.setVisible(false);
            botonBuscar.setDisable(false);
            tareaBusqueda.getException().printStackTrace();
        });
        tareaBusqueda.setOnSucceeded(e -> {
            spinner.setVisible(false);
            botonBuscar.setDisable(false);
            List<Falta> resultados = tareaBusqueda.getValue();
            tablaFaltas.setItems(FXCollections.observableArrayList(resultados));
            tablaFaltas.refresh();
        });
        new Thread(tareaBusqueda).start();
    }

}
