package mx.edu.utez.proyectointegrador.modelo;

public class Retardo {
    private int numRetardo;
    private Alumno alumno;
    private String fechaRetardo;
    private String horaEntrada;
    private String tiempoRetardo;
    private String justificado;
    //Constructores
    public Retardo() {}
    public Retardo(int numRetardo, Alumno alumno, String fechaRetardo, String horaEntrada, String tiempoRetardo, String justificado) {
        this.numRetardo = numRetardo;
        this.alumno = alumno;
        this.fechaRetardo = fechaRetardo;
        this.horaEntrada = horaEntrada;
        this.tiempoRetardo = tiempoRetardo;
        this.justificado = justificado;
    }
    //Numero de retardo
    public int getNumRetardo() {
        return numRetardo;
    }
    public void setNumRetardo(int numRetardo) {
        this.numRetardo = numRetardo;
    }
    //Matricula
    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    //Fecha del retardo
    public String getFechaRetardo() {
        return fechaRetardo;
    }
    public void setFechaRetardo(String fechaRetardo) {
        this.fechaRetardo = fechaRetardo;
    }
    //Hora de entrada
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    //Tiempo de retardo
    public String getTiempoRetardo() {
        return tiempoRetardo;
    }
    public void setTiempoRetardo(String tiempoRetardo) {
        this.tiempoRetardo = tiempoRetardo;
    }
    //Esta justificado?
    public String getJustificado() {
        return justificado;
    }
    public void setJustificado(String justificado) {
        this.justificado = justificado;
    }
}
