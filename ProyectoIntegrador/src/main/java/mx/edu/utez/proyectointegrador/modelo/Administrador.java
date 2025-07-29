package mx.edu.utez.proyectointegrador.modelo;

public class Administrador {
    private int idAdmin;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String usuario;
    private String contrasenia;
    private String correo;
    //Constructores
    public Administrador() {}
    public Administrador(int idAdmin, String nombre, String apellidoPaterno, String apellidoMaterno, String usuario, String contrasenia, String correo) {
        this.idAdmin = idAdmin;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.correo = correo;
    }
    //Id
    public int getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
    //Nombre
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //Apellido Paterno
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    //Apellido materno
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    //Usuario
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    //Contrasenia
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    //Correo
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
