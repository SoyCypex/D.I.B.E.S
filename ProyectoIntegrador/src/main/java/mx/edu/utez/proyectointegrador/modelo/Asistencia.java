package mx.edu.utez.proyectointegrador.modelo;

public class Asistencia {
    private int numRegistro;
    private Alumno alumno;
    private String fecha;
    private String horaEntrada;
    private String horaSalida;
    //Constructores
    public Asistencia() {}
    public Asistencia(int numRegistro, Alumno alumno, String fecha, String horaEntrada, String horaSalida) {
        this.numRegistro = numRegistro;
        this.alumno = alumno;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    //Numero de registro (asistencia)
    public int getNumRegistro() {
        return numRegistro;
    }
    public void setNumRegistro(int numRegistro) {
        this.numRegistro = numRegistro;
    }
    //Matricula
    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    //Fecha de asistencia
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    //Hora de entrada
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    //Hora de salida
    public String getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
}
