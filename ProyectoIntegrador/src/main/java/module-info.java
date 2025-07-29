module mx.edu.utez.proyectointegrador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ucp;
    requires java.desktop;
    requires jdk.jfr;
    requires jakarta.mail;


    opens mx.edu.utez.proyectointegrador to javafx.fxml;
    opens mx.edu.utez.proyectointegrador.modelo to javafx.fxml;
    exports mx.edu.utez.proyectointegrador;
    exports mx.edu.utez.proyectointegrador.modelo;
}