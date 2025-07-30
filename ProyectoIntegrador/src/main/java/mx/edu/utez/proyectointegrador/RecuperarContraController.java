package mx.edu.utez.proyectointegrador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import mx.edu.utez.proyectointegrador.modelo.dao.AlumnoDao;

public class RecuperarContraController {
    @FXML
    private TextField campoUsuario;
    @FXML
    private ProgressIndicator spinner;
    @FXML
    private Button recuperarBoton;

    @FXML
    void recuperarContra(ActionEvent event) {
        String usuario = campoUsuario.getText().trim();
        if (usuario.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor ingresa tu correo institucional.");
            return;
        }
        // Desactivar controles mientras se ejecuta
        campoUsuario.setDisable(true);
        recuperarBoton.setDisable(true);
        spinner.setVisible(true);
        Task<Boolean> tareaRecuperacion = new Task<>() {
            @Override
            protected Boolean call() {
                AlumnoDao dao = new AlumnoDao();
                String[] datos = dao.obtenerCredencialesAlumno(usuario); // método que hicimos antes

                if (datos != null) {
                    String correoDestino = datos[0];
                    String contrasena = datos[1];
                    return dao.enviarCorreo(correoDestino, contrasena); // Debes tener este método también
                }
                return null; // Usuario no encontrado
            }
        };
        tareaRecuperacion.setOnSucceeded(e -> {
            spinner.setVisible(false);
            campoUsuario.setDisable(false);
            recuperarBoton.setDisable(false);

            Boolean resultado = tareaRecuperacion.getValue();
            if (resultado == null) {
                mostrarAlerta("Usuario no encontrado", "El usuario ingresado no está registrado.");
            } else if (resultado) {
                mostrarAlerta("Correo enviado", "Se ha enviado la contraseña al correo registrado.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo enviar el correo.");
            }
        });
        tareaRecuperacion.setOnFailed(e -> {
            spinner.setVisible(false);
            campoUsuario.setDisable(false);
            recuperarBoton.setDisable(false);
            mostrarAlerta("Error", "Ocurrió un error inesperado. Revisa tu conexión.");
        });
        new Thread(tareaRecuperacion).start();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) campoUsuario.getScene().getWindow();
        stage.close();
    }
}
