package mx.edu.utez.proyectointegrador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Date;
import java.sql.Time;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BecariosController implements Initializable {
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
    private TableColumn<Alumno,Time> tablaEntrada;
    @FXML
    private TableColumn<Alumno,Time> tablaSalida;
    @FXML
    private TableColumn<Alumno, Date> tablaFecha;
    @FXML
    private TableColumn<Alumno,String> tablaIden;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourseBundle){
        AlumnoDao dao = new AlumnoDao();
        List<Alumno> datos = dao.readAlumnos();
        ObservableList<Alumno> datosObservables = FXCollections.observableArrayList(datos);

        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        tablaCuatri.setCellValueFactory(new PropertyValueFactory<>("cuatrimestre"));
        tablaEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        tablaSalida.setCellValueFactory(new PropertyValueFactory<>("salida"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tablaIden.setCellValueFactory(new PropertyValueFactory<>("iden"));
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
            stage.showAndWait();
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
}
