package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BecariosController implements Initializable {
    @FXML
    private ChoiceBox<String> filtro;
    @FXML
    private TextField buscador;
    @FXML
    private Button botonBuscar;
    @FXML
    private ProgressIndicator spinner;
    @FXML
    private TableView<Alumno> tablaBecario;
    @FXML
    private TableColumn<Alumno,String> tablaMatricula;
    @FXML
    private TableColumn<Alumno,String> tablaNombre;
    @FXML
    private TableColumn<Alumno,String> tablaCarrera;
    @FXML
    private TableColumn<Alumno,String> tablaCuatri;
    @FXML
    private TableColumn<Alumno,Timestamp> tablaEntrada;
    @FXML
    private TableColumn<Alumno,Timestamp> tablaSalida;
    @FXML
    private TableColumn<Alumno, Date> tablaFecha;
    @FXML
    private TableColumn<Alumno,String> tablaIden;
    @FXML
    private Button eliminarBecario;
    @FXML
    private Button abrirRegistrar;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle resourseBundle){
        filtro.setItems(FXCollections.observableArrayList("Matricula", "Nombre", "Carrera", "Cuatrimestre", "Hora Entrada", "Hora Salida", "Fecha", "ID encargado"));
        filtro.getSelectionModel().selectFirst(); //Selecciona la primera por defecto
        AlumnoDao dao = new AlumnoDao();
        List<Alumno> datos = dao.readAlumnos();
        Tooltip tooltip = new Tooltip("Doble clic para editar • Backspace o 'Eliminar' para borrar");
        Tooltip.install(tablaBecario, tooltip);
        ObservableList<Alumno> datosObservables = FXCollections.observableArrayList(datos);

        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        tablaCuatri.setCellValueFactory(new PropertyValueFactory<>("cuatrimestreActual"));
        tablaEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tablaSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFinalizacion"));
        tablaIden.setCellValueFactory(new PropertyValueFactory<>("idEncargado"));
        //Mostrar solo la hora de entrada
        tablaEntrada.setCellFactory(column -> new TableCell<Alumno, Timestamp>() {
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
        //Mostrar solo la hora de salida
        tablaSalida.setCellFactory(column -> new TableCell<Alumno, Timestamp>() {
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
        //Pintar los datos los datos
        tablaBecario.setItems(datosObservables);
        //Ponerle un evento a la tabla
        tablaBecario.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !tablaBecario.getSelectionModel().isEmpty()){
                Alumno seleccionado = tablaBecario.getSelectionModel().getSelectedItem();
                //Traer becario completo
                AlumnoDao d = new AlumnoDao();
                Alumno alumnoCompleto = d.findByMatricula(seleccionado.getMatricula());
                abrirVentanaEdicion(alumnoCompleto);
            }else if(event.getClickCount() == 1 && !tablaBecario.getSelectionModel().isEmpty()){
                eliminarBecario.setDisable(false);
            }
        });
        tablaBecario.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Alumno seleccionado = (Alumno)tablaBecario.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteAlumno(seleccionado.getMatricula());
                        tablaBecario.getItems().remove(seleccionado);
                        tablaBecario.refresh();
                    }
                }
            }
        });
    }

    public void abrirVentanaEdicion(Alumno alumno){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormModificacion.fxml"));
            Parent root = loader.load();
            //Pasar el alumno
            EditarBecarios controller = loader.getController();
            controller.setAlumno(alumno);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Becario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); //Espera a que se cierre la ventana
            AlumnoDao dao = new AlumnoDao();
            List<Alumno> datosActualizados = dao.readAlumnos();
            tablaBecario.setItems(FXCollections.observableArrayList(datosActualizados));
            tablaBecario.refresh();
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

    public void borrarBecario(){
        if(confirmDelete()){
            AlumnoDao dao = new AlumnoDao();
            Alumno seleccionado = (Alumno)tablaBecario.getSelectionModel().getSelectedItem();
            tablaBecario.getItems().remove(seleccionado);
            tablaBecario.refresh();
            dao.deleteAlumno(seleccionado.getMatricula());
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

    @FXML
    void registrarBecario(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormRegistro.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Becarios");
            stage.initModality(Modality.APPLICATION_MODAL); //Espera a que la ventana se cierre
            stage.showAndWait();
            //Una vez cerrada la ventana de registro, actualizamos la tabla:
            AlumnoDao dao = new AlumnoDao();
            List<Alumno> lista = dao.readAlumnos();
            tablaBecario.setItems(FXCollections.observableArrayList(lista));
            tablaBecario.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscar(ActionEvent event){
        String filtroSeleccionado = filtro.getValue();
        String textoBusqueda = buscador.getText();
        if (filtroSeleccionado == null || textoBusqueda.isBlank()) return;
        AlumnoDao dao = new AlumnoDao();
        spinner.setVisible(true);
        botonBuscar.setDisable(true);
        Task<List<Alumno>> tareaBusqueda = new Task<>() {
            @Override
            protected List<Alumno> call() throws Exception {
                return dao.readAlumnosEspecificos(filtroSeleccionado, textoBusqueda);
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
            List<Alumno> resultados = tareaBusqueda.getValue();
            tablaBecario.setItems(FXCollections.observableArrayList(resultados));
            tablaBecario.refresh();
        });
        new Thread(tareaBusqueda).start();
    }

}
