package mx.edu.utez.proyectointegrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import mx.edu.utez.proyectointegrador.modelo.dao.AdminitradorDAO;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasenia;
    @FXML
    private ProgressIndicator spinner;
    @FXML
    private Button botonIniciar;
    @FXML
    private Button botonRegresar;

    //Inicio de sesion (aldoromero12, AldoRA30)
    @FXML
    void iniciar(ActionEvent event) {
        String usuariotxt = usuario.getText();
        String contraseniatxt = contrasenia.getText();
        //Mostrar el spinner, deshabilitar botones y campos de texto
        usuario.setDisable(true);
        contrasenia.setDisable(true);
        spinner.setVisible(true);
        botonIniciar.setDisable(true);
        botonRegresar.setDisable(true);
        //Crear tarea en segundo plano
        Task<Boolean> tareaLogin = new Task<>() {
            @Override
            protected Boolean call() {
                AdminitradorDAO dao = new AdminitradorDAO();
                return dao.validarUsuario(usuariotxt, contraseniatxt);
            }
        };
        //Cuando termine la tarea
        tareaLogin.setOnSucceeded(e -> {
            boolean valido = tareaLogin.getValue();
            //Ocultar spinner, reactivar botones y campos de texto
            usuario.setDisable(false);
            contrasenia.setDisable(false);
            spinner.setVisible(false);
            botonIniciar.setDisable(false);
            botonRegresar.setDisable(false);

            if (valido) {
                try {
                    //Cargar nueva ventana
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminIndex.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Menú Administrador");
                    stage.setScene(new Scene(root));
                    stage.show();
                    //Mostrar alerta de éxito
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Éxito");
                    success.setHeaderText("Inicio de sesión correcto");
                    success.setContentText("Bienvenido al panel de administrador.");
                    success.showAndWait();
                    //Cerrar ventana actual
                    ((Stage) usuario.getScene().getWindow()).close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                //Alerta de error
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Usuario o contraseña incorrectos");
                alerta.setContentText("Verifica tus credenciales e intenta nuevamente.");
                alerta.showAndWait();
            }
        });
        tareaLogin.setOnFailed(e -> {
            //En caso de error en la conexión
            spinner.setVisible(false);
            botonIniciar.setDisable(false);
            botonRegresar.setDisable(false);
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Ocurrió un problema al iniciar sesión");
            error.setContentText("Revisa tu conexión o contacta al administrador.");
            error.showAndWait();
        });
        //Ejecutar tarea en nuevo hilo
        new Thread(tareaLogin).start();
    }
    //Regresar a la pantalla de inicio
    @FXML
    void regresar(ActionEvent event) {
        try{
            //Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaPrincipal.fxml"));
            Parent root = loader.load();
            //Crear una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Registro CDS");
            stage.setScene(new Scene(root));
            stage.show();
            //Cerrar la ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
