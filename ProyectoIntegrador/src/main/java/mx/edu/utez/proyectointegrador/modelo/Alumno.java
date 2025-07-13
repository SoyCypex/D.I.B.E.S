package mx.edu.utez.proyectointegrador.modelo;

public class Alumno {
    private String matricula;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String carrera;
    private String cuatrimestreActual;
    private String contrasenia;
    private String telefono;
    //Constructores
    public Alumno() {}
    public Alumno(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String carrera, String cuatrimestreActual, String contrasenia, String telefono) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.carrera = carrera;
        this.cuatrimestreActual = cuatrimestreActual;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }
    //Matricula
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
    //Apellico materno
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    //Carrera
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    //Cuatrimestre actual
    public String getCuatrimestreActual() {
        return cuatrimestreActual;
    }
    public void setCuatrimestreActual(String cuatrimestreActual) {
        this.cuatrimestreActual = cuatrimestreActual;
    }
    //Contrasenia
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    //Telefono
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
