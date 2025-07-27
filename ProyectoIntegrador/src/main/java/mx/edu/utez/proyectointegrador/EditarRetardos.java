package mx.edu.utez.proyectointegrador;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Retardo;
import mx.edu.utez.proyectointegrador.modelo.dao.RetardoDao;

public class EditarRetardos {
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField horaRetardo;
    @FXML
    private TextField horaIngreso;
    @FXML
    private TextField justificado;

    private Retardo retardo;
    private int idViejo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Metodo para obtener retardo desde Index
    public void setRetardo(Retardo retardo){
        this.retardo=retardo;
        this.idViejo = retardo.getNumRetardo();
        //Cargar datos en los campos
        matricula.setText(retardo.getMatricula());
        fecha.setValue(retardo.getFechaRetardo().toLocalDate());
        horaIngreso.setText(retardo.getHoraEntrada().toLocalDateTime().format(formatter));
        horaRetardo.setText(retardo.getTiempoRetardo().toLocalDateTime().format(formatter));
        justificado.setText(retardo.getJustificado());
    }

    public void modificarRetardo(){
        String matriculaF = matricula.getText();
        Date fechaF = Date.valueOf(fecha.getValue());
        //Combinar fecha + hora para construir Timestamp
        String fechaStr = fecha.getValue().toString();
        String entradaStr = fechaStr + " " + horaIngreso.getText();
        String tiempoStr = fechaStr + " " + horaRetardo.getText();
        Timestamp horaE = Timestamp.valueOf(entradaStr);
        Timestamp horaT = Timestamp.valueOf(tiempoStr);
        String justificadoF = justificado.getText();

        RetardoDao dao = new RetardoDao();
        retardo.setMatricula(matriculaF);
        retardo.setFechaRetardo(fechaF);
        retardo.setHoraEntrada(horaE);
        retardo.setTiempoRetardo(horaT);
        retardo.setJustificado(justificadoF);

        dao.updateRetardo(idViejo, retardo);

        Stage stage = (Stage) matricula.getScene().getWindow();
        stage.close();

    }

}
