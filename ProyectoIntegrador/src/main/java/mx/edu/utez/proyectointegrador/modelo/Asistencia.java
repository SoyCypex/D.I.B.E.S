package mx.edu.utez.proyectointegrador.modelo;

import java.sql.Date;
import java.sql.Timestamp;

public class Asistencia {
    private int numRegistro;
    private String matricula;
    private Date fecha;
    private Timestamp horaEntrada;
    private Timestamp horaSalida;
    //Constructores
    public Asistencia() {}
    public Asistencia(String matricula, Date fecha, Timestamp horaEntrada, Timestamp horaSalida) {
        this.matricula = matricula;
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
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    //Fecha de asistencia
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //Hora de entrada
    public Timestamp getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(Timestamp horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    //Hora de salida
    public Timestamp getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(Timestamp horaSalida) {
        this.horaSalida = horaSalida;
    }
}
