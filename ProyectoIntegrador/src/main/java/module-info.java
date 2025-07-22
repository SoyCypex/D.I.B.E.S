module mx.edu.utez.proyectointegrador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ucp;
    requires java.desktop;


    opens mx.edu.utez.proyectointegrador to javafx.fxml;
    exports mx.edu.utez.proyectointegrador;
}