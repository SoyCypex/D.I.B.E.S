package mx.edu.utez.proyectointegrador;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Falta;
import mx.edu.utez.proyectointegrador.modelo.dao.FaltaDao;

public class EditarFaltas {
    @FXML
    private TextField matricula;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField justificada;

    private Falta falta;
    private int idViejo;

    //Metodo para obtener falta desde Index
    public void setFaltas(Falta falta){
        this.falta=falta;
        this.idViejo = falta.getIdFalta();
        //Cargar datos en los campos
        matricula.setText(falta.getMatricula());
        fecha.setValue(falta.getFechaFalta().toLocalDate());
        justificada.setText(falta.getJustificada());
    }

    public void modificarFalta(){
        String matriculaF = matricula.getText();
        Date fechaF = Date.valueOf(fecha.getValue());
        String justificadaF = justificada.getText();

        FaltaDao dao = new FaltaDao();
        falta.setMatricula(matriculaF);
        falta.setFechaFalta(fechaF);
        falta.setJustificada(justificadaF);

        dao.updateFalta(idViejo, falta);

        Stage stage = (Stage) matricula.getScene().getWindow();
        stage.close();
    }
}
