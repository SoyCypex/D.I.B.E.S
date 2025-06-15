module mx.edu.utez.proyectointegrador {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.edu.utez.proyectointegrador to javafx.fxml;
    exports mx.edu.utez.proyectointegrador;
}