package mx.edu.utez.proyectointegrador;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Alumno;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;
import java.sql.Date;
import java.sql.Timestamp;

public class EditarBecarios {
    @FXML
    private TextField matriculaB;
    @FXML
    private TextField nombreB;
    @FXML
    private TextField aPaternoB;
    @FXML
    private TextField aMaternoB;
    @FXML
    private TextField carreraB;
    @FXML
    private TextField cuatrimestreB;
    @FXML
    private TextField contraseniaB;
    @FXML
    private TextField telefonoB;
    @FXML
    private TextField horaEntradaB;
    @FXML
    private TextField horaSalidaB;
    @FXML
    private DatePicker fechaFinalizacionB;
    @FXML
    private TextField idEncargadoB;

    private Alumno alumno;
    private String matriculaVieja;

    //Meotodo para obtener el alumno desde la vista Index
    public void setAlumno(Alumno alumno){
        this.alumno=alumno;
        this.matriculaVieja=alumno.getMatricula();

        //Cargar datos en los campos
        matriculaB.setText(alumno.getMatricula());
        nombreB.setText(alumno.getNombre());
        aPaternoB.setText(alumno.getApellidoPaterno());
        aMaternoB.setText(alumno.getApellidoMaterno());
        carreraB.setText(alumno.getCarrera());
        cuatrimestreB.setText(alumno.getCuatrimestreActual());
        contraseniaB.setText(alumno.getContrasenia());
        telefonoB.setText(alumno.getTelefono());
        horaEntradaB.setText(String.valueOf(alumno.getHoraEntrada()));
        horaSalidaB.setText(String.valueOf(alumno.getHoraSalida()));
        fechaFinalizacionB.setValue(alumno.getFechaFinalizacion().toLocalDate());
        idEncargadoB.setText(String.valueOf(alumno.getIdEncargado()));
    }

    public void actualizarBecario(){
        String nombre = nombreB.getText();
        String aPaterno = aPaternoB.getText();
        String aMaterno = aMaternoB.getText();
        String carrera = carreraB.getText();
        String cuatrimestre = cuatrimestreB.getText();
        String contrasenia = contraseniaB.getText();
        String telefono = telefonoB.getText();
        Timestamp horaEntrada = Timestamp.valueOf(horaEntradaB.getText());
        Timestamp horaSalida = Timestamp.valueOf(horaSalidaB.getText());
        Date fechaFinalizacion = Date.valueOf(fechaFinalizacionB.getValue());
        int idEncargado = Integer.parseInt(idEncargadoB.getText());

        alumno.setMatricula(matriculaVieja);
        alumno.setNombre(nombre);
        alumno.setApellidoPaterno(aPaterno);
        alumno.setApellidoMaterno(aMaterno);
        alumno.setCarrera(carrera);
        alumno.setCuatrimestreActual(cuatrimestre);
        alumno.setContrasenia(contrasenia);
        alumno.setTelefono(telefono);
        alumno.setHoraEntrada(horaEntrada);
        alumno.setHoraSalida(horaSalida);
        alumno.setFechaFinalizacion(fechaFinalizacion);
        alumno.setIdEncargado(idEncargado);

        AlumnoDao dao = new AlumnoDao();
        dao.updateAlumno(matriculaVieja, alumno);

        Stage stage = (Stage) matriculaB.getScene().getWindow();
        stage.close();
    }

}
