package mx.edu.utez.proyectointegrador.modelo;

public class Encargado {
    private int idEncargado;
    private String nombreCompleto;
    private String correo;
    private String puesto;
    private String horaEntrada;
    //Constructores
    public Encargado() {}
    public Encargado(int idEncargado, String nombreCompleto, String correo, String puesto, String horaEntrada) {
        this.idEncargado = idEncargado;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.puesto = puesto;
        this.horaEntrada = horaEntrada;
    }
    //Id
    public int getIdEncargado() {
        return idEncargado;
    }
    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }
    //Nombre
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    //Correo
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    //Puesto
    public String getPuesto() {
        return puesto;
    }
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    //Hora entrada
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}
