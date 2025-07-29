package mx.edu.utez.proyectointegrador;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.dao.AdminitradorDAO;
import javafx.concurrent.Task;

public class RecuperarAdminController {
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
            mostrarAlerta("Campo vacío", "Por favor ingresa el nombre de usuario.");
            return;
        }
        //Desactivar controles mientras se ejecuta
        campoUsuario.setDisable(true);
        recuperarBoton.setDisable(true);
        spinner.setVisible(true);
        Task<Boolean> tareaRecuperacion = new Task<>() {
            @Override
            protected Boolean call() {
                AdminitradorDAO dao = new AdminitradorDAO();
                String[] datos = dao.obtenerCorreoYContrasena(usuario);

                if (datos != null) {
                    String correoDestino = datos[0];
                    String contrasena = datos[1];
                    return dao.enviarCorreo(correoDestino, contrasena);
                }
                return null; // usuario no existe
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
