package mx.edu.utez.proyectointegrador.modelo.dao;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import mx.edu.utez.proyectointegrador.utils.OracleDatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AdminitradorDAO {
    public boolean validarUsuario(String usuario, String contrasenia) {
        String query = "SELECT * FROM ADMINISTRADOR WHERE USUARIO=? AND CONTRASENIA=?";
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] obtenerCorreoYContrasena(String usuario) {
        String[] datos = null;
        String query = "SELECT CORREO, CONTRASENIA FROM ADMINISTRADOR WHERE USUARIO=?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos = new String[]{
                        rs.getString("CORREO"),
                        rs.getString("CONTRASENIA")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public boolean enviarCorreo(String destino, String contrasena) {
        final String remitente = "20243ds030@utez.edu.mx"; //Correo
        final String clave = "qspp gzog sgci escr"; //clave de aplicación
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });
        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mensaje.setSubject("Recuperación de contraseña");
            mensaje.setText("Tu contraseña es: " + contrasena);
            Transport.send(mensaje);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
