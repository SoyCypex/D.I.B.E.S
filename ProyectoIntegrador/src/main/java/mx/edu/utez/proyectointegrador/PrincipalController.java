package mx.edu.utez.proyectointegrador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;

public class PrincipalController {
    @FXML
    private Label horas;
    @FXML
    private Label fechas;
    @FXML
    private Button botonEntrada;
    @FXML
    private Button botonSalida;
    @FXML
    private Button botonAdmin;

    private AnimationTimer timer;

    public void initialize() {
        relojPantalla();
    }

    public void relojPantalla() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalTime tiempoActual = LocalTime.now();
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                horas.setText(tiempoActual.format(formatoHora));
                fechas.setText(fechaActual.format(formatoFecha));
            }
        };
        timer.start();
    }
    public void detenerReloj() {
        if (timer != null) {
            timer.stop();
        }
    }
}