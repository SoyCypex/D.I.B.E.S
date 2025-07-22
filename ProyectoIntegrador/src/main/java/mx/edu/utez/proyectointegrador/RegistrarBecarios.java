package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.Date;
import javafx.scene.control.*;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;

import java.sql.Time;

public class RegistrarBecarios{
    @FXML
    private TextField matricula;
    @FXML
    private TextField nombre;
    @FXML
    private TextField aPaterno;
    @FXML
    private TextField aMaterno;
    @FXML
    private TextField carrera;
    @FXML
    private TextField cuatrimestre;
    @FXML
    private TextField contrasenia;
    @FXML
    private TextField telefono;
    @FXML
    private TextField horaEntrada;
    @FXML
    private TextField horaSalida;
    @FXML
    private DatePicker fechaFinalizacion;
    @FXML
    private TextField idEn;
    @FXML
    private Button registrar;

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void registrarBecario(ActionEvent event){
        //Validar campos obligatorios y formato
        if (matricula.getText().isEmpty() ||
               nombre.getText().isEmpty() ||
               contrasenia.getText().isEmpty()){
            mostrarAlerta("Error!", "Nombre, matricula y contrasenia son obligatorios");
            return;
        }
        try {
            //Crear becario
            Alumno nuevo = new Alumno(
                    matricula.getText(),
                    nombre.getText(),
                    aPaterno.getText(),
                    aMaterno.getText(),
                    carrera.getText(),
                    cuatrimestre.getText(),
                    contrasenia.getText(),
                    telefono.getText(),
                    Time.valueOf(horaEntrada.getText()),
                    Time.valueOf(horaSalida.getText()),
                    Date.valueOf(fechaFinalizacion.getValue()),
                    Integer.parseInt(idEn.getText())
            );

            AlumnoDao dao = new AlumnoDao();

            if (dao.createAlumno(nuevo)) {
                //Limpiar campos
                matricula.clear();
                nombre.clear();
                aPaterno.clear();
                aMaterno.clear();
                carrera.clear();
                cuatrimestre.clear();
                contrasenia.clear();
                telefono.clear();
                horaEntrada.clear();
                horaSalida.clear();
                fechaFinalizacion.setValue(null);
                idEn.clear();

                //Mensaje de exito
                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Éxito");
                exito.setHeaderText(null);
                exito.setContentText("Becario registrado correctamente");
                exito.showAndWait();
            } else {
                mostrarAlerta("Error!", "Becario no se pudo registrar");
            }
        } catch(Exception e){
            mostrarAlerta("Error!", "Ocurrio un error inesperado " + e.getMessage());
            e.printStackTrace();
        }
    }
}