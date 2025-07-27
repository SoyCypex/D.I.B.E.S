package mx.edu.utez.proyectointegrador;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Asistencia;
import mx.edu.utez.proyectointegrador.modelo.dao.AsistenciaDao;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.format.DateTimeFormatter;


public class EditarAsistencia {
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField horaEntrada;
    @FXML
    private TextField horaSalida;

    private Asistencia asistencia;
    private int IdViejo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Metodo para obtener asistencia desde Index
    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
        this.IdViejo = asistencia.getNumRegistro();
        //Cargar datos en los campos
        matricula.setText(asistencia.getMatricula());
        fecha.setValue(asistencia.getFecha().toLocalDate());
        horaEntrada.setText(asistencia.getHoraEntrada().toLocalDateTime().toLocalTime().format(formatter));
        horaSalida.setText(asistencia.getHoraSalida().toLocalDateTime().toLocalTime().format(formatter));
    }

    public void modificarAsistencia() {
        String matriculaA = matricula.getText();
        Date fechaA = Date.valueOf(fecha.getValue()); //yyyy-MM-dd
        //Combinar fecha + hora para construir Timestamp
        String fechaStr = fecha.getValue().toString();
        String entradaStr = fechaStr + " " + horaEntrada.getText();
        String salidaStr = fechaStr + " " + horaSalida.getText();
        Timestamp horaEA = Timestamp.valueOf(entradaStr);
        Timestamp horaSA = Timestamp.valueOf(salidaStr);

        AsistenciaDao dao = new AsistenciaDao();
        asistencia.setMatricula(matriculaA);
        asistencia.setFecha(fechaA);
        asistencia.setHoraEntrada(horaEA);
        asistencia.setHoraSalida(horaSA);

        dao.updateAsistencia(IdViejo, asistencia);

        Stage stage = (Stage) matricula.getScene().getWindow();
        stage.close();
    }


}
