package mx.edu.utez.modelo;

public class InfoAlumnos {
    private int idInfo;
    private Alumno alumno;
    private String horaEntrada;
    private String horaSalida;
    private String fechaFinalizacion;
    private Encargado encargado;
    //Constructores
    public InfoAlumnos() {}
    public InfoAlumnos(int idInfo, Alumno alumno, String horaEntrada, String horaSalida, String fechaFinalizacion, Encargado encargado) {
        this.idInfo = idInfo;
        this.alumno = alumno;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.fechaFinalizacion = fechaFinalizacion;
        this.encargado = encargado;
    }
    //ID
    public int getIdInfo() {
        return idInfo;
    }
    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }
    //Matricula
    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    //Hora entrada
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    //Hora salida
    public String getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    //Fecha finalizacion
    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }
    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    //ID Encargado
    public Encargado getEncargado() {
        return encargado;
    }
    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }
}
