package mx.edu.utez.proyectointegrador;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.Encargado;
import mx.edu.utez.proyectointegrador.modelo.dao.EncargadoDao;
import java.sql.Timestamp;

public class EditarEncargados {
    @FXML
    private TextField nombre;
    @FXML
    private TextField horaEntrada;
    @FXML
    private TextField correo;
    @FXML
    private TextField telefono;
    @FXML
    private TextField puesto;

    private Encargado encargado;
    private int IdViejo;

    //Metodo para obtener el encargado desde Index
    public void setEncargado(Encargado encargado){
        this.encargado = encargado;
        this.IdViejo = encargado.getIdEncargado();
        //Cargar datos en los campos
        nombre.setText(encargado.getNombreCompleto());
        telefono.setText(String.valueOf(encargado.getTelefono()));
        correo.setText(String.valueOf(encargado.getCorreo()));
        puesto.setText(String.valueOf(encargado.getPuesto()));
        horaEntrada.setText(String.valueOf(encargado.getHoraEntrada()));
    }

    public void modificar(){
        String nombreE = nombre.getText();
        String telefonoE = telefono.getText();
        String correoE = correo.getText();
        String puestoE = puesto.getText();
        Timestamp horaE = Timestamp.valueOf(horaEntrada.getText());

        EncargadoDao dao = new EncargadoDao();
        encargado.setNombreCompleto(nombreE);
        encargado.setTelefono(telefonoE);
        encargado.setCorreo(correoE);
        encargado.setPuesto(puestoE);
        encargado.setHoraEntrada(horaE);

        dao.updateEncargado(IdViejo, encargado);

        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }

}
